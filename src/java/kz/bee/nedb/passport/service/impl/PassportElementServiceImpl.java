package kz.bee.nedb.passport.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kz.bee.nedb.passport.classes.PassportElement;
import kz.bee.nedb.passport.classes.StudentElement;
import kz.bee.nedb.passport.classes.TeacherElement;
import kz.bee.nedb.passport.consts.Consts;
import kz.bee.nedb.passport.dao.ClassItemTreeDAO;
import kz.bee.nedb.passport.dao.FLCErrorDAO;
import kz.bee.nedb.passport.dao.IndicatorDAO;
import kz.bee.nedb.passport.dao.MeasureDAO;
import kz.bee.nedb.passport.dao.OrgTypeCITLinkDAO;
import kz.bee.nedb.passport.dao.OrgTypeIndLinkDAO;
import kz.bee.nedb.passport.dao.PassDataDAO;
import kz.bee.nedb.passport.dao.PassIndLinkDAO;
import kz.bee.nedb.passport.dao.PassIndSpLinkDAO;
import kz.bee.nedb.passport.dao.PassportDAO;
import kz.bee.nedb.passport.dao.SchoolListDAO;
import kz.bee.nedb.passport.dao.SchoolStudentLinkDAO;
import kz.bee.nedb.passport.dao.StudentDAO;
import kz.bee.nedb.passport.dao.StudentDataDAO;
import kz.bee.nedb.passport.dao.TeacherDAO;
import kz.bee.nedb.passport.dao.TeacherDataDAO;
import kz.bee.nedb.passport.entity.ClassItemTree;
import kz.bee.nedb.passport.entity.FLCError;
import kz.bee.nedb.passport.entity.Indicator;
import kz.bee.nedb.passport.entity.Measure;
import kz.bee.nedb.passport.entity.OrgTypeCITLink;
import kz.bee.nedb.passport.entity.OrgTypeIndLink;
import kz.bee.nedb.passport.entity.PassData;
import kz.bee.nedb.passport.entity.PassIndLink;
import kz.bee.nedb.passport.entity.PassIndSpLink;
import kz.bee.nedb.passport.entity.Passport;
import kz.bee.nedb.passport.entity.SchoolList;
import kz.bee.nedb.passport.entity.SchoolStudentLink;
import kz.bee.nedb.passport.entity.Student;
import kz.bee.nedb.passport.entity.StudentData;
import kz.bee.nedb.passport.entity.Teacher;
import kz.bee.nedb.passport.entity.TeacherData;
import kz.bee.nedb.passport.entity.wrapper.ClassItemTreeWrapper;
import kz.bee.nedb.passport.entity.wrapper.PassIndLinkWrapper;
import kz.bee.nedb.passport.entity.wrapper.PassIndSpLinkWrapper;
import kz.bee.nedb.passport.service.PassportElementService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PassportElementServiceImpl implements PassportElementService {
    private static final Logger LOGGER = Logger.getLogger(PassportElementServiceImpl.class);
    
    @Autowired
    private PassIndLinkDAO passIndLinkDAO;
    @Autowired
    private MeasureDAO measureDAO;
    @Autowired
    private IndicatorDAO indicatorDAO;
    @Autowired
    private PassIndSpLinkDAO passIndSpLinkDAO;
    @Autowired
    private PassDataDAO passDataDAO;
    @Autowired
    private ClassItemTreeDAO classItemTreeDAO;
    @Autowired
    private SchoolListDAO schoolListDAO;
    @Autowired
    private FLCErrorDAO flcErrorDAO;
    @Autowired
    private PassportDAO passportDAO;
    @Autowired
    private TeacherDataDAO teacherDataDAO;
    @Autowired
    private TeacherDAO teacherDAO;
    @Autowired
    private StudentDataDAO studentDataDAO;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private SchoolStudentLinkDAO schoolStudentLinkDAO;
    @Autowired
    private OrgTypeCITLinkDAO orgTypeCITLinkDAO;
    @Autowired
    private OrgTypeIndLinkDAO orgTypeIndLinkDAO;
    
    private List<PassIndLink> filter(Long orgTypeId, List<PassIndLink> passIndLinks) {
        List<PassIndLink> result = new ArrayList<PassIndLink>();
        
        for (PassIndLink passIndLink: passIndLinks) {
            List<OrgTypeIndLink> orgTypeIndLinks = orgTypeIndLinkDAO.listByPassIndLinkId(passIndLink.getId());
            
            if ((orgTypeIndLinks == null) || orgTypeIndLinks.isEmpty()) {
                result.add(passIndLink);
            } else {
                for (OrgTypeIndLink orgTypeIndLink: orgTypeIndLinks) {
                    if (orgTypeId.equals(orgTypeIndLink.getClassItemTreeId())) {
                        result.add(passIndLink);
                        break;
                    }
                }
            }
        }
        
        return result;
    }
    
    private List<ClassItemTree> filter(Long otClassItemTreeId, List<ClassItemTree> classItemTrees, Map<Long, Set<Long>> orgTypeCITLinkMap) {
        List<ClassItemTree> result = new ArrayList<ClassItemTree>();
        
        for (ClassItemTree classItemTree: classItemTrees) {
            Set<Long> orgTypeCITLinkSet = orgTypeCITLinkMap.get(classItemTree.getId());
            
            if (orgTypeCITLinkSet == null) {
                orgTypeCITLinkSet = new HashSet<Long>();
                orgTypeCITLinkMap.put(classItemTree.getId(), orgTypeCITLinkSet);
                
                List<OrgTypeCITLink> orgTypeCITLinks = orgTypeCITLinkDAO.listByClassItemTreeId(classItemTree.getId());
                if ((orgTypeCITLinks != null) && !orgTypeCITLinks.isEmpty()) {
                    for (OrgTypeCITLink orgTypeCITLink: orgTypeCITLinks) {
                        orgTypeCITLinkSet.add(orgTypeCITLink.getOtClassItemTreeId());
                    }
                }
            }
            
            if (orgTypeCITLinkSet.isEmpty() || orgTypeCITLinkSet.contains(otClassItemTreeId)) {
                result.add(classItemTree);
            }
        }
        
        return result;
    }

    @Override
    @Transactional
    public List<PassportElement> listForSchool(Long passId, Long schoolId) {
        SchoolList schoolList = schoolListDAO.select(schoolId);
        
        Map<Long, Set<Long>> orgTypeCITLinkMap = new HashMap<Long, Set<Long>>();
        
        List<PassIndLink> passIndLinks = PassIndLinkWrapper.sort(filter(schoolList.getOrgTypeId(), passIndLinkDAO.list(passId)));
        
        HashMap<Long, Measure> measureMap = new HashMap<Long, Measure>();
        HashMap<Long, Indicator> indicatorMap = new HashMap<Long, Indicator>();
        HashMap<Long, List<ClassItemTreeWrapper>> dictMap = new HashMap<Long, List<ClassItemTreeWrapper>>();
        
        ArrayList<PassportElement> passportElements = new ArrayList<PassportElement>();
        for (PassIndLink passIndLink: passIndLinks) {
            Long id = passIndLink.getMeasureId();
            Measure measure;
            if (id == null) {
                measure = null;
            } else if (measureMap.containsKey(id)) {
                measure = measureMap.get(id);
            } else {
                measure = measureDAO.select(id);
                measureMap.put(id, measure);
            }
            
            id = passIndLink.getIndId();
            Indicator indicator;
            if (id == null) {
                indicator = null;
            } else if (indicatorMap.containsKey(id)) {
                indicator = indicatorMap.get(id);
            } else {
                indicator = indicatorDAO.select(id);
                indicatorMap.put(id, indicator);
            }
            
            PassportElement passportElement = new PassportElement(
                        passIndLink,
                        measure,
                        indicator
                    );
            
            if (passIndLink.getDatatype() != null) {
                PassIndSpLink passIndSpLink;
                switch (passIndLink.getDatatype().intValue()) {
                    case 0: // 0 - число
                    case 1: // 1 - дата
                    case 2: // 2 - логическое значение
                    case 4: // 4 - текстовое
                    case 5: // 5 - многострочный текст
                        passportElement.setPassData(passDataDAO.selectByPassIndLink(passIndLink.getId(), schoolId));
                        passportElements.add(passportElement);
                        break;
                    case 3: // 3 - значение из справочника
                        passIndSpLink = passIndSpLinkDAO.select(passIndLink.getId());
                        if (passIndSpLink != null) {
                            passportElement.setPassIndSpLink(passIndSpLink);
                            passportElement.setPassData(passDataDAO.selectByPassIndLink(passIndLink.getId(), schoolId));
                            
                            if (passIndSpLink.getSpId() != null) {
                                List<ClassItemTreeWrapper> classItemTreeWrappers = dictMap.get(passIndSpLink.getSpId());
                                if (classItemTreeWrappers == null) {
//                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(ClassItemTreeWrapper.filter(classItemTreeDAO.listSpFull(passIndSpLink.getSpId()), citIds)));
                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(filter(schoolList.getOrgTypeId(), classItemTreeDAO.listSpFull(passIndSpLink.getSpId()), orgTypeCITLinkMap)));
                                    dictMap.put(passIndSpLink.getSpId(), classItemTreeWrappers);
                                }
                                
                                passportElement.putDict(passIndSpLink.getSpId(), classItemTreeWrappers);
                                passportElements.add(passportElement);
                            }
                        }
                        break;
                    case 6: // 6 - таблица
                        List<PassIndSpLinkWrapper> passIndSpLinkWrappers = PassIndSpLinkWrapper.wrap(passIndSpLinkDAO.list(passIndLink.getId()));
                        if ((passIndSpLinkWrappers != null) && !passIndSpLinkWrappers.isEmpty()) {
                            for (PassIndSpLinkWrapper passIndSpLinkWrapper: passIndSpLinkWrappers) {
                                List<ClassItemTreeWrapper> classItemTreeWrappers = dictMap.get(passIndSpLinkWrapper.getSpId());
                                if (classItemTreeWrappers == null) {
//                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(ClassItemTreeWrapper.filter(classItemTreeDAO.listSpFull(passIndSpLinkWrapper.getSpId()), citIds)));
                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(filter(schoolList.getOrgTypeId(), classItemTreeDAO.listSpFull(passIndSpLinkWrapper.getSpId()), orgTypeCITLinkMap)));
                                    dictMap.put(passIndSpLinkWrapper.getSpId(), classItemTreeWrappers);
                                }
                                
                                passIndSpLinkWrapper.setDict(classItemTreeWrappers);
                            }

                            passportElement.setHorPassIndSpLinkWrapper(PassIndSpLinkWrapper.buildTree(passIndSpLinkWrappers, false));
                            passportElement.setVerPassIndSpLinkWrapper(PassIndSpLinkWrapper.buildTree(passIndSpLinkWrappers, true));
                            passportElement.setListPassData(passDataDAO.listByPassIndLink(passIndLink.getId(), schoolId));
                            passportElements.add(passportElement);
                        }
                        break;
                    case 7: // 7 - группа логических значений
                        passIndSpLink = passIndSpLinkDAO.select(passIndLink.getId());
                        if (passIndSpLink != null) {
                            passportElement.setPassIndSpLink(passIndSpLink);
                            passportElement.setListPassData(passDataDAO.listByPassIndLink(passIndLink.getId(), schoolId));
                            
                            if (passIndSpLink.getSpId() != null) {
                                List<ClassItemTreeWrapper> classItemTreeWrappers = dictMap.get(passIndSpLink.getSpId());
                                if (classItemTreeWrappers == null) {
//                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(ClassItemTreeWrapper.filter(classItemTreeDAO.listSpFull(passIndSpLink.getSpId()), citIds)));
                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(filter(schoolList.getOrgTypeId(), classItemTreeDAO.listSpFull(passIndSpLink.getSpId()), orgTypeCITLinkMap)));
                                    dictMap.put(passIndSpLink.getSpId(), classItemTreeWrappers);
                                }
                                
                                passportElement.putDict(passIndSpLink.getSpId(), classItemTreeWrappers);
                                passportElements.add(passportElement);
                            }
                        }
                        break;
                }
            }
        }
        
        return passportElements;
    }

    @Override
    @Transactional
    public List<TeacherElement> listForTeacher(Long schoolId, Long teacherId) {
        SchoolList schoolList = schoolListDAO.select(schoolId);
        
        Map<Long, Set<Long>> orgTypeCITLinkMap = new HashMap<Long, Set<Long>>();
        
        List<Passport> passports = passportDAO.listForType(1L);
        List<PassIndLink> passIndLinks = new ArrayList<PassIndLink>();
        
        for (Passport passport: passports) {
            passIndLinks.addAll(PassIndLinkWrapper.sort(filter(schoolList.getOrgTypeId(), passIndLinkDAO.list(passport.getId()))));
        }
        
        passIndLinks = PassIndLinkWrapper.sort(passIndLinks);
        
        HashMap<Long, Measure> measureMap = new HashMap<Long, Measure>();
        HashMap<Long, Indicator> indicatorMap = new HashMap<Long, Indicator>();
        HashMap<Long, List<ClassItemTreeWrapper>> dictMap = new HashMap<Long, List<ClassItemTreeWrapper>>();
        
        ArrayList<TeacherElement> teacherElements = new ArrayList<TeacherElement>();
        for (PassIndLink passIndLink: passIndLinks) {
            Long id = passIndLink.getMeasureId();
            Measure measure;
            if (id == null) {
                measure = null;
            } else if (measureMap.containsKey(id)) {
                measure = measureMap.get(id);
            } else {
                measure = measureDAO.select(id);
                measureMap.put(id, measure);
            }
            
            id = passIndLink.getIndId();
            Indicator indicator;
            if (id == null) {
                indicator = null;
            } else if (indicatorMap.containsKey(id)) {
                indicator = indicatorMap.get(id);
            } else {
                indicator = indicatorDAO.select(id);
                indicatorMap.put(id, indicator);
            }
            
            TeacherElement teacherElement = new TeacherElement(
                        passIndLink,
                        measure,
                        indicator
                    );
            
            if (passIndLink.getDatatype() != null) {
                PassIndSpLink passIndSpLink;
                switch (passIndLink.getDatatype().intValue()) {
                    case 0: // 0 - число
                    case 1: // 1 - дата
                    case 2: // 2 - логическое значение
                    case 4: // 4 - текстовое
                    case 5: // 5 - многострочный текст
                        teacherElement.setTeacherData(teacherDataDAO.selectByPassIndLink(passIndLink.getId(), schoolId, teacherId));
                        teacherElements.add(teacherElement);
                        break;
                    case 3: // 3 - значение из справочника
                        passIndSpLink = passIndSpLinkDAO.select(passIndLink.getId());
                        if (passIndSpLink != null) {
                            teacherElement.setPassIndSpLink(passIndSpLink);
                            teacherElement.setTeacherData(teacherDataDAO.selectByPassIndLink(passIndLink.getId(), schoolId, teacherId));
                            
                            if (passIndSpLink.getSpId() != null) {
                                List<ClassItemTreeWrapper> classItemTreeWrappers = dictMap.get(passIndSpLink.getSpId());
                                if (classItemTreeWrappers == null) {
//                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(ClassItemTreeWrapper.filter(classItemTreeDAO.listSpFull(passIndSpLink.getSpId()), citIds)));
                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(filter(schoolList.getOrgTypeId(), classItemTreeDAO.listSpFull(passIndSpLink.getSpId()), orgTypeCITLinkMap)));
                                    dictMap.put(passIndSpLink.getSpId(), classItemTreeWrappers);
                                }
                                
                                teacherElement.putDict(passIndSpLink.getSpId(), classItemTreeWrappers);
                                teacherElements.add(teacherElement);
                            }
                        }
                        break;
                    case 6: // 6 - таблица
                        List<PassIndSpLinkWrapper> passIndSpLinkWrappers = PassIndSpLinkWrapper.wrap(passIndSpLinkDAO.list(passIndLink.getId()));
                        if ((passIndSpLinkWrappers != null) && !passIndSpLinkWrappers.isEmpty()) {
                            for (PassIndSpLinkWrapper passIndSpLinkWrapper: passIndSpLinkWrappers) {
                                List<ClassItemTreeWrapper> classItemTreeWrappers = dictMap.get(passIndSpLinkWrapper.getSpId());
                                if (classItemTreeWrappers == null) {
//                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(ClassItemTreeWrapper.filter(classItemTreeDAO.listSpFull(passIndSpLinkWrapper.getSpId()), citIds)));
                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(filter(schoolList.getOrgTypeId(), classItemTreeDAO.listSpFull(passIndSpLinkWrapper.getSpId()), orgTypeCITLinkMap)));
                                    dictMap.put(passIndSpLinkWrapper.getSpId(), classItemTreeWrappers);
                                }
                                
                                passIndSpLinkWrapper.setDict(classItemTreeWrappers);
                            }

                            teacherElement.setHorPassIndSpLinkWrapper(PassIndSpLinkWrapper.buildTree(passIndSpLinkWrappers, false));
                            teacherElement.setVerPassIndSpLinkWrapper(PassIndSpLinkWrapper.buildTree(passIndSpLinkWrappers, true));
                            teacherElement.setListTeacherData(teacherDataDAO.listByPassIndLink(passIndLink.getId(), schoolId, teacherId));
                            teacherElements.add(teacherElement);
                        }
                        break;
                    case 7: // 7 - группа логических значений
                        passIndSpLink = passIndSpLinkDAO.select(passIndLink.getId());
                        if (passIndSpLink != null) {
                            teacherElement.setPassIndSpLink(passIndSpLink);
                            teacherElement.setListTeacherData(teacherDataDAO.listByPassIndLink(passIndLink.getId(), schoolId, teacherId));
                            
                            if (passIndSpLink.getSpId() != null) {
                                List<ClassItemTreeWrapper> classItemTreeWrappers = dictMap.get(passIndSpLink.getSpId());
                                if (classItemTreeWrappers == null) {
//                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(ClassItemTreeWrapper.filter(classItemTreeDAO.listSpFull(passIndSpLink.getSpId()), citIds)));
                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(filter(schoolList.getOrgTypeId(), classItemTreeDAO.listSpFull(passIndSpLink.getSpId()), orgTypeCITLinkMap)));
                                    dictMap.put(passIndSpLink.getSpId(), classItemTreeWrappers);
                                }
                                
                                teacherElement.putDict(passIndSpLink.getSpId(), classItemTreeWrappers);
                                teacherElements.add(teacherElement);
                            }
                        }
                        break;
                }
            }
        }
        
        return teacherElements;
    }
    
    @Override
    @Transactional
    public List<StudentElement> listForStudent(Long schoolId, Long studentId) {
        SchoolList schoolList = schoolListDAO.select(schoolId);
        
        Map<Long, Set<Long>> orgTypeCITLinkMap = new HashMap<Long, Set<Long>>();
        
        List<Passport> passports = passportDAO.listForType(2L);
        List<PassIndLink> passIndLinks = new ArrayList<PassIndLink>();
        
        for (Passport passport: passports) {
            passIndLinks.addAll(PassIndLinkWrapper.sort(filter(schoolList.getOrgTypeId(), passIndLinkDAO.list(passport.getId()))));
        }
        
        passIndLinks = PassIndLinkWrapper.sort(passIndLinks);
        
        HashMap<Long, Measure> measureMap = new HashMap<Long, Measure>();
        HashMap<Long, Indicator> indicatorMap = new HashMap<Long, Indicator>();
        HashMap<Long, List<ClassItemTreeWrapper>> dictMap = new HashMap<Long, List<ClassItemTreeWrapper>>();
        
        ArrayList<StudentElement> studentElements = new ArrayList<StudentElement>();
        for (PassIndLink passIndLink: passIndLinks) {
            Long id = passIndLink.getMeasureId();
            Measure measure;
            if (id == null) {
                measure = null;
            } else if (measureMap.containsKey(id)) {
                measure = measureMap.get(id);
            } else {
                measure = measureDAO.select(id);
                measureMap.put(id, measure);
            }
            
            id = passIndLink.getIndId();
            Indicator indicator;
            if (id == null) {
                indicator = null;
            } else if (indicatorMap.containsKey(id)) {
                indicator = indicatorMap.get(id);
            } else {
                indicator = indicatorDAO.select(id);
                indicatorMap.put(id, indicator);
            }
            
            StudentElement studentElement = new StudentElement(
                        passIndLink,
                        measure,
                        indicator
                    );
            
            if (passIndLink.getDatatype() != null) {
                PassIndSpLink passIndSpLink;
                switch (passIndLink.getDatatype().intValue()) {
                    case 0: // 0 - число
                    case 1: // 1 - дата
                    case 2: // 2 - логическое значение
                    case 4: // 4 - текстовое
                    case 5: // 5 - многострочный текст
                        studentElement.setStudentData(studentDataDAO.selectByPassIndLink(passIndLink.getId(), schoolId, studentId));
                        studentElements.add(studentElement);
                        break;
                    case 3: // 3 - значение из справочника
                        passIndSpLink = passIndSpLinkDAO.select(passIndLink.getId());
                        if (passIndSpLink != null) {
                            studentElement.setPassIndSpLink(passIndSpLink);
                            studentElement.setStudentData(studentDataDAO.selectByPassIndLink(passIndLink.getId(), schoolId, studentId));
                            
                            if (passIndSpLink.getSpId() != null) {
                                List<ClassItemTreeWrapper> classItemTreeWrappers = dictMap.get(passIndSpLink.getSpId());
                                if (classItemTreeWrappers == null) {
//                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(ClassItemTreeWrapper.filter(classItemTreeDAO.listSpFull(passIndSpLink.getSpId()), citIds)));
                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(filter(schoolList.getOrgTypeId(), classItemTreeDAO.listSpFull(passIndSpLink.getSpId()), orgTypeCITLinkMap)));
                                    dictMap.put(passIndSpLink.getSpId(), classItemTreeWrappers);
                                }
                                
                                studentElement.putDict(passIndSpLink.getSpId(), classItemTreeWrappers);
                                studentElements.add(studentElement);
                            }
                        }
                        break;
                    case 6: // 6 - таблица
                        List<PassIndSpLinkWrapper> passIndSpLinkWrappers = PassIndSpLinkWrapper.wrap(passIndSpLinkDAO.list(passIndLink.getId()));
                        if ((passIndSpLinkWrappers != null) && !passIndSpLinkWrappers.isEmpty()) {
                            for (PassIndSpLinkWrapper passIndSpLinkWrapper: passIndSpLinkWrappers) {
                                List<ClassItemTreeWrapper> classItemTreeWrappers = dictMap.get(passIndSpLinkWrapper.getSpId());
                                if (classItemTreeWrappers == null) {
//                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(ClassItemTreeWrapper.filter(classItemTreeDAO.listSpFull(passIndSpLinkWrapper.getSpId()), citIds)));
                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(filter(schoolList.getOrgTypeId(), classItemTreeDAO.listSpFull(passIndSpLinkWrapper.getSpId()), orgTypeCITLinkMap)));
                                    dictMap.put(passIndSpLinkWrapper.getSpId(), classItemTreeWrappers);
                                }
                                
                                passIndSpLinkWrapper.setDict(classItemTreeWrappers);
                            }

                            studentElement.setHorPassIndSpLinkWrapper(PassIndSpLinkWrapper.buildTree(passIndSpLinkWrappers, false));
                            studentElement.setVerPassIndSpLinkWrapper(PassIndSpLinkWrapper.buildTree(passIndSpLinkWrappers, true));
                            studentElement.setListStudentData(studentDataDAO.listByPassIndLink(passIndLink.getId(), schoolId, studentId));
                            studentElements.add(studentElement);
                        }
                        break;
                    case 7: // 7 - группа логических значений
                        passIndSpLink = passIndSpLinkDAO.select(passIndLink.getId());
                        if (passIndSpLink != null) {
                            studentElement.setPassIndSpLink(passIndSpLink);
                            studentElement.setListStudentData(studentDataDAO.listByPassIndLink(passIndLink.getId(), schoolId, studentId));
                            
                            if (passIndSpLink.getSpId() != null) {
                                List<ClassItemTreeWrapper> classItemTreeWrappers = dictMap.get(passIndSpLink.getSpId());
                                if (classItemTreeWrappers == null) {
//                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(ClassItemTreeWrapper.filter(classItemTreeDAO.listSpFull(passIndSpLink.getSpId()), citIds)));
                                    classItemTreeWrappers = ClassItemTreeWrapper.buildTree(ClassItemTreeWrapper.wrap(filter(schoolList.getOrgTypeId(), classItemTreeDAO.listSpFull(passIndSpLink.getSpId()), orgTypeCITLinkMap)));
                                    dictMap.put(passIndSpLink.getSpId(), classItemTreeWrappers);
                                }
                                
                                studentElement.putDict(passIndSpLink.getSpId(), classItemTreeWrappers);
                                studentElements.add(studentElement);
                            }
                        }
                        break;
                }
            }
        }
        
        return studentElements;
    }

    @Override
    @Transactional
    public void saveForSchool(Long schoolId, Long passportId, Map<String, String[]> params) {
        schoolListDAO.unconfirm(schoolId);
        schoolListDAO.unapprove(schoolId);
        
        HashMap<Long, PassIndLink> passIndLinkMap = new HashMap<Long, PassIndLink>();
        for (PassIndLink passIndLink: passIndLinkDAO.list(passportId)) {
            passIndLinkMap.put(passIndLink.getId(), passIndLink);
            passDataDAO.clear(schoolId, passIndLink.getId());
        }
        
        for (Map.Entry<String, String[]> entry: params.entrySet()) {
            String name = entry.getKey();
            if (name.startsWith(PassportElement.FIELD_PREFIX)) {
                name = name.substring(PassportElement.FIELD_PREFIX.length() + 1);
                
                Long passIndLinkId;
                int pos = name.indexOf('-');
                if (pos == -1) {
                    try {
                        passIndLinkId = Long.valueOf(name);
                        name = "";
                    } catch (Exception ex) {
                        passIndLinkId = null;
                    }
                } else {
                    try {
                        passIndLinkId = Long.valueOf(name.substring(0, pos));
                        name = name.substring(pos + 1);
                    } catch (Exception ex) {
                        passIndLinkId = null;
                    }
                }
                
                PassIndLink passIndLink = passIndLinkMap.get(passIndLinkId);
                if (passIndLink == null) {
                    continue;
                }
                
                String value = null;
                if ((entry.getValue() != null) && (entry.getValue().length > 0)) {
                    value = entry.getValue()[0];
                    if (value.isEmpty()) {
                        value = null;
                    }
                }
                
                PassData passData = new PassData();
                passData.setSchoolId(schoolId);
                passData.setPassIndLinkId(passIndLinkId);
                
                boolean save = false;
                
                switch (passIndLink.getDatatype().intValue()) {
                    case 0: // 0 - число
                        if ((value != null) && !value.isEmpty()) {
                            try {
                                passData.setNumberValue(Double.valueOf(value));
                                save = true;
                            } catch (Exception ex) {
                                LOGGER.warn("sc number: parsing \"" + value + "\" as double", ex);
                            }
                        }
                        break;
                    case 1: // 1 - дата
                        if ((value != null) && !value.isEmpty()) {
                            try {
                                passData.setDateValue(new Timestamp(Consts.JAVASCRIPT_DATE_FORMAT.parse(value).getTime()));
                                save = true;
                            } catch (Exception ex) {
                                LOGGER.warn("sc date: parsing \"" + value + "\" as date \"" + Consts.JAVASCRIPT_DATE_FORMAT.toPattern() + "\"", ex);
                            }
                        }
                        break;
                    case 2: // 2 - логическое значение
//                        passData.setBooleanValue(Boolean.TRUE);
                        passData.setBooleanValue(1L);
                        save = true;
                        break;
                    case 3: // 3 - значение из справочника
                        if ((value != null) && !value.isEmpty()) {
                            try {
                                passData.setClassItemTreeId(Long.valueOf(value));
                                save = true;
                            } catch (Exception ex) {
                                LOGGER.warn("sc dict: parsing \"" + value + "\" as long", ex);
                            }
                        }
                        break;
                    case 4: // 4 - текстовое
                        if ((value != null) && !value.isEmpty()) {
                            passData.setStringValue(value);
                            save = true;
                        }
                        break;
                    case 5: // 5 - многострочный текст
                        if ((value != null) && !value.isEmpty()) {
                            passData.setStringValue(value);
                            save = true;
                        }
                        break;
                    case 6: // 6 - таблица
                        ArrayList<Long> dims = new ArrayList<Long>();
                        
                        save = true;
                        
                        while (!name.isEmpty()) {
                            pos = name.indexOf('-');
                            if (pos == -1) {
                                try {
                                    dims.add(Long.valueOf(name));
                                } catch (Exception ex) {
                                    LOGGER.warn("sc table: parsing dim \"" + name + "\" as long", ex);
                                    save = false;
                                    break;
                                }
                                name = "";
                            } else {
                                try {
                                    dims.add(Long.valueOf(name.substring(0, pos)));
                                } catch (Exception ex) {
                                    LOGGER.warn("sc table: parsing dim \"" + name.substring(0, pos) + "\" from \"" + name + "\" as long", ex);
                                    save = false;
                                    break;
                                }
                                name = name.substring(pos + 1);
                            }
                        }
                        
                        if (save) {
                            save = false;
                            passData.setDims(dims);

                            if (value != null) {
                                try {
                                    passData.setNumberValue(Double.valueOf(value));
                                    save = true;
                                } catch (Exception ex) {
                                    LOGGER.warn("sc table: parsing value \"" + value + "\" as double", ex);
                                }
                            }
                        }
                        
                        break;
                    case 7: // 7 - группа логических значений
//                        passData.setBooleanValue(Boolean.TRUE);
                        passData.setBooleanValue(1L);
                        try {
                            passData.setClassItemTreeId(Long.valueOf(name));
                            save = true;
                        } catch (Exception ex) {
                            LOGGER.warn("sc boolgroup: parsing long \"" + name + "\"", ex);
                        }
                        break;
                    default:
                        continue;
                }
                
                if (save) {
                    passDataDAO.save(passData);
                }
            }
        }
        
        //110
        
        flcErrorDAO.clear(schoolId);
        
        // форма собственности должна быть заполнена
        PassData flcPassData = passDataDAO.selectByPassIndLink(10L, schoolId);
//        System.out.println("----" + flcPassData.getPassIndLinkId() + " - " + flcPassData.getClassItemTreeId());
        if ((flcPassData == null) || (flcPassData.getClassItemTreeId() == null)) {
            FLCError flcError = new FLCError();
            flcError.setClassItemTreeId(173916L);
            flcError.setSchoolId(schoolId);
            flcErrorDAO.save(flcError);
        }
        
        // если кол-во классов больше 0, то и кол-во учащихся больше 0
        flcPassData = passDataDAO.selectByPassIndLink(73L, schoolId);
        
        if ((flcPassData != null) && (flcPassData.getNumberValue() != null) && flcPassData.getNumberValue().doubleValue() != 0D) {
            double sum = 0D;
            List<PassData> tablePassDatas = passDataDAO.listByPassIndLink(56L, schoolId);
            for (PassData tablePassData: tablePassDatas) {
                if ((tablePassData != null) && (tablePassData.getNumberValue() != null) && (tablePassData.getNumberValue().doubleValue() != 0D)) {
                    sum += tablePassData.getNumberValue().doubleValue();
                }
            }
            
            if (sum == 0D) {
                FLCError flcError = new FLCError();
                flcError.setClassItemTreeId(173917L);
                flcError.setSchoolId(schoolId);
                flcErrorDAO.save(flcError);
            }
        }
    }

    @Override
    @Transactional
    public void saveForTeacher(Long schoolId, Long teacherId, String f, String i, String o, Map<String, String[]> params) {
        schoolListDAO.unconfirm(schoolId);
        schoolListDAO.unapprove(schoolId);
        
        teacherDataDAO.clear(schoolId, teacherId);
        
        Teacher teacher = teacherDAO.select(teacherId);
        teacher.setF(f);
        teacher.setI(i);
        teacher.setO(o);
        teacherDAO.save(teacher);
        
        HashMap<Long, PassIndLink> passIndLinkMap = new HashMap<Long, PassIndLink>();
        for (Passport passport: passportDAO.listForType(1L)) {
            for (PassIndLink passIndLink: passIndLinkDAO.list(passport.getId())) {
                passIndLinkMap.put(passIndLink.getId(), passIndLink);
            }
        }
        
        for (Map.Entry<String, String[]> entry: params.entrySet()) {
            String name = entry.getKey();
            if (name.startsWith(PassportElement.FIELD_PREFIX)) {
                name = name.substring(PassportElement.FIELD_PREFIX.length() + 1);
                
                Long passIndLinkId;
                int pos = name.indexOf('-');
                if (pos == -1) {
                    try {
                        passIndLinkId = Long.valueOf(name);
                        name = "";
                    } catch (Exception ex) {
                        passIndLinkId = null;
                    }
                } else {
                    try {
                        passIndLinkId = Long.valueOf(name.substring(0, pos));
                        name = name.substring(pos + 1);
                    } catch (Exception ex) {
                        passIndLinkId = null;
                    }
                }
                
                PassIndLink passIndLink = passIndLinkMap.get(passIndLinkId);
                if (passIndLink == null) {
                    continue;
                }
                
                String value = null;
                if ((entry.getValue() != null) && (entry.getValue().length > 0)) {
                    value = entry.getValue()[0];
                    if (value.isEmpty()) {
                        value = null;
                    }
                }
                
                TeacherData teacherData = new TeacherData();
                teacherData.setSchoolId(schoolId);
                teacherData.setTeacherId(teacherId);
                teacherData.setPassIndLinkId(passIndLinkId);
                
                boolean save = false;
                
                switch (passIndLink.getDatatype().intValue()) {
                    case 0: // 0 - число
                        if ((value != null) && !value.isEmpty()) {
                            try {
                                teacherData.setNumberValue(Double.valueOf(value));
                                save = true;
                            } catch (Exception ex) {
                                LOGGER.warn("t number: parsing \"" + value + "\" as double", ex);
                            }
                        }
                        break;
                    case 1: // 1 - дата
                        if ((value != null) && !value.isEmpty()) {
                            try {
                                teacherData.setDateValue(new Timestamp(Consts.JAVASCRIPT_DATE_FORMAT.parse(value).getTime()));
                                save = true;
                            } catch (Exception ex) {
                                LOGGER.warn("t date: parsing \"" + value + "\" as date \"" + Consts.JAVASCRIPT_DATE_FORMAT.toPattern() + "\"", ex);
                            }
                        }
                        break;
                    case 2: // 2 - логическое значение
//                        teacherData.setBooleanValue(Boolean.TRUE);
                        teacherData.setBooleanValue(1L);
                        save = true;
                        break;
                    case 3: // 3 - значение из справочника
                        if ((value != null) && !value.isEmpty()) {
                            try {
                                teacherData.setClassItemTreeId(Long.valueOf(value));
                                save = true;
                            } catch (Exception ex) {
                                LOGGER.warn("t dict: parsing \"" + value + "\" as long", ex);
                            }
                        }
                        break;
                    case 4: // 4 - текстовое
                        if ((value != null) && !value.isEmpty()) {
                            teacherData.setStringValue(value);
                            save = true;
                        }
                        break;
                    case 5: // 5 - многострочный текст
                        if ((value != null) && !value.isEmpty()) {
                            teacherData.setStringValue(value);
                            save = true;
                        }
                        break;
                    case 6: // 6 - таблица
                        ArrayList<Long> dims = new ArrayList<Long>();
                        
                        save = true;
                        
                        while (!name.isEmpty()) {
                            pos = name.indexOf('-');
                            if (pos == -1) {
                                try {
                                    dims.add(Long.valueOf(name));
                                } catch (Exception ex) {
                                    LOGGER.warn("t table: parsing dim \"" + name + "\" as long", ex);
                                    save = false;
                                    break;
                                }
                                name = "";
                            } else {
                                try {
                                    dims.add(Long.valueOf(name.substring(0, pos)));
                                } catch (Exception ex) {
                                    LOGGER.warn("t table: parsing dim \"" + name.substring(0, pos) + "\" from \"" + name + "\" as long", ex);
                                    save = false;
                                    break;
                                }
                                name = name.substring(pos + 1);
                            }
                        }
                        
                        if (save) {
                            save = false;
                            teacherData.setDims(dims);

                            if (value != null) {
                                try {
                                    teacherData.setNumberValue(Double.valueOf(value));
                                    save = true;
                                } catch (Exception ex) {
                                    LOGGER.warn("t table: parsing value \"" + value + "\" as double", ex);
                                }
                            }
                        }
                        
                        break;
                    case 7: // 7 - группа логических значений
//                        teacherData.setBooleanValue(Boolean.TRUE);
                        teacherData.setBooleanValue(1L);
                        try {
                            teacherData.setClassItemTreeId(Long.valueOf(name));
                            save = true;
                        } catch (Exception ex) {
                            LOGGER.warn("t boolgroup: parsing long \"" + name + "\"", ex);
                        }
                        break;
                    default:
                        continue;
                }
                
                if (save) {
                    teacherDataDAO.save(teacherData);
                }
            }
        }
    }
    
    @Override
    @Transactional
    public void saveForStudent(Long schoolId, Long studentId, String f, String i, String o, Long classId, String parallel, Map<String, String[]> params) {
        schoolListDAO.unconfirm(schoolId);
        schoolListDAO.unapprove(schoolId);
        
        studentDataDAO.clear(schoolId, studentId);
        
        SchoolStudentLink schoolStudentLink = schoolStudentLinkDAO.selectBySchoolAndStudent(schoolId, studentId);
        schoolStudentLink.setClassId(classId);
        schoolStudentLink.setParallel(parallel);
        schoolStudentLinkDAO.save(schoolStudentLink);
        
        Student student = studentDAO.select(studentId);
        student.setF(f);
        student.setI(i);
        student.setO(o);
        studentDAO.save(student);
        
        HashMap<Long, PassIndLink> passIndLinkMap = new HashMap<Long, PassIndLink>();
        for (Passport passport: passportDAO.listForType(2L)) {
            for (PassIndLink passIndLink: passIndLinkDAO.list(passport.getId())) {
                passIndLinkMap.put(passIndLink.getId(), passIndLink);
            }
        }
        
        for (Map.Entry<String, String[]> entry: params.entrySet()) {
            String name = entry.getKey();
            if (name.startsWith(PassportElement.FIELD_PREFIX)) {
                name = name.substring(PassportElement.FIELD_PREFIX.length() + 1);
                
                Long passIndLinkId;
                int pos = name.indexOf('-');
                if (pos == -1) {
                    try {
                        passIndLinkId = Long.valueOf(name);
                        name = "";
                    } catch (Exception ex) {
                        passIndLinkId = null;
                    }
                } else {
                    try {
                        passIndLinkId = Long.valueOf(name.substring(0, pos));
                        name = name.substring(pos + 1);
                    } catch (Exception ex) {
                        passIndLinkId = null;
                    }
                }
                
                PassIndLink passIndLink = passIndLinkMap.get(passIndLinkId);
                if (passIndLink == null) {
                    continue;
                }
                
                String value = null;
                if ((entry.getValue() != null) && (entry.getValue().length > 0)) {
                    value = entry.getValue()[0];
                    if (value.isEmpty()) {
                        value = null;
                    }
                }
                
                StudentData studentData = new StudentData();
                studentData.setSchoolId(schoolId);
                studentData.setStudentId(studentId);
                studentData.setPassIndLinkId(passIndLinkId);
                
                boolean save = false;
                
                switch (passIndLink.getDatatype().intValue()) {
                    case 0: // 0 - число
                        if ((value != null) && !value.isEmpty()) {
                            try {
                                studentData.setNumberValue(Double.valueOf(value));
                                save = true;
                            } catch (Exception ex) {
                                LOGGER.warn("t number: parsing \"" + value + "\" as double", ex);
                            }
                        }
                        break;
                    case 1: // 1 - дата
                        if ((value != null) && !value.isEmpty()) {
                            try {
                                studentData.setDateValue(new Timestamp(Consts.JAVASCRIPT_DATE_FORMAT.parse(value).getTime()));
                                save = true;
                            } catch (Exception ex) {
                                LOGGER.warn("t date: parsing \"" + value + "\" as date \"" + Consts.JAVASCRIPT_DATE_FORMAT.toPattern() + "\"", ex);
                            }
                        }
                        break;
                    case 2: // 2 - логическое значение
//                        studentData.setBooleanValue(Boolean.TRUE);
                        studentData.setBooleanValue(1L);
                        save = true;
                        break;
                    case 3: // 3 - значение из справочника
                        if ((value != null) && !value.isEmpty()) {
                            try {
                                studentData.setClassItemTreeId(Long.valueOf(value));
                                save = true;
                            } catch (Exception ex) {
                                LOGGER.warn("t dict: parsing \"" + value + "\" as long", ex);
                            }
                        }
                        break;
                    case 4: // 4 - текстовое
                        if ((value != null) && !value.isEmpty()) {
                            studentData.setStringValue(value);
                            save = true;
                        }
                        break;
                    case 5: // 5 - многострочный текст
                        if ((value != null) && !value.isEmpty()) {
                            studentData.setStringValue(value);
                            save = true;
                        }
                        break;
                    case 6: // 6 - таблица
                        ArrayList<Long> dims = new ArrayList<Long>();
                        
                        save = true;
                        
                        while (!name.isEmpty()) {
                            pos = name.indexOf('-');
                            if (pos == -1) {
                                try {
                                    dims.add(Long.valueOf(name));
                                } catch (Exception ex) {
                                    LOGGER.warn("t table: parsing dim \"" + name + "\" as long", ex);
                                    save = false;
                                    break;
                                }
                                name = "";
                            } else {
                                try {
                                    dims.add(Long.valueOf(name.substring(0, pos)));
                                } catch (Exception ex) {
                                    LOGGER.warn("t table: parsing dim \"" + name.substring(0, pos) + "\" from \"" + name + "\" as long", ex);
                                    save = false;
                                    break;
                                }
                                name = name.substring(pos + 1);
                            }
                        }
                        
                        if (save) {
                            save = false;
                            studentData.setDims(dims);

                            if (value != null) {
                                try {
                                    studentData.setNumberValue(Double.valueOf(value));
                                    save = true;
                                } catch (Exception ex) {
                                    LOGGER.warn("t table: parsing value \"" + value + "\" as double", ex);
                                }
                            }
                        }
                        
                        break;
                    case 7: // 7 - группа логических значений
//                        studentData.setBooleanValue(Boolean.TRUE);
                        studentData.setBooleanValue(1L);
                        try {
                            studentData.setClassItemTreeId(Long.valueOf(name));
                            save = true;
                        } catch (Exception ex) {
                            LOGGER.warn("t boolgroup: parsing long \"" + name + "\"", ex);
                        }
                        break;
                    default:
                        continue;
                }
                
                if (save) {
                    studentDataDAO.save(studentData);
                }
            }
        }
    }
}