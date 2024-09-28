package com.example.ChatAppV2.controller;

import com.example.ChatAppV2.model.tmdb.Movie;
import com.example.ChatAppV2.model.tmdb.MovieSearch;
import com.example.ChatAppV2.service.AWSCognitoService;
import com.example.ChatAppV2.service.JSONService;
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
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/movie")
public class TMDBController {

    private final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    private final TMDBService tmdbService;
    private final JSONService jsonService;

    public TMDBController(TMDBService tmdbService, JSONService jsonService) {
        this.tmdbService = tmdbService;
        this.jsonService = jsonService;
    }

    @PostMapping("/fetchMovieId")
    public ResponseEntity<Integer> getMovieID(@RequestBody MovieSearch movieSearch) {
        String movieData = tmdbService.searchMovie(movieSearch);
        System.out.println(movieSearch.getQueryString());

        int id = jsonService.parseMovieSearchResponse(movieData);

        return ResponseEntity.ok(id);
    }

    @GetMapping("/fetchRecommendedMovies")
    public ResponseEntity<List<Movie>> getRecommendedMovies(@RequestParam String movieId) {
        String data = tmdbService.findMovieRecommendations(movieId);
        List<Movie> movies = jsonService.parseRecommendedMoviesResponse(data);

        return ResponseEntity.ok(movies);
    }

}
