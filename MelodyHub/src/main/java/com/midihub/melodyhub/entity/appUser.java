package com.midihub.melodyhub.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private boolean isCreator;

    @OneToMany(mappedBy = "creator")
    private List<MidiSequence> midiSequences;
}
