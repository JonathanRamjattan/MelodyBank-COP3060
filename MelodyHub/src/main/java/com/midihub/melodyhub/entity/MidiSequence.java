package com.midihub.melodyhub.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MidiSequence {
    private Long id;
    private String title;
    private String fileUrl;
    private String keySignature;
    private int tempoBpm;
    private String category;

    private User creator; // Many-to-One


    public MidiSequence(Long id, String title, String fileUrl, String keySignature, int tempoBpm, String category, User creator) {
        this.id = id;
        this.title = title;
        this.fileUrl = fileUrl;
        this.keySignature = keySignature;
        this.tempoBpm = tempoBpm;
        this.category = category;
        this.creator = creator;
    }

    // Getters & Setters
}
