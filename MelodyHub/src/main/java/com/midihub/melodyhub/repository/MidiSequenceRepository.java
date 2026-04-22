package com.midihub.melodyhub.repository;

import com.midihub.melodyhub.entity.MidiSequence;
import java.util.List;

public interface MidiSequenceRepository {

    MidiSequence save(MidiSequence midi);
    MidiSequence findById(Long id);
    List<MidiSequence> findAll();
    List<MidiSequence> findByCategory(String category);
}