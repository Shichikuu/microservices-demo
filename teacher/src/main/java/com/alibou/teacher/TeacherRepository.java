package com.alibou.teacher;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    public List<Teacher> findAllBySchoolId(Integer schoolId);
    public Teacher findByClassroomId(Integer classroomId);
}
