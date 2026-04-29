package com.midihub.melodyhub.repository;

import com.midihub.melodyhub.entity.MidiSequence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MidiSequenceRepository extends JpaRepository<MidiSequence, Long> {}