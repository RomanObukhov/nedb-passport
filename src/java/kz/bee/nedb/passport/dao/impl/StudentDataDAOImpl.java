package kz.bee.nedb.passport.dao.impl;

import java.util.List;
import kz.bee.nedb.passport.dao.StudentDataDAO;
import kz.bee.nedb.passport.entity.StudentData;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDataDAOImpl implements StudentDataDAO {
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public List<StudentData> listByStudentId(Long studentId) {
        return sessionFactory.getCurrentSession().createQuery("from StudentData where studentId = :studentId")
                .setLong("studentId", studentId)
                .list();
    }

    @Override
    public StudentData selectByPassIndLink(Long passIndLinkId, Long schoolId, Long studentId) {
        return (StudentData) sessionFactory.getCurrentSession().createQuery("from StudentData where passIndLinkId = :passIndLinkId and schoolId = :schoolId and studentId = :studentId")
                .setLong("passIndLinkId", passIndLinkId).setLong("schoolId", schoolId).setLong("studentId", studentId)
                .uniqueResult();
    }

    @Override
    public List<StudentData> listByPassIndLink(Long passIndLinkId, Long schoolId, Long studentId) {
        return sessionFactory.getCurrentSession().createQuery("from StudentData where passIndLinkId = :passIndLinkId and schoolId = :schoolId and studentId = :studentId")
                .setLong("passIndLinkId", passIndLinkId).setLong("schoolId", schoolId).setLong("studentId", studentId)
                .list();
    }

    @Override
    public void clear(Long studentId) {
        sessionFactory.getCurrentSession()
                .createQuery("delete StudentData where studentId = :studentId")
                .setLong("studentId", studentId)
                .executeUpdate();
    }

    @Override
    public void clear(Long schoolId, Long studentId) {
        sessionFactory.getCurrentSession()
                .createQuery("delete StudentData where schoolId = :schoolId and studentId = :studentId")
                .setLong("schoolId", schoolId).setLong("studentId", studentId)
                .executeUpdate();
    }

    @Override
    public void save(StudentData studentData) {
        sessionFactory.getCurrentSession().saveOrUpdate(studentData);
    }
}