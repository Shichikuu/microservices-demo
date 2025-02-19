package com.alibou.frontend.service;

import com.alibou.common.model.Course;
import com.alibou.common.model.CourseScore;
import com.alibou.frontend.client.CourseClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseClient courseClient;
    public Page<CourseScore> findCourseScoresByStudentId(Integer studentId, Pageable pageable) {
        return courseClient.findCourseScoresByStudentId(studentId, pageable);
    }

    public Page<CourseScore> findCourseScoresByStudentIdAndCourseName(Integer studentId, Pageable pageable, String courseName) {
        return courseClient.findCourseScoresByStudentIdAndCourseName(studentId, pageable, courseName);
    }

    public List<Course> findAllCourses() {
        return courseClient.findAllCourses();
    }

    public void saveCourseScore(CourseScore courseScore) {
        courseClient.saveCourseScore(courseScore);
    }
}
