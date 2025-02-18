package com.alibou.frontend.client;

import com.alibou.common.config.CustomFeignErrorDecoder;
import com.alibou.common.config.FeignConfig;
import com.alibou.common.model.Course;
import com.alibou.common.model.CourseScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        name = "courses-service",
        url = "${application.config.courses-url}",
        configuration = {FeignConfig.class}
)
public interface CourseClient {
    @GetMapping("/courseId={course-id}")
    Course findCourseById(@PathVariable("course-id") Integer courseId);

    @GetMapping("/studentId={student-id}")
    Page<CourseScore> findCourseScoresByStudentId(@PathVariable("student-id") Integer studentId, Pageable pageable);

    @GetMapping("/studentId={student-id}/courseName={course-name}")
    Page<CourseScore> findCourseScoresByStudentIdAndCourseName(@PathVariable("student-id") Integer studentId, Pageable pageable, @PathVariable("course-name") String courseName);
}