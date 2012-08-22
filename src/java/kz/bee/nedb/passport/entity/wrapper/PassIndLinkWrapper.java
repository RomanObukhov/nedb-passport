package kz.bee.nedb.passport.entity.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import kz.bee.nedb.passport.entity.PassIndLink;

public class PassIndLinkWrapper extends PassIndLink {
    public static List<PassIndLink> sort(List<PassIndLink> passIndLinks) {
        TreeMap<Long, PassIndLink> passIndLinkMap = new TreeMap<Long, PassIndLink>();
        
        for (PassIndLink passIndLink: passIndLinks) {
            passIndLinkMap.put(passIndLink.getOrderNum(), passIndLink);
        }
        
        return new ArrayList<PassIndLink>(passIndLinkMap.values());
    }
}