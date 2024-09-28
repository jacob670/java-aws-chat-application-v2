package com.example.ChatAppV2.service;

import com.example.ChatAppV2.model.tmdb.MovieSearch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class TMDBService {

    private final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    public static String BEARERTOKEN;
    public static String AUTHKEY;

    @Value("${mdb.auth.token}")
    public void setBearerToken(String bearerToken) {
        BEARERTOKEN = bearerToken;
    }

    @Value("${mdb.auth.key}")
    public void setAuthKey(String authKey) {
        AUTHKEY = authKey;
    }

    public String searchMovie(MovieSearch movieSearch) {
        String baseUrl = "https://api.themoviedb.org/3/search/movie";

        try {
            String encodedQuery = URLEncoder.encode(movieSearch.getQueryString(), "UTF-8");
            String finalUrl = String.format("%s?api_key=%s&query=%s", baseUrl, AUTHKEY, encodedQuery);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(finalUrl))
                    .GET()
                    .headers("accept", "application/json", "Authorization", BEARERTOKEN)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Request has failed");
            }
            return response.body();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Request has failed");
        }
    }

     public String findMovieRecommendations(String movieId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://lnqe826bld.execute-api.us-east-2.amazonaws.com/dev/recommendedMovies"+"?movieId=" + movieId))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Request has failed");
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Request has failed");
        }
    }
}
