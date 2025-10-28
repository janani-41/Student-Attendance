// ...existing code...
package com.example.moviesearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class MovieSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieSearchApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RestController
    @RequestMapping("/api/movies")
    static class MovieController {
        private final OmdbService omdbService;

        public MovieController(OmdbService omdbService) {
            this.omdbService = omdbService;
        }

        @GetMapping("/search")
        public OmdbSearchResponse search(@RequestParam("q") String q,
                                         @RequestParam(value = "page", required = false) Integer page) {
            return omdbService.search(q, page);
        }

        @GetMapping("/{imdbId}")
        public MovieDetail getById(@PathVariable String imdbId) {
            return omdbService.getByImdbId(imdbId);
        }
    }

    @org.springframework.stereotype.Service
    static class OmdbService {
        private final RestTemplate rest;
        // Default API key set to the key you provided
        private final String apiKey;
        private final String baseUrl;

        public OmdbService(RestTemplate rest,
                           @Value("${omdb.api.key:35b23593}") String apiKey,
                           @Value("${omdb.api.url:https://www.omdbapi.com/}") String baseUrl) {
            this.rest = rest;
            this.apiKey = apiKey;
            this.baseUrl = baseUrl;
        }

        public OmdbSearchResponse search(String query, Integer page) {
            var uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("apikey", apiKey)
                    .queryParam("s", query)
                    .queryParam("page", page == null ? 1 : page)
                    .build().toUriString();

            ResponseEntity<OmdbSearchResponse> resp = rest.getForEntity(uri, OmdbSearchResponse.class);
            OmdbSearchResponse body = resp.getBody();
            if (body == null) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Empty response from OMDb");
            if ("False".equalsIgnoreCase(body.response)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, body.error != null ? body.error : "Not found");
            }
            return body;
        }

        public MovieDetail getByImdbId(String imdbId) {
            var uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("apikey", apiKey)
                    .queryParam("i", imdbId)
                    .build().toUriString();

            ResponseEntity<MovieDetail> resp = rest.getForEntity(uri, MovieDetail.class);
            MovieDetail body = resp.getBody();
            if (body == null) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Empty response from OMDb");
            if ("False".equalsIgnoreCase(body.response)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, body.error != null ? body.error : "Not found");
            }
            return body;
        }
    }

    // Models (kept inside same file for single-file project)
    static class MovieSummary {
        @JsonProperty("Title") public String title;
        @JsonProperty("Year") public String year;
        @JsonProperty("imdbID") public String imdbID;
        @JsonProperty("Type") public String type;
        @JsonProperty("Poster") public String poster;
    }

    static class OmdbSearchResponse {
        @JsonProperty("Search") public List<MovieSummary> search;
        @JsonProperty("totalResults") public String totalResults;
        @JsonProperty("Response") public String response;
        @JsonProperty("Error") public String error;
    }

    static class MovieDetail {
        @JsonProperty("Title") public String title;
        @JsonProperty("Year") public String year;
        @JsonProperty("Rated") public String rated;
        @JsonProperty("Released") public String released;
        @JsonProperty("Runtime") public String runtime;
        @JsonProperty("Genre") public String genre;
        @JsonProperty("Director") public String director;
        @JsonProperty("Writer") public String writer;
        @JsonProperty("Actors") public String actors;
        @JsonProperty("Plot") public String plot;
        @JsonProperty("Language") public String language;
        @JsonProperty("Country") public String country;
        @JsonProperty("Awards") public String awards;
        @JsonProperty("Poster") public String poster;
        @JsonProperty("Ratings") public List<Map<String,String>> ratings;
        @JsonProperty("Metascore") public String metascore;
        @JsonProperty("imdbRating") public String imdbRating;
        @JsonProperty("imdbVotes") public String imdbVotes;
        @JsonProperty("imdbID") public String imdbID;
        @JsonProperty("Type") public String type;
        @JsonProperty("DVD") public String dvd;
        @JsonProperty("BoxOffice") public String boxOffice;
        @JsonProperty("Production") public String production;
        @JsonProperty("Website") public String website;
        @JsonProperty("Response") public String response;
        @JsonProperty("Error") public String error;
    }
}
// ...existing code...