package kz.bee.nedb.passport.web;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import kz.bee.nedb.passport.entity.Teacher;
import kz.bee.nedb.passport.service.PassportElementService;
import kz.bee.nedb.passport.service.SchoolListService;
import kz.bee.nedb.passport.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TeacherController {
    @Autowired
    private PassportElementService passportElementService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SchoolListService schoolListService;
    
    @RequestMapping(value="teacher-list")
    public String teacherList(
            Map<String, Object> map,
            @RequestParam(value="school", required=false, defaultValue="") Long schoolId
    ) {
        if (schoolId == null) {
            return "redirect:/";
        } else {
            map.put("schoolId", schoolId);
            map.put("school", schoolListService.select(schoolId));
            map.put("teachers", teacherService.listForSchool(schoolId));
            
            return "teacher-list";
        }
    }
    
    @RequestMapping(value="create-teacher", method=RequestMethod.POST)
    public String create(
            Map<String, Object> map,
            @RequestParam(value="school", required=false, defaultValue="") Long schoolId,
            @RequestParam(value="teacher-iin", required=false, defaultValue="") String iin,
            @RequestParam(value="teacher-f", required=false, defaultValue="") String f,
            @RequestParam(value="teacher-i", required=false, defaultValue="") String i,
            @RequestParam(value="teacher-o", required=false, defaultValue="") String o
    ) {
        if (schoolId == null) {
            return "redirect:/";
        } else if ((iin == null) || iin.isEmpty()) {
            return "redirect:/passport?school=" + schoolId;
        } else {
            Teacher teacher = teacherService.create(iin, f, i, o, schoolId);
            
            return new StringBuilder("redirect:/teacher?school=")
                    .append(schoolId)
                    .append("&teacher=")
                    .append(teacher.getId())
                    .toString();
        }
    }
    
    @RequestMapping(value="teacher")
    public String teacher(
            Map<String, Object> map,
            @RequestParam(value="school", required=false) Long schoolId,
            @RequestParam(value="teacher", required=false) Long teacherId
    ) {
        if (schoolId == null) {
            return "redirect:/";
        } else {
            map.put("schoolId", schoolId);
            map.put("teacherId", teacherId);
            map.put("school", schoolListService.select(schoolId));
            
            if (teacherId != null) {
                map.put("teacher", teacherService.select(teacherId));
                map.put("passportElements", passportElementService.listForTeacher(schoolId, teacherId));
            }

            return "teacher";
        }
    }
    
    @RequestMapping(value="save-teacher")
    public String save(
            Map<String, Object> map,
            @RequestParam(value="school", required=true) Long schoolId,
            @RequestParam(value="teacher", required=true) Long teacherId,
            @RequestParam(value="teacher-f", required=false, defaultValue="") String f,
            @RequestParam(value="teacher-i", required=false, defaultValue="") String i,
            @RequestParam(value="teacher-o", required=false, defaultValue="") String o,
            final HttpServletRequest request
    ) {
        passportElementService.saveForTeacher(schoolId, teacherId, f, i, o, request.getParameterMap());
        
        StringBuilder urlBuilder = new StringBuilder("redirect:/teacher?school=")
                .append(schoolId)
                .append("&teacher=")
                .append(teacherId);
        
        return urlBuilder.toString();
    }
    
    @RequestMapping(value="delete-teacher")
    public String delete(
            @RequestParam(value="school", required=false, defaultValue="") Long schoolId,
            @RequestParam(value="teacher", required=false, defaultValue="") Long teacherId
    ) {
        if (schoolId == null) {
            return "redirect:/";
        } else if (teacherId == null) {
            return "redirect:/teacher-list?school=" + schoolId;
        } else {
            teacherService.delete(schoolId, teacherId);
            
            return "redirect:/teacher-list?school=" + schoolId;
        }
    }
}