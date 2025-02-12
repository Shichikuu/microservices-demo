package com.alibou.common.model;

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
public class Classroom {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer schoolId;
}
