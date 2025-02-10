package com.alibou.frontend.controller;

import com.alibou.frontend.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService service;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("classrooms", service.getAllClassrooms());
        return "classroom";
    }

}
