package com.alibou.school;

import com.alibou.school.dto.ClassroomDTO;
import com.alibou.school.dto.FullSchoolResponse;
import com.alibou.school.model.Classroom;
import com.alibou.school.model.School;
import com.alibou.student.model.Student;
import com.alibou.teacher.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody School school) {
        service.saveSchool(school);
    }

    @PostMapping("/create-classroom")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Classroom classroom) {
        service.saveClassroom(classroom);
    }

    @GetMapping
    public ResponseEntity<List<School>> findAllSchools() {
        return ResponseEntity.ok(service.findAllSchools());
    }

    @GetMapping("/full/schoolId={school-id}")
    public ResponseEntity<FullSchoolResponse> findFullSchoolById(@PathVariable("school-id") Integer schoolId) {
        return ResponseEntity.ok(service.findSchoolById(schoolId));
    }

    @GetMapping("/schoolId={school-id}")
    public ResponseEntity<School> findSchoolById(@PathVariable("school-id") Integer schoolId) {
        return ResponseEntity.ok(service.getSchoolById(schoolId));
    }

    @GetMapping("/schoolId={school-id}/students")
    public ResponseEntity<List<Student>> findAllStudentsBySchool(@PathVariable("school-id") Integer schoolId) {
        return ResponseEntity.ok(service.findAllStudentsBySchool(schoolId));
    }

    @GetMapping("/schoolId={school-id}/teachers")
    public ResponseEntity<List<Teacher>> findAllTeachersBySchool(@PathVariable("school-id") Integer schoolId) {
        return ResponseEntity.ok(service.findAllTeachersBySchool(schoolId));
    }

    @GetMapping("/classroomId={classroom-id}")
    public ResponseEntity<ClassroomDTO> findClassroomById(@PathVariable("classroom-id") Integer classroomId) {
        return ResponseEntity.ok(service.findClassroomById(classroomId));
    }

    @GetMapping("/classrooms")
    public ResponseEntity<List<ClassroomDTO>> findAllClassrooms() {
        return ResponseEntity.ok(service.findAllClassrooms());
    }

    @DeleteMapping("/delete/schoolId={school-id}")
    public void deleteSchool(@PathVariable("school-id") Integer schoolId) {
        service.deleteSchool(schoolId);
    }

    @PutMapping("/update/schoolId={school-id}")
    public void updateSchool(@PathVariable("school-id") Integer schoolId, @RequestBody School school) {
        service.updateSchool(schoolId, school);
    }

}
