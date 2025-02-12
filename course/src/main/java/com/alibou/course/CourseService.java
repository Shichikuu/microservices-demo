package com.alibou.course;

import com.alibou.common.model.Course;
import com.alibou.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(Integer courseId) {
        return courseRepository.findById(courseId).orElse(Course.builder().name("NOT FOUND").build());
    }

}
