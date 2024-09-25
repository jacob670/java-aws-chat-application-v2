package com.example.ChatAppV2.controller;

import com.example.ChatAppV2.model.tmdb.MovieSearch;
import com.example.ChatAppV2.service.AWSCognitoService;
import com.example.ChatAppV2.service.TMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/movie")
public class TMDBController {

    private final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    private final TMDBService tmdbService;

    public TMDBController(TMDBService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @PostMapping("/fetchMovieId")
    public ResponseEntity<String> getMovieID(@RequestBody MovieSearch movieSearch) {
        String movieData = tmdbService.searchMovie(movieSearch);
        return ResponseEntity.ok(movieData);
    }

}
