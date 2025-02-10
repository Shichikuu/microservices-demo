package com.alibou.teacher;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer schoolId;
    private Integer classroomId;
    private Integer courseId;
    private String name;
    private String email;
}
