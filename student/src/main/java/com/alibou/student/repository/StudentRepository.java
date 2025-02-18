package com.alibou.student.repository;

import com.alibou.common.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAllBySchoolId(Integer schoolId);
    List<Student> findAllByClassroomId(Integer classroomId);
    Page<Student> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Student> findAllBySchoolId(Integer id, Pageable pageable);
    Page<Student> findAllBySchoolIdAndNameContainingIgnoreCase(Integer id, String name, Pageable pageable);
}
