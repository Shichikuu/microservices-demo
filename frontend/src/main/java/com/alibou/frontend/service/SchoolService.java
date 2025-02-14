package com.alibou.frontend.service;

import com.alibou.frontend.client.SchoolClient;
import com.alibou.common.dto.FullSchoolResponse;
import com.alibou.common.model.School;
import com.alibou.frontend.client.SchoolStudentClient;
import com.alibou.frontend.client.StudentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolClient schoolClient;
    private final SchoolStudentClient schoolStudentClient;

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

    public FullSchoolResponse getFullSchoolResponseById(Integer id){ return schoolClient.findFullSchoolById(id); }

    public void insertStudentToSchool(Integer schoolId, Integer studentId) {
        schoolStudentClient.insertStudentToSchool(schoolId, studentId);
    }

    public void removeStudentFromSchool(Integer schoolId, Integer studentId) {
        schoolStudentClient.removeStudentFromSchool(schoolId, studentId);
    }

    public Page<School> findAllSchoolsByName(String name, Pageable pageable) {
        Page<School> schools = schoolClient.search(name, pageable);
        return schools;
    }
}
