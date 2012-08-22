package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.PassIndLink;

public interface PassIndLinkDAO {
    public List<PassIndLink> list(Long passId);
}