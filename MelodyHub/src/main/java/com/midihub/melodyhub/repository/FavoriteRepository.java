package com.midihub.melodyhub.repository;

import com.midihub.melodyhub.entity.AppUser;
import com.midihub.melodyhub.entity.Favorite;
import com.midihub.melodyhub.entity.MidiSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByAppUser(AppUser appUser);

    Optional<Favorite> findByAppUserAndMidiSequence(AppUser appUser, MidiSequence midiSequence);

    boolean existsByAppUserAndMidiSequence(AppUser appUser, MidiSequence midiSequence);
    @Transactional
    void deleteByAppUserAndMidiSequence(AppUser appUser, MidiSequence midiSequence);
}