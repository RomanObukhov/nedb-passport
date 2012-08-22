package kz.bee.nedb.passport.entity.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import kz.bee.nedb.passport.entity.Passport;

public class PassportWrapper extends Passport {
    public static List<Passport> sort(List<Passport> passports) {
        TreeMap<Long, Passport> passportMap = new TreeMap<Long, Passport>();
        for (Passport passport: passports) {
            passportMap.put(passport.getId(), passport);
        }
        
        return new ArrayList<Passport>(passportMap.values());
    }
}