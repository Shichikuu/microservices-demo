package com.alibou.common.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
//    @ManyToOne
//    @JoinColumn(name = "school_id", insertable = false, updatable = false)
//    private School school;
    private Integer schoolId;
}
