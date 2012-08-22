package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.SchoolStudentLink;

public interface SchoolStudentLinkDAO {
    public void create(Long schoolId, Long studentId, Long classId, String parallel);
    public List<SchoolStudentLink> listForSchool(Long schoolId);
    public List<SchoolStudentLink> listForStudent(Long studentId);
    public void delete(Long schoolId, Long StudentId);
    public void save(SchoolStudentLink schoolStudentLink);
    public SchoolStudentLink selectBySchoolAndStudent(Long schoolId, Long studentId);
}