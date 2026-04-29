function Home() {
  return (
    <main className="page">
      <section className="hero">
        <h1>Build, Bank, and Share <span>MIDI </span></h1>
        <p>
          MelodyBank gives musicians and producers a place to organize melodies,
          chord progressions, loops, and creative ideas in one digital library.
        </p>
      </section>

      <section className="card-grid">
        <div className="card">
          <h3>Browse MIDI</h3>
          <p>Explore stored MIDI ideas by title, key, BPM, and category.</p>
        </div>

        <div className="card">
          <h3>Upload Ideas</h3>
          <p>Add new melodies, chords, and loops directly into the library.</p>
        </div>

        <div className="card">
          <h3>Producer Workflow</h3>
          <p>Designed for beat makers, musicians, and songwriters building a sound bank.</p>
        </div>
      </section>
    </main>
  );
}

export default Home;
