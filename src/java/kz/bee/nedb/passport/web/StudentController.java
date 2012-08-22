package kz.bee.nedb.passport.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import kz.bee.nedb.passport.classes.DBTags;
import kz.bee.nedb.passport.entity.ClassItemTree;
import kz.bee.nedb.passport.entity.SchoolStudentLink;
import kz.bee.nedb.passport.entity.Student;
import kz.bee.nedb.passport.service.ClassItemTreeService;
import kz.bee.nedb.passport.service.PassportElementService;
import kz.bee.nedb.passport.service.SchoolListService;
import kz.bee.nedb.passport.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {
    @Autowired
    private PassportElementService passportElementService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SchoolListService schoolListService;
    @Autowired
    private ClassItemTreeService classItemTreeService;
    
    @RequestMapping(value="student-list")
    public String studentList(
            Map<String, Object> map,
            @RequestParam(value="school", required=false, defaultValue="") Long schoolId
    ) {
        if (schoolId == null) {
            return "redirect:/";
        } else {
            List<ClassItemTree> classes = classItemTreeService.getBySpTag(DBTags.CLASS_TAG);
            TreeMap<Long, ClassItemTree> classMap = new TreeMap<Long, ClassItemTree>();
            for (ClassItemTree clazz: classes) {
                classMap.put(clazz.getId(), clazz);
            }
            
            List<SchoolStudentLink> links = studentService.getLinksBySchool(schoolId);
            HashMap<Long, SchoolStudentLink> linkMap = new HashMap<Long, SchoolStudentLink>();
            for (SchoolStudentLink link: links) {
                linkMap.put(link.getStudentId(), link);
            }
            
            map.put("schoolId", schoolId);
            map.put("school", schoolListService.select(schoolId));
            map.put("students", studentService.listForSchool(schoolId));
            map.put("classMap", classMap);
            map.put("linkMap", linkMap);
            
            return "student-list";
        }
    }
    
    @RequestMapping(value="create-student", method=RequestMethod.POST)
    public String create(
            Map<String, Object> map,
            @RequestParam(value="school", required=false, defaultValue="") Long schoolId,
            @RequestParam(value="student-iin", required=false, defaultValue="") String iin,
            @RequestParam(value="student-f", required=false, defaultValue="") String f,
            @RequestParam(value="student-i", required=false, defaultValue="") String i,
            @RequestParam(value="student-o", required=false, defaultValue="") String o,
            @RequestParam(value="student-class", required=false, defaultValue="") Long classId,
            @RequestParam(value="student-parallel", required=false, defaultValue="") String parallel
    ) {
        if (schoolId == null) {
            return "redirect:/";
        } else if ((iin == null) || iin.isEmpty() || (classId == null)) {
            return "redirect:/passport?school=" + schoolId;
        } else {
            Student student = studentService.create(iin, f, i, o, classId, parallel, schoolId);
            
            return new StringBuilder("redirect:/student?school=")
                    .append(schoolId)
                    .append("&student=")
                    .append(student.getId())
                    .toString();
        }
    }
    
    @RequestMapping(value="student")
    public String student(
            Map<String, Object> map,
            @RequestParam(value="school", required=false) Long schoolId,
            @RequestParam(value="student", required=false) Long studentId
    ) {
        if (schoolId == null) {
            return "redirect:/";
        } else {
            List<ClassItemTree> classes = classItemTreeService.getBySpTag(DBTags.CLASS_TAG);
            TreeMap<Long, ClassItemTree> classMap = new TreeMap<Long, ClassItemTree>();
            for (ClassItemTree clazz: classes) {
                classMap.put(clazz.getId(), clazz);
            }
            
            map.put("schoolId", schoolId);
            map.put("studentId", studentId);
            map.put("school", schoolListService.select(schoolId));
            map.put("classMap", classMap);
            
            if (studentId != null) {
                map.put("student", studentService.select(studentId));
                map.put("link", studentService.getSchoolStudentLink(schoolId, studentId));
                map.put("passportElements", passportElementService.listForStudent(schoolId, studentId));
            }

            return "student";
        }
    }
    
    @RequestMapping(value="save-student")
    public String save(
            Map<String, Object> map,
            @RequestParam(value="school", required=true) Long schoolId,
            @RequestParam(value="student", required=true) Long studentId,
            @RequestParam(value="student-f", required=false, defaultValue="") String f,
            @RequestParam(value="student-i", required=false, defaultValue="") String i,
            @RequestParam(value="student-o", required=false, defaultValue="") String o,
            @RequestParam(value="student-class", required=false, defaultValue="") Long classId,
            @RequestParam(value="student-parallel", required=false, defaultValue="") String parallel,
            final HttpServletRequest request
    ) {
        passportElementService.saveForStudent(schoolId, studentId, f, i, o, classId, parallel, request.getParameterMap());
        
        StringBuilder urlBuilder = new StringBuilder("redirect:/student?school=")
                .append(schoolId)
                .append("&student=")
                .append(studentId);
        
        return urlBuilder.toString();
    }
    
    @RequestMapping(value="delete-student")
    public String delete(
            @RequestParam(value="school", required=false, defaultValue="") Long schoolId,
            @RequestParam(value="student", required=false, defaultValue="") Long studentId
    ) {
        if (schoolId == null) {
            return "redirect:/";
        } else if (studentId == null) {
            return "redirect:/student-list?school=" + schoolId;
        } else {
            studentService.delete(schoolId, studentId);
            
            return "redirect:/student-list?school=" + schoolId;
        }
    }
}