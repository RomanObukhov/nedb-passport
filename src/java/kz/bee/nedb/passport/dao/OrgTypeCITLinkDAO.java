package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.OrgTypeCITLink;

public interface OrgTypeCITLinkDAO {
    public List<OrgTypeCITLink> listByOtClassItemTreeId(Long otClassItemTreeId);
    public List<OrgTypeCITLink> listByClassItemTreeId(Long classItemTreeId);
}