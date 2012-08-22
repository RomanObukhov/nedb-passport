package kz.bee.nedb.passport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="org_type_ind_link")
public class OrgTypeIndLink {
    private Long id;
    private Long passIndLinkId;
    private Long classItemTreeId;

    @Id
    @Column(name="id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="pass_ind_link_id")
    public Long getPassIndLinkId() {
        return passIndLinkId;
    }

    public void setPassIndLinkId(Long passIndLinkId) {
        this.passIndLinkId = passIndLinkId;
    }

    @Column(name="class_item_tree_id")
    public Long getClassItemTreeId() {
        return classItemTreeId;
    }

    public void setClassItemTreeId(Long classItemTreeId) {
        this.classItemTreeId = classItemTreeId;
    }
}