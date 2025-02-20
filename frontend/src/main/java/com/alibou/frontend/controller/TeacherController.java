package com.alibou.frontend.controller;

import com.alibou.common.dto.ClassroomDTO;
import com.alibou.common.model.Teacher;
import com.alibou.frontend.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public String index(@PageableDefault(size = 10) Pageable pageable, Model model, @RequestParam(value = "value", required = false) String name) {
        if(name != null) {
            model.addAttribute("key", name);
            Page<Teacher> teachers = teacherService.findAllTeachersPaged(name, pageable);
            model.addAttribute("teachers", teachers);
        } else {
            Page<Teacher> teachers = teacherService.findAllTeachersPaged("", pageable);
            model.addAttribute("teachers", teachers);
        }
        return "teacher";
    }

    @GetMapping("/insert-teacher")
    public String showInsertForm(Model model, @RequestParam Integer schoolId, @PageableDefault Pageable pageable, @RequestParam(value = "value", required = false) String name) {
        if(name != null) {
            model.addAttribute("key", name);
            model.addAttribute("teachers", teacherService.findAllTeachersPaged(name, pageable));
        } else {
            model.addAttribute("teachers", teacherService.findAllTeachersPaged("", pageable));
        }
        model.addAttribute("schoolId", schoolId);
        return "insert-teacher";
    }

    @PostMapping("/insert-teacher")
    public String insertTeacher(@RequestParam("schoolId") Integer schoolId, @RequestParam("teacherId") Integer teacherId, RedirectAttributes redirectAttributes) {
        if(teacherId == null) {
            redirectAttributes.addFlashAttribute("error", "Please select a teacher to insert!");
            return "redirect:/teachers/insert-teacher?schoolId=" + schoolId;
        }
        try {
            teacherService.insertTeacherToSchool(schoolId, teacherId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/teachers/insert-teacher?schoolId=" + schoolId;
        }
        redirectAttributes.addFlashAttribute("success", "Teacher inserted successfully!");
        return "redirect:/teachers/insert-teacher?schoolId=" + schoolId;
    }

    @GetMapping("/remove-from-school")
    public String removeTeacherFromSchool(@RequestParam("schoolId") Integer schoolId, @RequestParam("teacherId") Integer teacherId, RedirectAttributes redirectAttributes) {
        if(teacherId == null || schoolId == null) {
            return "redirect:/schools";
        }
        try {
            teacherService.removeTeacherFromSchool(teacherId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("TeacherError", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/schools/view/" + schoolId;
        }
        redirectAttributes.addFlashAttribute("teacherSuccess", "Teacher removed successfully!");
        return "redirect:/schools/view/" + schoolId;
    }

    @GetMapping("/view")
    public String showTeacherDetails(@RequestParam("teacherId") Integer teacherId, @RequestParam(value = "schoolId", required = false) Integer schoolId, Model model) {
        if(teacherId == null) {
            return "redirect:/";
        }
        Teacher teacher = teacherService.findTeacherById(teacherId);
        model.addAttribute("teacher", teacher);
        if(schoolId != null) {
            model.addAttribute("schoolId", schoolId);
        }
        return "teacher-details";
    }

    @PostMapping("/update-teacher")
    public String updateTeacher(@ModelAttribute Teacher teacher, @RequestParam(value = "schoolId", required = false) Integer schoolId, RedirectAttributes redirectAttributes) {
        if(teacher.getName() == null || teacher.getEmail() == null) {
            redirectAttributes.addFlashAttribute("error", "Teacher data must not be empty");
            if(schoolId != null){
                return "redirect:/teachers/view?teacherId=" + teacher.getId() + "&schoolId=" + schoolId;
            }
            return "redirect:/teachers/view?teacherId=" + teacher.getId();
        }
        try {
            teacherService.saveTeacher(teacher);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            if(schoolId != null){
                return "redirect:/teachers/view?teacherId=" + teacher.getId() + "&schoolId=" + schoolId;
            }
            return "redirect:/teachers/view?teacherId=" + teacher.getId();
        }
        redirectAttributes.addFlashAttribute("success", "Teacher data is updated successfully");
        if (schoolId != null) {
            return "redirect:/teachers/view?teacherId=" + teacher.getId() + "&schoolId=" + schoolId;
        }
        return "redirect:/teachers/view?teacherId=" + teacher.getId();
    }

    @GetMapping("/delete")
    public String deleteTeacher(@RequestParam("teacherId") Integer teacherId, RedirectAttributes redirectAttributes) {
        teacherService.deleteTeacher(teacherId);
        redirectAttributes.addFlashAttribute("teacherSuccess", "Teacher deleted successfully!");
        return "redirect:/teachers";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "create-teacher-form";
    }

    @PostMapping("/create")
    public String createTeacher(@ModelAttribute Teacher teacher, RedirectAttributes redirectAttributes) {
        if(teacher.getName() == null || teacher.getEmail() == null) {
            redirectAttributes.addFlashAttribute("error", "Teacher data must not be empty");
            return "redirect:/teachers/new";
        }
        try {
            teacherService.saveTeacher(teacher);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/teachers/new";
        }
        redirectAttributes.addFlashAttribute("success", "Teacher created successfully");
        return "redirect:/teachers";
    }


}
