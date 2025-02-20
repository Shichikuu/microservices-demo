package com.alibou.teacher;

import com.alibou.common.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Teacher teacher) {
        service.saveTeacher(teacher);
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> findAllTeachers() {
        return ResponseEntity.ok(service.findAllTeachers());
    }

    @GetMapping("/all-paged")
    public Page<Teacher> findAllTeachersPaged(@RequestParam("name") String name, Pageable pageable){
        return service.findAllTeachersPaged(name, pageable);
    }

    @GetMapping("/{school-id}/teachers")
    public ResponseEntity<List<Teacher>> findTeachersBySchool(@PathVariable("school-id") Integer schoolId) {
        return ResponseEntity.ok(service.findTeachersBySchoolId(schoolId));
    }

    @GetMapping("/schoolId={school-id}/teachers")
    public ResponseEntity<Page<Teacher>> findAllTeachersBySchool(@PathVariable("school-id") Integer schoolId, Pageable pageable) {
        return ResponseEntity.ok(service.findAllTeachersBySchoolId(schoolId, pageable));
    }

    @GetMapping("/{teacher-id}")
    public ResponseEntity<Teacher> findTeacherById(@PathVariable("teacher-id") Integer teacherId) {
        return ResponseEntity.ok(service.findTeacherById(teacherId));
    }

    @PostMapping("/removeAll/schoolId={school-id}")
    void removeAllTeachersBySchool(@PathVariable("school-id") Integer schoolId){
        service.removeAllTeachersBySchool(schoolId);
    }

    @PostMapping("/insert-teacher")
    void insertTeacherToSchool(@RequestParam Integer schoolId, @RequestParam Integer teacherId){
        service.insertTeacherToSchool(schoolId, teacherId);
    }

    @PostMapping("/remove-teacher")
    void removeTeacherFromSchool(@RequestParam Integer teacherId){
        service.removeTeacherFromSchool(teacherId);
    }

    @PostMapping("/delete")
    void delete(@RequestParam Integer teacherId){
        service.delete(teacherId);
    }

}
