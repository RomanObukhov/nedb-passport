package kz.bee.nedb.passport.dao.impl;

import java.util.List;
import kz.bee.nedb.passport.dao.OrgTypeIndLinkDAO;
import kz.bee.nedb.passport.entity.OrgTypeIndLink;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrgTypeIndLinkDAOImpl implements OrgTypeIndLinkDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<OrgTypeIndLink> listByClassItemTreeId(Long classItemTreeId) {
        return sessionFactory.getCurrentSession().createQuery("from OrgTypeIndLink where classItemTreeId = :classItemTreeId")
                .setLong("classItemTreeId", classItemTreeId)
                .list();
    }
    
    @Override
    public List<OrgTypeIndLink> listByPassIndLinkId(Long passIndLinkId) {
        return sessionFactory.getCurrentSession().createQuery("from OrgTypeIndLink where passIndLinkId = :passIndLinkId")
                .setLong("passIndLinkId", passIndLinkId)
                .list();
    }
}