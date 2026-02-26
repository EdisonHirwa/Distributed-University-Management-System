package com.university.dao;

import com.university.model.Department;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Department> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Department ORDER BY name", Department.class)
                .getResultList();
    }

    @Override
    public Optional<Department> findById(Long id) {
        Department dept = sessionFactory.getCurrentSession().get(Department.class, id);
        return Optional.ofNullable(dept);
    }

    @Override
    public void save(Department department) {
        sessionFactory.getCurrentSession().persist(department);
    }

    @Override
    public void update(Department department) {
        sessionFactory.getCurrentSession().merge(department);
    }

    @Override
    public void delete(Long id) {
        Department dept = sessionFactory.getCurrentSession().get(Department.class, id);
        if (dept != null) {
            sessionFactory.getCurrentSession().remove(dept);
        }
    }

    @Override
    public long count() {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(d) FROM Department d", Long.class)
                .uniqueResult();
    }
}

