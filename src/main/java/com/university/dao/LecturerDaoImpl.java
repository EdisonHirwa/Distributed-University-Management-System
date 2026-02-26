package com.university.dao;

import com.university.model.Lecturer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LecturerDaoImpl implements LecturerDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Lecturer> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Lecturer l LEFT JOIN FETCH l.department ORDER BY l.lastName", Lecturer.class)
                .getResultList();
    }

    @Override
    public Optional<Lecturer> findById(Long id) {
        Lecturer lecturer = sessionFactory.getCurrentSession().get(Lecturer.class, id);
        return Optional.ofNullable(lecturer);
    }

    @Override
    public List<Lecturer> findByDepartmentId(Long departmentId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Lecturer l WHERE l.department.id = :deptId ORDER BY l.lastName", Lecturer.class)
                .setParameter("deptId", departmentId)
                .getResultList();
    }

    @Override
    public void save(Lecturer lecturer) {
        sessionFactory.getCurrentSession().persist(lecturer);
    }

    @Override
    public void update(Lecturer lecturer) {
        sessionFactory.getCurrentSession().merge(lecturer);
    }

    @Override
    public void delete(Long id) {
        Lecturer lecturer = sessionFactory.getCurrentSession().get(Lecturer.class, id);
        if (lecturer != null) {
            sessionFactory.getCurrentSession().remove(lecturer);
        }
    }

    @Override
    public long count() {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(l) FROM Lecturer l", Long.class)
                .uniqueResult();
    }
}

