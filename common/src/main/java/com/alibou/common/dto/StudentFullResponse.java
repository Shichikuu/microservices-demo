package com.alibou.common.dto;

import com.alibou.common.model.Classroom;
import com.alibou.common.model.School;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentFullResponse {

    private Integer id;
    private String name;
    private String email;
    private Integer schoolId;
    private School school;
    private Integer classroomId;
    private Classroom classroom;

}
