package kz.bee.nedb.passport.service.impl;

import java.util.ArrayList;
import java.util.List;
import kz.bee.nedb.passport.dao.ClassItemTreeDAO;
import kz.bee.nedb.passport.dao.SchoolListDAO;
import kz.bee.nedb.passport.entity.ClassItemTree;
import kz.bee.nedb.passport.entity.SchoolList;
import kz.bee.nedb.passport.entity.wrapper.ClassItemTreeWrapper;
import kz.bee.nedb.passport.entity.wrapper.SchoolListWrapper;
import kz.bee.nedb.passport.service.ClassItemTreeService;
import kz.bee.nedb.passport.service.SchoolListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchoolListServiceImpl implements SchoolListService {
    @Autowired
    private SchoolListDAO schoolListDAO;
    @Autowired
    private ClassItemTreeDAO classItemTreeDAO;
    
    private List<SchoolListWrapper> nonTransListForKato(ClassItemTree classItemTree) {
        ArrayList<SchoolListWrapper> schoolListWrappers = new ArrayList<SchoolListWrapper>();
        
        if (classItemTree != null) {
            String code = classItemTree.getCode(); // 000000000
            if (code.substring(0, 2).equals("00")) {
                code = "";
            } else if (code.substring(2, 4).equals("00")) {
                code = code.substring(0, 2);
            } else if (code.substring(4, 6).equals("00")) {
                code = code.substring(0, 4);
            } else if (code.substring(6).equals("000")) {
                code = code.substring(0, 6);
            }
            
            List<ClassItemTree> classItemTrees = ClassItemTreeWrapper.sort(classItemTreeDAO.listKatoByCodePart(code));
            List<SchoolListWrapper> katoSchoolListWrappers = new ArrayList<SchoolListWrapper>();
            for (ClassItemTree katoItem: classItemTrees) {
                katoSchoolListWrappers.clear();
                for (SchoolList schoolList: schoolListDAO.list(katoItem.getId())) {
                    SchoolListWrapper schoolListWrapper = new SchoolListWrapper(schoolList);
                    schoolListWrapper.setClassItemTree(katoItem);
                    katoSchoolListWrappers.add(schoolListWrapper);
                }
                schoolListWrappers.addAll(SchoolListWrapper.sortWrappers(katoSchoolListWrappers));
            }
//            for (SchoolList schoolList: schoolListDAO.list(classItemTree.getId())) {
//                SchoolListWrapper schoolListWrapper = new SchoolListWrapper(schoolList);
//                schoolListWrapper.setClassItemTree(classItemTree);
//                schoolListWrappers.add(schoolListWrapper);
//            }
//            
//            for (ClassItemTree child: classItemTreeDAO.list(classItemTree.getId())) {
//                schoolListWrappers.addAll(nonTransListForKato(child));
//            }
        }
        
        return schoolListWrappers;
    }
    
    @Override
    @Transactional
    public List<SchoolList> list(Long classItemTreeId) {
        return schoolListDAO.list(classItemTreeId);
    }
    
    @Override
    @Transactional
    public SchoolList select(Long id) {
        return schoolListDAO.select(id);
    }

    @Override
    @Transactional
    public void confirm(Long id) {
        schoolListDAO.confirm(id);
    }
    
    @Override
    @Transactional
    public void approve(Long id) {
        schoolListDAO.approve(id);
    }

    @Override
    @Transactional
    public List<SchoolListWrapper> listForKato(Long classItemTreeId) {
        ClassItemTree classItemTree = classItemTreeDAO.select(classItemTreeId);
        
        return nonTransListForKato(classItemTree);
    }
    
    @Override
    @Transactional
    public void save(SchoolList schoolList) {
        schoolListDAO.save(schoolList);
    }
}