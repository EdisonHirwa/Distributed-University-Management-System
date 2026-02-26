package com.university.service;

import com.university.model.Department;
import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> findAll();
    Optional<Department> findById(Long id);
    void save(Department department);
    void update(Department department);
    void delete(Long id);
    long count();
}

