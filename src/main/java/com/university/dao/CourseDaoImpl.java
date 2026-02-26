package com.university.dao;

import com.university.model.Course;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseDaoImpl implements CourseDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Course> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Course c LEFT JOIN FETCH c.department LEFT JOIN FETCH c.lecturer ORDER BY c.title", Course.class)
                .getResultList();
    }

    @Override
    public Optional<Course> findById(Long id) {
        Course course = sessionFactory.getCurrentSession().get(Course.class, id);
        return Optional.ofNullable(course);
    }

    @Override
    public List<Course> findByDepartmentId(Long departmentId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Course c WHERE c.department.id = :deptId ORDER BY c.title", Course.class)
                .setParameter("deptId", departmentId)
                .getResultList();
    }

    @Override
    public List<Course> findByLecturerId(Long lecturerId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Course c WHERE c.lecturer.id = :lecId ORDER BY c.title", Course.class)
                .setParameter("lecId", lecturerId)
                .getResultList();
    }

    @Override
    public void save(Course course) {
        sessionFactory.getCurrentSession().persist(course);
    }

    @Override
    public void update(Course course) {
        sessionFactory.getCurrentSession().merge(course);
    }

    @Override
    public void delete(Long id) {
        Course course = sessionFactory.getCurrentSession().get(Course.class, id);
        if (course != null) {
            sessionFactory.getCurrentSession().remove(course);
        }
    }

    @Override
    public long count() {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(c) FROM Course c", Long.class)
                .uniqueResult();
    }
}

