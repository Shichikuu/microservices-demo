package com.alibou.course;

import com.alibou.common.model.Course;
import com.alibou.common.model.CourseScore;
import com.alibou.course.repository.CourseRepository;
import com.alibou.course.repository.CourseScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseScoreRepository courseScoreRepository;

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(Integer courseId) {
        return courseRepository.findById(courseId).orElse(Course.builder().name("NOT FOUND").build());
    }

    public Page<CourseScore> findCourseScoresByStudentId(Integer studentId, Pageable pageable) {
        return courseScoreRepository.findByStudentId(studentId, pageable);
    }

    public Page<CourseScore> findCourseScoresByStudentIdAndCourseName(Integer studentId, Pageable pageable, String courseName) {
        return courseScoreRepository.findByStudentIdAndCourse_NameContainingIgnoreCase(studentId, courseName, pageable);
    }
}
