package com.example.ChatAppV2.model.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class MovieRecommendationResponse {
    @JsonProperty("body")
    private String body;

    private int statusCode;
    private Headers headers;

    public List<Movie> getMovies() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, new TypeReference<List<Movie>>() {});
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }
}
