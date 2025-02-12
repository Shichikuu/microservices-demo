package com.alibou.school.repository;

import com.alibou.common.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    public List<Classroom> findAllBySchoolId(Integer schoolId);
    public void deleteAllBySchoolId(Integer schoolId);
}
