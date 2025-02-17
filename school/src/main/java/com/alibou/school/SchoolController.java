package com.alibou.school;

import com.alibou.common.dto.ClassroomDTO;
import com.alibou.common.dto.FullSchoolResponse;
import com.alibou.common.dto.StudentFullResponse;
import com.alibou.common.model.Classroom;
import com.alibou.common.model.School;
import com.alibou.common.model.Student;
import com.alibou.common.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/search")
    public Page<School> search(@RequestParam("name") String name, Pageable pageable) {
        return service.search(name, pageable);
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

//    @GetMapping("/classroomId={classroom-id}")
//    public ResponseEntity<ClassroomDTO> findClassroomById(@PathVariable("classroom-id") Integer classroomId) {
//        return ResponseEntity.ok(service.findClassroomById(classroomId));
//    }

    @GetMapping("/classroomId={classroom-id}")
    public ResponseEntity<Classroom> findClassroomById(@PathVariable("classroom-id") Integer classroomId) {
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

    @PostMapping("/assign/{teacher-id}/to/{classroom-id}")
    public void assignTeacherToClassroom(@PathVariable("teacher-id") Integer teacherId, @PathVariable("classroom-id") Integer classroomId) {
        try{
            service.assignTeacherToClassroom(teacherId, classroomId);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @DeleteMapping("/delete/classroom/{classroom-id}")
    public void deleteClassroom(@PathVariable("classroom-id") Integer classroomId) {
        service.deleteClassroom(classroomId);
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentFullResponse>> getAllStudentFullResponse() {
        return ResponseEntity.ok(service.getAllStudentFullResponse());
    }

    @GetMapping("/search-students")
    public Page<StudentFullResponse> searchStudents(@RequestParam("name") String name, Pageable pageable) {
        return service.searchStudents(name, pageable);
    }

}
