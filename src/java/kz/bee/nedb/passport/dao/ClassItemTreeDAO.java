package kz.bee.nedb.passport.dao;

import java.util.List;
import kz.bee.nedb.passport.entity.ClassItemTree;

public interface ClassItemTreeDAO {
    public List<ClassItemTree> list(Long parId);
    public List<ClassItemTree> listSp(Long spId);
    public List<ClassItemTree> listSpFull(Long spId);
    public List<ClassItemTree> listRegions();
    public ClassItemTree select(Long id);
    public List<ClassItemTree> listKatoByCodePart(String code);
}