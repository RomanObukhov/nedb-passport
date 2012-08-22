package kz.bee.nedb.passport.service;

import java.util.List;
import kz.bee.nedb.passport.entity.Teacher;

public interface TeacherService {
    public Teacher select(Long teacherId);
    public Teacher create(String iin, String f, String i, String o, Long schoolId);
    public List<Teacher> listForSchool(Long schoolId);
    public void delete(Long schoolId, Long teacherId);
}