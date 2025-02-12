package com.alibou.student.repository;

import com.alibou.common.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAllBySchoolId(Integer schoolId);
    List<Student> findAllByClassroomId(Integer classroomId);
}
