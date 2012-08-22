package kz.bee.nedb.passport.dao;

import kz.bee.nedb.passport.entity.Measure;

public interface MeasureDAO {
    public Measure select(Long id);
}