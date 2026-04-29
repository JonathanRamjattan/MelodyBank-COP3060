import { useState } from "react";

function Home() {
  const [search, setSearch] = useState("");

  const midiData = [
    { id: 1, title: "Neo Soul Chords", key: "Eb Minor" },
    { id: 2, title: "Lofi Melody", key: "C Major" }
  ];

  const filtered = midiData.filter((m) =>
    m.title.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div style={{ padding: "40px", fontFamily: "Arial" }}>
      <h1>🎹 MelodyHub</h1>

      <input
        placeholder="Search MIDI..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        style={{
          padding: "10px",
          marginBottom: "20px",
          width: "300px"
        }}
      />

      {filtered.map((midi) => (
        <div
          key={midi.id}
          style={{
            background: "#eee",
            padding: "15px",
            marginBottom: "10px",
            borderRadius: "10px"
          }}
        >
          <h3>{midi.title}</h3>
          <p>Key: {midi.key}</p>
          <button>Play</button>
        </div>
      ))}
    </div>
  );
}

export default Home;