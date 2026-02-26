package com.university.service;

import com.university.dao.LecturerDao;
import com.university.model.Lecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LecturerServiceImpl implements LecturerService {

    @Autowired
    private LecturerDao lecturerDao;

    @Override
    @Transactional(readOnly = true)
    public List<Lecturer> findAll() {
        return lecturerDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Lecturer> findById(Long id) {
        return lecturerDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lecturer> findByDepartmentId(Long departmentId) {
        return lecturerDao.findByDepartmentId(departmentId);
    }

    @Override
    public void save(Lecturer lecturer) {
        lecturerDao.save(lecturer);
    }

    @Override
    public void update(Lecturer lecturer) {
        lecturerDao.update(lecturer);
    }

    @Override
    public void delete(Long id) {
        lecturerDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return lecturerDao.count();
    }
}

