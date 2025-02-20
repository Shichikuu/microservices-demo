package com.alibou.teacher;

import com.alibou.common.model.School;
import com.alibou.common.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<Teacher> findTeachersBySchoolId(Integer schoolId) {
        return teacherRepository.findAllBySchoolId(schoolId);
    }

    public Page<Teacher> findAllTeachersBySchoolId(Integer schoolId, Pageable pageable) {
        return teacherRepository.findAllBySchoolId(schoolId, pageable);
    }

    public Teacher findTeacherById(Integer teacherId) {
        return teacherRepository.findById(teacherId).orElse(null);
    }

    public void removeAllTeachersBySchool(Integer schoolId) {
        List<Teacher> teachers = teacherRepository.findAllBySchoolId(schoolId);
        for(Teacher teacher: teachers){
            teacher.setSchool(null);
            teacherRepository.save(teacher);
        }
    }

    public Page<Teacher> findAllTeachersPaged(String name, Pageable pageable) {
        return teacherRepository.findAllByNameContainingIgnoreCase(name, pageable);
    }

    public void insertTeacherToSchool(Integer schoolId, Integer teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if(teacher != null){
            if(teacher.getSchool() != null && teacher.getSchool().getId() == schoolId){
                throw new IllegalArgumentException("Teacher already in this school");
            }
            teacher.setSchool(School.builder().id(schoolId).build());
            teacherRepository.save(teacher);
        }
    }

    public void removeTeacherFromSchool(Integer teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if(teacher != null){
            teacher.setSchool(null);
            teacherRepository.save(teacher);
        }
    }

    public void delete(Integer teacherId) {
        teacherRepository.deleteById(teacherId);
    }
}
