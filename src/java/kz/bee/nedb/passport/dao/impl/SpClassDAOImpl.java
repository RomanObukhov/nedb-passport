package kz.bee.nedb.passport.dao.impl;

import kz.bee.nedb.passport.dao.SpClassDAO;
import kz.bee.nedb.passport.entity.SpClass;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SpClassDAOImpl implements SpClassDAO {
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public SpClass selectByTag(Long tag) {
        return (SpClass) sessionFactory.getCurrentSession().createQuery("from SpClass where tag = :tag")
                .setLong("tag", tag)
                .uniqueResult();
    }
}