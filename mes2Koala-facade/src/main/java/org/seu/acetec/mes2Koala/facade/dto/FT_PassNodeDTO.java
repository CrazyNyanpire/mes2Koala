package org.seu.acetec.mes2Koala.facade.dto;

import java.util.Date;

import java.io.Serializable;

public class FT_PassNodeDTO implements Serializable {

	private Long id;

	private int version;

			
		private String site;
		
				
		private String result;
		
				
		private Date lastModifyTimestamp;
		
		private Date lastModifyTimestampEnd;
			
	
	public void setSite(String site) { 
		this.site = site;
	}

	public String getSite() {
		return this.site;
	}
		
			
	
	public void setResult(String result) { 
		this.result = result;
	}

	public String getResult() {
		return this.result;
	}
		
			
	
	public void setLastModifyTimestamp(Date lastModifyTimestamp) { 
		this.lastModifyTimestamp = lastModifyTimestamp;
	}

	public Date getLastModifyTimestamp() {
		return this.lastModifyTimestamp;
	}
		
		public void setLastModifyTimestampEnd(Date lastModifyTimestampEnd) { 
		this.lastModifyTimestampEnd = lastModifyTimestampEnd;
	}

	public Date getLastModifyTimestampEnd() {
		return this.lastModifyTimestampEnd;
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FT_PassNodeDTO other = (FT_PassNodeDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}