package com.alibou.frontend.service;

import com.alibou.common.model.Teacher;
import com.alibou.frontend.client.SchoolClient;
import com.alibou.frontend.client.TeacherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherClient client;

    public List<Teacher> findAllTeachersBySchool(Integer schoolId) {
        return client.findAllTeachersBySchool(schoolId);
    }

    public void assignTeacherToClassroom(Integer teacherId, Integer classroomId) {
        client.assignTeacherToClassroom(teacherId, classroomId);
    }

}
