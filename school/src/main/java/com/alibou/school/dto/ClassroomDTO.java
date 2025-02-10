package com.alibou.school.dto;

import com.alibou.student.model.Student;
import com.alibou.teacher.Teacher;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomDTO {
    private Integer id;
    private String name;
    private String schoolName;
    private List<Student> students;
    private Teacher assignedTeacher;
}
