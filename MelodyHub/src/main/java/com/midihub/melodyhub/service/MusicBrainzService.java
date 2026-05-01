package com.midihub.melodyhub.service;

import com.midihub.melodyhub.entity.ExternalArtist;
import com.midihub.melodyhub.repository.ExternalArtistRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class MusicBrainzService {

    private final ExternalArtistRepository externalArtistRepository;

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://musicbrainz.org/ws/2")
            .defaultHeader("User-Agent", "MelodyBank/1.0 (student-project@example.com)")
            .build();

    public MusicBrainzService(ExternalArtistRepository externalArtistRepository) {
        this.externalArtistRepository = externalArtistRepository;
    }

    public ExternalArtist searchAndSaveArtist(String artistName) {
        if (artistName == null || artistName.isBlank()) {
            return null;
        }

        Map response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/artist")
                        .queryParam("query", artistName)
                        .queryParam("fmt", "json")
                        .queryParam("limit", 1)
                        .build())
                .retrieve()
                .body(Map.class);

        if (response == null || response.get("artists") == null) {
            return null;
        }

        List artists = (List) response.get("artists");

        if (artists.isEmpty()) {
            return null;
        }

        Map firstArtist = (Map) artists.get(0);

        String musicBrainzId = (String) firstArtist.get("id");

        return externalArtistRepository.findByMusicBrainzId(musicBrainzId)
                .orElseGet(() -> {
                    ExternalArtist artist = new ExternalArtist();
                    artist.setMusicBrainzId(musicBrainzId);
                    artist.setName((String) firstArtist.get("name"));
                    artist.setCountry((String) firstArtist.get("country"));
                    artist.setType((String) firstArtist.get("type"));
                    artist.setDisambiguation((String) firstArtist.get("disambiguation"));

                    return externalArtistRepository.save(artist);
                });
    }
}