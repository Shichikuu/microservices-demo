package com.alibou.common.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(
        scope = School.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;


    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY)
    private List<Student> students;


    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY)
    private List<Teacher> teachers;


    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY)
    private List<Classroom> classrooms;
}
