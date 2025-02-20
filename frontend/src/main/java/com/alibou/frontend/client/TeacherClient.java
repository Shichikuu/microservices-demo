package com.alibou.frontend.client;

import com.alibou.common.config.CustomFeignErrorDecoder;
import com.alibou.common.config.FeignConfig;
import com.alibou.common.model.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "teacher-service", url = "${application.config.teachers-url}", configuration = {FeignConfig.class})
public interface TeacherClient {
    @GetMapping("/schoolId={school-id}/teachers")
    Page<Teacher> findAllTeachersBySchool(@PathVariable("school-id") Integer schoolId, Pageable pageable);

    @GetMapping("/classroomId={classroom-id}")
    Teacher findTeacherByClassroomId(@PathVariable("classroom-id") Integer classroomId);

    @GetMapping("/all-paged")
    Page<Teacher> findAllTeachersPaged(@RequestParam("name") String name, Pageable pageable);

    @PostMapping("/insert-teacher")
    void insertTeacherToSchool(@RequestParam Integer schoolId, @RequestParam Integer teacherId);

    @PostMapping("/remove-teacher")
    void removeTeacherFromSchool(@RequestParam Integer teacherId);

    @GetMapping("/{teacher-id}")
    public Teacher findTeacherById(@PathVariable("teacher-id") Integer teacherId);

    @PostMapping
    void save(@RequestBody Teacher teacher);

    @PostMapping("/delete")
    void delete(@RequestParam Integer teacherId);
}
