package com.midihub.melodyhub.service;

import com.midihub.melodyhub.entity.ExternalArtist;
import com.midihub.melodyhub.entity.MidiSequence;
import com.midihub.melodyhub.repository.MidiSequenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class MidiSequenceService {

    private final MidiSequenceRepository midiSequenceRepository;
    private final MusicBrainzService musicBrainzService;

    private final Path uploadPath = Paths.get("uploads/midi");

    public MidiSequenceService(
            MidiSequenceRepository midiSequenceRepository,
            MusicBrainzService musicBrainzService
    ) {
        this.midiSequenceRepository = midiSequenceRepository;
        this.musicBrainzService = musicBrainzService;
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
            String inspiredArtist,
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

        String safeOriginalFileName = originalFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String storedFileName = UUID.randomUUID() + "_" + safeOriginalFileName;
        Path filePath = uploadPath.resolve(storedFileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        ExternalArtist externalArtist = null;

        if (inspiredArtist != null && !inspiredArtist.isBlank()) {
            try {
                externalArtist = musicBrainzService.searchAndSaveArtist(inspiredArtist);
            } catch (Exception e) {
                System.out.println("MusicBrainz lookup failed. MIDI will still be saved.");
                System.out.println("Reason: " + e.getMessage());
            }
        }

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

        midi.setExternalArtist(externalArtist);

        return midiSequenceRepository.save(midi);
    }
}