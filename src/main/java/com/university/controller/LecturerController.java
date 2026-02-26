package com.university.controller;

import com.university.model.Department;
import com.university.model.Lecturer;
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
@RequestMapping("/lecturers")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public String listLecturers(Model model) {
        model.addAttribute("lecturers", lecturerService.findAll());
        return "lecturers/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("lecturer", new Lecturer());
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("formTitle", "Add New Lecturer");
        return "lecturers/form";
    }

    @PostMapping("/save")
    public String saveLecturer(@Valid @ModelAttribute("lecturer") Lecturer lecturer,
                               BindingResult result,
                               @RequestParam(name = "departmentId", required = false) Long departmentId,
                               Model model,
                               RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("formTitle", lecturer.getId() == null ? "Add New Lecturer" : "Edit Lecturer");
            return "lecturers/form";
        }
        // Resolve the Department entity from the submitted ID
        if (departmentId != null) {
            departmentService.findById(departmentId)
                    .ifPresent(lecturer::setDepartment);
        } else {
            lecturer.setDepartment(null);
        }

        if (lecturer.getId() == null) {
            lecturerService.save(lecturer);
            redirectAttrs.addFlashAttribute("successMessage", "Lecturer created successfully!");
        } else {
            lecturerService.update(lecturer);
            redirectAttrs.addFlashAttribute("successMessage", "Lecturer updated successfully!");
        }
        return "redirect:/lecturers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttrs) {
        return lecturerService.findById(id).map(lecturer -> {
            model.addAttribute("lecturer", lecturer);
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("selectedDepartmentId",
                    lecturer.getDepartment() != null ? lecturer.getDepartment().getId() : null);
            model.addAttribute("formTitle", "Edit Lecturer");
            return "lecturers/form";
        }).orElseGet(() -> {
            redirectAttrs.addFlashAttribute("errorMessage", "Lecturer not found!");
            return "redirect:/lecturers";
        });
    }

    @GetMapping("/delete/{id}")
    public String deleteLecturer(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {
        try {
            lecturerService.delete(id);
            redirectAttrs.addFlashAttribute("successMessage", "Lecturer deleted successfully!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "Cannot delete lecturer. They may have associated courses.");
        }
        return "redirect:/lecturers";
    }
}
