import React from "react";

function MidiPianoRoll({ notes = [] }) {
    if (!notes.length) {
        return <p>No note data available.</p>;
    }

    const minMidi = Math.min(...notes.map((n) => n.midi));
    const maxMidi = Math.max(...notes.map((n) => n.midi));
    const totalDuration = Math.max(...notes.map((n) => n.time + n.duration));

    const noteRange = maxMidi - minMidi + 1;
    const rowHeight = 18;
    const width = 1750;
    const height = noteRange * rowHeight;

    return (
        <div className="piano-roll-wrapper">
            <div
                className="piano-roll"
                style={{
                    position: "relative",
                    width: `${width}px`,
                    height: `${height}px`,
                }}
            >
                {notes.map((note, index) => {
                    const left = (note.time / totalDuration) * width;
                    const noteWidth = (note.duration / totalDuration) * width;
                    const top = (maxMidi - note.midi) * rowHeight;

                    return (
                        <div
                            key={index}
                            className="piano-note"
                            title={`${note.name} | time: ${note.time.toFixed(2)} | dur: ${note.duration.toFixed(2)}`}
                            style={{
                                position: "absolute",
                                left: `${left}px`,
                                top: `${top}px`,
                                width: `${Math.max(noteWidth, 6)}px`,
                                height: `${rowHeight - 2}px`,
                            }}
                        />
                    );
                })}
            </div>
        </div>
    );
}

export default MidiPianoRoll;