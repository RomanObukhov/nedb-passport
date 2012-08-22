package kz.bee.nedb.passport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SequenceGenerator(allocationSize=10, name="seq_school_teacher_link", sequenceName="seq_school_teacher_link")

@Entity
@Table(name="school_teacher_link")
public class SchoolTeacherLink {
    private Long id;
    private Long schoolId;
    private Long teacherId;

    @Id
    @GeneratedValue(generator="seq_school_teacher_link", strategy= GenerationType.SEQUENCE)
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

    @Column(name="teacher_id")
    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}