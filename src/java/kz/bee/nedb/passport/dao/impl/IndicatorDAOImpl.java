package kz.bee.nedb.passport.dao.impl;

import kz.bee.nedb.passport.dao.IndicatorDAO;
import kz.bee.nedb.passport.entity.Indicator;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IndicatorDAOImpl implements IndicatorDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Indicator select(Long id) {
        if (id == null) {
            return null;
        } else {
            return (Indicator) sessionFactory.getCurrentSession().createQuery("from Indicator where id = :id").setLong("id", id).uniqueResult();
        }
    }
}