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
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "school_id", insertable = false, updatable = false)
//    private School school;
    private Integer schoolId;

//    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
//    private List<Classroom> classrooms;
}
