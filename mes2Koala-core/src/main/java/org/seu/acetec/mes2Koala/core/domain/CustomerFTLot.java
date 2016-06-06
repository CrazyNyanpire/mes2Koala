package org.seu.acetec.mes2Koala.core.domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "E_FT_CUSTOMER_LOT")
@Access(AccessType.PROPERTY)
public class CustomerFTLot extends CustomerLot {

    private static final long serialVersionUID = 7884795641361084632L;

    private String packageNumber;

    private String dateCode;                // Date Code

    private String materialType;            // 物料类型

    private String taxType;                 // 保税类型

    private String wireBond;

    private String waferLot;

    private String MFGPN;

    private String waferManufacturer;       // 晶圆厂商

    private FTInfo ftInfo;
    private List<FTLot> ftLots;

    @OneToMany(mappedBy = "customerFTLot", fetch = FetchType.EAGER)
    public List<FTLot> getFtLots() {
		return ftLots;
	}

	public void setFtLots(List<FTLot> ftLots) {
		this.ftLots = ftLots;
	}

    @ManyToOne
    @JoinColumn(name = "FT_INFO_ID", referencedColumnName = "ID")
    public FTInfo getFtInfo() {
        return ftInfo;
    }

    public void setFtInfo(FTInfo ftInfo) {
        this.ftInfo = ftInfo;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getWireBond() {
        return wireBond;
    }

    public void setWireBond(String wireBond) {
        this.wireBond = wireBond;
    }

    public String getWaferLot() {
        return waferLot;
    }

    public void setWaferLot(String waferLot) {
        this.waferLot = waferLot;
    }

    public String getMFGPN() {
        return MFGPN;
    }

    public void setMFGPN(String mFGPN) {
        MFGPN = mFGPN;
    }

    public String getWaferManufacturer() {
        return waferManufacturer;
    }

    public void setWaferManufacturer(String waferManufacturer) {
        this.waferManufacturer = waferManufacturer;
    }


    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerFTLot other = (CustomerFTLot) obj;
		if (MFGPN == null) {
			if (other.MFGPN != null)
				return false;
		} else if (!MFGPN.equals(other.MFGPN))
			return false;
		if (dateCode == null) {
			if (other.dateCode != null)
				return false;
		} else if (!dateCode.equals(other.dateCode))
			return false;
		if (ftInfo == null) {
			if (other.ftInfo != null)
				return false;
		} else if (!ftInfo.equals(other.ftInfo))
			return false;
		//if (ftLot == null) {
		//	if (other.ftLot != null)
		//		return false;
		//} else if (!ftLot.equals(other.ftLot))
		//	return false;
		if (materialType == null) {
			if (other.materialType != null)
				return false;
		} else if (!materialType.equals(other.materialType))
			return false;
		if (packageNumber == null) {
			if (other.packageNumber != null)
				return false;
		} else if (!packageNumber.equals(other.packageNumber))
			return false;
		if (taxType == null) {
			if (other.taxType != null)
				return false;
		} else if (!taxType.equals(other.taxType))
			return false;
		if (waferLot == null) {
			if (other.waferLot != null)
				return false;
		} else if (!waferLot.equals(other.waferLot))
			return false;
		if (waferManufacturer == null) {
			if (other.waferManufacturer != null)
				return false;
		} else if (!waferManufacturer.equals(other.waferManufacturer))
			return false;
		if (wireBond == null) {
			if (other.wireBond != null)
				return false;
		} else if (!wireBond.equals(other.wireBond))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((MFGPN == null) ? 0 : MFGPN.hashCode());
		result = prime * result + ((dateCode == null) ? 0 : dateCode.hashCode());
		result = prime * result + ((ftInfo == null) ? 0 : ftInfo.hashCode());
		//result = prime * result + ((ftLot == null) ? 0 : ftLot.hashCode());
		result = prime * result + ((materialType == null) ? 0 : materialType.hashCode());
		result = prime * result + ((packageNumber == null) ? 0 : packageNumber.hashCode());
		result = prime * result + ((taxType == null) ? 0 : taxType.hashCode());
		result = prime * result + ((waferLot == null) ? 0 : waferLot.hashCode());
		result = prime * result + ((waferManufacturer == null) ? 0 : waferManufacturer.hashCode());
		result = prime * result + ((wireBond == null) ? 0 : wireBond.hashCode());
		return result;
	}

    @Override
    public String[] businessKeys() {
        return new String[0];
    }
}
