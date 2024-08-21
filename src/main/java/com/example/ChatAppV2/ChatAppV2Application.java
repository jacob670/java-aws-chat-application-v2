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

		String idToken = "eyJraWQiOiJRUlZEXC9qMkhtWjhqeHk2UUp3K0laU0ZJbkRTTzdjYW4yZm1VZVRCYU1UUT0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJmMWJiODU2MC0xMGExLTcwMzUtNDA0MS0zMDJjOTlkMTQ2ODYiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLWVhc3QtMi5hbWF6b25hd3MuY29tXC91cy1lYXN0LTJfUjJMSkZXdGdpIiwiY29nbml0bzp1c2VybmFtZSI6InRlc3R1c2VyIiwib3JpZ2luX2p0aSI6ImIyZmVmNGMxLWQ5MDgtNGY0ZC05OTFlLWUxYjNlZjQ0M2I5MSIsImF1ZCI6IjQ1YXU2bHV2N2JxOXA4OWd1ZGppNjhwcDhuIiwiZXZlbnRfaWQiOiI2YmI5ZmRkNi1jYjdkLTRlMzEtYTZkYy0yOGMxYzdmZDY2NGEiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTcyNDI1NTI4MiwiZXhwIjoxNzI0MjU4ODgyLCJpYXQiOjE3MjQyNTUyODIsImp0aSI6ImM3ZmE4MDI4LTgzYWEtNDljNi1hODYxLTFlYzQ1M2FhMjM1OSIsImVtYWlsIjoiZXN0ZXZlcy5qYWNvYi5jQGdtYWlsLmNvbSJ9.LWEkX4YwswLKg9XFtPyJDR1q9WCFAi-mRr35LLJnmw9S2s3DXGl1DMn7SjbKQFGc-MXGHDjeP_jI4_hoHqQtuHYuHJ2yUIs4AVrPoN2_Ol65WQ30rfZphmqYdXKz1EoYg68yNEcfjmc6Om165mdZHVnjoqHE3Gi2L00j91utuKKNSsV9Yl0EqJ2iYtBa4Zzwi_4HBWyGDAz4h93_woRFdjouHBxj9uaeGKQwmxKXc3pTQtVxwznaf445R3dlIuyVLH3OC3YHulgXW3nl4YVKYejIzN3mo3Jp0r4CP5rrPd1WCQVQHeb29yjYBh7O6m-6U-ptFGtTdog94Szr_-jriw";
//
//		String region = "us-east-2";
//		String userPoolId = "us-east-2_R2LJFWtgi";
//		String jwksUrl = "https://cognito-idp." + region + ".amazonaws.com/"+userPoolId;
//
//		try {
//			// Create a JWK Provider
//			JwkProvider provider = new JwkProviderBuilder(jwksUrl)
//					.cached(10, 24, TimeUnit.HOURS) // Cache up to 10 keys for 24 hours
//					.rateLimited(10, 1, TimeUnit.MINUTES) // If rate limit is hit, it will block up to 10 requests per minute
//					.build();
//
//			// Decode the token to get the header
//			DecodedJWT jwt = JWT.decode(idToken);
//
//			// Get the JWK for the key ID in the token header
//			Jwk jwk = provider.get(jwt.getKeyId());
//
//			// Get the public key from the JWK
//			RSAPublicKey publicKey = (RSAPublicKey) jwk.getPublicKey();
//
//			// Verify the token using the public key
//			Algorithm algorithm = Algorithm.RSA256(publicKey, null);
//			algorithm.verify(jwt);
//
//			// Successfully decoded and verified token
//			System.out.println("Token decoded successfully!");
//			System.out.println("Subject: " + jwt.getSubject());
//			System.out.println("Issuer: " + jwt.getIssuer());
//			System.out.println("Expiration: " + jwt.getExpiresAt());
//			System.out.println("Claims: " + jwt.getClaims());
//
//
//			String name = jwt.getClaim("cognito:username").toString();
//			System.out.println(name);
//		} catch (JWTVerificationException e) {
//			// Invalid signature or claims
//			System.err.println("Invalid token: " + e.getMessage());
//		} catch (Exception e) {
//			// Handle other exceptions
//			e.printStackTrace();
//		}
	}
}
