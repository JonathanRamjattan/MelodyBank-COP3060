package com.midihub.melodyhub;

import com.midihub.melodyhub.entity.MidiSequence;
import com.midihub.melodyhub.repository.MidiSequenceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MidiSequenceRepository midiSequenceRepository;

    public DataSeeder(MidiSequenceRepository midiSequenceRepository) {
        this.midiSequenceRepository = midiSequenceRepository;
    }

    @Override
    public void run(String... args) {
        if (midiSequenceRepository.count() == 0) {
            midiSequenceRepository.save(new MidiSequence(null, "Neo Soul Chords", "Eb Minor", 90, "Chords", null));
            midiSequenceRepository.save(new MidiSequence(null, "Lofi Melody", "C Major", 78, "Melody", null));
            midiSequenceRepository.save(new MidiSequence(null, "Trap Piano Loop", "A Minor", 140, "Loop", null));
        }
    }
}
