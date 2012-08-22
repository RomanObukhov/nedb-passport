package kz.bee.nedb.passport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.context.i18n.LocaleContextHolder;

@Entity
@Table(name="measure")
public class Measure {
    private Long id;
    private String rname;
    private String kname;
    private String ename;
    private String shortRname;
    private String shortKname;
    private String shortEname;
    private Double multiplier;
    private Long parId;

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

    @Column(name="short_rname")
    public String getShortRname() {
        return shortRname;
    }

    public void setShortRname(String shortRname) {
        this.shortRname = shortRname;
    }

    @Column(name="short_kname")
    public String getShortKname() {
        return shortKname;
    }

    public void setShortKname(String shortKname) {
        this.shortKname = shortKname;
    }

    @Column(name="short_ename")
    public String getShortEname() {
        return shortEname;
    }

    public void setShortEname(String shortEname) {
        this.shortEname = shortEname;
    }

    @Column(name="multiplier")
    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    @Column(name="par_id")
    public Long getParId() {
        return parId;
    }

    public void setParId(Long parId) {
        this.parId = parId;
    }
    
    @Transient
    public String getLocalizedName() {
        String lang = LocaleContextHolder.getLocale().getLanguage();
        if ("en".equalsIgnoreCase(lang)) {
            return ename;
        } else if ("kz".equalsIgnoreCase(lang)) {
            return kname;
        } else {
            return rname;
        }
    }
    
    @Transient
    public String getLocalizedShortName() {
        String lang = LocaleContextHolder.getLocale().getLanguage();
        if ("en".equalsIgnoreCase(lang)) {
            return shortEname;
        } else if ("kz".equalsIgnoreCase(lang)) {
            return shortKname;
        } else {
            return shortRname;
        }
    }
}