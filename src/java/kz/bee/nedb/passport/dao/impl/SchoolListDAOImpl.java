package kz.bee.nedb.passport.dao.impl;

import java.util.List;
import kz.bee.nedb.passport.dao.SchoolListDAO;
import kz.bee.nedb.passport.entity.SchoolList;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolListDAOImpl implements SchoolListDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<SchoolList> list(Long classItemTreeId) {
        return sessionFactory.getCurrentSession().createQuery("from SchoolList where classItemTreeId = :classItemTreeId").setLong("classItemTreeId", classItemTreeId).list();
    }
    
    @Override
    public SchoolList select(Long id) {
        if (id == null) {
            return null;
        } else {
            return (SchoolList) sessionFactory.getCurrentSession().createQuery("from SchoolList where id = :id").setLong("id", id).uniqueResult();
        }
    }

    @Override
    public void save(SchoolList schoolList) {
        sessionFactory.getCurrentSession().saveOrUpdate(schoolList);
    }

    @Override
    public void confirm(Long id) {
        SchoolList schoolList = select(id);
        if (schoolList != null) {
//            schoolList.setPassConfirmed(Boolean.TRUE);
            schoolList.setPassConfirmed(1L);
            save(schoolList);
        }
    }
    
    @Override
    public void approve(Long id) {
        SchoolList schoolList = select(id);
        if (schoolList != null) {
//            schoolList.setPassApproved(Boolean.TRUE);
            schoolList.setPassApproved(1L);
            save(schoolList);
        }
    }

    @Override
    public void unconfirm(Long id) {
        SchoolList schoolList = select(id);
        if (schoolList != null) {
//            schoolList.setPassConfirmed(Boolean.FALSE);
            schoolList.setPassConfirmed(0L);
            save(schoolList);
        }
    }
    
    @Override
    public void unapprove(Long id) {
        SchoolList schoolList = select(id);
        if (schoolList != null) {
//            schoolList.setPassApproved(Boolean.FALSE);
            schoolList.setPassApproved(0L);
            save(schoolList);
        }
    }
}