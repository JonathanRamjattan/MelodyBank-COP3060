import { useState } from "react";
import axios from "axios";

function Form() {
  const [title, setTitle] = useState("");
  const [keyVal, setKeyVal] = useState("");
  const [bpm, setBpm] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!title || !keyVal || !bpm) {
      setMessage("All fields are required.");
      return;
    }

    if (isNaN(parseInt(bpm))) {
      setMessage("BPM must be a number.");
      return;
    }

    axios.post("http://localhost:8080/api/midi", {
      title,
      key: keyVal,
      bpm: parseInt(bpm),
      category: "User Upload"
    })
    .then(() => {
      setMessage("MIDI added successfully.");
      setTitle("");
      setKeyVal("");
      setBpm("");
    })
    .catch(() => {
      setMessage("Unable to submit MIDI. Make sure the backend is running.");
    });
  };

  return (
    <main className="page">
      <h1>Upload MIDI</h1>
      <p>Add a new melody, chord progression, or loop to the library.</p>

      <form className="form-box" onSubmit={handleSubmit}>
        <input placeholder="MIDI Title" value={title} onChange={e => setTitle(e.target.value)} />
        <input placeholder="Key, example: C Minor" value={keyVal} onChange={e => setKeyVal(e.target.value)} />
        <input placeholder="BPM, example: 140" value={bpm} onChange={e => setBpm(e.target.value)} />
        <button type="submit">Add to Library</button>
        {message && <p>{message}</p>}
      </form>
    </main>
  );
}

export default Form;
