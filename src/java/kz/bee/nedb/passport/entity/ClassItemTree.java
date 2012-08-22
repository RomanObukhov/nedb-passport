package kz.bee.nedb.passport.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import kz.bee.nedb.passport.consts.Consts;
import org.springframework.context.i18n.LocaleContextHolder;

@SequenceGenerator(allocationSize=10, name="seq_class_item_tree", sequenceName="seq_class_item_tree")

@Entity
@Table(name="class_item_tree")
public class ClassItemTree {
    private Long id;
    private Long parId;
    private Long spId;
    private String rname;
    private String kname;
    private String ename;
    private Timestamp begDate;
    private Timestamp endDate;
    private Timestamp updTime;
    private Long treeId;
    private String code;
    private Long attr1;
    
    public ClassItemTree() {
        super();
    }
    
    public ClassItemTree(Timestamp begDate) {
        super();
        
        this.begDate = begDate;
    }
    
    public ClassItemTree(ClassItemTree cloneTarget) {
        super();
        
        this.parId = cloneTarget.getParId();
        this.spId = cloneTarget.getSpId();
        this.rname = cloneTarget.getRname();
        this.kname = cloneTarget.getKname();
        this.ename = cloneTarget.getEname();
        this.begDate = cloneTarget.getBegDate();
        this.endDate = cloneTarget.getEndDate();
        this.updTime = cloneTarget.getUpdTime();
        this.treeId = cloneTarget.getTreeId();
    }

    @Id
    @GeneratedValue(generator="seq_class_item_tree", strategy= GenerationType.SEQUENCE)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="par_id")
    public Long getParId() {
        return parId;
    }

    public void setParId(Long parId) {
        this.parId = parId;
    }

    @Column(name="sp_id")
    public Long getSpId() {
        return spId;
    }

    public void setSpId(Long spId) {
        this.spId = spId;
    }

    @Column(name="rname")
    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    @Column(name="kname")
    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname;
    }

    @Column(name="ename")
    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    @Column(name="beg_date")
    public Timestamp getBegDate() {
        return begDate;
    }

    public void setBegDate(Timestamp begDate) {
        this.begDate = begDate;
    }

    @Column(name="end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Column(name="upd_time")
    public Timestamp getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Timestamp updTime) {
        this.updTime = updTime;
    }

    @Column(name="tree_id")
    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    @Column(name="code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name="attr1")
    public Long getAttr1() {
        return attr1;
    }

    public void setAttr1(Long attr1) {
        this.attr1 = attr1;
    }
 
    @Transient
    public String getLocalizedName() {
        String lang = LocaleContextHolder.getLocale().getLanguage();
        if (Consts.LANG_EN.equalsIgnoreCase(lang)) {
            return ename;
        } else if (Consts.LANG_KK.equalsIgnoreCase(lang)) {
            return kname;
        } else {
            return rname;
        }
    }
}