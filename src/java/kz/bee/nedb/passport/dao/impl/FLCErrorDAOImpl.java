package kz.bee.nedb.passport.dao.impl;

import java.util.List;
import kz.bee.nedb.passport.dao.FLCErrorDAO;
import kz.bee.nedb.passport.entity.FLCError;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FLCErrorDAOImpl implements FLCErrorDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<FLCError> list(Long schoolId) {
        return sessionFactory.getCurrentSession().createQuery("from FLCError where schoolId = :schoolId").setLong("schoolId", schoolId).list();
    }

    @Override
    public void save(FLCError flcError) {
        sessionFactory.getCurrentSession().saveOrUpdate(flcError);
    }

    @Override
    public void clear(Long schoolId) {
        sessionFactory.getCurrentSession().createQuery("delete from FLCError where schoolId = :schoolId").setLong("schoolId", schoolId).executeUpdate();
    }
}