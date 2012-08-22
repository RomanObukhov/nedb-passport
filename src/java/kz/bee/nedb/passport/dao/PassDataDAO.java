package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.PassData;

public interface PassDataDAO {
    public PassData selectByPassIndLink(Long passIndLinkId, Long schoolId);
    public List<PassData> listByPassIndLink(Long passIndLinkId, Long schoolId);
    public PassData selectByPassIndSpLink(Long passIndSpLinkId);
    public List<PassData> listByPassIndSpLink(Long passIndSpLinkId);
    public void clear(Long schoolId, Long passIndLinkId);
    public void save(PassData passData);
}