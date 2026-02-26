package com.university.dao;

import com.university.model.Lecturer;
import java.util.List;
import java.util.Optional;

public interface LecturerDao {
    List<Lecturer> findAll();
    Optional<Lecturer> findById(Long id);
    List<Lecturer> findByDepartmentId(Long departmentId);
    void save(Lecturer lecturer);
    void update(Lecturer lecturer);
    void delete(Long id);
    long count();
}

