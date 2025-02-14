package com.alibou.common.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    private Integer schoolId;
    private Integer teacherId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "school_id", insertable = false, updatable = false)
//    private School school;
//
//    // One classroom has many students
//    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
//    private List<Student> students;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
//    private Teacher teacher;
}
