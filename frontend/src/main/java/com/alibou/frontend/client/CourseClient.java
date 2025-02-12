package com.alibou.frontend.client;

import com.alibou.common.config.CustomFeignErrorDecoder;
import com.alibou.common.config.FeignConfig;
import com.alibou.common.model.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "courses-service",
        url = "${application.config.courses-url}",
        configuration = {FeignConfig.class}
)
public interface CourseClient {
    @GetMapping({"/courseId={course-id}"})
    Course findCourseById(@PathVariable("course-id") Integer courseId);
}