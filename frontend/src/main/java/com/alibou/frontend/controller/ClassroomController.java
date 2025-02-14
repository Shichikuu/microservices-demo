package com.alibou.frontend.controller;

import com.alibou.common.dto.ClassroomDTO;
import com.alibou.common.dto.FullSchoolResponse;
import com.alibou.common.model.Classroom;
import com.alibou.common.model.School;
import com.alibou.common.model.Student;
import com.alibou.common.model.Teacher;
import com.alibou.frontend.service.ClassroomService;
import com.alibou.frontend.service.SchoolService;
import com.alibou.frontend.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;
    private final TeacherService teacherService;
    private final SchoolService schoolService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("classrooms", classroomService.getAllClassrooms());
        return "classroom";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model, @RequestParam("schoolId") Integer schoolId) {
        if(schoolId == null) {
            return "redirect:/";
        }
        Classroom classroom = new Classroom();
        School school = schoolService.getSchoolById(schoolId);
        classroom.setSchool(school);
        model.addAttribute("classroom", classroom);
        return "classroom-form";
    }

    @PostMapping
    public String createClassroom(@ModelAttribute Classroom classroom) {
        classroomService.saveClassroom(classroom);
        return "redirect:/view/" + classroom.getSchool().getId();
    }


    @GetMapping("/view/{id}")
    public String showClassroom(@PathVariable("id") Integer id, Model model) {
        ClassroomDTO classroom = classroomService.getClassroomById(id);
        model.addAttribute("classroom", classroom);
        model.addAttribute("teachers", teacherService.findAllTeachersBySchool(classroom.getSchoolId()));
        return "classroom-details";
    }

    @GetMapping("/delete")
    public String deleteClassroom(@RequestParam("classroomId") Integer classroomId, @RequestParam("schoolId") Integer schoolId) {
        classroomService.deleteClassroom(classroomId);
        return "redirect:/view/" + schoolId;
    }

    @PostMapping("/assign-teacher")
    public String assignTeacherToClassroom(@ModelAttribute("classroom") ClassroomDTO classroom, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Teacher teacher = classroom.getAssignedTeacher();
        if(bindingResult.hasErrors()) {
            return "redirect:/classrooms/view/" + classroom.getId();
        }
        if(teacher.getId() == null) {
            redirectAttributes.addFlashAttribute("error", "Please select a teacher to assign!");
            return "redirect:/classrooms/view/" + classroom.getId();
        }
        try {
            classroomService.assignTeacherToClassroom(teacher.getId(), classroom.getId());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "redirect:/classrooms/view/" + classroom.getId();
        }
        redirectAttributes.addFlashAttribute("success", "Teacher assigned successfully!");
        return "redirect:/classrooms/view/" + classroom.getId();
    }

    @GetMapping("/{id}/insert-student")
    public String showInsertStudentForm(@PathVariable("id") Integer id, Model model) {
        ClassroomDTO classroom = classroomService.getClassroomById(id);
        model.addAttribute("classroom", classroom);
        FullSchoolResponse school = schoolService.getFullSchoolResponseById(classroom.getSchoolId());
        model.addAttribute("students", school.getStudents());
        model.addAttribute("student", new Student());
        return "insert-student";
    }

    @PostMapping("/{id}/insert-student")
    public String insertStudent(@PathVariable("id") Integer id, @ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        if(student.getId() == null) {
            redirectAttributes.addFlashAttribute("error", "Please select a student to assign!");
            return "redirect:/classrooms/" + id + "/insert-student";
        }
        try {
            classroomService.insertStudentToClassroom(id, student.getId());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/classrooms/" + id + "/insert-student";
        }
        redirectAttributes.addFlashAttribute("success", "Student inserted successfully!");
        return "redirect:/classrooms/view/" + id;
    }

    @GetMapping("/{id}/remove-student")
    public String showRemoveStudentForm(@PathVariable("id") Integer id, @RequestParam("studentId") Integer studentId) {
        if(studentId == null) {
            return "redirect:/classrooms/view/" + id;
        }
        classroomService.removeStudentFromClassroom(id, studentId);
        return "redirect:/classrooms/view/" + id;

    }

}
