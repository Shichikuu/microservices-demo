package com.alibou.course;

import com.alibou.common.model.Course;
import com.alibou.common.model.CourseScore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService service;

    @PostMapping
    public void saveCourse(@RequestBody Course course) {
        service.saveCourse(course);
    }

    @GetMapping
    public ResponseEntity<List<Course>> findAllCourses() {
        return ResponseEntity.ok(service.findAllCourses());
    }

    @GetMapping("/courseId={course-id}")
    public ResponseEntity<Course> findCourseById(@PathVariable("course-id") Integer courseId) {
        return ResponseEntity.ok(service.findCourseById(courseId));
    }

    @GetMapping("/studentId={student-id}")
    public Page<CourseScore> findCourseScoresByStudentId(@PathVariable("student-id") Integer studentId, Pageable pageable){
        return service.findCourseScoresByStudentId(studentId, pageable);
    }

    @GetMapping("/studentId={student-id}/courseName={course-name}")
    public Page<CourseScore> findCourseScoresByStudentIdAndCourseName(@PathVariable("student-id") Integer studentId, Pageable pageable, @PathVariable("course-name") String courseName){
        return service.findCourseScoresByStudentIdAndCourseName(studentId, pageable, courseName);
    }

    @PostMapping("/insert-score")
    public void saveCourseScore(@RequestBody CourseScore courseScore) {
        service.saveCourseScore(courseScore);
    }
}
