package com.alibou.course;

import com.alibou.course.model.Course;
import lombok.RequiredArgsConstructor;
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
}
