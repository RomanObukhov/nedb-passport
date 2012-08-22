package kz.bee.nedb.passport.service;

import java.util.List;
import kz.bee.nedb.passport.entity.ClassItemTree;

public interface ClassItemTreeService {
    public List<ClassItemTree> list(Long parId);
    public List<ClassItemTree> listSp(Long spId);
    public List<ClassItemTree> listRegions();
    public ClassItemTree select(Long id);
    public List<ClassItemTree> getBreadcrumbs(Long id);
    public List<ClassItemTree> getBySpTag(Long tag);
}