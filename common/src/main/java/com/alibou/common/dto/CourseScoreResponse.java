package com.alibou.common.dto;

import com.alibou.common.model.Course;
import com.alibou.common.model.Student;
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
