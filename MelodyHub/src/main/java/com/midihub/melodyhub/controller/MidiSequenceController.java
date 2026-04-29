package com.midihub.melodyhub.controller;

import com.midihub.melodyhub.entity.MidiSequence;
import com.midihub.melodyhub.service.MidiSequenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/midi")
@CrossOrigin(origins = "http://localhost:5173")
public class MidiSequenceController {

    private final MidiSequenceService midiSequenceService;

    public MidiSequenceController(MidiSequenceService midiSequenceService) {
        this.midiSequenceService = midiSequenceService;
    }

    @GetMapping
    public List<MidiSequence> getAllMidi() {
        return midiSequenceService.getAllMidi();
    }

    @GetMapping("/{id}")
    public MidiSequence getMidiById(@PathVariable Long id) {
        return midiSequenceService.getMidiById(id);
    }

    @PostMapping
    public MidiSequence createMidi(@RequestBody MidiSequence midi) {
        return midiSequenceService.createMidi(midi);
    }

    @DeleteMapping("/{id}")
    public void deleteMidi(@PathVariable Long id) {
        midiSequenceService.deleteMidi(id);
    }
}
