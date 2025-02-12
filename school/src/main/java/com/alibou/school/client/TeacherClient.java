package com.alibou.school.client;

import com.alibou.common.model.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "teacher-service", url = "${application.config.teachers-url}")
public interface TeacherClient {
    @GetMapping("/schoolId={school-id}/teachers")
    List<Teacher> findAllTeachersBySchool(@PathVariable("school-id") Integer schoolId);

    @GetMapping("/classroomId={classroom-id}")
    Teacher findTeacherByClassroomId(@PathVariable("classroom-id") Integer classroomId);

    @PutMapping("/assign/{teacher-id}/to/{classroom-id}")
    void assignTeacherToClassroom(@PathVariable("teacher-id") Integer teacherId, @PathVariable("classroom-id") Integer classroomId);
}
