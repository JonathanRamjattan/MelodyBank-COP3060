package com.midihub.melodyhub.service;


import com.midihub.melodyhub.entity.MidiSequence;

import java.util.ArrayList;
import java.util.List;

public class MidiSequenceService {

    private List<MidiSequence> midiList = new ArrayList<>();

    public MidiSequence createMidi(MidiSequence midi) {
        midiList.add(midi);
        return midi;
    }

    public List<MidiSequence> getAllMidi() {
        return midiList;
    }

    public MidiSequence getMidiById(Long id) {
        return midiList.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteMidi(Long id) {
        midiList.removeIf(m -> m.getId().equals(id));
    }
}