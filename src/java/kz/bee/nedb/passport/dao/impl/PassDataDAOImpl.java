package kz.bee.nedb.passport.dao.impl;

import java.util.List;
import kz.bee.nedb.passport.dao.PassDataDAO;
import kz.bee.nedb.passport.entity.PassData;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PassDataDAOImpl implements PassDataDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public PassData selectByPassIndLink(Long passIndLinkId, Long schoolId) {
        if (passIndLinkId == null) {
            return null;
        } else {
            return (PassData) sessionFactory.getCurrentSession().createQuery("from PassData where schoolId = :schoolId and passIndLinkId = :passIndLinkId")
                    .setLong("schoolId", schoolId).setLong("passIndLinkId", passIndLinkId).uniqueResult();
        }
    }

    @Override
    public List<PassData> listByPassIndLink(Long passIndLinkId, Long schoolId) {
        if (passIndLinkId == null) {
            return null;
        } else {
            return sessionFactory.getCurrentSession().createQuery("from PassData where schoolId = :schoolId and passIndLinkId = :passIndLinkId")
                    .setLong("schoolId", schoolId).setLong("passIndLinkId", passIndLinkId).list();
        }
    }

    @Override
    public PassData selectByPassIndSpLink(Long passIndSpLinkId) {
        if (passIndSpLinkId == null) {
            return null;
        } else {
            return (PassData) sessionFactory.getCurrentSession().createQuery("from PassData where passIndSpLinkId = :passIndSpLinkId").setLong("passIndSpLinkId", passIndSpLinkId).uniqueResult();
        }
    }

    @Override
    public List<PassData> listByPassIndSpLink(Long passIndSpLinkId) {
        if (passIndSpLinkId == null) {
            return null;
        } else {
            return sessionFactory.getCurrentSession().createQuery("from PassData where passIndSpLinkId = :passIndSpLinkId").setLong("passIndSpLinkId", passIndSpLinkId).list();
        }
    }

    @Override
    public void clear(Long schoolId, Long passIndLinkId) {
        sessionFactory.getCurrentSession().createQuery("delete from PassData where schoolId = :schoolId and passIndLinkId = :passIndLinkId")
                .setLong("schoolId", schoolId).setLong("passIndLinkId", passIndLinkId).executeUpdate();
    }

    @Override
    public void save(PassData passData) {
        sessionFactory.getCurrentSession().saveOrUpdate(passData);
    }
}