package org.seu.acetec.mes2Koala.core.domain;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by LCN on 2015/12/2.
 * Improved by LHB
 */
@Entity
@Table(name = "E_Label_Plan")
@Access(AccessType.PROPERTY)
public class LabelPlan extends MES2AbstractEntity {

    private Label labelInside;

    private Label labelOutside;

    private Label labelReel;

    private InternalProduct internalProduct;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "LABEL_INSIDE_ID")
    public Label getLabelInside() {
        return labelInside;
    }

    public void setLabelInside(Label labelInside) {
        this.labelInside = labelInside;
    }

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "LABEL_OUTSIDE_ID")
    public Label getLabelOutside() {
        return labelOutside;
    }

    public void setLabelOutside(Label labelOutside) {
        this.labelOutside = labelOutside;
    }

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "LABEL_REEL_ID")
    public Label getLabelReel() {
        return labelReel;
    }

    public void setLabelReel(Label labelReel) {
        this.labelReel = labelReel;
    }

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "INTERNAL_PRODUCT_ID")
    public InternalProduct getInternalProduct() {
        return internalProduct;
    }

    public void setInternalProduct(InternalProduct internalProduct) {
        this.internalProduct = internalProduct;
    }

	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}

}
