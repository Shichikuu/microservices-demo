package com.alibou.school;

import com.alibou.common.model.Student;
import com.alibou.school.client.StudentClient;
import com.alibou.school.repository.ClassroomRepository;
import com.alibou.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentClient client;
    private final ClassroomRepository classroomRepository;
    private final SchoolRepository schoolRepository;

    public void insertStudentToClassroom(Integer classroomId, Integer studentId) {
        Student student = client.findStudentById(studentId);
        if(student.getClassroom() != null && student.getClassroom().getId().equals(classroomId)){
            throw new IllegalArgumentException("Student already in this classroom");
        }
        student.setClassroom(classroomRepository.findById(classroomId).orElseThrow(() -> new IllegalArgumentException("Classroom not found")));
        client.save(student);
    }

    public void removeStudentFromClassroom(Integer classroomId, Integer studentId) {
        Student student = client.findStudentById(studentId);
        if (student.getClassroom() == null || !student.getClassroom().getId().equals(classroomId)) {
            throw new IllegalArgumentException("Student not in this classroom");
        }
        student.setClassroom(null);
        client.save(student);
    }


    public void insertStudentToSchool(Integer schoolId, Integer studentId) {
        Student student = client.findStudentById(studentId);
        if(student.getSchool() != null && student.getSchool().getId().equals(schoolId)){
            throw new IllegalArgumentException("Student already in this school");
        }
        student.setSchool(schoolRepository.findById(schoolId).orElseThrow(() -> new IllegalArgumentException("School not found")));
        client.save(student);
    }

    public void removeStudentFromSchool(Integer schoolId, Integer studentId) {
        Student student = client.findStudentById(studentId);
        if (student.getSchool() == null || !student.getSchool().getId().equals(schoolId)) {
            throw new IllegalArgumentException("Student not in this school");
        }
        student.setSchool(null);
        student.setClassroom(null);
        client.save(student);
    }

    public void removeAllStudentsBySchool(Integer schoolId) {
        List<Student> students = client.findAllStudentsBySchool(schoolId);
        for (Student student : students) {
            removeStudentFromSchool(schoolId, student.getId());
        }
    }
}
