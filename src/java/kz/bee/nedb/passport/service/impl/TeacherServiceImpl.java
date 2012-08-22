package kz.bee.nedb.passport.service.impl;

import java.util.ArrayList;
import java.util.List;
import kz.bee.nedb.passport.dao.SchoolTeacherLinkDAO;
import kz.bee.nedb.passport.dao.TeacherDAO;
import kz.bee.nedb.passport.dao.TeacherDataDAO;
import kz.bee.nedb.passport.entity.SchoolTeacherLink;
import kz.bee.nedb.passport.entity.Teacher;
import kz.bee.nedb.passport.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherDAO teacherDAO;
    @Autowired
    private TeacherDataDAO teacherDataDAO;
    @Autowired
    private SchoolTeacherLinkDAO schoolTeacherLinkDAO;

    @Override
    @Transactional
    public Teacher select(Long teacherId) {
        return teacherDAO.select(teacherId);
    }

    @Override
    @Transactional
    public Teacher create(String iin, String f, String i, String o, Long schoolId) {
        Teacher teacher = teacherDAO.select(iin);
        
        if (teacher == null) {
            teacher = new Teacher();
            teacher.setIin(iin);
        }
        teacher.setF(f);
        teacher.setI(i);
        teacher.setO(o);
        
        teacherDAO.save(teacher);
        
        SchoolTeacherLink schoolTeacherLink = schoolTeacherLinkDAO.selectBySchoolAndTeacher(schoolId, teacher.getId());
        if (schoolTeacherLink == null) {
            schoolTeacherLinkDAO.create(schoolId, teacher.getId());
        }
        
        return teacher;
    }
    
    @Override
    @Transactional
    public List<Teacher> listForSchool(Long schoolId) {
        List<SchoolTeacherLink> schoolTeacherLinks = schoolTeacherLinkDAO.listForSchool(schoolId);
        List<Teacher> teachers = new ArrayList<Teacher>();
        for (SchoolTeacherLink schoolTeacherLink: schoolTeacherLinks) {
            Teacher teacher = teacherDAO.select(schoolTeacherLink.getTeacherId());
            
            teachers.add(teacher);
        }
        return teachers;
    }
    
    @Override
    @Transactional
    public void delete(Long schoolId, Long teacherId) {
        schoolTeacherLinkDAO.delete(schoolId, teacherId);
        List<SchoolTeacherLink> schoolTeacherLinks = schoolTeacherLinkDAO.listForTeacher(teacherId);
        
        if ((schoolTeacherLinks == null) || schoolTeacherLinks.isEmpty()) {
            teacherDataDAO.clear(teacherId);
            teacherDAO.delete(teacherId);
        }
    }
}