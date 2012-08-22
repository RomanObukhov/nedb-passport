package kz.bee.nedb.passport.service.impl;

import java.util.ArrayList;
import java.util.List;
import kz.bee.nedb.passport.dao.SchoolStudentLinkDAO;
import kz.bee.nedb.passport.dao.StudentDAO;
import kz.bee.nedb.passport.dao.StudentDataDAO;
import kz.bee.nedb.passport.entity.SchoolStudentLink;
import kz.bee.nedb.passport.entity.Student;
import kz.bee.nedb.passport.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private StudentDataDAO studentDataDAO;
    @Autowired
    private SchoolStudentLinkDAO schoolStudentLinkDAO;

    @Override
    @Transactional
    public Student select(Long studentId) {
        return studentDAO.select(studentId);
    }

    @Override
    @Transactional
    public Student create(String iin, String f, String i, String o, Long classId, String parallel, Long schoolId) {
        Student student = studentDAO.select(iin);
        
        if (student == null) {
            student = new Student();
            student.setIin(iin);
        }
        
        student.setF(f);
        student.setI(i);
        student.setO(o);
        
        studentDAO.save(student);
        
        SchoolStudentLink schoolStudentLink = schoolStudentLinkDAO.selectBySchoolAndStudent(schoolId, student.getId());
        if (schoolStudentLink == null) {
            schoolStudentLinkDAO.create(schoolId, student.getId(), classId, parallel);
        }
        
        return student;
    }
    
    @Override
    @Transactional
    public List<Student> listForSchool(Long schoolId) {
        List<SchoolStudentLink> schoolStudentLinks = schoolStudentLinkDAO.listForSchool(schoolId);
        List<Student> students = new ArrayList<Student>();
        for (SchoolStudentLink schoolStudentLink: schoolStudentLinks) {
            Student student = studentDAO.select(schoolStudentLink.getStudentId());
            
            students.add(student);
        }
        return students;
    }
    
    @Override
    @Transactional
    public void delete(Long schoolId, Long studentId) {
        schoolStudentLinkDAO.delete(schoolId, studentId);
        List<SchoolStudentLink> schoolStudentLinks = schoolStudentLinkDAO.listForStudent(studentId);
        
        if ((schoolStudentLinks == null) || schoolStudentLinks.isEmpty()) {
            studentDataDAO.clear(studentId);
            studentDAO.delete(studentId);
        }
    }
    
    @Override
    @Transactional
    public SchoolStudentLink getSchoolStudentLink(Long schoolId, Long studentId) {
        return schoolStudentLinkDAO.selectBySchoolAndStudent(schoolId, studentId);
    }
    
    @Override
    @Transactional
    public List<SchoolStudentLink> getLinksBySchool(Long schoolId) {
        return schoolStudentLinkDAO.listForSchool(schoolId);
    }
}