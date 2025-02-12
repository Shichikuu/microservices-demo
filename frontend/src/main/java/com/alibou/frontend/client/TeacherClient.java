package com.alibou.frontend.client;

import com.alibou.common.config.CustomFeignErrorDecoder;
import com.alibou.common.config.FeignConfig;
import com.alibou.common.model.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "teacher-service", url = "${application.config.teachers-url}", configuration = {FeignConfig.class})
public interface TeacherClient {
    @GetMapping("/schoolId={school-id}/teachers")
    List<Teacher> findAllTeachersBySchool(@PathVariable("school-id") Integer schoolId);

    @GetMapping("/classroomId={classroom-id}")
    Teacher findTeacherByClassroomId(@PathVariable("classroom-id") Integer classroomId);
}
