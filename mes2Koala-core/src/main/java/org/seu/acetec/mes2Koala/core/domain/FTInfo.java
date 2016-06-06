package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "E_FTINFO")
@Access(AccessType.PROPERTY)
public class FTInfo extends InternalProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8118907466382499399L;



	private String H_P_1;
	private String H_P_2;
	private String H_P_3;
	private String note;
	private String packingFactory;
	private String packingType;
	private String size;
	private String TestSys1;
	private String TestSys2;
	private String TestSys3;
	private String waferFactory;
	// public EQCSetting m_EQCSetting;
	// private EQCSetting eqcSetting;

	//ReelCode固定码
	private String reelFixCode;
	//Reel盘 数量
	private String reelQty;


	public String getH_P_1() {
		return H_P_1;
	}

	public void setH_P_1(String h_P_1) {
		H_P_1 = h_P_1;
	}

	public String getH_P_2() {
		return H_P_2;
	}

	public void setH_P_2(String h_P_2) {
		H_P_2 = h_P_2;
	}

	public String getH_P_3() {
		return H_P_3;
	}

	public void setH_P_3(String h_P_3) {
		H_P_3 = h_P_3;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPackingFactory() {
		return packingFactory;
	}

	public void setPackingFactory(String packingFactory) {
		this.packingFactory = packingFactory;
	}

	public String getPackingType() {
		return packingType;
	}

	public void setPackingType(String packingType) {
		this.packingType = packingType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTestSys1() {
		return TestSys1;
	}

	public void setTestSys1(String testSys1) {
		TestSys1 = testSys1;
	}

	public String getTestSys2() {
		return TestSys2;
	}

	public void setTestSys2(String testSys2) {
		TestSys2 = testSys2;
	}

	public String getTestSys3() {
		return TestSys3;
	}

	public void setTestSys3(String testSys3) {
		TestSys3 = testSys3;
	}

	public String getWaferFactory() {
		return waferFactory;
	}

	public void setWaferFactory(String waferFactory) {
		this.waferFactory = waferFactory;
	}

	public String getReelFixCode() {
		return reelFixCode;
	}

	public void setReelFixCode(String reelFixCode) {
		this.reelFixCode = reelFixCode;
	}

	public String getReelQty() {
		return reelQty;
	}

	public void setReelQty(String reelQty) {
		this.reelQty = reelQty;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FTInfo other = (FTInfo) obj;
		if (H_P_1 == null) {
			if (other.H_P_1 != null)
				return false;
		} else if (!H_P_1.equals(other.H_P_1))
			return false;
		if (H_P_2 == null) {
			if (other.H_P_2 != null)
				return false;
		} else if (!H_P_2.equals(other.H_P_2))
			return false;
		if (H_P_3 == null) {
			if (other.H_P_3 != null)
				return false;
		} else if (!H_P_3.equals(other.H_P_3))
			return false;
		if (TestSys1 == null) {
			if (other.TestSys1 != null)
				return false;
		} else if (!TestSys1.equals(other.TestSys1))
			return false;
		if (TestSys2 == null) {
			if (other.TestSys2 != null)
				return false;
		} else if (!TestSys2.equals(other.TestSys2))
			return false;
		if (TestSys3 == null) {
			if (other.TestSys3 != null)
				return false;
		} else if (!TestSys3.equals(other.TestSys3))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (packingFactory == null) {
			if (other.packingFactory != null)
				return false;
		} else if (!packingFactory.equals(other.packingFactory))
			return false;
		if (packingType == null) {
			if (other.packingType != null)
				return false;
		} else if (!packingType.equals(other.packingType))
			return false;
		if (reelFixCode == null) {
			if (other.reelFixCode != null)
				return false;
		} else if (!reelFixCode.equals(other.reelFixCode))
			return false;
		if (reelQty == null) {
			if (other.reelQty != null)
				return false;
		} else if (!reelQty.equals(other.reelQty))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (waferFactory == null) {
			if (other.waferFactory != null)
				return false;
		} else if (!waferFactory.equals(other.waferFactory))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((H_P_1 == null) ? 0 : H_P_1.hashCode());
		result = prime * result + ((H_P_2 == null) ? 0 : H_P_2.hashCode());
		result = prime * result + ((H_P_3 == null) ? 0 : H_P_3.hashCode());
		result = prime * result + ((TestSys1 == null) ? 0 : TestSys1.hashCode());
		result = prime * result + ((TestSys2 == null) ? 0 : TestSys2.hashCode());
		result = prime * result + ((TestSys3 == null) ? 0 : TestSys3.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((packingFactory == null) ? 0 : packingFactory.hashCode());
		result = prime * result + ((packingType == null) ? 0 : packingType.hashCode());
		result = prime * result + ((reelFixCode == null) ? 0 : reelFixCode.hashCode());
		result = prime * result + ((reelQty == null) ? 0 : reelQty.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((waferFactory == null) ? 0 : waferFactory.hashCode());
		return result;
	}
}
