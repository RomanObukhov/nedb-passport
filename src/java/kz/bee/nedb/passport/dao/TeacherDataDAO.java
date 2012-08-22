package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.PassData;
import kz.bee.nedb.passport.entity.TeacherData;

public interface TeacherDataDAO {
    public List<TeacherData> listByTeacherId(Long teacherId);
    public TeacherData selectByPassIndLink(Long passIndLinkId, Long schoolId, Long teacherId);
    public List<TeacherData> listByPassIndLink(Long passIndLinkId, Long schoolId, Long teacherId);
    public void clear(Long teacherId);
    public void clear(Long schoolId, Long teacherId);
    public void save(TeacherData teacherData);
}