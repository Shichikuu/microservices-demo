package com.alibou.frontend.controller;

import com.alibou.common.dto.ClassroomDTO;
import com.alibou.common.model.Teacher;
import com.alibou.frontend.service.ClassroomService;
import com.alibou.frontend.service.SchoolService;
import com.alibou.frontend.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;
    private final TeacherService teacherService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("classrooms", classroomService.getAllClassrooms());
        return "classroom";
    }

    @GetMapping("/view/{id}")
    public String showClassroom(@PathVariable("id") Integer id, Model model) {
        ClassroomDTO classroom = classroomService.getClassroomById(id);
        model.addAttribute("classroom", classroom);
        model.addAttribute("teachers", teacherService.findAllTeachersBySchool(id));
        return "classroom-details";
    }

}
