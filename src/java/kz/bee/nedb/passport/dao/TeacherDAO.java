package kz.bee.nedb.passport.dao;

import kz.bee.nedb.passport.entity.Teacher;

public interface TeacherDAO {
    public Teacher select(Long id);
    public Teacher select(String iin);
    public void save(Teacher teacher);
    public void delete(Long id);
}