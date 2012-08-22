package kz.bee.nedb.passport.dao.impl;

import java.util.List;
import kz.bee.nedb.passport.dao.TeacherDataDAO;
import kz.bee.nedb.passport.entity.PassData;
import kz.bee.nedb.passport.entity.TeacherData;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDataDAOImpl implements TeacherDataDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<TeacherData> listByTeacherId(Long teacherId) {
        return sessionFactory.getCurrentSession().createQuery("from TeacherData where teacherId = :teacherId").setLong("teacherId", teacherId).list();
    }
    
    @Override
    public TeacherData selectByPassIndLink(Long passIndLinkId, Long schoolId, Long teacherId) {
        return (TeacherData) sessionFactory.getCurrentSession()
                .createQuery("from TeacherData where teacherId = :teacherId and schoolId = :schoolId and passIndLinkId = :passIndLinkId")
                .setLong("teacherId", teacherId).setLong("schoolId", schoolId).setLong("passIndLinkId", passIndLinkId)
                .uniqueResult();
    }
    
    @Override
    public List<TeacherData> listByPassIndLink(Long passIndLinkId, Long schoolId, Long teacherId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from TeacherData where teacherId = :teacherId and schoolId = :schoolId and passIndLinkId = :passIndLinkId")
                .setLong("teacherId", teacherId).setLong("schoolId", schoolId).setLong("passIndLinkId", passIndLinkId)
                .list();
    }
    
    @Override
    public void clear(Long teacherId) {
        sessionFactory.getCurrentSession()
                .createQuery("delete TeacherData where teacherId = :teacherId")
                .setLong("teacherId", teacherId)
                .executeUpdate();
    }
    
    @Override
    public void clear(Long schoolId, Long teacherId) {
        sessionFactory.getCurrentSession()
                .createQuery("delete TeacherData where schoolId = :schoolId and teacherId = :teacherId")
                .setLong("schoolId", schoolId).setLong("teacherId", teacherId)
                .executeUpdate();
    }
    
    @Override
    public void save(TeacherData teacherData) {
        sessionFactory.getCurrentSession().saveOrUpdate(teacherData);
    }
}