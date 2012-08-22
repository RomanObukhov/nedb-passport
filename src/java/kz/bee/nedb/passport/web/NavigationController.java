package kz.bee.nedb.passport.web;

import java.util.List;
import java.util.Map;
import kz.bee.nedb.passport.entity.ClassItemTree;
import kz.bee.nedb.passport.entity.wrapper.ClassItemTreeWrapper;
import kz.bee.nedb.passport.entity.wrapper.SchoolListWrapper;
import kz.bee.nedb.passport.service.ClassItemTreeService;
import kz.bee.nedb.passport.service.SchoolListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NavigationController {
    @Autowired
    private ClassItemTreeService classItemTreeService;
    @Autowired
    private SchoolListService schoolListService;
    
    @RequestMapping(value="/")
    public String home(Map<String, Object> map) {
        map.put("navItems", ClassItemTreeWrapper.sort(classItemTreeService.listRegions()));
        
        return "home";
    }
    
    @RequestMapping(value="/navigation")
    public String navigation(Map<String, Object> map,
            @RequestParam(value="id", required=true) Long id
    ) {
        List<ClassItemTree> classItemTrees = classItemTreeService.list(id);
        List<ClassItemTree> classItemTreesSorted = ClassItemTreeWrapper.sort(classItemTrees);
        List<ClassItemTree> breadcrumbs = classItemTreeService.getBreadcrumbs(id);
        
        map.put("navId", id);
        map.put("navItems", classItemTreesSorted);
//        map.put("schools", SchoolListWrapper.sort(schoolListService.list(id)));
        map.put("schools", schoolListService.listForKato(id));
        map.put("breadcrumbs", breadcrumbs);
        
        return "navigation";
    }
}