package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author harlow
 * @version 1.0
 * @updated 18-一月-2016 10:53:53
 */
@Entity
@Table(name = "e_sampleshipping")
@Access(AccessType.PROPERTY)
public class SampleShipping extends MES2AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5053616728970135004L;
	/**
	 * 小样品出货数量
	 */
	private int qty;
	/**
	 * 批次总数量
	 */
	private int qtyTotal;
	/**
	 * 品质
	 */
	private String quality;
	/**
	 * 备注
	 */
	private String note;
	public FTLot ftLot;
	public ReelDisk reelDisk;

	public SampleShipping() {

	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getQtyTotal() {
		return qtyTotal;
	}

	public void setQtyTotal(int qtyTotal) {
		this.qtyTotal = qtyTotal;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ft_lot_ID", referencedColumnName = "ID")
	public FTLot getFtLot() {
		return ftLot;
	}

	public void setFtLot(FTLot ftLot) {
		this.ftLot = ftLot;
	}

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "reel_disk_ID", referencedColumnName = "ID")
	public ReelDisk getReelDisk() {
		return reelDisk;
	}

	public void setReelDisk(ReelDisk reelDisk) {
		this.reelDisk = reelDisk;
	}

	@Override
	public String[] businessKeys() {
		return new String[] { String.valueOf(getId()),
				this.getCreateTimestamp().toString() };
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ftLot == null) ? 0 : ftLot.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + qty;
		result = prime * result + qtyTotal;
		result = prime * result + ((quality == null) ? 0 : quality.hashCode());
		result = prime * result
				+ ((reelDisk == null) ? 0 : reelDisk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SampleShipping other = (SampleShipping) obj;
		if (ftLot == null) {
			if (other.ftLot != null)
				return false;
		} else if (!ftLot.equals(other.ftLot))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (qty != other.qty)
			return false;
		if (qtyTotal != other.qtyTotal)
			return false;
		if (quality == null) {
			if (other.quality != null)
				return false;
		} else if (!quality.equals(other.quality))
			return false;
		if (reelDisk == null) {
			if (other.reelDisk != null)
				return false;
		} else if (!reelDisk.equals(other.reelDisk))
			return false;
		return true;
	}

}