package com.university.service;

import com.university.dao.DepartmentDao;
import com.university.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return departmentDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Department> findById(Long id) {
        return departmentDao.findById(id);
    }

    @Override
    public void save(Department department) {
        departmentDao.save(department);
    }

    @Override
    public void update(Department department) {
        departmentDao.update(department);
    }

    @Override
    public void delete(Long id) {
        departmentDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return departmentDao.count();
    }
}

