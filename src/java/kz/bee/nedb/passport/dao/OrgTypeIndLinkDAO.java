package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.OrgTypeIndLink;

public interface OrgTypeIndLinkDAO {
    public List<OrgTypeIndLink> listByClassItemTreeId(Long classItemTreeId);
    public List<OrgTypeIndLink> listByPassIndLinkId(Long passIndLinkId);
}