package com.alibou.common.config;



import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        // Parse the response body (if available)
        String responseBody = null;
        try {
            if (response.body() != null) {
                responseBody = new String(response.body().asInputStream().readAllBytes());
            }
        } catch (Exception e) {
            responseBody = "Unable to parse response body";
        }

        // Map HTTP status codes to exceptions
        switch (response.status()) {
            case 400:
                return new IllegalArgumentException("Bad Request: " + responseBody);
            case 500:
                return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error: " + responseBody);
            default:
                return new RuntimeException("Unexpected error: " + response.status() + " - " + responseBody);
        }
    }
}
