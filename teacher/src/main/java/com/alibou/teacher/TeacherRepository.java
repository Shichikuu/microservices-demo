package com.alibou.teacher;

import com.alibou.common.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    public List<Teacher> findAllBySchoolId(Integer schoolId);
    public Page<Teacher> findAllBySchoolId(Integer schoolId, Pageable pageable);

    public Page<Teacher> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
