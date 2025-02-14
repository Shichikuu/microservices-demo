package com.alibou.student;

import com.alibou.common.dto.CourseScoreResponse;
import com.alibou.common.dto.StudentReportDTO;
import com.alibou.common.model.CourseScore;
import com.alibou.common.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Student student) {
        service.saveStudent(student);
    }

    @PostMapping("/insert/classroomId={classroom-id}/studentId={student-id}")
    public void insertStudentToClassroom(@PathVariable("classroom-id") Integer classroomId, @PathVariable("student-id") Integer studentId) {
        service.insertStudentToClassroom(classroomId, studentId);
    }

    @PostMapping("/remove/classroomId={classroom-id}/studentId={student-id}")
    public void removeStudentFromClassroom(@PathVariable("classroom-id") Integer classroomId, @PathVariable("student-id") Integer studentId) {
        service.removeStudentFromClassroom(classroomId, studentId);
    }

    @PostMapping("/insert/schoolId={school-id}/studentId={student-id}")
    public void insertStudentToSchool(@PathVariable("school-id") Integer schoolId, @PathVariable("student-id") Integer studentId) {
        service.insertStudentToSchool(schoolId, studentId);
    }

    @PostMapping("/remove/schoolId={school-id}/studentId={student-id}")
    public void removeStudentFromSchool(@PathVariable("school-id") Integer schoolId, @PathVariable("student-id") Integer studentId) {
        service.removeStudentFromSchool(schoolId, studentId);
    }

    @GetMapping
    public ResponseEntity<List<Student>> findAllStudents() {
        return ResponseEntity.ok(service.findAllStudents());
    }

    @GetMapping("/{student-id}")
    public ResponseEntity<Student> findStudentById(@PathVariable("student-id") Integer studentId) {
        return ResponseEntity.ok(service.findStudentById(studentId));
    }

    @GetMapping("/schoolId={school-id}/students")
    public ResponseEntity<List<Student>> findAllStudents(@PathVariable("school-id") Integer schoolId) {
        return ResponseEntity.ok(service.findAllStudentsBySchool(schoolId));
    }

    @GetMapping("/report/studentId={student-id}")
    public ResponseEntity<StudentReportDTO> findStudentReport(@PathVariable("student-id") Integer studentId) {
        return ResponseEntity.ok(service.findStudentReport(studentId));
    }

    @GetMapping("/classroomId={classroom-id}")
    public ResponseEntity<List<Student>> findAllStudentByClassroomId(@PathVariable("classroom-id") Integer classroomId) {
        return ResponseEntity.ok(service.findAllStudentByClassroomId(classroomId));
    }

    @GetMapping("/score/courseId={course-id}/studentId={student-id}")
    public ResponseEntity<CourseScoreResponse> findCourseScore(@PathVariable("course-id") Integer courseId, @PathVariable("student-id") Integer studentId){
        return ResponseEntity.ok(service.findCourseScore(courseId, studentId));
    }

    @GetMapping("/studentId={student-id}")
    public ResponseEntity<List<CourseScore>> findCourseScoreByStudentId(@PathVariable("student-id") Integer studentId){
        return ResponseEntity.ok(service.findCourseScoreByStudentId(studentId));
    }

    @PostMapping("/save-score")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCourseScore(@RequestBody CourseScore courseScore) {
        service.saveCourseScore(courseScore);
    }

    @PostMapping("/removeAll/schoolId={school-id}")
    void removeAllStudentsBySchool(@PathVariable("school-id") Integer schoolId){
        service.removeAllStudentsBySchool(schoolId);
    }

    @GetMapping("/search")
    public Page<Student> search(@RequestParam("name") String name, @PageableDefault(size = 10) Pageable pageable) {
        return service.findAllStudentsByName(name, pageable);
    }
}
