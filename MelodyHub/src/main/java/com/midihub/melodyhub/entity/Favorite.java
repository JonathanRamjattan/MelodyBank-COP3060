package com.midihub.melodyhub.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Favorite {

    private Long id;
    private User user;
    private MidiSequence midiSequence;

    public Favorite() {}

    public Favorite(Long id, User user, MidiSequence midiSequence) {
        this.id = id;
        this.user = user;
        this.midiSequence = midiSequence;
    }

}
