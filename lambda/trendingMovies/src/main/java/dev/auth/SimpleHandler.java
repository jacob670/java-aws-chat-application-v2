package dev.auth;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SimpleHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        // BEARERTOKEN is hidden in this directory from lambdas
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/trending/movie/day?language=en-US"))
                .GET()
                .headers("accept", "application/json", "Authorization", BEARERTOKEN)
                .build();

        HttpClient client = HttpClient.newHttpClient();

        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Access-Control-Allow-Origin", "*");
            headers.put("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            headers.put("Access-Control-Allow-Headers", "Content-Type, Authorization");

            HttpResponse<String> res = client.send(request1, HttpResponse.BodyHandlers.ofString());

            // Headers for CORS Config
            response.setHeaders(headers);
            response.setBody(res.body());

            return response;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}