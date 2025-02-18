package com.alibou.frontend.client;

import com.alibou.common.config.FeignConfig;
import com.alibou.common.dto.ClassroomDTO;
import com.alibou.common.dto.FullSchoolResponse;
import com.alibou.common.dto.StudentFullResponse;
import com.alibou.common.model.Classroom;
import com.alibou.common.model.School;
import com.alibou.common.model.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "school-service", url = "${application.config.schools-url}", configuration = {FeignConfig.class})
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

    @GetMapping("/classroomId={id}")
    public Classroom getClassroomById(@PathVariable("id") Integer id);

//    @GetMapping("/classroomId={id}")
//    public ClassroomDTO getClassroomById(@PathVariable("id") Integer id);

    @GetMapping("/full/schoolId={school-id}")
    public FullSchoolResponse findFullSchoolById(@PathVariable("school-id") Integer schoolId);

    @PostMapping("/create-classroom")
    public void saveClassroom(@RequestBody Classroom classroom);

    @DeleteMapping("/delete/classroom/{classroom-id}")
    public void deleteClassroom(@PathVariable("classroom-id") Integer classroomId);

    @PostMapping("/assign/{teacher-id}/to/{classroom-id}")
    void assignTeacherToClassroom(@PathVariable("teacher-id") Integer teacherId, @PathVariable("classroom-id") Integer classroomId);

    @GetMapping("/students")
    public List<StudentFullResponse> getAllFullStudentResponse();

    @GetMapping("/search")
    Page<School> search(@RequestParam("name") String name, Pageable pageable);

    @GetMapping("/search-students")
    public Page<StudentFullResponse> searchStudents(@RequestParam("name") String name, Pageable pageable);
}
