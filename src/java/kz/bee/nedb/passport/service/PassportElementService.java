package kz.bee.nedb.passport.service;

import java.util.List;
import java.util.Map;
import kz.bee.nedb.passport.classes.PassportElement;
import kz.bee.nedb.passport.classes.StudentElement;
import kz.bee.nedb.passport.classes.TeacherElement;

public interface PassportElementService {
    public List<PassportElement> listForSchool(Long passId, Long schoolId);
    public List<TeacherElement> listForTeacher(Long schoolId, Long teacherId);
    public List<StudentElement> listForStudent(Long schoolId, Long studentId);
    public void saveForSchool(Long schoolId, Long passportId, Map<String, String[]> params);
    public void saveForTeacher(Long schoolId, Long teacherId, String f, String i, String o, Map<String, String[]> params);
    public void saveForStudent(Long schoolId, Long studentId, String f, String i, String o, Long classId, String parallel, Map<String, String[]> params);
}