package com.alibou.frontend.controller;

import com.alibou.common.model.Student;
import com.alibou.frontend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    public final StudentService service;

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

}
