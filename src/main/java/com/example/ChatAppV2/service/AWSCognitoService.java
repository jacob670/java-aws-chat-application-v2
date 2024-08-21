package com.example.ChatAppV2.service;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.ChatAppV2.model.aws.CognitoTokenResponse;
import com.example.ChatAppV2.model.aws.CognitoUserProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    public CognitoTokenResponse authenticateUser(CognitoIdentityProviderClient cognitoIdentityProviderClient, String userPoolId, String clientId, String username, String password) {
        String secretHash = AWSCognitoService.computeSecretHash(username, CLIENTID, CLIENTSECRET);

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

        return new CognitoTokenResponse(
                response.authenticationResult().accessToken(),
                response.authenticationResult().idToken(),
                response.authenticationResult().refreshToken(),
                response.authenticationResult().expiresIn()
        );
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

    public CognitoUserProperties getApplicationUserProperties(String idToken) {

        String region = "us-east-2";
        String userPoolId = "us-east-2_R2LJFWtgi";
        String jwksUrl = "https://cognito-idp." + region + ".amazonaws.com/"+userPoolId;

        try {
            // Create a JWK Provider
            JwkProvider provider = new JwkProviderBuilder(jwksUrl)
                    .cached(10, 24, TimeUnit.HOURS) // Cache up to 10 keys for 24 hours
                    .rateLimited(10, 1, TimeUnit.MINUTES) // If rate limit is hit, it will block up to 10 requests per minute
                    .build();

            // Decode the token to get the header
            DecodedJWT jwt = JWT.decode(idToken);

            // Get the JWK for the key ID in the token header
            Jwk jwk = provider.get(jwt.getKeyId());

            // Get the public key from the JWK
            RSAPublicKey publicKey = (RSAPublicKey) jwk.getPublicKey();

            // Verify the token using the public key
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            algorithm.verify(jwt);

            return new CognitoUserProperties(
                    jwt.getClaim("cognito:username").asString(),
                    jwt.getClaim("email").asString(),
                    jwt.getIssuer(),
                    jwt.getClaim("email_verified").asBoolean()
            );

        } catch (JWTVerificationException e) {
            System.err.println("Invalid token: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
}
