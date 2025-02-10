package com.alibou.school.dto;

import com.alibou.school.model.Classroom;
import com.alibou.school.model.Event;
import com.alibou.student.model.Student;
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
    List<Classroom> classrooms;
    List<Event> events;
}
