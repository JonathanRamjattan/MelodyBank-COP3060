package com.midihub.melodyhub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MidiSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String keySignature;
    private int tempoBpm;
    private String category;

    // Example: "dark_chords.mid"
    private String originalFileName;

    // Example: "3f8c1b22-9212-4f3c-a4db-dark_chords.mid"
    private String storedFileName;

    // Example: "/uploads/midi/3f8c1b22-9212-4f3c-a4db-dark_chords.mid"
    private String filePath;

    // Optional but useful
    private Long fileSize;

    // Example: "audio/midi" or "application/octet-stream"
    private String contentType;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private AppUser creator;
}