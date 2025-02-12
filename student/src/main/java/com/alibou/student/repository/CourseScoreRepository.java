package com.alibou.student.repository;

import com.alibou.common.model.CourseScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseScoreRepository extends JpaRepository<CourseScore, Integer> {
    public CourseScore findByCourseIdAndStudentId(Integer courseId, Integer studentId);
    public List<CourseScore> findByStudentId(Integer studentId);
}
