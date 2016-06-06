package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 用于接收前端传入的分批信息
 * @author Howard
 *
 */
public class CustomerFTLotSeparateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8870594871887085480L;

	private Long parentId;
	private List<Long>  separationQties;

	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public List<Long> getSeparationQties() {
		return separationQties;
	}
	public void setSeparationQties(List<Long> separationQties) {
		this.separationQties = separationQties;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((separationQties == null) ? 0 : separationQties.hashCode());
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
		CustomerFTLotSeparateDTO other = (CustomerFTLotSeparateDTO) obj;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (separationQties == null) {
			if (other.separationQties != null)
				return false;
		} else if (!separationQties.equals(other.separationQties))
			return false;
		return true;
	}
	
	
}
