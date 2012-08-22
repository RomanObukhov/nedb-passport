package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.SchoolList;

public interface SchoolListDAO {
    public List<SchoolList> list(Long classItemTreeId);
    public SchoolList select(Long id);
    public void save(SchoolList schoolList);
    public void confirm(Long id);
    public void approve(Long id);
    public void unconfirm(Long id);
    public void unapprove(Long id);
}