package com.alibou.course.repository;

import com.alibou.common.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, Integer> {

}
