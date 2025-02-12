package com.alibou.frontend.service;

import com.alibou.common.model.Classroom;
import com.alibou.frontend.client.SchoolClient;
import com.alibou.common.dto.ClassroomDTO;
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

    public void saveClassroom(Classroom classroom) {
        client.saveClassroom(classroom);
    }

    public void deleteClassroom(Integer id) {
        client.deleteClassroom(id);
    }

    public ClassroomDTO getClassroomById(Integer id) {
        return client.getClassroomById(id);
    }

    public void assignTeacherToClassroom(Integer teacherId, Integer classroomId) {
        client.assignTeacherToClassroom(teacherId, classroomId);
    }

}
