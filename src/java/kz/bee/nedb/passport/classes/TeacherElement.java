package kz.bee.nedb.passport.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kz.bee.nedb.passport.consts.Consts;
import kz.bee.nedb.passport.entity.Indicator;
import kz.bee.nedb.passport.entity.Measure;
import kz.bee.nedb.passport.entity.PassIndLink;
import kz.bee.nedb.passport.entity.PassIndSpLink;
import kz.bee.nedb.passport.entity.TeacherData;
import kz.bee.nedb.passport.entity.wrapper.ClassItemTreeWrapper;
import kz.bee.nedb.passport.entity.wrapper.PassIndSpLinkWrapper;
import org.apache.log4j.Logger;

public class TeacherElement {
    private static final Logger LOGGER = Logger.getLogger(TeacherElement.class);
    private static final String CELL_TITLE_ITEM_SEPARATOR = " | ";

    public static final String FIELD_PREFIX = "field";
    
    private PassIndLink passIndLink = null;
    private Indicator indicator = null;
    private Measure measure = null;
    private PassIndSpLink passIndSpLink = null;
    private PassIndSpLinkWrapper horPassIndSpLinkWrapper = null;
    private PassIndSpLinkWrapper verPassIndSpLinkWrapper = null;
    private TeacherData teacherData = null;
    private List<TeacherData> listTeacherData = null;
    private HashMap<Long, List<ClassItemTreeWrapper>> dictMap = new HashMap<Long, List<ClassItemTreeWrapper>>();
    
//    private PassData getPassDataByHeaderCellWrappers(HeaderCellWrapper hHeaderCellWrapper, HeaderCellWrapper vHeaderCellWrapper, PassDataFactory<T> passDataFactory) {
    private TeacherData getPassDataByHeaderCellWrappers(HeaderCellWrapper hHeaderCellWrapper, HeaderCellWrapper vHeaderCellWrapper) {
        ArrayList<Long> passDataCoords = new ArrayList<Long>(hHeaderCellWrapper.getValues());
        passDataCoords.addAll(vHeaderCellWrapper.getValues());
        
        for (TeacherData listData: listTeacherData) {
            if (listData.match(passDataCoords)) {
                return listData;
            }
        }
        
        TeacherData listData = new TeacherData();
        listData.setDims(passDataCoords);
        
        return listData;
    }
    
    private String getElementIdFrom(TeacherData data) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        
        if (data.getDimv1() != null) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append("-");
            }
            stringBuilder.append(data.getDimv1());
        }
        
        if (data.getDimv2() != null) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append("-");
            }
            stringBuilder.append(data.getDimv2());
        }
        
        if (data.getDimv3() != null) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append("-");
            }
            stringBuilder.append(data.getDimv3());
        }
        
        if (data.getDimv4() != null) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append("-");
            }
            stringBuilder.append(data.getDimv4());
        }
        
        if (data.getDimv5() != null) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append("-");
            }
            stringBuilder.append(data.getDimv5());
        }
        
        if (data.getDimv6() != null) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append("-");
            }
            stringBuilder.append(data.getDimv6());
        }
        
        if (data.getDimv7() != null) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append("-");
            }
            stringBuilder.append(data.getDimv7());
        }
        
        if (data.getDimv8() != null) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append("-");
            }
            stringBuilder.append(data.getDimv8());
        }
        
        if (data.getDimv9() != null) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append("-");
            }
            stringBuilder.append(data.getDimv9());
        }
        
        if (data.getDimv10() != null) {
            if (first) {
//                first = false;
            } else {
                stringBuilder.append("-");
            }
            stringBuilder.append(data.getDimv10());
        }
        
        return stringBuilder.toString();
    }
    
    private List<Long> getListPassDataDims() {
        ArrayList<Long> result = new ArrayList<Long>();
        
        for (TeacherData data: listTeacherData) {
//            result.addAll(data.getDims());
            result.add(data.getClassItemTreeId());
        }
        
        return result;
    }
    
    private String toNumberEdit() {
        if (passIndLink == null) {
            return "<!-- number: no passIndLink -->";
        } else if (indicator == null) {
            return "<!-- number: no indicator -->";
        } else {
            return new StringBuilder("<td valign=\"top\">")
                    .append(indicator.getLocalizedName())
                    .append("</td><td valign=\"top\"><input type=\"text\" name=\"")
//                    .append(FIELD_NUMBER)
                    .append(FIELD_PREFIX)
                    .append("-")
                    .append(passIndLink.getId())
                    .append("\" id=\"field-number-")
                    .append(passIndLink.getId())
                    .append("\" value=\"")
                    .append(teacherData == null ? "" : teacherData.getNumberValue().intValue())
                    .append("\"/></td>")
                    .toString();
        }
    }
    
    private String toDateEdit() {
        if (passIndLink == null) {
            return "<!-- date: no passIndLink -->";
        } else if (indicator == null) {
            return "<!-- date: no indicator -->";
        } else {
            return new StringBuilder("<td valign=\"top\">")
                    .append(indicator.getLocalizedName())
                    .append("</td><td valign=\"top\"><input type=\"text\" name=\"")
//                    .append(FIELD_DATE)
                    .append(FIELD_PREFIX)
                    .append("-")
                    .append(passIndLink.getId())
                    .append("\" id=\"field-date-")
                    .append(passIndLink.getId())
                    .append("\" value=\"")
                    .append(teacherData == null ? "" : Consts.JAVASCRIPT_DATE_FORMAT.format(teacherData.getDateValue()))
                    .append("\"/></td>")
                    .toString();
        }
    }
    
    private String toCheckbox() {
        if (passIndLink == null) {
            return "<!-- checkbox: no passIndLink -->";
        } else if (indicator == null) {
            return "<!-- checkbox: no indicator -->";
        } else {
            return new StringBuilder("<td valign=\"top\">")
                    .append(indicator.getLocalizedName())
                    .append("</td><td valign=\"top\"><input type=\"checkbox\" name=\"")
//                    .append(FIELD_CHECKBOX)
                    .append(FIELD_PREFIX)
                    .append("-")
                    .append(passIndLink.getId())
                    .append("\" id=\"field-checkbox-")
                    .append(passIndLink.getId())
                    .append("\" ")
                    .append(teacherData == null ? "" : Boolean.TRUE.equals(teacherData.getBooleanValue()) ? "checked" : "")
                    .append("/></td>")
                    .toString();
        }
    }
    
    private String toCombobox() {
        if (passIndLink == null) {
            return "<!-- combobox: no passIndLink -->";
        } else if (indicator == null) {
            return "<!-- combobox: no indicator -->";
        } else if (passIndSpLink == null) {
            return "<!-- combobox: no passIndSpLink -->";
        } else {
            List<ClassItemTreeWrapper> classItemTreeWrappers = dictMap.get(passIndSpLink.getSpId());
            if ((classItemTreeWrappers == null) || classItemTreeWrappers.isEmpty()) {
                return "<!-- combobox: no classItemTreeWrappers -->";
            } else {
                StringBuilder stringBuilder = new StringBuilder("<td valign=\"top\">")
                        .append(indicator.getLocalizedName())
                        .append("</td><td valign=\"top\"><select name=\"")
//                        .append(FIELD_COMBOBOX)
                        .append(FIELD_PREFIX)
                        .append("-")
                        .append(passIndLink.getId())
                        .append("\" id=\"field-combobox-")
                        .append(passIndLink.getId())
                        .append("\"><option value=\"\">...</option>");
                
                for (ClassItemTreeWrapper classItemTreeWrapper: classItemTreeWrappers) {
                    stringBuilder.append(classItemTreeWrapper.toOption(0, teacherData == null ? null : teacherData.getClassItemTreeId()));
                }
                
                return stringBuilder
                        .append("</select></td>")
                        .toString();
            }
        }
    }
    
    private String toTextEdit() {
        if (passIndLink == null) {
            return "<!-- text: no passIndLink -->";
        } else if (indicator == null) {
            return "<!-- text: no indicator -->";
        } else {
            return new StringBuilder("<td valign=\"top\">")
                    .append(indicator.getLocalizedName())
                    .append("</td><td valign=\"top\"><input type=\"text\" name=\"")
//                    .append(FIELD_TEXT)
                    .append(FIELD_PREFIX)
                    .append("-")
                    .append(passIndLink.getId())
                    .append("\" id=\"field-text-")
                    .append(passIndLink.getId())
                    .append("\" value=\"")
                    .append(teacherData == null ? "" : teacherData.getStringValue())
                    .append("\"/></td>")
                    .toString();
        }
    }
    
    private String toMultilineEdit() {
        if (passIndLink == null) {
            return "<!-- multiline edit: no passIndLink -->";
        } else if (indicator == null) {
            return "<!-- multiline edit: no indicator -->";
        } else {
            return new StringBuilder("<td valign=\"top\">")
                    .append(indicator.getLocalizedName())
                    .append("</td><td valign=\"top\"><textarea name=\"")
//                    .append(FIELD_MULTILINE)
                    .append(FIELD_PREFIX)
                    .append("-")
                    .append(passIndLink.getId())
                    .append("\" id=\"field-multiline-")
                    .append(passIndLink.getId())
                    .append("\">")
                    .append(teacherData == null ? "" : teacherData.getStringValue())
                    .append("</textarea></td>")
                    .toString();
        }
    }
    
    private String toTable() {
        if (passIndLink == null) {
            return "<!-- table: no passIndLink -->";
        } else if (indicator == null) {
            return "<!-- table: no indicator -->";
        } else if ((getHorPassIndSpLinkWrapper() == null) || (getVerPassIndSpLinkWrapper() == null)) {
            return "<!-- table: no passIndSpLinkWrappers -->";
        } else {
            StringBuilder stringBuilder = new StringBuilder("<td valign=\"top\">")
                    .append(indicator.getLocalizedName())
                    .append("</td><td valign=\"top\"><table border=\"1\">");
            
            int colCount = horPassIndSpLinkWrapper.getWidth() + verPassIndSpLinkWrapper.getDepth();
            int rowCount = verPassIndSpLinkWrapper.getWidth() + horPassIndSpLinkWrapper.getDepth();
            
            List<HeaderCellWrapper> hHeaderCellWrappers = new ArrayList<HeaderCellWrapper>();
            List<HeaderCellWrapper> vHeaderCellWrappers = new ArrayList<HeaderCellWrapper>();
            
            String[][] table = new String[rowCount][colCount];
            table[0][0] = new StringBuilder("<td rowspan=\"")
                    .append(horPassIndSpLinkWrapper.getDepth())
                    .append("\" colspan=\"")
                    .append(verPassIndSpLinkWrapper.getDepth())
                    .append("\"></td>")
                    .toString();
            
            int repeats = 1;
            int level = 0;
            
            PassIndSpLinkWrapper passIndSpLinkWrapper = horPassIndSpLinkWrapper;
            while (passIndSpLinkWrapper != null) {
                hHeaderCellWrappers = HeaderCellWrapper.append(hHeaderCellWrappers, passIndSpLinkWrapper.getDict());
                
                int colWidth = passIndSpLinkWrapper.getColWidth();
                int colNumber = verPassIndSpLinkWrapper.getDepth();
                
                for (int i = 0; i < repeats; i ++) {
                    for (ClassItemTreeWrapper classItemTreeWrapper: passIndSpLinkWrapper.getDict()) {
                        table[level][colNumber] = new StringBuilder("<td colspan=\"")
                                .append(colWidth)
                                .append("\">")
                                .append(classItemTreeWrapper.getLocalizedName())
                                .append("</td>")
                                .toString();
                        
                        colNumber += colWidth;
                    }
                }
                
                repeats *= passIndSpLinkWrapper.getDict().size();
                
                passIndSpLinkWrapper = passIndSpLinkWrapper.getChild();
                level ++;
            }
            
            repeats = 1;
            level = 0;
            
            passIndSpLinkWrapper = verPassIndSpLinkWrapper;
            while (passIndSpLinkWrapper != null) {
                vHeaderCellWrappers = HeaderCellWrapper.append(vHeaderCellWrappers, passIndSpLinkWrapper.getDict());
                
                int colWidth = passIndSpLinkWrapper.getColWidth();
                int colNumber = horPassIndSpLinkWrapper.getDepth();
                
                for (int i = 0; i < repeats; i ++) {
                    for (ClassItemTreeWrapper classItemTreeWrapper: passIndSpLinkWrapper.getDict()) {
                        table[colNumber][level] = new StringBuilder("<td rowspan=\"")
                                .append(colWidth)
                                .append("\">")
                                .append(classItemTreeWrapper.getLocalizedName())
                                .append("</td>")
                                .toString();
                        
                        colNumber += colWidth;
                    }
                }
                
                repeats *= passIndSpLinkWrapper.getDict().size();
                
                passIndSpLinkWrapper = passIndSpLinkWrapper.getChild();
                level ++;
            }
            
            int horDepth = horPassIndSpLinkWrapper.getDepth();
            int verDepth = verPassIndSpLinkWrapper.getDepth();
            
            
            
            for (int i = 0; i < vHeaderCellWrappers.size(); i ++) {
                HeaderCellWrapper vHeaderCellWrapper = vHeaderCellWrappers.get(i);
                for (int j = 0; j < hHeaderCellWrappers.size(); j++) {
                    HeaderCellWrapper hHeaderCellWrapper = hHeaderCellWrappers.get(j);
                    TeacherData listData = getPassDataByHeaderCellWrappers(hHeaderCellWrapper, vHeaderCellWrapper);
                    
//                    if ((listData != null) && (listData.getNumberValue() != null)) {
//                        System.out.println("--------table value: " + listData.getNumberValue());
//                    }
                    
                    table[i + horDepth][j + verDepth] = new StringBuilder("<td><input type=\"text\" name=\"")
//                            .append(FIELD_TABLE)
                            .append(FIELD_PREFIX)
                            .append("-")
                            .append(passIndLink.getId())
                            .append("-")
                            .append(getElementIdFrom(listData))
                            .append("\" style=\"width: 50px;\" title=\"")
                            .append(hHeaderCellWrapper.getLabelsWithSeparator(CELL_TITLE_ITEM_SEPARATOR))
                            .append(CELL_TITLE_ITEM_SEPARATOR)
                            .append(vHeaderCellWrapper.getLabelsWithSeparator(CELL_TITLE_ITEM_SEPARATOR))
                            .append("\" id=\"field-table-")
                            .append(passIndLink.getId())
                            .append("-")
                            .append(getElementIdFrom(listData))
                            .append("\" value=\"")
                            .append(listData.getNumberValue() == null ? "" : listData.getNumberValue().intValue())
                            .append("\"></td>")
                            .toString();
                }
            }
            
            for (int i = 0; i < table.length; i ++) {
                stringBuilder.append("<tr>");
                
                for (int j = 0; j < table[i].length; j ++) {
                    if (table[i][j] != null) {
                        stringBuilder.append(table[i][j]);
                    }
                }
                
                stringBuilder
                        .append("</tr>")
                        .append(System.getProperty("line.separator"));
            }
            
            return stringBuilder
                    .append("</table></td>")
                    .toString();
        }
    }
    
    private String toBooleanGroup() {
        if (passIndLink == null) {
            return "<!-- boolean group: no passIndLink -->";
        } else if (indicator == null) {
            return "<!-- boolean group: no indicator -->";
        } else if (passIndSpLink == null) {
            return "<!-- boolean group: no passIndSpLink -->";
        } else {
            StringBuilder stringBuilder = new StringBuilder("<td valign=\"top\">")
                    .append(indicator.getLocalizedName())
                    .append("</td><td valign=\"top\"><ul>");
            
            for (ClassItemTreeWrapper classItemTreeWrapper: getDict(passIndSpLink.getSpId())) {
//                stringBuilder.append(classItemTreeWrapper.toListItem(getListPassDataDims(), FIELD_BOOLGROUP + "-" + passIndLink.getId()));
                stringBuilder.append(classItemTreeWrapper.toListItem(getListPassDataDims(), FIELD_PREFIX + "-" + passIndLink.getId(), "field-listitem-" + passIndLink.getId()));
            }
            
            return stringBuilder
                    .append("</ul></td>")
                    .toString();
        }
    }
    
    public TeacherElement(PassIndLink passIndLink, Measure measure, Indicator indicator) {
        super();
        
        this.passIndLink = passIndLink;
        this.measure = measure;
        this.indicator = indicator;
    }

    public PassIndLink getPassIndLink() {
        return passIndLink;
    }

    public void setPassIndLink(PassIndLink passIndLink) {
        this.passIndLink = passIndLink;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public PassIndSpLink getPassIndSpLink() {
        return passIndSpLink;
    }

    public void setPassIndSpLink(PassIndSpLink passIndSpLink) {
        this.passIndSpLink = passIndSpLink;
    }

    public PassIndSpLinkWrapper getHorPassIndSpLinkWrapper() {
        return horPassIndSpLinkWrapper;
    }

    public void setHorPassIndSpLinkWrapper(PassIndSpLinkWrapper horPassIndSpLinkWrapper) {
        this.horPassIndSpLinkWrapper = horPassIndSpLinkWrapper;
    }

    public PassIndSpLinkWrapper getVerPassIndSpLinkWrapper() {
        return verPassIndSpLinkWrapper;
    }

    public void setVerPassIndSpLinkWrapper(PassIndSpLinkWrapper verPassIndSpLinkWrapper) {
        this.verPassIndSpLinkWrapper = verPassIndSpLinkWrapper;
    }

    public TeacherData getTeacherData() {
        return teacherData;
    }

    public void setTeacherData(TeacherData teacherData) {
        this.teacherData = teacherData;
    }

    public List<TeacherData> getListTeacherData() {
        return listTeacherData;
    }

    public void setListTeacherData(List<TeacherData> listTeacherData) {
        this.listTeacherData = listTeacherData;
    }
    
    public String toFormField() {
        try {
            if ((passIndLink != null) && (passIndLink.getDatatype() != null)) {
                switch (passIndLink.getDatatype().intValue()) {
                    case 0:
                        return toNumberEdit();
                    case 1:
                        return toDateEdit();
                    case 2:
                        return toCheckbox();
                    case 3:
                        return toCombobox();
                    case 4:
                        return toTextEdit();
                    case 5:
                        return toMultilineEdit();
                    case 6:
                        return toTable();
                    case 7:
                        return toBooleanGroup();
                    default:
                        return "<!-- unknown datatype -->";
                }
            } else {
                return "<!-- no datatype -->";
            }
        } catch (Exception ex) {
            LOGGER.warn("error", ex);
            return "<!-- error -->";
        }
    }
    
    public List<ClassItemTreeWrapper> getDict(Long dictId) {
        return dictMap.get(dictId);
    }
    
    public void putDict(Long dictId, List<ClassItemTreeWrapper> dict) {
        dictMap.put(dictId, dict);
    }
}