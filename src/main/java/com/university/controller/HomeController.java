package com.university.controller;

import com.university.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("studentCount", studentService.count());
        model.addAttribute("lecturerCount", lecturerService.count());
        model.addAttribute("courseCount", courseService.count());
        model.addAttribute("departmentCount", departmentService.count());
        return "home";
    }
}

