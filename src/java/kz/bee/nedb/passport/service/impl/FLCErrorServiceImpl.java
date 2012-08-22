package kz.bee.nedb.passport.service.impl;

import java.util.ArrayList;
import java.util.List;
import kz.bee.nedb.passport.dao.FLCErrorDAO;
import kz.bee.nedb.passport.entity.ClassItemTree;
import kz.bee.nedb.passport.entity.FLCError;
import kz.bee.nedb.passport.service.ClassItemTreeService;
import kz.bee.nedb.passport.service.FLCErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FLCErrorServiceImpl implements FLCErrorService{
    @Autowired
    private FLCErrorDAO flcErrorDAO;
    @Autowired
    private ClassItemTreeService classItemTreeService;

    @Override
    @Transactional
    public List<ClassItemTree> listErrors(Long schoolId) {
        List<FLCError> flcErrors =  flcErrorDAO.list(schoolId);
        ArrayList<ClassItemTree> classItemTrees = new ArrayList<ClassItemTree>();
        for (FLCError flcError: flcErrors) {
            ClassItemTree classItemTree = classItemTreeService.select(flcError.getClassItemTreeId());
            if (classItemTree != null) {
                classItemTrees.add(classItemTree);
            }
        }
        return classItemTrees;
    }

    @Override
    @Transactional
    public void save(FLCError flcError) {
        flcErrorDAO.save(flcError);
    }
}