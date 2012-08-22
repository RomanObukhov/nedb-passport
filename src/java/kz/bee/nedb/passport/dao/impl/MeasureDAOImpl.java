package kz.bee.nedb.passport.dao.impl;

import kz.bee.nedb.passport.dao.MeasureDAO;
import kz.bee.nedb.passport.entity.Measure;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MeasureDAOImpl implements MeasureDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Measure select(Long id) {
        if (id == null) {
            return null;
        } else {
            return (Measure) sessionFactory.getCurrentSession().createQuery("from Measure where id = :id").setLong("id", id).uniqueResult();
        }
    }
}