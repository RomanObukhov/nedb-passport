package kz.bee.nedb.passport.dao.impl;

import java.util.List;
import kz.bee.nedb.passport.dao.PassIndSpLinkDAO;
import kz.bee.nedb.passport.entity.PassIndSpLink;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PassIndSpLinkDAOImpl implements PassIndSpLinkDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public PassIndSpLink select(Long passIndLinkId) {
        if (passIndLinkId == null) {
            return null;
        } else {
            return (PassIndSpLink) sessionFactory.getCurrentSession().createQuery("from PassIndSpLink where passIndLinkId = :passIndLinkId").setLong("passIndLinkId", passIndLinkId).uniqueResult();
        }
    }

    @Override
    public List<PassIndSpLink> list(Long passIndLinkId) {
        if (passIndLinkId == null) {
            return null;
        } else {
            return sessionFactory.getCurrentSession().createQuery("from PassIndSpLink where passIndLinkId = :passIndLinkId").setLong("passIndLinkId", passIndLinkId).list();
        }
    }
}