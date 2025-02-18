package com.alibou.frontend.service;

import com.alibou.common.dto.StudentFullResponse;
import com.alibou.common.model.Student;
import com.alibou.frontend.client.SchoolClient;
import com.alibou.frontend.client.StudentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final SchoolClient schoolClient;
    private final StudentClient studentClient;

    public List<StudentFullResponse> getAllStudents() {
        return schoolClient.getAllFullStudentResponse();
    }

    public void saveStudent(Student student) {
        studentClient.saveStudent(student);
    }

    public Page<Student> findAllStudentsByName(String name, Pageable pageable) {
        Page<Student> students = studentClient.search(name, pageable);
        return students;
    }

    public Page<Student> findStudentsBySchoolAndName(Integer schoolId, String name, Pageable pageable) {
        return studentClient.findStudentsBySchoolAndName(schoolId, name, pageable);
    }

    public Page<Student> findStudentsBySchool(Integer schoolId, Pageable pageable) {
        return studentClient.findStudentsBySchool(schoolId, pageable);
    }

    public Student findStudentById(Integer studentId) {
        return studentClient.findStudentById(studentId);
    }
}
