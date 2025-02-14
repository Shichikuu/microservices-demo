package com.alibou.school.repository;

import com.alibou.common.model.School;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Integer> {
    Page<School> findAllByNameContainingIgnoreCase(String name, org.springframework.data.domain.Pageable pageable);
}
