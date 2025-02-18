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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    @PostMapping("/update-student-data")
    public String updateStudentData(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        if(student.getName() == null || student.getEmail() == null) {
            redirectAttributes.addFlashAttribute("error", "Student data must not be empty");
            return "redirect:/learning/report?studentId=" + student.getId();
        }
        try {
            studentService.saveStudent(student);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/learning/report?studentId=" + student.getId();
        }
        redirectAttributes.addFlashAttribute("success", "Student data is updated successfully");
        return "redirect:/learning/report?studentId=" + student.getId();
    }
}
