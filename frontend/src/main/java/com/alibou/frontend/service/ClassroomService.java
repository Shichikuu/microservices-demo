package com.alibou.frontend.service;

import com.alibou.frontend.client.SchoolClient;
import com.alibou.school.dto.ClassroomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final SchoolClient client;

    public List<ClassroomDTO> getAllClassrooms() {
        return client.findAllClassrooms();
    }
}
