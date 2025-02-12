package com.alibou.frontend.controller;

import com.alibou.common.dto.ClassroomDTO;
import com.alibou.common.model.Teacher;
import com.alibou.frontend.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/assign")
    public String assignTeacherToClassroom(@ModelAttribute("classroom") ClassroomDTO classroom, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Teacher teacher = classroom.getAssignedTeacher();
        if(bindingResult.hasErrors()) {
            return "redirect:/classrooms/view/" + classroom.getId();
        }

        try {
            teacherService.assignTeacherToClassroom(teacher.getId(), classroom.getId());
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/classrooms/view/" + classroom.getId();
        }
        redirectAttributes.addFlashAttribute("success", "Teacher assigned successfully!");
        return "redirect:/classrooms/view/" + classroom.getId();
    }
}
