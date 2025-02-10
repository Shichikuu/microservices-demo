package com.alibou.school;

import com.alibou.school.client.StudentClient;
import com.alibou.school.client.TeacherClient;
import com.alibou.school.dto.ClassroomDTO;
import com.alibou.school.dto.FullSchoolResponse;
import com.alibou.school.model.Classroom;
import com.alibou.school.model.School;
import com.alibou.school.repository.ClassroomRepository;
import com.alibou.school.repository.EventRepository;
import com.alibou.school.repository.SchoolRepository;
import com.alibou.student.model.Student;
import com.alibou.teacher.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final ClassroomRepository classroomRepository;
    private final EventRepository eventRepository;
    private final StudentClient studentClient;
    private final TeacherClient teacherClient;

    public void saveSchool(School student) {
        schoolRepository.save(student);
    }

    public List<School> findAllSchools() {
        return schoolRepository.findAll();
    }


    public FullSchoolResponse findSchoolById(Integer schoolId) {
        var school = schoolRepository.findById(schoolId).orElse(School.builder().name("NOT FOUND").email("NOT FOUND").build());
        var students = studentClient.findAllStudentsBySchool(schoolId);
        var classrooms = classroomRepository.findAllBySchoolId(schoolId);
        var events = eventRepository.findAllBySchoolId(schoolId);
        return FullSchoolResponse.builder().name(school.getName()).email(school.getEmail()).students(students).classrooms(classrooms).events(events).build();
    }

    public List<Student> findAllStudentsBySchool(Integer schoolId) {
        return studentClient.findAllStudentsBySchool(schoolId);
    }

    public List<Teacher> findAllTeachersBySchool(Integer schoolId){
        return teacherClient.findAllTeachersBySchool(schoolId);
    }

    public ClassroomDTO findClassroomById(Integer classroomId) {
        var cr = classroomRepository.findById(classroomId).orElse(Classroom.builder().name("NOT FOUND").schoolId(0).build());
        var t = teacherClient.findTeacherByClassroomId(cr.getId());
        var s = studentClient.findAllStudentsByClassroomId(cr.getId());
        return ClassroomDTO.builder().name(cr.getName()).assignedTeacher(t).students(s).build();
    }


    public void saveClassroom(Classroom classroom) {
        classroomRepository.save(classroom);
    }
}
