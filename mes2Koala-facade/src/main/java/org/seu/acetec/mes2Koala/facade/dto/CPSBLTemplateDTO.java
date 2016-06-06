package org.seu.acetec.mes2Koala.facade.dto;

import org.seu.acetec.mes2Koala.core.domain.CPSBLTemplate.LimitUnit;
import org.seu.acetec.mes2Koala.core.domain.CPSBLTemplate.Quality;
import org.seu.acetec.mes2Koala.core.domain.CPSBLTemplate.Type;

import java.util.Date;
import java.io.Serializable;

public class CPSBLTemplateDTO implements Serializable {

	private static final long serialVersionUID = -1551497615241737645L;

	private Long id;

    private int version;
			
	private String site;
				
	private LimitUnit limitUnit;
		
	private String testRange;
		
	private String controlType;
	
	private Date lastModifyTimestamp;
		
	private Date lastModifyTimestampEnd;
				
	private Type type;
		
	private Integer logic;
				
	private String lastModifyEmployNo;
			
	private Date createTimestamp;
	
	private Date createTimestampEnd;
				
	private String node;
								
	private Double upperLimit;
		
	private String createEmployNo;
						
	private Double lowerLimit;
		
	private String parentIntegrationIds;
		
	private String name;
						
	private Integer rule;
		
	private Quality quality;
		
	private InternalProductDTO internalProductDTO;

	public void setControlType(String controlType) { 
		this.controlType = controlType;
	}

	public String getControlType() {
		return this.controlType;
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

	public void setLogic(Integer logic) { 
		this.logic = logic;
	}

	public Integer getLogic() {
		return this.logic;
	}
		
	public void setLastModifyEmployNo(String lastModifyEmployNo) { 
		this.lastModifyEmployNo = lastModifyEmployNo;
	}

	public String getLastModifyEmployNo() {
		return this.lastModifyEmployNo;
	}
		
	public void setCreateTimestamp(Date createTimestamp) { 
		this.createTimestamp = createTimestamp;
	}

	public Date getCreateTimestamp() {
		return this.createTimestamp;
	}
		
		public void setCreateTimestampEnd(Date createTimestampEnd) { 
		this.createTimestampEnd = createTimestampEnd;
	}

	public Date getCreateTimestampEnd() {
		return this.createTimestampEnd;
	}

	public void setNode(String node) { 
		this.node = node;
	}

	public String getNode() {
		return this.node;
	}

	public void setUpperLimit(Double upperLimit) { 
		this.upperLimit = upperLimit;
	}

	public Double getUpperLimit() {
		return this.upperLimit;
	}

	public void setCreateEmployNo(String createEmployNo) { 
		this.createEmployNo = createEmployNo;
	}

	public String getCreateEmployNo() {
		return this.createEmployNo;
	}

	public void setLowerLimit(Double lowerLimit) { 
		this.lowerLimit = lowerLimit;
	}

	public Double getLowerLimit() {
		return this.lowerLimit;
	}
		
	public void setParentIntegrationIds(String parentIntegrationIds) { 
		this.parentIntegrationIds = parentIntegrationIds;
	}

	public String getParentIntegrationIds() {
		return this.parentIntegrationIds;
	}

	public void setName(String name) { 
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setRule(Integer rule) { 
		this.rule = rule;
	}

	public Integer getRule() {
		return this.rule;
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
		CPSBLTemplateDTO other = (CPSBLTemplateDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public LimitUnit getLimitUnit() {
		return limitUnit;
	}

	public void setLimitUnit(LimitUnit limitUnit) {
		this.limitUnit = limitUnit;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Quality getQuality() {
		return quality;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	public InternalProductDTO getInternalProductDTO() {
		return internalProductDTO;
	}

	public void setInternalProductDTO(InternalProductDTO internalProductDTO) {
		this.internalProductDTO = internalProductDTO;
	}

	public String getTestRange() {
		return testRange;
	}

	public void setTestRange(String testRange) {
		this.testRange = testRange;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
}