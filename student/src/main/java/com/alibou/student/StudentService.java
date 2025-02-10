package com.alibou.student;

import com.alibou.course.model.Course;
import com.alibou.student.client.CourseClient;
import com.alibou.student.dto.CourseScoreResponse;
import com.alibou.student.dto.StudentReportDTO;
import com.alibou.student.model.CourseScore;
import com.alibou.student.model.Student;
import com.alibou.student.repository.CourseScoreRepository;
import com.alibou.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
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


    public List<Student> findAllStudentsBySchool(Integer schoolId) {
        return studentRepository.findAllBySchoolId(schoolId);
    }

    public CourseScoreResponse findCourseScore(Integer courseId, Integer studentId) {
        CourseScore cs = courseScoreRepository.findByCourseIdAndStudentId(courseId, studentId);
        Course c = courseClient.findCourseById(courseId);
        Student s = studentRepository.findById(studentId).orElse(Student.builder().name("NOT FOUND").build());
        return CourseScoreResponse.builder().course(c).student(s).score(cs.getScore()).build();
    }

    public StudentReportDTO findStudentReport(Integer studentId) {
        Student s = studentRepository.findById(studentId).orElse(Student.builder().name("NOT FOUND").build());
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
}
