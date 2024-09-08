package com.example.ChatAppV2.controller;

import com.example.ChatAppV2.model.tmdb.MovieSearch;
import com.example.ChatAppV2.service.AWSCognitoService;
import com.example.ChatAppV2.service.TMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/movie")
public class TMDBController {

    @Autowired
    private TMDBService tmdbService;

    @PostMapping("/fetchMovieId")
    public ResponseEntity<String> getMovieID(@RequestBody MovieSearch movieSearch) {

    }

}
