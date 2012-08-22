package kz.bee.nedb.passport.dao;

import kz.bee.nedb.passport.entity.SpClass;

public interface SpClassDAO {
    public SpClass selectByTag(Long tag);
}