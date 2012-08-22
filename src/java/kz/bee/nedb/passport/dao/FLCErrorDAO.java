package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.FLCError;

public interface FLCErrorDAO {
    public List<FLCError> list(Long schoolId);
    public void clear(Long schoolId);
    public void save(FLCError flcError);
}