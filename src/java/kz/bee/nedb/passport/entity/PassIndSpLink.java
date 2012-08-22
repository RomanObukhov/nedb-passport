package kz.bee.nedb.passport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pass_ind_sp_link")
public class PassIndSpLink {
    private Long id;
    private Long parId;
    private Long passIndLinkId;
    private Long spId;
    private Long orient;

    @Id
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

    @Column(name="pass_ind_link_id")
    public Long getPassIndLinkId() {
        return passIndLinkId;
    }

    public void setPassIndLinkId(Long passIndLinkId) {
        this.passIndLinkId = passIndLinkId;
    }

    @Column(name="sp_id")
    public Long getSpId() {
        return spId;
    }

    public void setSpId(Long spId) {
        this.spId = spId;
    }

    @Column(name="orient")
    public Long getOrient() {
        return orient;
    }

    public void setOrient(Long orient) {
        this.orient = orient;
    }
}