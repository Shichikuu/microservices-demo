package com.alibou.school.repository;

import com.alibou.common.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    public List<Event> findAllBySchoolId(Integer schoolId);
}
