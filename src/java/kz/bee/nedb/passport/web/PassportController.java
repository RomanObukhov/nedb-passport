package kz.bee.nedb.passport.web;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import kz.bee.nedb.passport.classes.DBTags;
import kz.bee.nedb.passport.entity.ClassItemTree;
import kz.bee.nedb.passport.entity.Passport;
import kz.bee.nedb.passport.entity.SchoolList;
import kz.bee.nedb.passport.entity.wrapper.ClassItemTreeWrapper;
import kz.bee.nedb.passport.entity.wrapper.PassportWrapper;
import kz.bee.nedb.passport.service.ClassItemTreeService;
import kz.bee.nedb.passport.service.FLCErrorService;
import kz.bee.nedb.passport.service.PassportElementService;
import kz.bee.nedb.passport.service.PassportService;
import kz.bee.nedb.passport.service.SchoolListService;
import kz.bee.nedb.passport.service.TeacherService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PassportController {
    private static final Logger LOGGER = Logger.getLogger(PassportController.class);
    
    @Autowired
    private SchoolListService schoolListService;
    @Autowired
    private PassportService passportService;
    @Autowired
    private ClassItemTreeService classItemTreeService;
    @Autowired
    private PassportElementService passportElementService;
    @Autowired
    private FLCErrorService flcErrorService;
    @Autowired
    private TeacherService teacherService;
    
    @RequestMapping(value="/passport")
    public String passport(Map<String, Object> map,
            @RequestParam(value="school", required=false) Long schoolId,
            @RequestParam(value="passport", required=false, defaultValue="") Long pasportId
    ) {
        if (schoolId == null) {
            return "redirect:/";
        } else {
            List<Passport> passports = PassportWrapper.sort(passportService.list(pasportId, 0L));
            SchoolList schoolList = schoolListService.select(schoolId);
            
            if (pasportId == null) {
                map.put("orgTypes", ClassItemTreeWrapper.sort(classItemTreeService.getBySpTag(DBTags.ORG_TYPE_TAG)));
            }
            
            map.put("schoolId", schoolId);
            map.put("pasportId", pasportId);

            map.put("school", schoolList);
            map.put("passports", passports);
            map.put("breadcrumbs", passportService.getBreadcrumbs(pasportId));
            map.put("passport", passportService.select(pasportId));
            map.put("navBreadcrumbs", classItemTreeService.getBreadcrumbs(schoolList.getClassItemTreeId()));

            if (pasportId != null) {
                map.put("passportElements", passportElementService.listForSchool(pasportId, schoolId));
            } else {
                map.put("teachers", teacherService.listForSchool(schoolId));
            }
            
            map.put("flcErrors", flcErrorService.listErrors(schoolId));

            return "passport";
        }
    }
    
    @RequestMapping(value="/save-passport", method=RequestMethod.POST)
    public String save(Map<String, Object> map,
            @RequestParam(value="school", required=true) Long schoolId,
            @RequestParam(value="passport", required=true) Long pasportId,
            final HttpServletRequest request
    ) {
        passportElementService.saveForSchool(schoolId, pasportId, request.getParameterMap());
        
        StringBuilder urlBuilder = new StringBuilder("redirect:/passport?school=")
                .append(schoolId)
                .append("&passport=")
                .append(pasportId);
        
        return urlBuilder.toString();
    }
    
    @RequestMapping(value="/save-school-data", method=RequestMethod.POST)
    public String saveSchoolData(
            @RequestParam(value="school", required=true) Long schoolId,
            @RequestParam(value="rname", required=false, defaultValue="") String rname,
            @RequestParam(value="kname", required=false, defaultValue="") String kname,
            @RequestParam(value="ename", required=false, defaultValue="") String ename,
            @RequestParam(value="orgType", required=true) Long orgTypeId
    ) {
        SchoolList schoolList = schoolListService.select(schoolId);
        
        if (schoolList != null) {
            schoolList.setRname(rname);
            schoolList.setKname(kname);
            schoolList.setEname(ename);
            schoolList.setOrgTypeId(orgTypeId);
            schoolListService.save(schoolList);
        }
        
        return new StringBuilder("redirect:/passport?school=")
                .append(schoolId)
                .toString();
    }
    
    @RequestMapping(value="confirm-passport")
    public String confirm(
            @RequestParam(value="school", required=true) Long schoolId,
            @RequestParam(value="passport", required=false, defaultValue="") Long pasportId
    ) {
        schoolListService.confirm(schoolId);
        
        StringBuilder urlBuilder = new StringBuilder("redirect:/passport?school=")
                .append(schoolId);
        if (pasportId != null) {
            urlBuilder
                    .append("&passport=")
                    .append(pasportId);
        }
        
        return urlBuilder.toString();
    }
    
    @RequestMapping(value="approve-passport")
    public String approve (
            @RequestParam(value="nav", required=false, defaultValue="") Long navId,
            @RequestParam(value="school", required=false, defaultValue="") Long schoolId
    ) {
        schoolListService.approve(schoolId);
        
        if (navId != null) {
            return "redirect:/navigation?id=" + navId;
        } else {
            return "redirect:/navigation";
        }
    }
}