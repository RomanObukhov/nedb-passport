package kz.bee.nedb.passport.dao;

import kz.bee.nedb.passport.entity.Student;

public interface StudentDAO {
    public Student select(Long id);
    public Student select(String iin);
    public void save(Student student);
    public void delete(Long id);
}