package com.alibou.course.repository;

import com.alibou.common.model.CourseScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseScoreRepository extends JpaRepository<CourseScore, Integer> {
    Page<CourseScore> findByStudentId(Integer studentId, Pageable pageable);
    Page<CourseScore> findByStudentIdAndCourse_NameContainingIgnoreCase(Integer studentId, String courseName, Pageable pageable);
}
