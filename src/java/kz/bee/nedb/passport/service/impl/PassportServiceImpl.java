package kz.bee.nedb.passport.service.impl;

import java.util.ArrayList;
import java.util.List;
import kz.bee.nedb.passport.dao.PassportDAO;
import kz.bee.nedb.passport.entity.Passport;
import kz.bee.nedb.passport.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PassportServiceImpl implements PassportService {
    @Autowired
    private PassportDAO passportDAO;

    @Override
    @Transactional
    public List<Passport> list(Long parId, Long passType) {
        return passportDAO.list(parId, passType);
    }
    
    @Override
    @Transactional
    public Passport select(Long id) {
        return passportDAO.select(id);
    }
    
    @Override
    @Transactional
    public List<Passport> getBreadcrumbs(Long id) {
        ArrayList<Passport> result = new ArrayList<Passport>();
        
        Passport current = passportDAO.select(id);
        while (current != null) {
            result.add(0, current);
            if (current.getParId() == null) {
                current = null;
            } else {
                current = passportDAO.select(current.getParId());
            }
        }
        
        return result;
    }
}