package com.alibou.school;

import com.alibou.school.client.StudentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final StudentClient client;

    public void saveSchool(School student) {
        schoolRepository.save(student);
    }

    public List<School> findAllSchools() {
        return schoolRepository.findAll();
    }


    public FullSchoolResponse findSchoolWithStudents(Integer schoolId) {
        var school = schoolRepository.findById(schoolId).orElse(School.builder().name("NOT FOUND").email("NOT FOUND").build());
        var students = client.findAllStudentsBySchool(schoolId);
        return FullSchoolResponse.builder().name(school.getName()).email(school.getEmail()).students(students).build();
    }
}
