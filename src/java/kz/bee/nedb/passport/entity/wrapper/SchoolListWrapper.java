package kz.bee.nedb.passport.entity.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import kz.bee.nedb.passport.entity.ClassItemTree;
import kz.bee.nedb.passport.entity.SchoolList;

public class SchoolListWrapper extends SchoolList {
    private ClassItemTree classItemTree;
    
    public static List<SchoolList> sort(List<SchoolList> schoolLists) {
        TreeMap<String, SchoolList> schoolListMap = new TreeMap<String, SchoolList>();
        for (SchoolList schoolList: schoolLists) {
            schoolListMap.put(schoolList.getLocalizedName(), schoolList);
        }
        
        return new ArrayList<SchoolList>(schoolListMap.values());
    }
    
    public static List<SchoolListWrapper> sortWrappers(List<SchoolListWrapper> schoolListWrappers) {
        TreeMap<String, SchoolListWrapper> schoolListMap = new TreeMap<String, SchoolListWrapper>();
        for (SchoolListWrapper schoolList: schoolListWrappers) {
            schoolListMap.put(schoolList.getLocalizedName(), schoolList);
        }
        
        return new ArrayList<SchoolListWrapper>(schoolListMap.values());
    }
    
    public SchoolListWrapper(SchoolList schoolList) {
        super();
        
        this.setId(schoolList.getId());
        this.setClassItemTreeId(schoolList.getClassItemTreeId());
        this.setRname(schoolList.getRname());
        this.setKname(schoolList.getKname());
        this.setEname(schoolList.getEname());
        this.setPassConfirmed(schoolList.getPassConfirmed());
        this.setPassApproved(schoolList.getPassApproved());
    }

    public ClassItemTree getClassItemTree() {
        return classItemTree;
    }

    public void setClassItemTree(ClassItemTree classItemTree) {
        this.classItemTree = classItemTree;
    }
}