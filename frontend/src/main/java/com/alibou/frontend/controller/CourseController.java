package com.alibou.frontend.controller;

import com.alibou.common.model.Course;
import com.alibou.common.model.CourseScore;
import com.alibou.common.model.Student;
import com.alibou.frontend.service.CourseService;
import com.alibou.frontend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/courses")
    public String index(Model model, @PageableDefault Pageable pageable, @RequestParam(value = "value", required = false) String name) {
        if (name != null) {
            model.addAttribute("key", name);
            Page<Course> courses = courseService.findCourses(name, pageable);
            model.addAttribute("courses", courses);
        } else {
            Page<Course> courses = courseService.findCourses("", pageable);
            model.addAttribute("courses", courses);
        }
        return "course";
    }

    @GetMapping("/create-course")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        return "course-form";
    }

    @PostMapping("/create-course")
    public String createCourse(@ModelAttribute Course course, RedirectAttributes redirectAttributes) {
        if(course.getName() == null || course.getName().isEmpty() || course.getName().isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Course name must not be empty");
            return "redirect:/learning/create";
        }
        try {
            courseService.saveCourse(course);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/learning/create";
        }
        redirectAttributes.addFlashAttribute("success", "Course created successfully");
        return "redirect:/learning/courses";
    }

    @GetMapping("/courses/delete")
    public String deleteCourse(@RequestParam Integer courseId, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(courseId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/learning/courses";
        }
        redirectAttributes.addFlashAttribute("success", "Course deleted successfully");
        return "redirect:/learning/courses";
    }

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

    @GetMapping("/update-score")
    public String showUpdateScoreForm(Model model, @RequestParam Integer courseScoreId) {
        if(!model.containsAttribute("courseScore")) {
            CourseScore courseScore = courseService.findCourseScoreById(courseScoreId);
            if(courseScoreId == null || courseScore == null) {
                return "redirect:/";
            }
            model.addAttribute("courseScore", courseScore);
        }
        return "insert-score";

    }

    @PostMapping("/update-score")
    public String updateScore(@ModelAttribute CourseScore courseScore, RedirectAttributes redirectAttributes){
        if(courseScore.getScore() == null){
            redirectAttributes.addFlashAttribute("scoreError", "Score must not be empty");
            redirectAttributes.addFlashAttribute("courseScore", courseScore);
            return "redirect:/learning/update-score?courseScoreId=" + courseScore.getId();
        }else if(courseScore.getScore() < 0 || courseScore.getScore() > 100){
            redirectAttributes.addFlashAttribute("scoreError", "Score must be between 0 and 100");
            redirectAttributes.addFlashAttribute("courseScore", courseScore);
            return "redirect:/learning/update-score?courseScoreId=" + courseScore.getId();
        }

        try {
            courseService.saveCourseScore(courseScore);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("scoreError", e.getMessage());return "redirect:/learning/update-score?courseScoreId=" + courseScore.getId();
        }
        redirectAttributes.addFlashAttribute("insertSuccess", "Score updated successfully");
        return "redirect:/learning/report?studentId=" + courseScore.getStudent().getId();
    }

    @GetMapping("/delete-score")
    public String deleteScore(@RequestParam Integer courseScoreId, @RequestParam Integer studentId, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourseScore(courseScoreId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/learning/report?studentId=" + studentId;
        }
        redirectAttributes.addFlashAttribute("success", "Score deleted successfully");
        return "redirect:/learning/report?studentId=" + studentId;
    }
}
