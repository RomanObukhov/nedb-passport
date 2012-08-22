package kz.bee.nedb.passport.service;

import java.util.List;
import kz.bee.nedb.passport.entity.SchoolList;
import kz.bee.nedb.passport.entity.wrapper.SchoolListWrapper;

public interface SchoolListService {
    public List<SchoolList> list(Long classItemTreeId);
    public SchoolList select(Long id);
    public void confirm(Long id);
    public void approve(Long id);
    public List<SchoolListWrapper> listForKato(Long classItemTreeId);
    public void save(SchoolList schoolList);
}