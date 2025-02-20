package com.alibou.frontend.controller;

import com.alibou.common.model.CourseScore;
import com.alibou.common.model.Student;
import com.alibou.frontend.service.CourseService;
import com.alibou.frontend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    public final StudentService service;
    public final CourseService courseService;

    @GetMapping
    public String index(@PageableDefault(size = 10) Pageable pageable, Model model, @RequestParam(value = "value", required = false) String name) {
        if (name != null) {
            model.addAttribute("key", name);
            Page<Student> students = service.findAllStudentsByName(name, pageable);
            model.addAttribute("students", students);
        } else {
            Page<Student> students = service.findAllStudentsByName("", pageable);
            model.addAttribute("students", students);
        }
        return "student";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @PostMapping
    public String createStudent(@ModelAttribute Student student) {
        service.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/delete")
    public String deleteStudent(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        service.deleteStudent(id);
        redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        return "redirect:/students";
    }

    @GetMapping("/view")
    public String viewStudent(@RequestParam Integer studentId, Model model, Pageable pageable, @RequestParam(value = "value", required = false) String courseName) {
        Student student = service.findStudentById(studentId);
        model.addAttribute("student", student);
        if(courseName != null) {
            Page<CourseScore> courseScores = courseService.findCourseScoresByStudentIdAndCourseName(studentId, pageable, courseName);
            model.addAttribute("courseScores", courseScores);
            model.addAttribute("key", courseName);
            return "student-details";
        }
        Page<CourseScore> courseScores = courseService.findCourseScoresByStudentId(studentId, pageable);
        model.addAttribute("courseScores", courseScores);
        return "student-details";
    }

    @PostMapping("/update-student-data")
    public String updateStudentData(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        if(student.getName() == null || student.getEmail() == null) {
            redirectAttributes.addFlashAttribute("error", "Student data must not be empty");
            return "redirect:/students/view?studentId=" + student.getId();
        }
        try {
            service.saveStudent(student);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/students/view?studentId=" + student.getId();
        }
        redirectAttributes.addFlashAttribute("success", "Student data is updated successfully");
        return "redirect:/students/view?studentId=" + student.getId();
    }


}
