package kz.bee.nedb.passport.dao.impl;

import java.util.List;
import kz.bee.nedb.passport.dao.SchoolTeacherLinkDAO;
import kz.bee.nedb.passport.entity.SchoolTeacherLink;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolTeacherLinkDAOImpl implements SchoolTeacherLinkDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Long schoolId, Long teacherId) {
        SchoolTeacherLink schoolTeacherLink = new SchoolTeacherLink();
        schoolTeacherLink.setSchoolId(schoolId);
        schoolTeacherLink.setTeacherId(teacherId);
        sessionFactory.getCurrentSession().saveOrUpdate(schoolTeacherLink);
    }
    
    @Override
    public SchoolTeacherLink selectBySchoolAndTeacher(Long schoolId, Long teacherId) {
        return (SchoolTeacherLink) sessionFactory.getCurrentSession().createQuery("from SchoolTeacherLink where schoolId = :schoolId and teacherId = :teacherId")
                .setLong("schoolId", schoolId).setLong("teacherId", teacherId)
                .uniqueResult();
    }
    
    @Override
    public List<SchoolTeacherLink> listForSchool(Long schoolId) {
        return sessionFactory.getCurrentSession().createQuery("from SchoolTeacherLink where schoolId = :schoolId")
                .setLong("schoolId", schoolId)
                .list();
    }
    
    @Override
    public List<SchoolTeacherLink> listForTeacher(Long teacherId) {
        return sessionFactory.getCurrentSession().createQuery("from SchoolTeacherLink where teacherId = :teacherId")
                .setLong("teacherId", teacherId)
                .list();
    }
    
    @Override
    public void delete(Long schoolId, Long teacherId) {
        sessionFactory.getCurrentSession().createQuery("delete from SchoolTeacherLink where schoolId = :schoolId and teacherId = :teacherId")
                .setLong("schoolId", schoolId).setLong("teacherId", teacherId)
                .executeUpdate();
    }
}