package kz.bee.nedb.passport.dao;

import kz.bee.nedb.passport.entity.Indicator;

public interface IndicatorDAO {
    public Indicator select(Long id);
}