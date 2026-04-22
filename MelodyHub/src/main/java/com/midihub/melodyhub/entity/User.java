package com.midihub.melodyhub.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class User {

    private Long id;
    private String name;
    private String username;
    private String email;
    private boolean isCreator;

    private List<MidiSequence> midiSequences; // One-to-Many

    public User() {}

    public User(Long id, String username, String email, boolean isCreator) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isCreator = isCreator;
    }

}
