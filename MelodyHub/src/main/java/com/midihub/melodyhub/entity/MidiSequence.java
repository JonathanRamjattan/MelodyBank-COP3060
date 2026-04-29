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

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private AppUser creator;
}
