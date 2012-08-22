package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.StudentData;

public interface StudentDataDAO {
    public List<StudentData> listByStudentId(Long studentId);
    public StudentData selectByPassIndLink(Long passIndLinkId, Long schoolId, Long studentId);
    public List<StudentData> listByPassIndLink(Long passIndLinkId, Long schoolId, Long studentId);
    public void clear(Long studentId);
    public void clear(Long schoolId, Long studentId);
    public void save(StudentData studentData);
}