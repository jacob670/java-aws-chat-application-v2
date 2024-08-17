package com.example.ChatAppV2;

import com.example.ChatAppV2.config.AWSConfig;
import com.example.ChatAppV2.controller.AuthController;
import com.example.ChatAppV2.service.AWSCognitoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ChatAppV2Application {

	public static void main(String[] args) {
		SpringApplication.run(ChatAppV2Application.class, args);




		//awsCognitoService.signUp(identityProviderClient, "TallGuy", "aA1!aaaa", "esteves.jacob.c@gmail.com");
		//awsCognitoService.confirmSignUp(AWSCognitoService.CLIENTID, identityProviderClient, "474730", "TallGuy");
	}

}
