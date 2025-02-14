package com.alibou.school;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schools/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @PostMapping("/insert/classroomId={classroom-id}/studentId={student-id}")
    public void insertStudentToClassroom(@PathVariable("classroom-id") Integer classroomId, @PathVariable("student-id") Integer studentId) {
        service.insertStudentToClassroom(classroomId, studentId);
    }

    @PostMapping("/remove/classroomId={classroom-id}/studentId={student-id}")
    public void removeStudentFromClassroom(@PathVariable("classroom-id") Integer classroomId, @PathVariable("student-id") Integer studentId) {
        service.removeStudentFromClassroom(classroomId, studentId);
    }

    @PostMapping("/insert/schoolId={school-id}/studentId={student-id}")
    public void insertStudentToSchool(@PathVariable("school-id") Integer schoolId, @PathVariable("student-id") Integer studentId) {
        service.insertStudentToSchool(schoolId, studentId);
    }

    @PostMapping("/remove/schoolId={school-id}/studentId={student-id}")
    public void removeStudentFromSchool(@PathVariable("school-id") Integer schoolId, @PathVariable("student-id") Integer studentId) {
        service.removeStudentFromSchool(schoolId, studentId);
    }

    @PostMapping("/removeAll/schoolId={school-id}")
    void removeAllStudentsBySchool(@PathVariable("school-id") Integer schoolId){
        service.removeAllStudentsBySchool(schoolId);
    }
}
