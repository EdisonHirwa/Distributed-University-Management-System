package com.university.controller;

import com.university.model.Course;
import com.university.service.CourseService;
import com.university.service.DepartmentService;
import com.university.service.LecturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired private CourseService courseService;
    @Autowired private DepartmentService departmentService;
    @Autowired private LecturerService lecturerService;

    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "courses/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("lecturers", lecturerService.findAll());
        model.addAttribute("formTitle", "Add New Course");
        return "courses/form";
    }

    @PostMapping("/save")
    public String saveCourse(
            @Valid @ModelAttribute("course") Course course,
            BindingResult result,
            @RequestParam(name = "departmentId", required = false) Long departmentId,
            @RequestParam(name = "lecturerId",   required = false) Long lecturerId,
            Model model,
            RedirectAttributes redirectAttrs) {

        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("lecturers", lecturerService.findAll());
            model.addAttribute("formTitle", course.getId() == null ? "Add New Course" : "Edit Course");
            return "courses/form";
        }
        if (departmentId != null) {
            departmentService.findById(departmentId).ifPresent(course::setDepartment);
        } else {
            course.setDepartment(null);
        }
        if (lecturerId != null) {
            lecturerService.findById(lecturerId).ifPresent(course::setLecturer);
        } else {
            course.setLecturer(null);
        }
        if (course.getId() == null) {
            courseService.save(course);
            redirectAttrs.addFlashAttribute("successMessage", "Course created successfully!");
        } else {
            courseService.update(course);
            redirectAttrs.addFlashAttribute("successMessage", "Course updated successfully!");
        }
        return "redirect:/courses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttrs) {
        return courseService.findById(id).map(course -> {
            model.addAttribute("course", course);
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("lecturers", lecturerService.findAll());
            model.addAttribute("selectedDepartmentId",
                    course.getDepartment() != null ? course.getDepartment().getId() : null);
            model.addAttribute("selectedLecturerId",
                    course.getLecturer() != null ? course.getLecturer().getId() : null);
            model.addAttribute("formTitle", "Edit Course");
            return "courses/form";
        }).orElseGet(() -> {
            redirectAttrs.addFlashAttribute("errorMessage", "Course not found!");
            return "redirect:/courses";
        });
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {
        try {
            courseService.delete(id);
            redirectAttrs.addFlashAttribute("successMessage", "Course deleted successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "Cannot delete course. It may have enrolled students.");
        }
        return "redirect:/courses";
    }
}
