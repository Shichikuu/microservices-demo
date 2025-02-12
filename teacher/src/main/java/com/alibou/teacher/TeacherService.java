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

    public Teacher findTeacherByClassroomId(Integer classroomId) {
        return teacherRepository.findByClassroomId(classroomId);
    }

    public void assignTeacherToClassroom(Integer teacherId, Integer classroomId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
        if(teacher.getClassroomId() != null) {
            throw new IllegalArgumentException("Teacher already assigned to a classroom");
        }
        teacher.setClassroomId(classroomId);
        teacherRepository.save(teacher);
    }

}
