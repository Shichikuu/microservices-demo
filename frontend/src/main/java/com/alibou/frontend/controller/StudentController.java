package com.alibou.frontend.controller;

import com.alibou.common.dto.FullSchoolResponse;
import com.alibou.common.model.School;
import com.alibou.frontend.service.SchoolService;
import com.alibou.frontend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    public final StudentService service;
    public final SchoolService schoolService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("students", service.getAllStudents());
        return "student";
    }
}
