package com.alibou.teacher;

import com.alibou.common.model.Teacher;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/schoolId={school-id}/teachers")
    public ResponseEntity<List<Teacher>> findAllTeachersBySchool(@PathVariable("school-id") Integer schoolId) {
        return ResponseEntity.ok(service.findAllTeachersBySchoolId(schoolId));
    }

    @GetMapping("/{teacher-id}")
    public ResponseEntity<Teacher> findTeacherById(@PathVariable("teacher-id") Integer teacherId) {
        return ResponseEntity.ok(service.findTeacherById(teacherId));
    }

    @PostMapping("/removeAll/schoolId={school-id}")
    void removeAllTeachersBySchool(@PathVariable("school-id") Integer schoolId){
        service.removeAllTeachersBySchool(schoolId);
    }
}
