package com.midihub.melodyhub.service;

import com.midihub.melodyhub.entity.AppUser;
import com.midihub.melodyhub.entity.Favorite;
import com.midihub.melodyhub.entity.MidiSequence;
import com.midihub.melodyhub.repository.AppUserRepository;
import com.midihub.melodyhub.repository.FavoriteRepository;
import com.midihub.melodyhub.repository.MidiSequenceRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MidiSequenceRepository midiSequenceRepository;
    private final AppUserRepository appUserRepository;

    public FavoriteService(
            FavoriteRepository favoriteRepository,
            MidiSequenceRepository midiSequenceRepository,
            AppUserRepository appUserRepository
    ) {
        this.favoriteRepository = favoriteRepository;
        this.midiSequenceRepository = midiSequenceRepository;
        this.appUserRepository = appUserRepository;
    }

    private AppUser getLoggedInUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must be logged in.");
        }

        return appUserRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found."));
    }

    public List<MidiSequence> getFavoriteMidis(HttpSession session) {
        AppUser user = getLoggedInUser(session);

        return favoriteRepository.findByAppUser(user)
                .stream()
                .map(Favorite::getMidiSequence)
                .toList();
    }

    public List<Long> getFavoriteMidiIds(HttpSession session) {
        AppUser user = getLoggedInUser(session);

        return favoriteRepository.findByAppUser(user)
                .stream()
                .map(favorite -> favorite.getMidiSequence().getId())
                .toList();
    }

    public Favorite addFavorite(Long midiId, HttpSession session) {
        AppUser user = getLoggedInUser(session);

        MidiSequence midi = midiSequenceRepository.findById(midiId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MIDI not found."));

        boolean alreadyFavorited = favoriteRepository.existsByAppUserAndMidiSequence(user, midi);

        if (alreadyFavorited) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "MIDI is already favorited.");
        }

        Favorite favorite = new Favorite();
        favorite.setAppUser(user);
        favorite.setMidiSequence(midi);

        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(Long midiId, HttpSession session) {
        AppUser user = getLoggedInUser(session);

        MidiSequence midi = midiSequenceRepository.findById(midiId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MIDI not found."));

        favoriteRepository.deleteByAppUserAndMidiSequence(user, midi);
    }
}