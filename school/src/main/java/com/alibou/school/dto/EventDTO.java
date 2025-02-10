package com.alibou.school.dto;

import com.alibou.student.model.Student;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO {
    private String name;
    private String description;
    private List<Student> students;
}
