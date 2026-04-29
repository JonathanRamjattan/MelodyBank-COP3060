package com.midihub.melodyhub.repository;

import com.midihub.melodyhub.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {}
