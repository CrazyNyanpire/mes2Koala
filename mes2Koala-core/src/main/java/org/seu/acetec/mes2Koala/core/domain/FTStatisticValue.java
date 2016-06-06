package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "E_FT_STATISTICS_VALUE")
@Access(AccessType.PROPERTY)
public class FTStatisticValue extends MES2AbstractEntity{

	private String name;
	private String qty;
	private String quality;
	private String nodename;
	private String site;
	
	
	
	@Override
	public String[] businessKeys() {
		// TODO Auto-generated method stub
		return null;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getQty() {
		return qty;
	}



	public void setQty(String qty) {
		this.qty = qty;
	}



	public String getQuality() {
		return quality;
	}



	public void setQuality(String quality) {
		this.quality = quality;
	}



	public String getNodename() {
		return nodename;
	}



	public void setNodename(String nodename) {
		this.nodename = nodename;
	}



	public String getSite() {
		return site;
	}



	public void setSite(String site) {
		this.site = site;
	}

}
