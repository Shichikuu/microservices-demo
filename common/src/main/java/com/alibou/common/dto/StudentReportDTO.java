package com.alibou.common.dto;

import com.alibou.common.model.Student;
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
