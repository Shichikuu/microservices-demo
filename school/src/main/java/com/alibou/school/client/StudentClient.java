package com.alibou.school.client;

import com.alibou.common.model.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "student-service", url = "${application.config.students-url}")
public interface StudentClient {

    @GetMapping("/schoolId={school-id}/students")
    List<Student> findAllStudentsBySchool(@PathVariable("school-id") Integer schoolId);

    @GetMapping("/classroomId={classroom-id}")
    List<Student> findAllStudentsByClassroomId(@PathVariable("classroom-id") Integer classroomId);

    @PostMapping
    void saveStudent(@RequestBody Student student);

    @PostMapping("/removeAll/schoolId={school-id}")
    void removeAllStudentsBySchool(@PathVariable("school-id") Integer schoolId);

    @GetMapping("/{student-id}")
    Student findStudentById(@PathVariable("student-id") Integer studentId);

    @GetMapping
    List<Student> findAllStudents();

}
