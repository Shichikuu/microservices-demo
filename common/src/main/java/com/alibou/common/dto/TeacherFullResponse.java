package com.alibou.common.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherFullResponse {
    private Integer id;
    private Integer schoolId;
    private Integer classroomId;
    private Integer courseId;
    private String name;
    private String email;
}
