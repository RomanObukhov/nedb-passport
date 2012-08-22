package kz.bee.nedb.passport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="org_type_cit_link")
public class OrgTypeCITLink {
    private Long id;
    private Long classItemTreeId;
    private Long otClassItemTreeId;

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

    @Column(name="ot_class_item_tree_id")
    public Long getOtClassItemTreeId() {
        return otClassItemTreeId;
    }

    public void setOtClassItemTreeId(Long otClassItemTreeId) {
        this.otClassItemTreeId = otClassItemTreeId;
    }
}