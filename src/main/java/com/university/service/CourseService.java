package com.university.service;

import com.university.model.Course;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    List<Course> findByDepartmentId(Long departmentId);
    List<Course> findByLecturerId(Long lecturerId);
    void save(Course course);
    void update(Course course);
    void delete(Long id);
    long count();
}

