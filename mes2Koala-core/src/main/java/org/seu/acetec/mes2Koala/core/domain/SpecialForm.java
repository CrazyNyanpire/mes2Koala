package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.openkoala.koala.commons.domain.KoalaAbstractEntity;

@Entity
@Table(name = "E_SPECIAL_FORM")
@Access(AccessType.PROPERTY)
public class SpecialForm extends MES2AbstractEntity {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -270826667002044071L;

	private boolean checkBoxForm;	
	private boolean checkSelfForm;	
	private boolean flowForm;
	private boolean indexForm;	
	private boolean lossForm;
	private boolean reelcodeForm;
	private boolean sizeForm;
	private boolean summaryForm;
	
	public boolean isCheckBoxForm() {
		return checkBoxForm;
	}

	public void setCheckBoxForm(boolean checkBoxForm) {
		this.checkBoxForm = checkBoxForm;
	}


	public boolean isCheckSelfForm() {
		return checkSelfForm;
	}


	public void setCheckSelfForm(boolean checkSelfForm) {
		this.checkSelfForm = checkSelfForm;
	}


	public boolean isFlowForm() {
		return flowForm;
	}


	public void setFlowForm(boolean flowForm) {
		this.flowForm = flowForm;
	}


	public boolean isIndexForm() {
		return indexForm;
	}


	public void setIndexForm(boolean indexForm) {
		this.indexForm = indexForm;
	}


	public boolean isLossForm() {
		return lossForm;
	}


	public void setLossForm(boolean lossForm) {
		this.lossForm = lossForm;
	}


	public boolean isReelcodeForm() {
		return reelcodeForm;
	}


	public void setReelcodeForm(boolean reelcodeForm) {
		this.reelcodeForm = reelcodeForm;
	}


	public boolean isSizeForm() {
		return sizeForm;
	}


	public void setSizeForm(boolean sizeForm) {
		this.sizeForm = sizeForm;
	}


	public boolean isSummaryForm() {
		return summaryForm;
	}


	public void setSummaryForm(boolean summaryForm) {
		this.summaryForm = summaryForm;
	}
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}


}
