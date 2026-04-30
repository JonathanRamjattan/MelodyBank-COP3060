package com.midihub.melodyhub.controller;

import com.midihub.melodyhub.entity.Favorite;
import com.midihub.melodyhub.entity.MidiSequence;
import com.midihub.melodyhub.service.FavoriteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public List<MidiSequence> getMyFavorites(HttpSession session) {
        return favoriteService.getFavoriteMidis(session);
    }

    @GetMapping("/ids")
    public List<Long> getMyFavoriteIds(HttpSession session) {
        return favoriteService.getFavoriteMidiIds(session);
    }

    @PostMapping("/{midiId}")
    public Favorite addFavorite(@PathVariable Long midiId, HttpSession session) {
        return favoriteService.addFavorite(midiId, session);
    }

    @DeleteMapping("/{midiId}")
    public void removeFavorite(@PathVariable Long midiId, HttpSession session) {
        favoriteService.removeFavorite(midiId, session);
    }
}