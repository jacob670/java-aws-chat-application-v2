package com.example.ChatAppV2.model.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Headers {
    @JsonProperty("Access-Control-Allow-Origin")
    private String accessControlAllowOrigin;

    @JsonProperty("Access-Control-Allow-Headers")
    private String accessControlAllowHeaders;

    @JsonProperty("Access-Control-Allow-Methods")
    private String accessControlAllowMethods;

    @JsonProperty("Content-Type")
    private String contentType;

    public String getAccessControlAllowOrigin() {
        return accessControlAllowOrigin;
    }

    public void setAccessControlAllowOrigin(String accessControlAllowOrigin) {
        this.accessControlAllowOrigin = accessControlAllowOrigin;
    }

    public String getAccessControlAllowHeaders() {
        return accessControlAllowHeaders;
    }

    public void setAccessControlAllowHeaders(String accessControlAllowHeaders) {
        this.accessControlAllowHeaders = accessControlAllowHeaders;
    }

    public String getAccessControlAllowMethods() {
        return accessControlAllowMethods;
    }

    public void setAccessControlAllowMethods(String accessControlAllowMethods) {
        this.accessControlAllowMethods = accessControlAllowMethods;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
