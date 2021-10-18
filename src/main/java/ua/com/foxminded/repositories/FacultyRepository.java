package ua.com.foxminded.repositories;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.domain.Faculty;

@Repository
@Transactional
public class FacultyRepository implements GenericRepository<Faculty> {
    private SessionFactory sessionFactory;

    @Autowired
    public FacultyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Faculty faculty) {
        sessionFactory.getCurrentSession().persist(faculty);
    }

    @Override
    public List<Faculty> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM Faculty", Faculty.class).getResultList();
    }

    @Override
    public Faculty findById(int id) {
        return sessionFactory.getCurrentSession().get(Faculty.class, id);
    }

    @Override
    public void update(Faculty faculty) {
        sessionFactory.getCurrentSession().update(faculty);
    }

    @Override
    public void delete(Faculty faculty) {
        sessionFactory.getCurrentSession().delete(faculty);
    }
}