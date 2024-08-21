package com.example.ChatAppV2;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.ChatAppV2.service.AWSCognitoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.interfaces.RSAPublicKey;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ChatAppV2Application {

	public static void main(String[] args) {
		SpringApplication.run(ChatAppV2Application.class, args);
	}
}
