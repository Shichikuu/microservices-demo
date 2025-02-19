package com.alibou.frontend.controller;

import com.alibou.common.model.Course;
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

import java.util.List;


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

    @GetMapping("/insert-score")
    public String showInsertScoreForm(Model model, @RequestParam Integer studentId) {
        if(studentId == null) {
            return "redirect:/";
        }
        model.addAttribute("student", studentService.findStudentById(studentId));
        if (!model.containsAttribute("courseScore")) {
            CourseScore courseScore = new CourseScore();
            courseScore.setStudent(Student.builder().id(studentId).build());
            model.addAttribute("courseScore", courseScore);
        }
        List<Course> courses = courseService.findAllCourses();
        model.addAttribute("courses", courses);
        return "insert-score";

    }

    @PostMapping("/insert-score")
    public String insertScore(@ModelAttribute CourseScore courseScore, RedirectAttributes redirectAttributes){
        if(courseScore.getScore() == null){
            redirectAttributes.addFlashAttribute("scoreError", "Score must not be empty");
            redirectAttributes.addFlashAttribute("courseScore", courseScore);
            return "redirect:/learning/insert-score?studentId=" + courseScore.getStudent().getId();
        }else if(courseScore.getScore() < 0 || courseScore.getScore() > 100){
            redirectAttributes.addFlashAttribute("scoreError", "Score must be between 0 and 100");
            redirectAttributes.addFlashAttribute("courseScore", courseScore);
            return "redirect:/learning/insert-score?studentId=" + courseScore.getStudent().getId();
        }

        try {
            courseService.saveCourseScore(courseScore);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("scoreError", e.getMessage());return "redirect:/learning/insert-score?studentId=" + courseScore.getStudent().getId();
        }
        redirectAttributes.addFlashAttribute("insertSuccess", "Score inserted successfully");
        return "redirect:/learning/report?studentId=" + courseScore.getStudent().getId();
    }
}
