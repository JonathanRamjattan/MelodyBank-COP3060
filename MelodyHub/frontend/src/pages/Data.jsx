import { useEffect, useRef, useState } from "react";
import axios from "axios";
import * as Tone from "tone";
import { Midi } from "@tonejs/midi";
import MidiPianoRoll from "../components/MidiPianoRoll";

function Data() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [playingId, setPlayingId] = useState(null);
  const [expandedId, setExpandedId] = useState(null);

  const synthRef = useRef(null);
  const [midiNotes, setMidiNotes] = useState({});
  const [favoriteIds, setFavoriteIds] = useState([]);
  const backendUrl = "http://localhost:8080";

  useEffect(() => {
    axios
        .get(`${backendUrl}/api/midi`)
        .then((res) => {
          setData(res.data);
          setLoading(false);
        })
        .catch((err) => {
          console.error("Load MIDI error:", err);
          setError("Unable to load MIDI library. Make sure the backend is running.");
          setLoading(false);
        });

    axios
        .get(`${backendUrl}/api/favorites/ids`, {
          withCredentials: true,
        })
        .then((res) => {
          setFavoriteIds(res.data);
        })
        .catch(() => {
          setFavoriteIds([]);
        });

    return () => {
      stopMidi();
    };
  }, []);

  const stopMidi = () => {
    Tone.Transport.stop();
    Tone.Transport.cancel();

    if (synthRef.current) {
      synthRef.current.dispose();
      synthRef.current = null;
    }

    setPlayingId(null);
  };

  const playMidi = async (item) => {
    try {
      if (!item.filePath) {
        alert("This MIDI does not have a file attached yet.");
        return;
      }

      stopMidi();

      await Tone.start();

      if (Tone.context.state !== "running") {
        await Tone.context.resume();
      }

      const fileUrl = `${backendUrl}${item.filePath}`;
      const response = await fetch(fileUrl);

      if (!response.ok) {
        throw new Error(`Could not fetch MIDI file. Status: ${response.status}`);
      }

      const arrayBuffer = await response.arrayBuffer();
      const midi = new Midi(arrayBuffer);

      const allNotes = midi.tracks.flatMap((track) => track.notes);

      if (allNotes.length === 0) {
        alert("This MIDI file loaded, but it has no playable notes.");
        return;
      }

      const synth = new Tone.PolySynth(Tone.Synth).toDestination();
      synth.volume.value = -8;
      synthRef.current = synth;

      const now = Tone.now() + 0.1;

      allNotes.forEach((note) => {
        synth.triggerAttackRelease(
            note.name,
            note.duration,
            now + note.time,
            note.velocity
        );
      });

      setPlayingId(item.id);

      const duration = midi.duration || 5;

      setTimeout(() => {
        setPlayingId(null);
      }, duration * 1000);
    } catch (err) {
      console.error("Play MIDI error:", err);
      alert("Unable to play MIDI file. Check the browser console.");
      setPlayingId(null);
    }
  };

  const toggleDropdown = async (item) => {
    if (expandedId === item.id) {
      setExpandedId(null);
      return;
    }

    setExpandedId(item.id);
    await loadMidiNotes(item);
  };

  const loadMidiNotes = async (item) => {
    try {
      if (!item.filePath || midiNotes[item.id]) return;

      const fileUrl = `${backendUrl}${item.filePath}`;
      const response = await fetch(fileUrl);

      if (!response.ok) {
        throw new Error("Could not fetch MIDI file.");
      }

      const arrayBuffer = await response.arrayBuffer();
      const midi = new Midi(arrayBuffer);

      const allNotes = midi.tracks.flatMap((track) => track.notes);

      setMidiNotes((prev) => ({
        ...prev,
        [item.id]: allNotes,
      }));
    } catch (err) {
      console.error("Load MIDI notes error:", err);
    }
  };


  if (loading) {
    return (
        <main className="page">
          <h1>Loading MIDI Library...</h1>
        </main>
    );
  }

  if (error) {
    return (
        <main className="page">
          <h1>MIDI Library</h1>
          <p>{error}</p>
        </main>
    );
  }
  const toggleFavorite = async (item) => {
    try {
      const isFavorited = favoriteIds.includes(item.id);

      if (isFavorited) {
        await axios.delete(`${backendUrl}/api/favorites/${item.id}`, {
          withCredentials: true,
        });

        setFavoriteIds((prev) => prev.filter((id) => id !== item.id));
      } else {
        await axios.post(
            `${backendUrl}/api/favorites/${item.id}`,
            {},
            {
              withCredentials: true,
            }
        );

        setFavoriteIds((prev) => [...prev, item.id]);
      }
    } catch (err) {
      console.error("Favorite error:", err);

      if (err.response?.status === 401 || err.response?.status === 403) {
        alert("You must be logged in to favorite MIDI files.");
      } else {
        alert("Unable to update favorite.");
      }
    }
  };

  return (
      <main className="page">
        <h1>MIDI Library</h1>
        <p>Browse, preview, and download MIDI files from MelodyHub.</p>

        {data.length === 0 ? (
            <p>No MIDI files are available yet.</p>
        ) : (
            <section className="midi-list">
              <div className="midi-list-header">
                <span>Title</span>
                <span>Key</span>
                <span>BPM</span>
                <span>Category</span>
                <span>Actions</span>
              </div>

              {data.map((item) => (
                  <div className="midi-row-wrapper" key={item.id}>
                    <div className="midi-row">
                      <span className="midi-title">{item.title}</span>
                      <span>{item.keySignature}</span>
                      <span>{item.tempoBpm}</span>
                      <span>{item.category}</span>

                      <div className="midi-actions">
                        <button onClick={() => playMidi(item)}>
                          {playingId === item.id ? "Playing..." : "Play"}
                        </button>

                        <button onClick={stopMidi}>Stop</button>

                        <button onClick={() => toggleDropdown(item)}>
                          {expandedId === item.id ? "Hide" : "Details"}
                        </button>
                        <button onClick={() => toggleFavorite(item)}>
                          {favoriteIds.includes(item.id) ? "★ Favorited" : "☆ Favorite"}
                        </button>

                        {item.filePath && (
                            <a
                                href={`${backendUrl}${item.filePath}`}
                                download
                                className="download-link"
                            >
                              Download
                            </a>
                        )}
                      </div>
                    </div>

                    {expandedId === item.id && (
                        <div className="midi-dropdown">
                          <p>
                            <strong>Original File:</strong>{" "}
                            {item.originalFileName || "No file name available"}
                          </p>

                          <p>
                            <strong>Stored File:</strong>{" "}
                            {item.storedFileName || "No stored file name available"}
                          </p>

                          <p>
                            <strong>File Path:</strong>{" "}
                            {item.filePath || "No file path available"}
                          </p>

                          <p>
                            <strong>File Size:</strong>{" "}
                            {item.fileSize ? `${Math.round(item.fileSize / 1024)} KB` : "Unknown"}
                          </p>

                          <p>
                            <strong>Content Type:</strong>{" "}
                            {item.contentType || "Unknown"}
                          </p>

                          {item.filePath && (
                              <p>
                                <strong>Direct URL:</strong>{" "}
                                <a
                                    href={`${backendUrl}${item.filePath}`}
                                    target="_blank"
                                    rel="noreferrer"
                                >
                                  Open MIDI File
                                </a>
                              </p>
                          )}

                          <h4 className="piano-roll-title">MIDI Notes</h4>
                          <MidiPianoRoll notes={midiNotes[item.id] || []} />
                        </div>
                    )}
                  </div>
              ))}
            </section>
        )}
      </main>
  );
}

export default Data;