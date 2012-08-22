package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.SchoolTeacherLink;

public interface SchoolTeacherLinkDAO {
    public void create(Long schoolId, Long teacherId);
    public SchoolTeacherLink selectBySchoolAndTeacher(Long schoolId, Long teacherId);
    public List<SchoolTeacherLink> listForSchool(Long schoolId);
    public List<SchoolTeacherLink> listForTeacher(Long teacherId);
    public void delete(Long schoolId, Long teacherId);
}