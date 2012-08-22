package kz.bee.nedb.passport.dao.impl;

import kz.bee.nedb.passport.dao.TeacherDAO;
import kz.bee.nedb.passport.entity.Teacher;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDAOImpl implements TeacherDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Teacher select(Long id) {
        return (Teacher) sessionFactory.getCurrentSession().createQuery("from Teacher where id = :id").setLong("id", id).uniqueResult();
    }

    @Override
    public Teacher select(String iin) {
        return (Teacher) sessionFactory.getCurrentSession().createQuery("from Teacher where iin = :iin").setString("iin", iin).uniqueResult();
    }

    @Override
    public void save(Teacher teacher) {
        sessionFactory.getCurrentSession().saveOrUpdate(teacher);
    }
    
    @Override
    public void delete(Long id) {
        sessionFactory.getCurrentSession().createQuery("delete from Teacher where id = :id").setLong("id", id).executeUpdate();
    }
}