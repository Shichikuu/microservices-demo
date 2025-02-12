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
public class CourseScore {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer courseId;
    private Integer studentId;
    private Integer score;
}

