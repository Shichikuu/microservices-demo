package com.alibou.course;

import com.alibou.common.model.Course;
import com.alibou.common.model.CourseScore;
import com.alibou.course.repository.CourseRepository;
import com.alibou.course.repository.CourseScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseScoreRepository courseScoreRepository;

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(Integer courseId) {
        return courseRepository.findById(courseId).orElse(Course.builder().name("NOT FOUND").build());
    }

    public Page<CourseScore> findCourseScoresByStudentId(Integer studentId, Pageable pageable) {
        return courseScoreRepository.findByStudent_Id(studentId, pageable);
    }

    public Page<CourseScore> findCourseScoresByStudentIdAndCourseName(Integer studentId, Pageable pageable, String courseName) {
        return courseScoreRepository.findByStudent_IdAndCourse_NameContainingIgnoreCase(studentId, courseName, pageable);
    }

    public void saveCourseScore(CourseScore courseScore) {
        Optional<CourseScore> existingCourseScore = courseScoreRepository.findByStudent_IdAndCourse_IdAndYearAndSemester(courseScore.getStudent().getId(), courseScore.getCourse().getId(), courseScore.getYear(), courseScore.getSemester()).stream().findFirst();
        String dateString = "";
        if(existingCourseScore.isEmpty()) {
            if(courseScore.getSemester().equals("Odd Semester")) {
                dateString = "01-01-" + courseScore.getYear();
            }else if(courseScore.getSemester().equals("Even Semester")) {
                dateString = "01-07-" + courseScore.getYear();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date date = formatter.parse(dateString);
                courseScore.setDate(date);
                courseScoreRepository.save(courseScore);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            existingCourseScore.get().setScore(courseScore.getScore());;
            courseScoreRepository.save(existingCourseScore.get());
        }
    }

    public void updateCourseScore(CourseScore courseScore) {
        courseScoreRepository.save(courseScore);
    }
}
