package com.alibou.common.config;



import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

import java.io.IOException;
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

            if (methodKey.contains("insertStudentToClassroom")) {
                return new IllegalArgumentException("Student already in this classroom");
            }

            if (methodKey.contains("insertStudentToSchool")) {
                return new IllegalArgumentException("Student already in this school");
            }

        } catch (IOException e) {
            // Log error if needed and fall back to default decoder
        }
        return defaultDecoder.decode(methodKey, response);
    }
}
