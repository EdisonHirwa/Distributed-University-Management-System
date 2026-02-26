package com.university.controller;

import com.university.model.Student;
import com.university.service.CourseService;
import com.university.service.DepartmentService;
import com.university.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Student student = new Student();
        student.setEnrollmentDate(LocalDate.now());
        model.addAttribute("student", student);
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("formTitle", "Add New Student");
        return "students/form";
    }

    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                              BindingResult result,
                              @RequestParam(name = "departmentId", required = false) Long departmentId,
                              Model model,
                              RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("formTitle", student.getId() == null ? "Add New Student" : "Edit Student");
            return "students/form";
        }
        // Resolve Department entity from submitted ID
        if (departmentId != null) {
            departmentService.findById(departmentId).ifPresent(student::setDepartment);
        } else {
            student.setDepartment(null);
        }

        if (student.getId() == null) {
            studentService.save(student);
            redirectAttrs.addFlashAttribute("successMessage", "Student created successfully!");
        } else {
            studentService.update(student);
            redirectAttrs.addFlashAttribute("successMessage", "Student updated successfully!");
        }
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttrs) {
        return studentService.findById(id).map(student -> {
            model.addAttribute("student", student);
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("selectedDepartmentId",
                    student.getDepartment() != null ? student.getDepartment().getId() : null);
            model.addAttribute("formTitle", "Edit Student");
            return "students/form";
        }).orElseGet(() -> {
            redirectAttrs.addFlashAttribute("errorMessage", "Student not found!");
            return "redirect:/students";
        });
    }

    @GetMapping("/view/{id}")
    public String viewStudent(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttrs) {
        return studentService.findByIdWithCourses(id).map(student -> {
            model.addAttribute("student", student);
            return "students/view";
        }).orElseGet(() -> {
            redirectAttrs.addFlashAttribute("errorMessage", "Student not found!");
            return "redirect:/students";
        });
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {
        try {
            studentService.delete(id);
            redirectAttrs.addFlashAttribute("successMessage", "Student deleted successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "Cannot delete student.");
        }
        return "redirect:/students";
    }
}
