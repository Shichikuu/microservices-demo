package com.alibou.student;

import com.alibou.common.dto.StudentFullResponse;
import com.alibou.common.model.Course;
import com.alibou.common.model.School;
import com.alibou.student.client.CourseClient;
import com.alibou.common.dto.CourseScoreResponse;
import com.alibou.common.dto.StudentReportDTO;
import com.alibou.common.model.CourseScore;
import com.alibou.common.model.Student;
import com.alibou.student.repository.CourseScoreRepository;
import com.alibou.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseScoreRepository courseScoreRepository;
    private final CourseClient courseClient;

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Student findStudentById(Integer studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }


    public List<Student> findAllStudentsBySchool(Integer schoolId) {
        return studentRepository.findAllBySchoolId(schoolId);
    }

    public CourseScoreResponse findCourseScore(Integer courseId, Integer studentId) {

        CourseScore cs = courseScoreRepository.findByCourseIdAndStudentId(courseId, studentId);
        Course c = courseClient.findCourseById(courseId);
        Student s = findStudentById(studentId);
        return CourseScoreResponse.builder().course(c).student(s).score(cs.getScore()).build();
    }

    public StudentReportDTO findStudentReport(Integer studentId) {
        Student s = findStudentById(studentId);
        List<CourseScore> cs = courseScoreRepository.findByStudentId(studentId);
        List<CourseScoreResponse> csr = new ArrayList<>();
        for (CourseScore i : cs) {
            Course c = courseClient.findCourseById(i.getCourseId());
            csr.add(CourseScoreResponse.builder().course(c).student(s).score(i.getScore()).build());
        }
        return StudentReportDTO.builder().student(s).courseScoreResponses(csr).build();
    }

    public List<Student> findAllStudentByClassroomId(Integer classroomId) {
        return studentRepository.findAllByClassroomId(classroomId);
    }

    public List<CourseScore> findCourseScoreByStudentId(Integer studentId) {
        return courseScoreRepository.findByStudentId(studentId);
    }

    public void saveCourseScore(CourseScore courseScore) {
        courseScoreRepository.save(courseScore);
    }

    public void insertStudentToClassroom(Integer classroomId, Integer studentId) {
        Student student = findStudentById(studentId);
        if(student.getClassroomId() != null && student.getClassroomId().equals(classroomId)){
            throw new IllegalArgumentException("Student already in this classroom");
        }
        student.setClassroomId(classroomId);
        studentRepository.save(student);
    }

    public void removeStudentFromClassroom(Integer classroomId, Integer studentId) {
        Student student = findStudentById(studentId);
        if (student.getClassroomId() == null || !student.getClassroomId().equals(classroomId)) {
            throw new IllegalArgumentException("Student not in this classroom");
        }
        student.setClassroomId(null);
        studentRepository.save(student);
    }


    public void insertStudentToSchool(Integer schoolId, Integer studentId) {
        Student student = findStudentById(studentId);
        if(student.getSchoolId() != null && student.getSchoolId().equals(schoolId)){
            throw new IllegalArgumentException("Student already in this school");
        }
        student.setSchoolId(schoolId);
        studentRepository.save(student);
    }

    public void removeStudentFromSchool(Integer schoolId, Integer studentId) {
        Student student = findStudentById(studentId);
        if (student.getSchoolId() == null || !student.getSchoolId().equals(schoolId)) {
            throw new IllegalArgumentException("Student not in this school");
        }
        student.setSchoolId(null);
        student.setClassroomId(null);
        studentRepository.save(student);
    }

    public void removeAllStudentsBySchool(Integer schoolId) {
        List<Student> students = studentRepository.findAllBySchoolId(schoolId);
        for (Student student : students) {
            removeStudentFromSchool(schoolId, student.getId());
        }
    }

    public Page<Student> findAllStudentsByName(String name, Pageable pageable) {
        return studentRepository.findAllByNameContainingIgnoreCase(name, pageable);
    }

}
