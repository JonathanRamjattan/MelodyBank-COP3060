import { useEffect, useState } from "react";
import axios from "axios";

function Data() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    axios.get("http://localhost:8080/api/midi")
      .then(res => {
        setData(res.data);
        setLoading(false);
      })
      .catch(() => {
        setError("Unable to load MIDI library. Make sure the backend is running.");
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <main className="page"><h1>Loading MIDI Library...</h1></main>;
  }

  if (error) {
    return <main className="page"><h1>MIDI Library</h1><p>{error}</p></main>;
  }

  return (
    <main className="page">
      <h1>MIDI Library</h1>
      <p>Browse MIDI files from the MelodyBank backend.</p>

      {data.length === 0 ? (
        <p>No MIDI files are available yet.</p>
      ) : (
        <section className="card-grid">
          {data.map(item => (
            <div className="card midi-card" key={item.id}>
              <h3>{item.title}</h3>
              <p>Key: {item.key}</p>
              <p>BPM: {item.bpm}</p>
              <p>Category: {item.category}</p>
            </div>
          ))}
        </section>
      )}
    </main>
  );
}

export default Data;
