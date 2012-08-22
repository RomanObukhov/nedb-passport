package kz.bee.nedb.passport.dao.impl;

import java.util.List;
import kz.bee.nedb.passport.dao.PassportDAO;
import kz.bee.nedb.passport.entity.Passport;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PassportDAOImpl implements PassportDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Passport> list(Long parId, Long passType) {
        if (parId == null) {
            return sessionFactory.getCurrentSession().createQuery("from Passport where parId = null and passType = :passType").setLong("passType", passType).list();
        } else {
            return sessionFactory.getCurrentSession().createQuery("from Passport where parId = :parId and passType = :passType").setLong("parId", parId).setLong("passType", passType).list();
        }
    }
    
    @Override
    public Passport select(Long id) {
        if (id == null) {
            return null;
        } else {
            return (Passport) sessionFactory.getCurrentSession().createQuery("from Passport where id = :id").setLong("id", id).uniqueResult();
        }
    }

    @Override
    public List<Passport> listForType(Long passType) {
        if (passType == null) {
            return null;
        } else {
            return sessionFactory.getCurrentSession().createQuery("from Passport where passType = :passType").setLong("passType", passType).list();
        }
    }
}