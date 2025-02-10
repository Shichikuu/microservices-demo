package com.alibou.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

    public List<Teacher> findAllTeachersBySchoolId(Integer schoolId) {
        return teacherRepository.findAllBySchoolId(schoolId);
    }

    public Teacher findTeacherByClassroomId(Integer classroomId) {
        return teacherRepository.findByClassroomId(classroomId);
    }

}
