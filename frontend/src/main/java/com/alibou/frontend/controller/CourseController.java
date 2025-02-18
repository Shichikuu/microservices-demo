package com.alibou.frontend.controller;

import com.alibou.common.model.CourseScore;
import com.alibou.common.model.Student;
import com.alibou.frontend.service.CourseService;
import com.alibou.frontend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/learning")
public class CourseController {

    private final CourseService courseService;
    private final StudentService studentService;

    @GetMapping("/report")
    public String getStudentReport(@RequestParam Integer studentId, Model model, Pageable pageable, @RequestParam(value = "value", required = false) String courseName) {
        Student student = studentService.findStudentById(studentId);
        model.addAttribute("student", student);
        if(courseName != null) {
            Page<CourseScore> courseScores = courseService.findCourseScoresByStudentIdAndCourseName(studentId, pageable, courseName);
            model.addAttribute("courseScores", courseScores);
            model.addAttribute("key", courseName);
            return "student-report";
        }
        Page<CourseScore> courseScores = courseService.findCourseScoresByStudentId(studentId, pageable);
        model.addAttribute("courseScores", courseScores);
        return "student-report";
    }
}
