package com.alibou.common.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @ManyToOne
//    @JoinColumn(name = "course_id")
//    private Course course;
    private Integer courseId;
//    @ManyToOne
//    @JoinColumn(name = "student_id")
//    private Student student;
    private Integer studentId;
    private Integer score;
}

