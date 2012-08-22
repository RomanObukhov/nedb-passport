package kz.bee.nedb.passport.dao.impl;

import kz.bee.nedb.passport.dao.StudentDAO;
import kz.bee.nedb.passport.entity.Student;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAOImpl implements StudentDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Student select(Long id) {
        return (Student) sessionFactory.getCurrentSession().createQuery("from Student where id = :id").setLong("id", id).uniqueResult();
    }

    @Override
    public Student select(String iin) {
        return (Student) sessionFactory.getCurrentSession().createQuery("from Student where iin = :iin").setString("iin", iin).uniqueResult();
    }

    @Override
    public void save(Student student) {
        sessionFactory.getCurrentSession().saveOrUpdate(student);
    }
    
    @Override
    public void delete(Long id) {
        sessionFactory.getCurrentSession().createQuery("delete from Student where id = :id").setLong("id", id).executeUpdate();
    }
}