package com.midihub.melodyhub.controller;

import com.midihub.melodyhub.entity.MidiSequence;
import com.midihub.melodyhub.service.MidiSequenceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/midi")
@CrossOrigin(origins = "*")
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

    // Optional: keeps your old JSON POST working
    @PostMapping
    public MidiSequence createMidi(@RequestBody MidiSequence midi) {
        return midiSequenceService.createMidi(midi);
    }

    // New: real MIDI file upload endpoint
    @PostMapping("/upload")
    public MidiSequence uploadMidi(
            @RequestParam("title") String title,
            @RequestParam("keySignature") String keySignature,
            @RequestParam("tempoBpm") int tempoBpm,
            @RequestParam("category") String category,
            @RequestParam(value = "artistName", required = false) String artistName,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return midiSequenceService.uploadMidi(
                title,
                keySignature,
                tempoBpm,
                category,
                artistName,
                file
        );
    }

    @DeleteMapping("/{id}")
    public void deleteMidi(@PathVariable Long id) {
        midiSequenceService.deleteMidi(id);
    }
}