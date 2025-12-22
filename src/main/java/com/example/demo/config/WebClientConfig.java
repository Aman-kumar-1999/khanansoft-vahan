package com.example.demo.config;

// Use your actual config package

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	private static final String FULL_URL = "https://khanansoft.bihar.gov.in/portal/CitizenRpt/ePassReportAllConsigner.aspx?ASHxNlmoFyLHysPqHK2s1TwFgowNPHidOTrr5XhEWswdu5gpobGqv+72I%2f1rwkLPB0JdWHjzMrgGY8WgzHvrQsMFBsTpyEs8qxGPUUBCa%2fnuq+dowsC1AuMXB8GOhlTExOId%2fpS80TQ=";

	
	
    // Spring Boot automatically provides a WebClient.Builder bean
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        
        // This is where you configure your WebClient instance.
        // You can set a common base URL, default headers, timeouts, etc.
        
        return builder
                .baseUrl(FULL_URL).defaultHeader(HttpHeaders.ACCEPT,
        				"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
        				.defaultHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br, zstd")
        				.defaultHeader(HttpHeaders.CACHE_CONTROL, "max-age=0")
        				.defaultHeader(HttpHeaders.CONNECTION, "keep-alive").defaultHeader("sec-fetch-dest", "document")
        				.defaultHeader("sec-fetch-mode", "navigate").defaultHeader("sec-fetch-site", "none")
        				.defaultHeader("sec-fetch-user", "?1").defaultHeader("upgrade-insecure-requests", "1").build();
    }
}