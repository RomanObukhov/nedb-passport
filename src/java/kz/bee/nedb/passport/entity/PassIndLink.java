package kz.bee.nedb.passport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.context.i18n.LocaleContextHolder;

@Entity
@Table(name="pass_ind_link")
public class PassIndLink {
    private Long id;
    private Long indId;
    private Long passId;
    private Long datatype;
    private Long measureId;
    private Long orderNum;
    private String dataformat;
    private String dataFormatRmessage;
    private String dataFormatKmessage;
    private String dataFormatEmessage;

    @Id
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="ind_id")
    public Long getIndId() {
        return indId;
    }

    public void setIndId(Long indId) {
        this.indId = indId;
    }

    @Column(name="pass_id")
    public Long getPassId() {
        return passId;
    }

    public void setPassId(Long passId) {
        this.passId = passId;
    }

    @Column(name="datatype")
    public Long getDatatype() {
        return datatype;
    }

    public void setDatatype(Long datatype) {
        this.datatype = datatype;
    }

    @Column(name="measure_id")
    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    @Column(name="order_num")
    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    @Column(name="dataformat")
    public String getDataformat() {
        return dataformat;
    }

    public void setDataformat(String dataformat) {
        this.dataformat = dataformat;
    }

    @Column(name="data_format_rmessage")
    public String getDataFormatRmessage() {
        return dataFormatRmessage;
    }

    public void setDataFormatRmessage(String dataFormatRmessage) {
        this.dataFormatRmessage = dataFormatRmessage;
    }

    @Column(name="data_format_kmessage")
    public String getDataFormatKmessage() {
        return dataFormatKmessage;
    }

    public void setDataFormatKmessage(String dataFormatKmessage) {
        this.dataFormatKmessage = dataFormatKmessage;
    }

    @Column(name="data_format_emessage")
    public String getDataFormatEmessage() {
        return dataFormatEmessage;
    }

    public void setDataFormatEmessage(String dataFormatEmessage) {
        this.dataFormatEmessage = dataFormatEmessage;
    }
    
    @Transient
    public String getLocalizedFormatMessage() {
        String lang = LocaleContextHolder.getLocale().getLanguage();
        
        if ("kk".equalsIgnoreCase(lang)) {
            return dataFormatKmessage;
        } else if ("en".equalsIgnoreCase(lang)) {
            return dataFormatEmessage;
        } else {
            return dataFormatRmessage;
        }
    }
}