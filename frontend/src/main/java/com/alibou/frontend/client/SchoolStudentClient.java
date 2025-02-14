package com.alibou.frontend.client;

import com.alibou.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "school-student-service", url = "${application.config.schools-students-url}", configuration = {FeignConfig.class})
public interface SchoolStudentClient {
    @PostMapping("/insert/classroomId={classroom-id}/studentId={student-id}")
    public void insertStudentToClassroom(@PathVariable("classroom-id") Integer classroomId, @PathVariable("student-id") Integer studentId);

    @PostMapping("/remove/classroomId={classroom-id}/studentId={student-id}")
    public void removeStudentFromClassroom(@PathVariable("classroom-id") Integer classroomId, @PathVariable("student-id") Integer studentId);

    @PostMapping("/insert/schoolId={school-id}/studentId={student-id}")
    public void insertStudentToSchool(@PathVariable("school-id") Integer schoolId, @PathVariable("student-id") Integer studentId);

    @PostMapping("/remove/schoolId={school-id}/studentId={student-id}")
    public void removeStudentFromSchool(@PathVariable("school-id") Integer schoolId, @PathVariable("student-id") Integer studentId);
}
