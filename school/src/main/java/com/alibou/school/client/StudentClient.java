package com.alibou.school.client;

import com.alibou.common.model.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "student-service", url = "${application.config.students-url}")
public interface StudentClient {

    @GetMapping("/schoolId={school-id}/students")
    List<Student> findAllStudentsBySchool(@PathVariable("school-id") Integer schoolId);

    @GetMapping("/classroomId={classroom-id}")
    List<Student> findAllStudentsByClassroomId(@PathVariable("classroom-id") Integer classroomId);

    @PostMapping
    void save(@RequestBody Student student);

    @GetMapping("/{student-id}")
    Student findStudentById(@PathVariable("student-id") Integer studentId);

    @GetMapping
    List<Student> findAllStudents();

    @GetMapping("/search")
    Page<Student> search(@RequestParam("name") String name, @PageableDefault(size = 10) Pageable pageable);

}
