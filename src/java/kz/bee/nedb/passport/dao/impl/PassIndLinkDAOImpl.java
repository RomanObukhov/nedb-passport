package kz.bee.nedb.passport.dao.impl;

import java.util.ArrayList;
import java.util.List;
import kz.bee.nedb.passport.dao.PassIndLinkDAO;
import kz.bee.nedb.passport.entity.PassIndLink;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PassIndLinkDAOImpl implements PassIndLinkDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<PassIndLink> list(Long passId) {
        if (passId == null) {
            return new ArrayList<PassIndLink>();
        } else {
            return sessionFactory.getCurrentSession().createQuery("from PassIndLink where passId = :passId").setLong("passId", passId).list();
        }
    }
}