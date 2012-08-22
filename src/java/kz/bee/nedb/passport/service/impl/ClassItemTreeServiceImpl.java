package kz.bee.nedb.passport.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;
import kz.bee.nedb.passport.dao.ClassItemTreeDAO;
import kz.bee.nedb.passport.dao.SpClassDAO;
import kz.bee.nedb.passport.entity.ClassItemTree;
import kz.bee.nedb.passport.entity.SpClass;
import kz.bee.nedb.passport.service.ClassItemTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClassItemTreeServiceImpl implements ClassItemTreeService {
    @Autowired
    private ClassItemTreeDAO classItemTreeDAO;
    @Autowired
    private SpClassDAO spClassDAO;

    @Override
    @Transactional
    public List<ClassItemTree> list(Long parId) {
        return classItemTreeDAO.list(parId);
    }

    @Override
    @Transactional
    public List<ClassItemTree> listSp(Long spId) {
        return classItemTreeDAO.listSp(spId);
    }

    @Override
    @Transactional
    public List<ClassItemTree> listRegions() {
        return classItemTreeDAO.listRegions();
    }
    
    @Override
    @Transactional
    public ClassItemTree select(Long id) {
        return classItemTreeDAO.select(id);
    }
    
    @Override
    @Transactional
    public List<ClassItemTree> getBreadcrumbs(Long id) {
        ArrayList<ClassItemTree> result = new ArrayList<ClassItemTree>();
        
        ClassItemTree classItemTree = classItemTreeDAO.select(id);
        while (classItemTree != null) {
            result.add(0, classItemTree);
            if (classItemTree.getParId() == null) {
                classItemTree = null;
            } else {
                classItemTree = classItemTreeDAO.select(classItemTree.getParId());
            }
        }
        
        return result;
    }
    
    @Override
    @Transactional
    public List<ClassItemTree> getBySpTag(Long tag) {
        SpClass spClass = spClassDAO.selectByTag(tag);
        if (spClass == null) {
            return new ArrayList<ClassItemTree>();
        } else {
            return classItemTreeDAO.listSpFull(spClass.getId());
        }
    }
}