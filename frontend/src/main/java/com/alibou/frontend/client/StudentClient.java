package com.alibou.frontend.client;


import com.alibou.common.config.FeignConfig;
import com.alibou.common.model.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "student-service", url = "${application.config.students-url}", configuration = {FeignConfig.class})
public interface StudentClient {

    @GetMapping("/schoolId={school-id}/students")
    List<Student> findAllStudentsBySchool(@PathVariable("school-id") Integer schoolId);

    @GetMapping("/classroomId={classroom-id}")
    List<Student> findAllStudentsByClassroomId(@PathVariable("classroom-id") Integer classroomId);

    @PostMapping("/insert/classroomId={classroom-id}/studentId={student-id}")
    public void insertStudentToClassroom(@PathVariable("classroom-id") Integer classroomId, @PathVariable("student-id") Integer studentId);

    @PostMapping("/remove/classroomId={classroom-id}/studentId={student-id}")
    public void removeStudentFromClassroom(@PathVariable("classroom-id") Integer classroomId, @PathVariable("student-id") Integer studentId);

    @PostMapping("/insert/schoolId={school-id}/studentId={student-id}")
    public void insertStudentToSchool(@PathVariable("school-id") Integer schoolId, @PathVariable("student-id") Integer studentId);

    @PostMapping("/remove/schoolId={school-id}/studentId={student-id}")
    public void removeStudentFromSchool(@PathVariable("school-id") Integer schoolId, @PathVariable("student-id") Integer studentId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void saveStudent(@RequestBody Student student);

    @GetMapping("/search")
    public Page<Student> search(@RequestParam("name") String name, @PageableDefault(size = 10) Pageable pageable);
}
