package com.alibou.common.dto;

import com.alibou.common.model.Student;
import com.alibou.common.model.Teacher;
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
    private Integer schoolId;
    private Integer teacherId;
    private List<Student> students;
    private Teacher assignedTeacher;
}
