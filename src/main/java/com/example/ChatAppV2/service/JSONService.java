package com.example.ChatAppV2.service;

import com.example.ChatAppV2.model.tmdb.Movie;
import com.example.ChatAppV2.model.tmdb.MovieRecommendationResponse;
import com.example.ChatAppV2.model.tmdb.MovieSearchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JSONService {
    public int parseMovieSearchResponse(String movieData) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> ids = new ArrayList<>();

        try {
            MovieSearchResponse movieSearchResponse = objectMapper.readValue(movieData, MovieSearchResponse.class);
            for (Movie movie :  movieSearchResponse.getResults()) {
                ids.add(movie.getId());
            }

            return ids.get(0);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Movie> parseRecommendedMoviesResponse(String movieData) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            MovieRecommendationResponse movieRecommendationResponse = objectMapper.readValue(movieData, MovieRecommendationResponse.class);
            return movieRecommendationResponse.getMovies();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
