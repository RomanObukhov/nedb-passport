package kz.bee.nedb.passport.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import kz.bee.nedb.passport.dao.ClassItemTreeDAO;
import kz.bee.nedb.passport.entity.ClassItemTree;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClassItemTreeDAOImpl implements ClassItemTreeDAO {
    @Autowired
    private SessionFactory sessionFactory;
    
    private static Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }
    
    private static String getDateCondition(String currentDateParamName) {
        //begDate
        //endDate
        return new StringBuilder("(((begDate is null) or (begDate <= :")
                .append(currentDateParamName)
                .append(")) and ((endDate is null) or (endDate >= :")
                .append(currentDateParamName)
                .append(")))")
                .toString();
    }

    @Override
    public List<ClassItemTree> list(Long parId) {
        return sessionFactory.getCurrentSession().createQuery("from ClassItemTree where parId = :parId and " + getDateCondition("currentDate"))
                .setLong("parId", parId).setTimestamp("currentDate", getCurrentTime())
                .list();
    }

    @Override
    public List<ClassItemTree> listSp(Long spId) {
        return sessionFactory.getCurrentSession().createQuery("from ClassItemTree where spId = :spId and parId = null and " + getDateCondition("currentDate"))
                .setLong("spId", spId).setTimestamp("currentDate", getCurrentTime())
                .list();
    }
    
    @Override
    public List<ClassItemTree> listSpFull(Long spId) {
//        String sql = "from ClassItemTree where spId = :spId and " + getDateCondition("currentDate") + " || (" + getCurrentTime() + ")";
//        System.out.println("----" + sql);
        return sessionFactory.getCurrentSession().createQuery("from ClassItemTree where spId = :spId and " + getDateCondition("currentDate"))
                .setLong("spId", spId).setTimestamp("currentDate", getCurrentTime())
                .list();
    }

    @Override
    public List<ClassItemTree> listRegions() {
        return listSp(Long.valueOf(60L));
    }
    
    @Override
    public ClassItemTree select(Long id) {
        if (id == null) {
            return null;
        } else {
            return (ClassItemTree) sessionFactory.getCurrentSession().createQuery("from ClassItemTree where id = :id").setLong("id", id).uniqueResult();
        }
    }

    @Override
    public List<ClassItemTree> listKatoByCodePart(String code) {
        return sessionFactory.getCurrentSession().createQuery("from ClassItemTree where spId = 60 and code like :code and " + getDateCondition("currentDate"))
                .setString("code", code + "%").setTimestamp("currentDate", getCurrentTime())
                .list();
    }
}