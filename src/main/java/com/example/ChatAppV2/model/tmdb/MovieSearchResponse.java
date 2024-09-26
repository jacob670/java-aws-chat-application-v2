package com.example.ChatAppV2.model.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MovieSearchResponse {
    private List<Movie> results;
    private int page;
    private int totalPages;
    private int totalResults;

    public List<Movie> getResults() {
        return results;
    }

    @JsonProperty("page")
    public int getPage() {
        return page;
    }

    @JsonProperty("total_pages")
    public int getTotalPages() {
        return totalPages;
    }

    @JsonProperty("total_results")
    public int getTotalResults() {
        return totalResults;
    }
}
