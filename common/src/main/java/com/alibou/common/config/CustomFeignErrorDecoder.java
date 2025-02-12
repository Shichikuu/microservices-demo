package com.alibou.common.config;



import com.alibou.common.model.ExceptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CustomFeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            // Read the response body as a string
            String body = "";
            if (response.body() != null) {
                body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            }
            // If the error body contains our known error message,
            // throw a custom exception (or even a plain IllegalArgumentException)
            if (methodKey.contains("assignTeacherToClassroom")) {
                return new IllegalArgumentException("Teacher already assigned to a classroom");
            }

        } catch (IOException e) {
            // Log error if needed and fall back to default decoder
        }
        return defaultDecoder.decode(methodKey, response);
    }
}
