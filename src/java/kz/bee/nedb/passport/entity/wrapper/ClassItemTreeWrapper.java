package kz.bee.nedb.passport.entity.wrapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;
import kz.bee.nedb.passport.entity.ClassItemTree;
import org.springframework.context.i18n.LocaleContextHolder;

public class ClassItemTreeWrapper extends ClassItemTree {
    private List<ClassItemTreeWrapper> children = new ArrayList<ClassItemTreeWrapper>();
    
    public static List<ClassItemTree> sort(List<ClassItemTree> classItemTrees) {
        TreeMap<String, ClassItemTree> classItemTreeMap = new TreeMap<String, ClassItemTree>();
        for (ClassItemTree classItemTree: classItemTrees) {
            classItemTreeMap.put(classItemTree.getCode(), classItemTree);
        }
        
        return new ArrayList<ClassItemTree>(classItemTreeMap.values());
    }
    
    public static List<ClassItemTreeWrapper> wrap(List<ClassItemTree> classItemTrees) {
        int count = 0;
        TreeMap<String, ClassItemTreeWrapper> classItemTreeMap = new TreeMap<String, ClassItemTreeWrapper>();
        for (ClassItemTree classItemTree: classItemTrees) {
            classItemTreeMap.put(classItemTree.getCode(), new ClassItemTreeWrapper(classItemTree));
        }
        
        return new ArrayList<ClassItemTreeWrapper>(classItemTreeMap.values());
    }
    
    public static List<ClassItemTreeWrapper> buildTree(List<ClassItemTreeWrapper> classItemTreeWrappers) {
        if ((classItemTreeWrappers == null) || classItemTreeWrappers.isEmpty()) {
            return classItemTreeWrappers;
        } else {
            HashMap<Long, ClassItemTreeWrapper> hashMap = new HashMap<Long, ClassItemTreeWrapper>();
            for (ClassItemTreeWrapper classItemTreeWrapper: classItemTreeWrappers) {
                hashMap.put(classItemTreeWrapper.getId(), classItemTreeWrapper);
            }
            
            ArrayList<ClassItemTreeWrapper> result = new ArrayList<ClassItemTreeWrapper>();
            
            for (ClassItemTreeWrapper classItemTreeWrapper: classItemTreeWrappers) {
                ClassItemTreeWrapper parentClassItemTreeWrapper = hashMap.get(classItemTreeWrapper.getParId());
                if (parentClassItemTreeWrapper == null) {
                    result.add(classItemTreeWrapper);
                } else {
                    parentClassItemTreeWrapper.addChild(classItemTreeWrapper);
                }
            }
            
            return result;
        }
    }
    
    public ClassItemTreeWrapper(ClassItemTree classItemTree) {
        super();
        
        this.setId(classItemTree.getId());
        this.setParId(classItemTree.getParId());
        this.setSpId(classItemTree.getSpId());
        this.setRname(classItemTree.getRname());
        this.setKname(classItemTree.getKname());
        this.setEname(classItemTree.getEname());
        this.setBegDate(classItemTree.getBegDate());
        this.setEndDate(classItemTree.getEndDate());
        this.setUpdTime(classItemTree.getUpdTime());
        this.setTreeId(classItemTree.getTreeId());
        this.setCode(classItemTree.getCode());
        this.setAttr1(classItemTree.getAttr1());
    }

    public List<ClassItemTreeWrapper> getChildren() {
        return children;
    }

    public void setChildren(List<ClassItemTreeWrapper> children) {
        this.children = children;
    }
    
    public void addChild(ClassItemTreeWrapper child) {
        children.add(child);
    }
    
    public String toOption(int level, Long selected) {
        StringBuilder stringBuilder = new StringBuilder("<option value=\"")
                .append(getId())
                .append("\"");
        
        if (getId().equals(selected)) {
            stringBuilder.append(" selected>");
        } else {
            stringBuilder.append(">");
        }
        
        for (int i = 0; i < level; i++) {
            stringBuilder.append("&nbsp;&nbsp;");
        }
        
        stringBuilder
//                .append(getCode())
//                .append(" - ")
                .append(getLocalizedName())
                .append("</option>");
        
        if ((children != null) && !children.isEmpty()) {
            for (ClassItemTreeWrapper child: children) {
                stringBuilder.append(child.toOption(level + 1, selected));
            }
        }
        
        return stringBuilder.toString();
    }
    
    public String toListItem(List<Long> selected, String namePrefix, String idPrefix) {
        StringBuilder stringBuilder = new StringBuilder("<li>");
        
        if ((children == null) || children.isEmpty()) {
            stringBuilder
                    .append("<input type=\"checkbox\" name=\"")
                    .append(namePrefix)
                    .append("-")
                    .append(getId())
                    .append("\" id=\"")
                    .append(idPrefix)
                    .append("-")
                    .append(getId())
                    .append("\"");
            
            if (selected.contains(getId())) {
                stringBuilder.append(" checked/>");
            } else {
                stringBuilder.append("/>");
            }
            
            stringBuilder
                    .append(getLocalizedName());
        } else {
            stringBuilder
                    .append(getLocalizedName())
                    .append("<ul>");
            
            for (ClassItemTreeWrapper child: children) {
                stringBuilder.append(child.toListItem(selected, namePrefix, idPrefix));
            }
            
            stringBuilder.append("</ul>");
        }
        
        return stringBuilder
                .append("</li>")
                .toString();
    }
}