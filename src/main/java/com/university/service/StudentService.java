package com.university.service;

import com.university.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> findAll();
    Optional<Student> findById(Long id);
    Optional<Student> findByIdWithCourses(Long id);
    List<Student> findByDepartmentId(Long departmentId);
    void save(Student student);
    void update(Student student);
    void delete(Long id);
    long count();
}
