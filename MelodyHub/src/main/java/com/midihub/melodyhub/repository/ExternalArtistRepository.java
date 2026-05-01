package com.midihub.melodyhub.repository;

import com.midihub.melodyhub.entity.ExternalArtist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExternalArtistRepository extends JpaRepository<ExternalArtist, Long> {
    Optional<ExternalArtist> findByMusicBrainzId(String musicBrainzId);
}