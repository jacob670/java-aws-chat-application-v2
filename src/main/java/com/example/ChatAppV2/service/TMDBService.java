package com.example.ChatAppV2.service;

import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TMDBService {

    private final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    public static String BEARERTOKEN;

    @Value("${mdb.auth.token}")
    public void setBearerToken(String bearerToken) {
        BEARERTOKEN = bearerToken;
    }

    public String fetchMovieID() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.themoviedb.org/3/search/movie?include_adult=false&language=en-US&page=1"))
                    .GET()
                    .headers("accept", "application/json", "Authorization", BEARERTOKEN)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println("error has occurred");
        }
        return null;
    }
}
