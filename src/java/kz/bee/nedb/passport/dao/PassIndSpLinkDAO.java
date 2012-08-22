package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.PassIndSpLink;

public interface PassIndSpLinkDAO {
    public PassIndSpLink select(Long passIndLinkId);
    public List<PassIndSpLink> list(Long passIndLinkId);
}