package kz.bee.nedb.passport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import kz.bee.nedb.passport.consts.Consts;
import org.springframework.context.i18n.LocaleContextHolder;

@Entity
@Table(name="school_list")
public class SchoolList {
    private Long id;
    private Long classItemTreeId;
    private String rname;
    private String kname;
    private String ename;
    private Long passConfirmed;
    private Long passApproved;
    private Long orgTypeId;

    @Id
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="class_item_tree_id")
    public Long getClassItemTreeId() {
        return classItemTreeId;
    }

    public void setClassItemTreeId(Long classItemTreeId) {
        this.classItemTreeId = classItemTreeId;
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

    @Column(name="pass_confirmed")
    public Long getPassConfirmed() {
        return passConfirmed;
    }

    public void setPassConfirmed(Long passConfirmed) {
        this.passConfirmed = passConfirmed;
    }

    @Column(name="pass_approved")
    public Long getPassApproved() {
        return passApproved;
    }

    public void setPassApproved(Long passApproved) {
        this.passApproved = passApproved;
    }

    @Column(name="org_type_id")
    public Long getOrgTypeId() {
        return orgTypeId;
    }

    public void setOrgTypeId(Long orgTypeId) {
        this.orgTypeId = orgTypeId;
    }
    
    @Transient
    public String getLocalizedName() {
        String lang = LocaleContextHolder.getLocale().getLanguage();
        
        if (Consts.LANG_KK.equalsIgnoreCase(lang)) {
            return kname;
        } else if (Consts.LANG_EN.equalsIgnoreCase(lang)) {
            return ename;
        } else {
            return rname;
        }
    }
}
