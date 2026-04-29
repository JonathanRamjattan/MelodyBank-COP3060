package com.midihub.melodyhub.service;

import com.midihub.melodyhub.entity.Favorite;

import java.util.ArrayList;
import java.util.List;

public class FavoriteService {

    private List<Favorite> favorites = new ArrayList<>();

    public Favorite addFavorite(Favorite favorite) {
        favorites.add(favorite);
        return favorite;
    }

    public List<Favorite> getUserFavorites(Long userId) {
        return favorites.stream()
                .filter(f -> f.getAppUser().getId().equals(userId))
                .toList();
    }
}