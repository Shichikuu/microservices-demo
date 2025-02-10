package com.alibou.frontend.client;

import com.alibou.school.dto.ClassroomDTO;
import com.alibou.school.model.School;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "school-service", url = "${application.config.schools-url}")
public interface SchoolClient {
    @GetMapping
    public List<School> findAllSchools();

    @GetMapping("/schoolId={id}")
    public School getSchoolById(@PathVariable("id") Integer id);

    @PostMapping
    public void save(@RequestBody School school);

    @PutMapping("/update/schoolId={id}")
    public void updateSchool(@PathVariable("id") Integer id, @RequestBody School school);

    @DeleteMapping("/delete/schoolId={id}")
    public void deleteSchool(@PathVariable("id") Integer id);

    @GetMapping("/classrooms")
    public List<ClassroomDTO> findAllClassrooms();
}
