package com.university.dao;

import com.university.model.Student;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentDaoImpl implements StudentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Student> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery(
                    "SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.department ORDER BY s.lastName",
                    Student.class)
                .getResultList();
    }

    @Override
    public Optional<Student> findById(Long id) {
        // Plain fetch — no lazy collections touched
        Student student = sessionFactory.getCurrentSession().get(Student.class, id);
        return Optional.ofNullable(student);
    }

    /** Eagerly fetches courses — use this for the student detail view */
    @Override
    public Optional<Student> findByIdWithCourses(Long id) {
        List<Student> result = sessionFactory.getCurrentSession()
                .createQuery(
                    "SELECT s FROM Student s " +
                    "LEFT JOIN FETCH s.department " +
                    "LEFT JOIN FETCH s.courses " +
                    "WHERE s.id = :id",
                    Student.class)
                .setParameter("id", id)
                .getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<Student> findByDepartmentId(Long departmentId) {
        return sessionFactory.getCurrentSession()
                .createQuery(
                    "FROM Student s WHERE s.department.id = :deptId ORDER BY s.lastName",
                    Student.class)
                .setParameter("deptId", departmentId)
                .getResultList();
    }

    @Override
    public void save(Student student) {
        sessionFactory.getCurrentSession().persist(student);
    }

    @Override
    public void update(Student student) {
        sessionFactory.getCurrentSession().merge(student);
    }

    @Override
    public void delete(Long id) {
        Student student = sessionFactory.getCurrentSession().get(Student.class, id);
        if (student != null) {
            sessionFactory.getCurrentSession().remove(student);
        }
    }

    @Override
    public long count() {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(s) FROM Student s", Long.class)
                .uniqueResult();
    }
}
