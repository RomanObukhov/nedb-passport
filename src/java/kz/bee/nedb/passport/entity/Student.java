package kz.bee.nedb.passport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SequenceGenerator(allocationSize=10, name="seq_student", sequenceName="seq_student")

@Entity
@Table(name="student")
public class Student {
    private Long id;
    private String iin;
    private String f;
    private String i;
    private String o;

    @Id
    @GeneratedValue(generator="seq_student", strategy= GenerationType.SEQUENCE)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="iin")
    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    @Column(name="f")
    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    @Column(name="i")
    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    @Column(name="o")
    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }
}