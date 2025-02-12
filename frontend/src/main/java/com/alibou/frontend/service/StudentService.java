package com.alibou.frontend.service;

import com.alibou.common.dto.StudentFullResponse;
import com.alibou.frontend.client.SchoolClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final SchoolClient schoolClient;

    public List<StudentFullResponse> getAllStudents() {
        return schoolClient.getAllFullStudentResponse();
    }
}
