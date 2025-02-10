package com.alibou.student.client;

import com.alibou.course.model.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "courses-service", url = "${application.config.courses-url}")
public interface CourseClient {

    @GetMapping("/courseId={course-id}")
    public Course findCourseById(@PathVariable("course-id") Integer courseId);

}
