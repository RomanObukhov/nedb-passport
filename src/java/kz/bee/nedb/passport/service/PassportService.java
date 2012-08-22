package kz.bee.nedb.passport.service;

import java.util.List;
import kz.bee.nedb.passport.entity.Passport;

public interface PassportService {
    public List<Passport> list(Long parId, Long passType);
    public Passport select(Long id);
    public List<Passport> getBreadcrumbs(Long id);
}