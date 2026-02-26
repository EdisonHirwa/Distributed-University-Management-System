package com.university.service;

import com.university.dao.StudentDao;
import com.university.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findById(Long id) {
        return studentDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findByIdWithCourses(Long id) {
        return studentDao.findByIdWithCourses(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByDepartmentId(Long departmentId) {
        return studentDao.findByDepartmentId(departmentId);
    }

    @Override
    public void save(Student student) {
        studentDao.save(student);
    }

    @Override
    public void update(Student student) {
        studentDao.update(student);
    }

    @Override
    public void delete(Long id) {
        studentDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return studentDao.count();
    }
}
