package com.alibou.frontend.service;

import com.alibou.common.model.Teacher;
import com.alibou.frontend.client.SchoolClient;
import com.alibou.frontend.client.TeacherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherClient client;
    private final SchoolClient schoolClient;

    public Page<Teacher> findAllTeachersBySchool(Integer schoolId, Pageable pageable) {
        return client.findAllTeachersBySchool(schoolId, pageable);
    }


    public Page<Teacher> findAllTeachersPaged(String name, Pageable pageable) {
        return client.findAllTeachersPaged(name, pageable);
    }

    public void insertTeacherToSchool(Integer schoolId, Integer teacherId) {
        client.insertTeacherToSchool(schoolId, teacherId);
    }

    public void removeTeacherFromSchool(Integer teacherId) {
        client.removeTeacherFromSchool(teacherId);
    }

    public Teacher findTeacherById(Integer teacherId) {
        return client.findTeacherById(teacherId);
    }

    public void saveTeacher(Teacher teacher) {
        client.save(teacher);
    }

    public void deleteTeacher(Integer teacherId) {
        schoolClient.removeTeacherFromAllClasses(teacherId);
        client.delete(teacherId);
    }
}
