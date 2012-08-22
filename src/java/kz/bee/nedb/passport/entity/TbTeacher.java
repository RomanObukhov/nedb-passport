package kz.bee.nedb.passport.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SequenceGenerator(allocationSize=10, name="seq_tb_teacher", sequenceName="seq_tb_teacher")

@Entity
@Table(name="tb_teacher")
public class TbTeacher {
    private Long id;
    private Long teacherDataId;
    private Long passIndSpLinkId;
    private Long teacherId;
    private Double numberValue;
    private Long booleanValue;
    private Date dateValue;
    private Long classItemTreeId;
    private Long dimv1;
    private Long dimv2;
    private Long dimv3;
    private Long dimv4;
    private Long dimv5;
    private Long dimv6;
    private Long dimv7;
    private Long dimv8;
    private Long dimv9;
    private Long dimv10;
    private Long passIndLinkId;
    private String stringValue;
    private Long orderNum;
    private Long schoolId;
    private int mnt;
    private int god;
    private Date confDate;

    @Id
    @GeneratedValue(generator="seq_tb_teacher", strategy= GenerationType.SEQUENCE)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="conf_date")
    public Date getConfDate() {
        return confDate;
    }

    public void setConfDate(Date confDate) {
        this.confDate = confDate;
    }
    
    @Column(name="teacher_data_id")
    public Long getTeacherDataId() {
        return teacherDataId;
    }

    public void setTeacherDataId(Long teacherDataId) {
        this.teacherDataId = teacherDataId;
    }

    @Column(name="mnt")
    public int getMnt() {
        return mnt;
    }

    public void setMnt(int mnt) {
        this.mnt = mnt;
    }

    @Column(name="god")
    public int getGod() {
        return god;
    }

    public void setGod(int god) {
        this.god = god;
    }

    @Column(name="pass_ind_sp_link_id")
    public Long getPassIndSpLinkId() {
        return passIndSpLinkId;
    }

    public void setPassIndSpLinkId(Long passIndSpLinkId) {
        this.passIndSpLinkId = passIndSpLinkId;
    }

    @Column(name="teacher_id")
    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Column(name="number_value")
    public Double getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(Double numberValue) {
        this.numberValue = numberValue;
    }

    @Column(name="boolean_value")
    public Long getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Long booleanValue) {
        this.booleanValue = booleanValue;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="date_value")
    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    @Column(name="class_item_tree_id")
    public Long getClassItemTreeId() {
        return classItemTreeId;
    }

    public void setClassItemTreeId(Long classItemTreeId) {
        this.classItemTreeId = classItemTreeId;
    }

    @Column(name="dimv1")
    public Long getDimv1() {
        return dimv1;
    }

    public void setDimv1(Long dimv1) {
        this.dimv1 = dimv1;
    }

    @Column(name="dimv2")
    public Long getDimv2() {
        return dimv2;
    }

    public void setDimv2(Long dimv2) {
        this.dimv2 = dimv2;
    }

    @Column(name="dimv3")
    public Long getDimv3() {
        return dimv3;
    }

    public void setDimv3(Long dimv3) {
        this.dimv3 = dimv3;
    }

    @Column(name="dimv4")
    public Long getDimv4() {
        return dimv4;
    }

    public void setDimv4(Long dimv4) {
        this.dimv4 = dimv4;
    }

    @Column(name="dimv5")
    public Long getDimv5() {
        return dimv5;
    }

    public void setDimv5(Long dimv5) {
        this.dimv5 = dimv5;
    }

    @Column(name="dimv6")
    public Long getDimv6() {
        return dimv6;
    }

    public void setDimv6(Long dimv6) {
        this.dimv6 = dimv6;
    }

    @Column(name="dimv7")
    public Long getDimv7() {
        return dimv7;
    }

    public void setDimv7(Long dimv7) {
        this.dimv7 = dimv7;
    }

    @Column(name="dimv8")
    public Long getDimv8() {
        return dimv8;
    }

    public void setDimv8(Long dimv8) {
        this.dimv8 = dimv8;
    }

    @Column(name="dimv9")
    public Long getDimv9() {
        return dimv9;
    }

    public void setDimv9(Long dimv9) {
        this.dimv9 = dimv9;
    }

    @Column(name="dimv10")
    public Long getDimv10() {
        return dimv10;
    }

    public void setDimv10(Long dimv10) {
        this.dimv10 = dimv10;
    }

    @Column(name="pass_ind_link_id")
    public Long getPassIndLinkId() {
        return passIndLinkId;
    }

    public void setPassIndLinkId(Long passIndLinkId) {
        this.passIndLinkId = passIndLinkId;
    }

    @Column(name="string_value")
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Column(name="order_num")
    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    @Column(name="school_id")
    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
    
    public boolean match(List<Long> dims) {
        if ((dims == null) || dims.isEmpty()) {
            return false;
        } else {
            int index = 1;
            for (Long dim: dims) {
                switch (index) {
                    case 1:
                        if (!dim.equals(dimv1)) {
                            return false;
                        }
                        break;
                    case 2:
                        if (!dim.equals(dimv2)) {
                            return false;
                        }
                        break;
                    case 3:
                        if (!dim.equals(dimv3)) {
                            return false;
                        }
                        break;
                    case 4:
                        if (!dim.equals(dimv4)) {
                            return false;
                        }
                        break;
                    case 5:
                        if (!dim.equals(dimv5)) {
                            return false;
                        }
                        break;
                    case 6:
                        if (!dim.equals(dimv6)) {
                            return false;
                        }
                        break;
                    case 7:
                        if (!dim.equals(dimv7)) {
                            return false;
                        }
                        break;
                    case 8:
                        if (!dim.equals(dimv8)) {
                            return false;
                        }
                        break;
                    case 9:
                        if (!dim.equals(dimv9)) {
                            return false;
                        }
                        break;
                    case 10:
                        if (!dim.equals(dimv10)) {
                            return false;
                        }
                        break;
                    default:
                        return false;
                }
                index ++;
            }
            
            return true;
        }
    }
    
    @Transient
    public List<Long> getDims() {
        ArrayList<Long> result = new ArrayList<Long>();
        
        if (dimv1 != null) {
            result.add(dimv1);
        }
        
        if (dimv2 != null) {
            result.add(dimv2);
        }
        
        if (dimv3 != null) {
            result.add(dimv3);
        }
        
        if (dimv4 != null) {
            result.add(dimv4);
        }
        
        if (dimv5 != null) {
            result.add(dimv5);
        }
        
        if (dimv6 != null) {
            result.add(dimv6);
        }
        
        if (dimv7 != null) {
            result.add(dimv7);
        }
        
        if (dimv8 != null) {
            result.add(dimv8);
        }
        
        if (dimv9 != null) {
            result.add(dimv9);
        }
        
        if (dimv10 != null) {
            result.add(dimv10);
        }
        
        return result;
    }
    
    public void setDims(List<Long> dims) {
        if ((dims != null) && !dims.isEmpty()) {
            int index = 1;
            for (Long dim: dims) {
                switch (index) {
                    case 1:
                        dimv1 = dim;
                        break;
                    case 2:
                        dimv2 = dim;
                        break;
                    case 3:
                        dimv3 = dim;
                        break;
                    case 4:
                        dimv4 = dim;
                        break;
                    case 5:
                        dimv5 = dim;
                        break;
                    case 6:
                        dimv6 = dim;
                        break;
                    case 7:
                        dimv7 = dim;
                        break;
                    case 8:
                        dimv8 = dim;
                        break;
                    case 9:
                        dimv9 = dim;
                        break;
                    case 10:
                        dimv10 = dim;
                        break;
                    default:
                        break;
                }
                index ++;
            }
        }
    }
}