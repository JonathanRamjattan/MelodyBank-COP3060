package com.midihub.melodyhub.repository;

import com.midihub.melodyhub.entity.Favorite;

import java.util.List;

public interface FavoriteRepository {
    Favorite save(Favorite favorite);
    List<Favorite> findByUserId(Long userId);
}
