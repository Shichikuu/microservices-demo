package com.alibou.frontend.controller;

import com.alibou.frontend.service.SchoolService;
import com.alibou.school.model.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/schools", "/"})
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService service;

    @GetMapping
    public String getAllSchools(Model model){
        model.addAttribute("schools", service.getAllSchools());
        return "school";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("school", new School());
        return "school-form"; // A Thymeleaf template with your form
    }

    @PostMapping
    public String createSchool(@ModelAttribute School school) {
        service.saveSchool(school);
        return "redirect:/schools";
    }

    // Show the form to edit an existing school
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        School school = service.getSchoolById(id);
        model.addAttribute("school", school);
        return "school-form";  // reuse the form for create and update
    }

    // Process the update form submission
    @PostMapping("/update/{id}")
    public String updateSchool(@PathVariable("id") Integer id, @ModelAttribute School school) {
        // Ensure the ID is set on the school object (or use a hidden field in the form)
        school.setId(id);
        service.updateSchool(id, school);
        return "redirect:/schools";
    }

    // Delete a school
    @GetMapping("/delete/{id}")
    public String deleteSchool(@PathVariable("id") Integer id) {
        service.deleteSchool(id);
        return "redirect:/schools";
    }
}
