package com.alibou.course.repository;

import com.alibou.common.model.CourseScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseScoreRepository extends JpaRepository<CourseScore, Integer> {
    Page<CourseScore> findByStudent_Id(Integer studentId, Pageable pageable);
    Page<CourseScore> findByStudent_IdAndCourse_NameContainingIgnoreCase(Integer studentId, String courseName, Pageable pageable);

    List<CourseScore> findByStudent_IdAndCourse_IdAndYearAndSemester(Integer id, Integer courseId, Integer year, String semester);
}
