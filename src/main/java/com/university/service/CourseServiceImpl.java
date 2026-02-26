package com.university.service;

import com.university.dao.CourseDao;
import com.university.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findByDepartmentId(Long departmentId) {
        return courseDao.findByDepartmentId(departmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findByLecturerId(Long lecturerId) {
        return courseDao.findByLecturerId(lecturerId);
    }

    @Override
    public void save(Course course) {
        courseDao.save(course);
    }

    @Override
    public void update(Course course) {
        courseDao.update(course);
    }

    @Override
    public void delete(Long id) {
        courseDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return courseDao.count();
    }
}

