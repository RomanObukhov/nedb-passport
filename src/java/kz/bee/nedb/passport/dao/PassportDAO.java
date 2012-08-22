package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.Passport;

public interface PassportDAO {
    public List<Passport> list(Long parId, Long passType);
    public Passport select(Long id);
    public List<Passport> listForType(Long passType);
}