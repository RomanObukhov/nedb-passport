package kz.bee.nedb.passport.dao.impl;

import java.util.List;
import kz.bee.nedb.passport.dao.OrgTypeCITLinkDAO;
import kz.bee.nedb.passport.entity.OrgTypeCITLink;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrgTypeCITLinkDAOImpl implements OrgTypeCITLinkDAO {
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public List<OrgTypeCITLink> listByOtClassItemTreeId(Long otClassItemTreeId) {
        return sessionFactory.getCurrentSession().createQuery("from OrgTypeCITLink where otClassItemTreeId = :otClassItemTreeId")
                .setLong("otClassItemTreeId", otClassItemTreeId)
                .list();
    }
    
    @Override
    public List<OrgTypeCITLink> listByClassItemTreeId(Long classItemTreeId) {
        return sessionFactory.getCurrentSession().createQuery("from OrgTypeCITLink where classItemTreeId = :classItemTreeId")
                .setLong("classItemTreeId", classItemTreeId)
                .list();
    }
}