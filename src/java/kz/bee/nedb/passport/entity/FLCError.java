package kz.bee.nedb.passport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SequenceGenerator(allocationSize=10, name="seq_flc_error", sequenceName="seq_flc_error")

@Entity
@Table(name="flc_error")
public class FLCError {
    private Long id;
    private Long schoolId;
    private Long classItemTreeId;

    @Id
    @GeneratedValue(generator="seq_flc_error", strategy= GenerationType.SEQUENCE)
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="school_id")
    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    @Column(name="class_item_tree_id")
    public Long getClassItemTreeId() {
        return classItemTreeId;
    }

    public void setClassItemTreeId(Long classItemTreeId) {
        this.classItemTreeId = classItemTreeId;
    }
}