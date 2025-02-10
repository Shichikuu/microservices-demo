package com.alibou.student.dto;

import com.alibou.course.model.Course;
import com.alibou.student.model.Student;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseScoreResponse {

    private Course course;
    private Student student;
    private Integer score;
}
