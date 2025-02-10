package com.alibou.student.dto;

import com.alibou.student.model.Student;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentReportDTO {
    private Student student;
    private List<CourseScoreResponse> courseScoreResponses;
}
