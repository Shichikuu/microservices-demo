package com.alibou.school;

import com.alibou.common.dto.StudentFullResponse;
import com.alibou.school.client.StudentClient;
import com.alibou.school.client.TeacherClient;
import com.alibou.common.dto.ClassroomDTO;
import com.alibou.common.dto.FullSchoolResponse;
import com.alibou.common.model.Classroom;
import com.alibou.common.model.School;
import com.alibou.school.repository.ClassroomRepository;
import com.alibou.school.repository.EventRepository;
import com.alibou.school.repository.SchoolRepository;
import com.alibou.common.model.Student;
import com.alibou.common.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        var classrooms = findAllFullClassroomsBySchoolId(schoolId);
        var events = eventRepository.findAllBySchoolId(schoolId);
        var teachers = teacherClient.findAllTeachersBySchool(schoolId);
        return FullSchoolResponse.builder().id(schoolId).name(school.getName()).teachers(teachers).email(school.getEmail()).students(students).classrooms(classrooms).events(events).build();
    }

    public List<Student> findAllStudentsBySchool(Integer schoolId) {
        return studentClient.findAllStudentsBySchool(schoolId);
    }

    public List<Teacher> findAllTeachersBySchool(Integer schoolId){
        return teacherClient.findAllTeachersBySchool(schoolId);
    }

    public ClassroomDTO findClassroomById(Integer classroomId) {
        var cr = classroomRepository.findById(classroomId).orElse(Classroom.builder().name("NOT FOUND").schoolId(0).build());
        if(cr.getTeacherId() == null){
            var s = studentClient.findAllStudentsByClassroomId(cr.getId());
            var sc = schoolRepository.findById(cr.getSchoolId()).orElse(School.builder().name("NOT FOUND").email("NOT FOUND").build());
            return ClassroomDTO.builder().schoolId(sc.getId()).id(cr.getId()).name(cr.getName()).schoolName(sc.getName()).students(s).build();
        }
        var t = teacherClient.findTeacherById(cr.getTeacherId());
        var s = studentClient.findAllStudentsByClassroomId(cr.getId());
        var sc = schoolRepository.findById(cr.getSchoolId()).orElse(School.builder().name("NOT FOUND").email("NOT FOUND").build());
        return ClassroomDTO.builder().schoolId(sc.getId()).id(cr.getId()).name(cr.getName()).schoolName(sc.getName()).teacherId(t.getId()).assignedTeacher(t).students(s).build();
    }

    public Classroom findClassById(Integer classroomId) {
        return classroomRepository.findById(classroomId).orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
    }

    public void saveClassroom(Classroom classroom) {
        classroomRepository.save(classroom);
    }

    public void deleteClassroom(Integer classroomId) {
        var c = findClassById(classroomId);
        var s = studentClient.findAllStudentsByClassroomId(classroomId);
        for (Student student : s) {
            student.setClassroomId(null);
            studentClient.saveStudent(student);
        }
        classroomRepository.deleteById(classroomId);
    }

    public void deleteSchool(Integer schoolId) {
        classroomRepository.deleteAllBySchoolId(schoolId);
        teacherClient.removeAllTeachersBySchool(schoolId);
        studentClient.removeAllStudentsBySchool(schoolId);
        schoolRepository.deleteById(schoolId);
    }

    public void updateSchool(Integer schoolId, School school) {
        if(schoolRepository.existsById(schoolId)){
            school.setId(schoolId);
            school.setName(school.getName());
            school.setEmail(school.getEmail());
            schoolRepository.save(school);
        }
    }

    public School getSchoolById(Integer schoolId) {
        return schoolRepository.findById(schoolId).orElse(School.builder().name("NOT FOUND").email("NOT FOUND").build());
    }

    public List<ClassroomDTO> findAllClassrooms() {
        var classrooms = classroomRepository.findAll();
        return classrooms.stream().map(cr -> {
            if(cr.getTeacherId() == null){
                var s = studentClient.findAllStudentsByClassroomId(cr.getId());
                var sc = schoolRepository.findById(cr.getSchoolId()).orElse(School.builder().name("NOT FOUND").email("NOT FOUND").build());
                return ClassroomDTO.builder().schoolId(sc.getId()).id(cr.getId()).name(cr.getName()).schoolName(sc.getName()).students(s).build();
            }else{
                var t = teacherClient.findTeacherById(cr.getTeacherId());
                var s = studentClient.findAllStudentsByClassroomId(cr.getId());
                var sc = schoolRepository.findById(cr.getSchoolId()).orElse(School.builder().name("NOT FOUND").email("NOT FOUND").build());
                return ClassroomDTO.builder().schoolId(sc.getId()).id(cr.getId()).name(cr.getName()).schoolName(sc.getName()).students(s).assignedTeacher(t).teacherId(t.getId()).build();
            }
        }).toList();
    }

    public List<ClassroomDTO> findAllFullClassroomsBySchoolId(Integer schoolId) {
        var classrooms = classroomRepository.findAllBySchoolId(schoolId);
        return classrooms.stream().map(cr -> {
            if(cr.getTeacherId() == null){
                var s = studentClient.findAllStudentsByClassroomId(cr.getId());
                var sc = schoolRepository.findById(cr.getSchoolId()).orElse(School.builder().name("NOT FOUND").email("NOT FOUND").build());
                return ClassroomDTO.builder().schoolId(sc.getId()).id(cr.getId()).name(cr.getName()).schoolName(sc.getName()).students(s).build();
            }else{
                var t = teacherClient.findTeacherById(cr.getTeacherId());
                var s = studentClient.findAllStudentsByClassroomId(cr.getId());
                var sc = schoolRepository.findById(cr.getSchoolId()).orElse(School.builder().name("NOT FOUND").email("NOT FOUND").build());
                return ClassroomDTO.builder().schoolId(sc.getId()).id(cr.getId()).name(cr.getName()).schoolName(sc.getName()).students(s).assignedTeacher(t).teacherId(t.getId()).build();
            }

        }).toList();
    }

    public void assignTeacherToClassroom(Integer teacherId, Integer classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
        classroom.setTeacherId(teacherId);
        classroomRepository.save(classroom);
    }

    public StudentFullResponse getStudentFullResponse(Integer studentId) {
        var student = studentClient.findStudentById(studentId);
        if(student.getSchoolId() != null){
            var school = getSchoolById(student.getSchoolId());
            if(student.getClassroomId() != null) {
                var classroom = findClassById(student.getClassroomId());
                return StudentFullResponse.builder().id(studentId).name(student.getName()).email(student.getEmail()).schoolId(school.getId()).school(school).classroomId(classroom.getId()).classroom(classroom).build();
            }
            return StudentFullResponse.builder().id(studentId).name(student.getName()).email(student.getEmail()).schoolId(school.getId()).school(school).build();
        }
        return StudentFullResponse.builder().id(studentId).name(student.getName()).email(student.getEmail()).build();
    }

    public List<StudentFullResponse> getAllStudentFullResponse(){
        List<Student> students = studentClient.findAllStudents();
        List<StudentFullResponse> fullStudents = new ArrayList<>();
        for (Student student : students){
            fullStudents.add(getStudentFullResponse(student.getId()));
        }
        return fullStudents;
    }

}
