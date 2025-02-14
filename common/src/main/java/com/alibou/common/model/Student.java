package com.alibou.common.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private Integer schoolId;
    private Integer classroomId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "school_id", insertable = false, updatable = false)
//    private School school;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "classroom_id", insertable = false, updatable = false)
//    private Classroom classroom;
}
