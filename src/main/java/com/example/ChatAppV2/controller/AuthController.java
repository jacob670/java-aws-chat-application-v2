package com.example.ChatAppV2.controller;

import com.example.ChatAppV2.config.AWSConfig;
import com.example.ChatAppV2.model.AccountData;
import com.example.ChatAppV2.model.LoginData;
import com.example.ChatAppV2.service.AWSCognitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CodeMismatchException;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(AWSConfig.ACCESSKEY, AWSConfig.SECRETKEY);

    private final CognitoIdentityProviderClient identityProviderClient = CognitoIdentityProviderClient.builder()
            .region(Region.US_EAST_2)
            .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
            .build();

    @PostMapping("/signUpRequest")
    public ResponseEntity<String> submitSignUpRequest(@RequestBody AccountData accountData) throws NoSuchAlgorithmException, InvalidKeyException {
        AWSCognitoService awsCognitoService = new AWSCognitoService();

        if (!isValidPassword(accountData.getPassword())) {
            //return new ResponseEntity<>("Invalid Password", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Password");
        }
            //awsCognitoService.signUp(identityProviderClient, accountData.getName(), accountData.getPassword(), accountData.getEmail());

            String token = UUID.randomUUID().toString();
            String confirmationUrl = "confirm/" + token;

//            return ResponseEntity.ok(confirmationUrl);

        return ResponseEntity.ok(confirmationUrl);

    }



    private boolean isValidPassword(String password) {
        String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%?=*&]).{8,20})";
        return password.matches(passwordPattern);
    }

    @GetMapping("confirmSignUp/{token}")
    public ResponseEntity<String> submitConfirmationSignUpCode(@PathVariable String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        return ResponseEntity.ok(token);
    }


    /*
    * Little bug to fix with redirect after sign up
    * */
    @PostMapping("confirmSignUp/{token}")
    public ResponseEntity<String> submitCode(@PathVariable String token, @RequestBody AccountData accountData) throws NoSuchAlgorithmException, InvalidKeyException {
        AWSCognitoService awsCognitoService = new AWSCognitoService();

        try {
        awsCognitoService.confirmSignUp(AWSCognitoService.CLIENTID, identityProviderClient, accountData.getConfirmationCode(), accountData.getUsername());
        } catch(CodeMismatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid confirmation code was found");
        }
        return ResponseEntity.ok("Successfully Confirmed");
    }

    private boolean validateToken(String token) {return true;}


    @PostMapping("/loginRequest")
    public ResponseEntity<String> submitLoginRequest(@RequestBody LoginData loginData) {
        AWSCognitoService awsCognitoService = new AWSCognitoService();
//        System.out.println(awsCognitoService.authenticateUser(identityProviderClient, AWSCognitoService.USERPOOLID, AWSCognitoService.CLIENTID,
//                            loginData.getName(), loginData.getPassword()));


        //awsCognitoService.authenticateUser(identityProviderClient, AWSCognitoService.CLIENTID, AWSCognitoService.IAMID, loginData.getName(), loginData.getName(), loginData.getPassword());
        return ResponseEntity.ok("Form submitted successfully");
    }
}

