package com.financebuddha.finbud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${aws.accessKey:}")
    private String accessKey;

    @Value("${aws.secretKey:}")
    private String secretKey;

    @Value("${aws.region:}")
    private String region;

    // ❌ REMOVE ALL BEANS FOR NOW
    // We are NOT using AWS currently
}