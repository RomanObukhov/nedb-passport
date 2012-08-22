package kz.bee.nedb.passport.dao.impl;

import java.util.List;
import kz.bee.nedb.passport.dao.SchoolStudentLinkDAO;
import kz.bee.nedb.passport.entity.SchoolStudentLink;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolStudentLinkDAOImpl implements SchoolStudentLinkDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Long schoolId, Long studentId, Long classId, String parallel) {
        SchoolStudentLink schoolStudentLink = new SchoolStudentLink();
        schoolStudentLink.setSchoolId(schoolId);
        schoolStudentLink.setStudentId(studentId);
        schoolStudentLink.setClassId(classId);
        schoolStudentLink.setParallel(parallel);
        sessionFactory.getCurrentSession().saveOrUpdate(schoolStudentLink);
    }
    
    @Override
    public List<SchoolStudentLink> listForSchool(Long schoolId) {
        return sessionFactory.getCurrentSession().createQuery("from SchoolStudentLink where schoolId = :schoolId")
                .setLong("schoolId", schoolId)
                .list();
    }
    
    @Override
    public List<SchoolStudentLink> listForStudent(Long studentId) {
        return sessionFactory.getCurrentSession().createQuery("from SchoolStudentLink where studentId = :studentId")
                .setLong("studentId", studentId)
                .list();
    }
    
    @Override
    public void delete(Long schoolId, Long studentId) {
        sessionFactory.getCurrentSession().createQuery("delete from SchoolStudentLink where schoolId = :schoolId and studentId = :studentId")
                .setLong("schoolId", schoolId).setLong("studentId", studentId)
                .executeUpdate();
    }
    
    @Override
    public void save(SchoolStudentLink schoolStudentLink) {
        sessionFactory.getCurrentSession().saveOrUpdate(schoolStudentLink);
    }
    
    @Override
    public SchoolStudentLink selectBySchoolAndStudent(Long schoolId, Long studentId) {
        return (SchoolStudentLink) sessionFactory.getCurrentSession().createQuery("from SchoolStudentLink where schoolId = :schoolId and studentId = :studentId")
                .setLong("schoolId", schoolId).setLong("studentId", studentId)
                .uniqueResult();
    }
}