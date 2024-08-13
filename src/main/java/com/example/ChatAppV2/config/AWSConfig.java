package com.example.ChatAppV2.config;

import com.example.ChatAppV2.service.AWSCognitoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
public class AWSConfig {
    public static String ACCESSKEY;
    public static String SECRETKEY;

    @Value("${aws.iam.accessKey}")
    public void setAcessKey(String accessKey) {
        ACCESSKEY = accessKey;
    }

    @Value("${aws.iam.secretKey}")
    public void setSecretKey(String secretkey) {
        SECRETKEY = secretkey;
    }
}