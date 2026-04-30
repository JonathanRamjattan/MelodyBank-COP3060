package com.midihub.melodyhub.service;

import com.midihub.melodyhub.entity.MidiSequence;
import com.midihub.melodyhub.repository.MidiSequenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class MidiSequenceService {

    private final MidiSequenceRepository midiSequenceRepository;

    private final Path uploadPath = Paths.get("uploads/midi");

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

    public MidiSequence uploadMidi(
            String title,
            String keySignature,
            int tempoBpm,
            String category,
            MultipartFile file
    ) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("MIDI file is required.");
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null ||
                (!originalFileName.toLowerCase().endsWith(".mid")
                        && !originalFileName.toLowerCase().endsWith(".midi"))) {
            throw new IllegalArgumentException("Only .mid or .midi files are allowed.");
        }

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String storedFileName = UUID.randomUUID() + "_" + originalFileName;
        Path filePath = uploadPath.resolve(storedFileName);

        Files.copy(file.getInputStream(), filePath);

        MidiSequence midi = new MidiSequence();
        midi.setTitle(title);
        midi.setKeySignature(keySignature);
        midi.setTempoBpm(tempoBpm);
        midi.setCategory(category);

        midi.setOriginalFileName(originalFileName);
        midi.setStoredFileName(storedFileName);
        midi.setFilePath("/uploads/midi/" + storedFileName);
        midi.setFileSize(file.getSize());
        midi.setContentType(file.getContentType());

        return midiSequenceRepository.save(midi);
    }
}