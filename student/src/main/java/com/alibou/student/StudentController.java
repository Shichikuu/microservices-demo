package com.alibou.student;

import com.alibou.student.dto.CourseScoreResponse;
import com.alibou.student.dto.StudentReportDTO;
import com.alibou.student.model.CourseScore;
import com.alibou.student.model.Student;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public ResponseEntity<List<Student>> findAllStudents() {
        return ResponseEntity.ok(service.findAllStudents());
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
}
