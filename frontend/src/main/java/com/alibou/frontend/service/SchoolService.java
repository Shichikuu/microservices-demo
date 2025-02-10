package com.alibou.frontend.service;

import com.alibou.frontend.client.SchoolClient;
import com.alibou.school.model.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolClient schoolClient;

    public List<School> getAllSchools() {
        return schoolClient.findAllSchools();
    }

    public School getSchoolById(Integer id) {
        return schoolClient.getSchoolById(id);
    }

    public void saveSchool(School school) {
        schoolClient.save(school);
    }

    public void updateSchool(Integer id, School school) {
        schoolClient.updateSchool(id, school);
    }

    public void deleteSchool(Integer id) {
        schoolClient.deleteSchool(id);
    }
}
