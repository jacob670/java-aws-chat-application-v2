package com.example.ChatAppV2.controller;

import com.example.ChatAppV2.config.AWSConfig;
import com.example.ChatAppV2.model.AccountData;
import com.example.ChatAppV2.model.LoginData;
import com.example.ChatAppV2.service.AWSCognitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(AWSConfig.ACCESSKEY, AWSConfig.SECRETKEY);

    private final CognitoIdentityProviderClient identityProviderClient = CognitoIdentityProviderClient.builder()
            .region(Region.US_EAST_2)
            .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
            .build();

    private boolean signUpFormSubmitted = false;







    /*
    * METHODS
    * */

    @PostMapping("/signUpRequest")
    public ResponseEntity<String> submitSignUpRequest(@RequestBody AccountData accountData) {

        boolean signUpSuccessful = true;

        if (signUpSuccessful) {
            return ResponseEntity.ok("Sign-up successful");
        } else {
            return ResponseEntity.status(400).body("Sign-up failed");
        }
    }

    @PostMapping("/loginRequest")
    public ResponseEntity<String> submitLoginRequest(@RequestBody LoginData loginData) {
        AWSCognitoService awsCognitoService = new AWSCognitoService();
        System.out.println(awsCognitoService.authenticateUser(identityProviderClient, AWSCognitoService.USERPOOLID, AWSCognitoService.CLIENTID,
                            loginData.getName(), loginData.getPassword()));


        //awsCognitoService.authenticateUser(identityProviderClient, AWSCognitoService.CLIENTID, AWSCognitoService.IAMID, formData.getName(), formData.getPassword());
        return ResponseEntity.ok("Form submitted successfully");
    }
}

