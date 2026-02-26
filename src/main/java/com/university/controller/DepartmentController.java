package com.university.controller;

import com.university.model.Department;
import com.university.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "departments/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("formTitle", "Add New Department");
        return "departments/form";
    }

    @PostMapping("/save")
    public String saveDepartment(@Valid @ModelAttribute("department") Department department,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("formTitle", department.getId() == null ? "Add New Department" : "Edit Department");
            return "departments/form";
        }
        if (department.getId() == null) {
            departmentService.save(department);
            redirectAttrs.addFlashAttribute("successMessage", "Department created successfully!");
        } else {
            departmentService.update(department);
            redirectAttrs.addFlashAttribute("successMessage", "Department updated successfully!");
        }
        return "redirect:/departments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttrs) {
        return departmentService.findById(id).map(dept -> {
            model.addAttribute("department", dept);
            model.addAttribute("formTitle", "Edit Department");
            return "departments/form";
        }).orElseGet(() -> {
            redirectAttrs.addFlashAttribute("errorMessage", "Department not found!");
            return "redirect:/departments";
        });
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {
        try {
            departmentService.delete(id);
            redirectAttrs.addFlashAttribute("successMessage", "Department deleted successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "Cannot delete department. It may have associated records.");
        }
        return "redirect:/departments";
    }
}
