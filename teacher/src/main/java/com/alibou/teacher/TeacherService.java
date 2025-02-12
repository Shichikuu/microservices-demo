package com.alibou.teacher;

import com.alibou.common.model.Teacher;
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

    public Teacher findTeacherById(Integer teacherId) {
        return teacherRepository.findById(teacherId).orElse(null);
    }

    public void removeAllTeachersBySchool(Integer schoolId) {
        List<Teacher> teachers = teacherRepository.findAllBySchoolId(schoolId);
        for(Teacher teacher: teachers){
            teacher.setSchoolId(null);
            teacherRepository.save(teacher);
        }
    }
}
