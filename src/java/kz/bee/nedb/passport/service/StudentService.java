package kz.bee.nedb.passport.service;

import java.util.List;
import kz.bee.nedb.passport.entity.SchoolStudentLink;
import kz.bee.nedb.passport.entity.Student;

public interface StudentService {
    public Student select(Long studentId);
    public Student create(String iin, String f, String i, String o, Long classId, String parallel, Long schoolId);
    public List<Student> listForSchool(Long schoolId);
    public void delete(Long schoolId, Long studentId);
    public SchoolStudentLink getSchoolStudentLink(Long schoolId, Long studentId);
    public List<SchoolStudentLink> getLinksBySchool(Long schoolId);
}