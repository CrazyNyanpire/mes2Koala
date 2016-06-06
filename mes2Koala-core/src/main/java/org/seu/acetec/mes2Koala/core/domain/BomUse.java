package org.seu.acetec.mes2Koala.core.domain;


import javax.persistence.*;

/**
 * @author yuxia
 * @version 2015/12/1
 */
@Entity
@Table(name = "E_BOM_USE")
@Access(value = AccessType.PROPERTY)
public class BomUse extends MES2AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = -1612410591658901549L;

    // TODO
    //
    // @ManyToOne(cascade = CascadeType.ALL)
    // @JoinColumn( name = "BOM_LIST_ID", referencedColumnName = "ID")
    // private AcetecLot acetecLot;

    private boolean selected;

    private BomTemplate bomTemplate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Column(name = "IS_SELECTED")
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     *
     * http://stackoverflow.com/questions/2302802/object-references-an-unsaved-transient-instance-save-the-transient-instance-be
     */
    @ManyToOne(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
    @JoinColumn(name = "E_BOM_ID", referencedColumnName = "ID")
    public BomTemplate getBomTemplate() {
        return bomTemplate;
    }

    public void setBomTemplate(BomTemplate bomTemplate) {
        this.bomTemplate = bomTemplate;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }
}
