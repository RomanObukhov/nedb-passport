package kz.bee.nedb.passport.classes;

import java.util.ArrayList;
import java.util.List;
import kz.bee.nedb.passport.entity.wrapper.ClassItemTreeWrapper;

public class HeaderCellWrapper {
    private List<Long> values = new ArrayList<Long>();
    private List<String> labels = new ArrayList<String>();
    
    public static List<HeaderCellWrapper> append(List<HeaderCellWrapper> headerCellWrappers, List<ClassItemTreeWrapper> classItemTreeWrappers) {
        ArrayList<HeaderCellWrapper> result = new ArrayList<HeaderCellWrapper>();
        
        if (headerCellWrappers.isEmpty()) {
            for (ClassItemTreeWrapper classItemTreeWrapper: classItemTreeWrappers) {
                result.add(new HeaderCellWrapper(classItemTreeWrapper.getId(), classItemTreeWrapper.getLocalizedName()));
            }
        } else {
            for (HeaderCellWrapper headerCellWrapper: headerCellWrappers) {
                for (ClassItemTreeWrapper classItemTreeWrapper: classItemTreeWrappers) {
                    result.add(new HeaderCellWrapper(headerCellWrapper, classItemTreeWrapper.getId(), classItemTreeWrapper.getLocalizedName()));
                }
            }
        }
        
        return result;
    }
    
    public HeaderCellWrapper(Long value, String label) {
        super();
        values.add(value);
        labels.add(label);
    }
    
    public HeaderCellWrapper(HeaderCellWrapper headerCellWrapper, Long newValue, String newLabel) {
        super();
        values.addAll(headerCellWrapper.values);
        values.add(newValue);
        labels.addAll(headerCellWrapper.labels);
        labels.add(newLabel);
    }

    public List<Long> getValues() {
        return values;
    }

    public void setValues(List<Long> values) {
        this.values = values;
    }
    
    public String getLabelsWithSeparator(String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        
        boolean first = true;
        for (String label: labels) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append(separator);
            }
            stringBuilder.append(label);
        }
        
        return stringBuilder.toString();
    }
}