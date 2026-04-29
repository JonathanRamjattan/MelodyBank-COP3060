package com.midihub.melodyhub.service;

import com.midihub.melodyhub.entity.MidiSequence;
import com.midihub.melodyhub.repository.MidiSequenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MidiSequenceService {

    private final MidiSequenceRepository midiSequenceRepository;

    public MidiSequenceService(MidiSequenceRepository midiSequenceRepository) {
        this.midiSequenceRepository = midiSequenceRepository;
    }

    public MidiSequence createMidi(MidiSequence midi) {
        return midiSequenceRepository.save(midi);
    }

    public List<MidiSequence> getAllMidi() {
        return midiSequenceRepository.findAll();
    }

    public MidiSequence getMidiById(Long id) {
        return midiSequenceRepository.findById(id).orElse(null);
    }

    public void deleteMidi(Long id) {
        midiSequenceRepository.deleteById(id);
    }
}
