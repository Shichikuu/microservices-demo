package com.alibou.frontend.controller;

import com.alibou.common.dto.ClassroomDTO;
import com.alibou.common.dto.StudentFullResponse;
import com.alibou.common.model.Student;
import com.alibou.common.model.Teacher;
import com.alibou.frontend.service.SchoolService;
import com.alibou.common.dto.FullSchoolResponse;
import com.alibou.common.model.School;
import com.alibou.frontend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping({"/schools", "/"})
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService service;
    private final StudentService studentService;

    @GetMapping
    public String getAllSchools(@PageableDefault(size = 10) @SortDefault("id") Pageable pageable, Model model, @RequestParam(value = "value", required = false) String name){
        if (name != null) {
            model.addAttribute("key", name);
            model.addAttribute("schools", service.findAllSchoolsByName(name, pageable));
        } else {
            model.addAttribute("schools", service.findAllSchoolsByName("", pageable));
        }
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

    @GetMapping("/view/{id}")
    public String viewSchoolDetails(@PathVariable("id") Integer id, Model model){
        FullSchoolResponse school = service.getFullSchoolResponseById(id);
        model.addAttribute("school", school);
        return "school-details";
    }

    @GetMapping("/{id}/insert-student")
    public String showInsertStudentForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("student", new Student());
        List<StudentFullResponse> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        School school = service.getSchoolById(id);
        model.addAttribute("school", school);
        return "insert-student";
    }

    @PostMapping("/{id}/insert-student")
    public String insertStudent(@PathVariable("id") Integer id, @ModelAttribute Student student, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "redirect:/schools/" + id + "/insert-student";
        }
        if(student.getId() == null) {
            redirectAttributes.addFlashAttribute("error", "Please select a student to insert!");
            return "redirect:/schools/" + id + "/insert-student";
        }
        try {
            service.insertStudentToSchool(id, student.getId());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/schools/" + id + "/insert-student";
        }
        redirectAttributes.addFlashAttribute("success", "Student inserted successfully!");
        return "redirect:/schools/" + id + "/insert-student";
    }

    @GetMapping("/{id}/remove-student")
    public String showRemoveStudentForm(@PathVariable("id") Integer id, @RequestParam("studentId") Integer studentId) {
        if(studentId == null) {
            return "redirect:/schools/view/" + id;
        }
        service.removeStudentFromSchool(id, studentId);
        return "redirect:/schools/view/" + id;
    }


}
