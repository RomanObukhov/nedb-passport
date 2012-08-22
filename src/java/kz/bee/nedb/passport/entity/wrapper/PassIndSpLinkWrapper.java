package kz.bee.nedb.passport.entity.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import kz.bee.nedb.passport.entity.PassIndSpLink;

public class PassIndSpLinkWrapper extends PassIndSpLink {
    private PassIndSpLinkWrapper child = null;
    private List<ClassItemTreeWrapper> dict = null;
    
    public static List<PassIndSpLinkWrapper> wrap(List<PassIndSpLink> passIndSpLinks) {
        if (passIndSpLinks == null) {
            return null;
        } else if (passIndSpLinks.isEmpty()) {
            return new ArrayList<PassIndSpLinkWrapper>();
        } else {
            TreeMap<Long, PassIndSpLinkWrapper> sortedMap = new TreeMap<Long, PassIndSpLinkWrapper>();
            for (PassIndSpLink passIndSpLink: passIndSpLinks) {
                sortedMap.put(passIndSpLink.getId(), new PassIndSpLinkWrapper(passIndSpLink));
            }
            
            return new ArrayList<PassIndSpLinkWrapper>(sortedMap.values());
        }
    }
    
    public static PassIndSpLinkWrapper buildTree(List<PassIndSpLinkWrapper> passIndSpLinkWrappers, boolean orient) {
        if ((passIndSpLinkWrappers == null) || passIndSpLinkWrappers.isEmpty()) {
            return null;
        } else {
            HashMap<Long, PassIndSpLinkWrapper> hashMap = new HashMap<Long, PassIndSpLinkWrapper>();
            
            for (PassIndSpLinkWrapper passIndSpLinkWrapper: passIndSpLinkWrappers) {
                hashMap.put(passIndSpLinkWrapper.getId(), passIndSpLinkWrapper);
            }
            
            PassIndSpLinkWrapper result = null;
            for (PassIndSpLinkWrapper passIndSpLinkWrapper: passIndSpLinkWrappers) {
                if (Boolean.valueOf(orient).equals(passIndSpLinkWrapper.getOrient())) {
                    PassIndSpLinkWrapper parentPassIndSpLinkWrapper = hashMap.get(passIndSpLinkWrapper.getParId());
                    if (parentPassIndSpLinkWrapper == null) {
                        result = passIndSpLinkWrapper;
                    } else {
                        parentPassIndSpLinkWrapper.child = passIndSpLinkWrapper;
                    }
                }
            }
            
            return result;
        }
    }
    
    public PassIndSpLinkWrapper(PassIndSpLink passIndSpLink) {
        super();
        
        this.setId(passIndSpLink.getId());
        this.setParId(passIndSpLink.getParId());
        this.setPassIndLinkId(passIndSpLink.getPassIndLinkId());
        this.setSpId(passIndSpLink.getSpId());
        this.setOrient(passIndSpLink.getOrient());
    }

    public PassIndSpLinkWrapper getChild() {
        return child;
    }

    public void setChild(PassIndSpLinkWrapper child) {
        this.child = child;
    }

    public List<ClassItemTreeWrapper> getDict() {
        return dict;
    }

    public void setDict(List<ClassItemTreeWrapper> dict) {
        this.dict = dict;
    }
    
    public int getDepth() {
        if (child == null) {
            return 1;
        } else {
            return child.getDepth() + 1;
        }
    }
    
    public int getWidth() {
        int width = dict.size();
        
        if (child != null) {
            width *= child.getWidth();
        }
        
        return width;
    }
    
    public int getColWidth() {
        if (child == null) {
            return 1;
        } else {
            return child.getWidth();
        }
    }
}