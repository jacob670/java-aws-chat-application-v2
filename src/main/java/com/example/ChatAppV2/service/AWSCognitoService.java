package com.example.ChatAppV2.service;

import com.example.ChatAppV2.config.AWSConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.rmi.ServerError;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class AWSCognitoService {

    public static String CLIENTID;
    public static String CLIENTSECRET;
    public static String USERPOOLID;

    @Value("${aws.cognito.clientId}")
    public void setClientId(String clientId) {
        CLIENTID = clientId;
    }

    @Value("${aws.cognito.clientSecret}")
    public void setClientSecret(String clientSecret) {
        CLIENTSECRET = clientSecret;
    }

    @Value("${aws.cognito.userPoolId}")
    public void setUSERPOOLID(String userPoolId) {
        USERPOOLID = userPoolId;
    }


    /*
    * Authorization and Hash Methods
    */

    public void signUp(CognitoIdentityProviderClient cognitoIdentityProviderClient, String userName, String password, String email) throws NoSuchAlgorithmException, InvalidKeyException {
        AttributeType attributeType = AttributeType.builder()
                .name("email")
                .value(email)
                .build();

        List<AttributeType> attributeTypes = new ArrayList<>();
        attributeTypes.add(attributeType);

        String secretVal = calculateSecretHash(CLIENTID, CLIENTSECRET, userName);

        try {
            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .userAttributes(attributeTypes)
                    .username(userName)
                    .clientId(CLIENTID)
                    .password(password)
                    .secretHash(secretVal)
                    .build();

            cognitoIdentityProviderClient.signUp(signUpRequest);
            System.out.println("User has been signed up ");
        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String userName) throws NoSuchAlgorithmException, InvalidKeyException {
        final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

        SecretKeySpec signingKey = new SecretKeySpec(
                userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
                HMAC_SHA256_ALGORITHM
        );

        Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        mac.init(signingKey);
        mac.update(userName.getBytes(StandardCharsets.UTF_8));
        byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));

        return java.util.Base64.getEncoder().encodeToString(rawHmac);
    }

    public void confirmSignUp(String clientId, CognitoIdentityProviderClient identityProviderClient, String confirmationCode, String username) throws NoSuchAlgorithmException, InvalidKeyException {
        String secretVal = calculateSecretHash(CLIENTID, CLIENTSECRET, username);

        ConfirmSignUpRequest confirmSignUpRequest = ConfirmSignUpRequest.builder()
                .clientId(clientId)
                .confirmationCode(confirmationCode)
                .username(username)
                .secretHash(secretVal)
                .build();

        identityProviderClient.confirmSignUp(confirmSignUpRequest);
        System.out.println("User " + username + " was confirmed");
    }

    public String authenticateUser(CognitoIdentityProviderClient cognitoIdentityProviderClient, String userPoolId, String clientId, String username, String password) {
        String secretHash = AWSCognitoService.computeSecretHash("TallGuy", CLIENTID, CLIENTSECRET);


        Map<String, String> authParameters = new HashMap<>();
        authParameters.put("USERNAME", username);
        authParameters.put("PASSWORD", password);
        authParameters.put("SECRET_HASH", secretHash);

        AdminInitiateAuthRequest req = AdminInitiateAuthRequest.builder()
                .authFlow(AuthFlowType.ADMIN_USER_PASSWORD_AUTH)
                .clientId(clientId)
                .userPoolId(userPoolId)
                .authParameters(authParameters)
                .build();

        AdminInitiateAuthResponse response = cognitoIdentityProviderClient.adminInitiateAuth(req);

        System.out.println(response.authenticationResult());
        System.out.println(response.challengeNameAsString());

        return response.authenticationResult().accessToken();
    }

    public static String computeSecretHash(String username, String clientId, String clientSecret) {
        try {
            String message = username + clientId;
            SecretKeySpec signingKey = new SecretKeySpec(clientSecret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Error computing secret hash", e);
        }
    }

    public static boolean isUsernameTaken(CognitoIdentityProviderClient cognitoIdentityProviderClient, String username, String poolId) {

        /*
        * if an exception is thrown, the username is not in the cognito pool, therefore returns false
        * if the try block runs successfully, there was a user with that name already in the pool, therefore already exists and returns true
        * */

        try {
            AdminGetUserRequest adminGetUserRequest = AdminGetUserRequest.builder()
                    .username(username)
                    .userPoolId(poolId)
                    .build();

            AdminGetUserResponse adminGetUserResponse = cognitoIdentityProviderClient.adminGetUser(adminGetUserRequest);
            System.out.println("This user was created at: " + adminGetUserResponse.userCreateDate());
        }
        catch (UserNotFoundException e) {
            return false;
        }
        return true;
    }
}