package kz.bee.nedb.passport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import kz.bee.nedb.passport.consts.Consts;
import org.springframework.context.i18n.LocaleContextHolder;

@Entity
@Table(name="indicator")
public class Indicator {
    private Long id;
    private String rname;
    private String kname;
    private String ename;

    @Id
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    @Transient
    public String getLocalizedName() {
        String lang = LocaleContextHolder.getLocale().getLanguage();
        if (Consts.LANG_KK.equals(lang)) {
            return kname;
        } else if (Consts.LANG_EN.equals(lang)) {
            return ename;
        } else {
            return rname;
        }
    }
}