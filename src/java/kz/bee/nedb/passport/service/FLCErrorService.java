package kz.bee.nedb.passport.service;

import java.util.List;
import kz.bee.nedb.passport.entity.ClassItemTree;
import kz.bee.nedb.passport.entity.FLCError;

public interface FLCErrorService {
    public List<ClassItemTree> listErrors(Long schoolId);
    public void save(FLCError flcError);
}