package com.alibou.common.dto;

import com.alibou.common.model.Event;
import com.alibou.common.model.Student;
import com.alibou.common.model.Teacher;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullSchoolResponse {

    private String name;
    private String email;

    List<Student> students;
    List<ClassroomDTO> classrooms;
    List<Teacher> teachers;
    List<Event> events;
}
