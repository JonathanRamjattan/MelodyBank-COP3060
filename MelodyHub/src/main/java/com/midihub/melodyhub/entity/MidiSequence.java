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

    private String originalFileName;
    private String storedFileName;
    private String filePath;
    private Long fileSize;
    private String contentType;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private AppUser creator;

    @ManyToOne
    @JoinColumn(name = "external_artist_id")
    private ExternalArtist externalArtist;
}