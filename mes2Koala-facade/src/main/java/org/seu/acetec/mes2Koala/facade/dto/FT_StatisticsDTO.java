package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;

public class FT_StatisticsDTO implements Serializable {

	private Long id;

	private int version;

							
    private String yield = "0";
    private String loss = "0";
    private String backUp = "0";
    private String other = "0";
    private String resultSum = "0";
    private String markF = "0";
    private String fail = "0";
    private String lat = "0";
    
	private String site1Name = "";
	private String site2Name = "";
	private String site3Name = "";
	private String site4Name = "";
	private String site5Name = "";
	private String site6Name = "";
	private String site7Name = "";
	private String site8Name = "";
	private String site9Name = "";
	private String site10Name = "";
	private String site11Name = "";
	private String site12Name = "";
	private String site13Name = "";
	private String site14Name = "";
	private String site15Name = "";
	
	private String site1Num = "0";
	private String site2Num = "0";
	private String site3Num = "0";
	private String site4Num = "0";
	private String site5Num = "0";
	private String site6Num = "0";
	private String site7Num = "0";
	private String site8Num = "0";
	private String site9Num = "0";
	private String site10Num = "0";
	private String site11Num = "0";
	private String site12Num = "0";
	private String site13Num = "0";
	private String site14Num = "0";
	private String site15Num = "0";
	
	private String site1Quality = "";
	private String site2Quality = "";
	private String site3Quality = "";
	private String site4Quality = "";
	private String site5Quality = "";
	private String site6Quality = "";
	private String site7Quality = "";
	private String site8Quality = "";
	private String site9Quality = "";
	private String site10Quality = "";
	private String site11Quality = "";
	private String site12Quality = "";
	private String site13Quality = "";
	private String site14Quality = "";
	private String site15Quality = "";
	
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
		FT_StatisticsDTO other = (FT_StatisticsDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

	public String getLoss() {
		return loss;
	}

	public void setLoss(String loss) {
		this.loss = loss;
	}

	public String getBackUp() {
		return backUp;
	}

	public void setBackUp(String backUp) {
		this.backUp = backUp;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getResultSum() {
		return resultSum;
	}

	public void setResultSum(String resultSum) {
		this.resultSum = resultSum;
	}

	public String getMarkF() {
		return markF;
	}

	public void setMarkF(String markF) {
		this.markF = markF;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getSite1Name() {
		return site1Name;
	}

	public void setSite1Name(String site1Name) {
		this.site1Name = site1Name;
	}

	public String getSite2Name() {
		return site2Name;
	}

	public void setSite2Name(String site2Name) {
		this.site2Name = site2Name;
	}

	public String getSite3Name() {
		return site3Name;
	}

	public void setSite3Name(String site3Name) {
		this.site3Name = site3Name;
	}

	public String getSite4Name() {
		return site4Name;
	}

	public void setSite4Name(String site4Name) {
		this.site4Name = site4Name;
	}

	public String getSite5Name() {
		return site5Name;
	}

	public void setSite5Name(String site5Name) {
		this.site5Name = site5Name;
	}

	public String getSite1Num() {
		return site1Num;
	}

	public void setSite1Num(String site1Num) {
		this.site1Num = site1Num;
	}

	public String getSite2Num() {
		return site2Num;
	}

	public void setSite2Num(String site2Num) {
		this.site2Num = site2Num;
	}

	public String getSite3Num() {
		return site3Num;
	}

	public void setSite3Num(String site3Num) {
		this.site3Num = site3Num;
	}

	public String getSite4Num() {
		return site4Num;
	}

	public void setSite4Num(String site4Num) {
		this.site4Num = site4Num;
	}

	public String getSite5Num() {
		return site5Num;
	}

	public void setSite5Num(String site5Num) {
		this.site5Num = site5Num;
	}

	public String getSite1Quality() {
		return site1Quality;
	}

	public void setSite1Quality(String site1Quality) {
		this.site1Quality = site1Quality;
	}

	public String getSite2Quality() {
		return site2Quality;
	}

	public void setSite2Quality(String site2Quality) {
		this.site2Quality = site2Quality;
	}

	public String getSite3Quality() {
		return site3Quality;
	}

	public void setSite3Quality(String site3Quality) {
		this.site3Quality = site3Quality;
	}

	public String getSite4Quality() {
		return site4Quality;
	}

	public void setSite4Quality(String site4Quality) {
		this.site4Quality = site4Quality;
	}

	public String getSite5Quality() {
		return site5Quality;
	}

	public void setSite5Quality(String site5Quality) {
		this.site5Quality = site5Quality;
	}
	
	public String getSite6Name() {
		return site6Name;
	}
	public void setSite6Name(String site6Name) {
		this.site6Name = site6Name;
	}
	public String getSite7Name() {
		return site7Name;
	}
	public void setSite7Name(String site7Name) {
		this.site7Name = site7Name;
	}
	public String getSite8Name() {
		return site8Name;
	}
	public void setSite8Name(String site8Name) {
		this.site8Name = site8Name;
	}
	public String getSite9Name() {
		return site9Name;
	}
	public void setSite9Name(String site9Name) {
		this.site9Name = site9Name;
	}
	public String getSite10Name() {
		return site10Name;
	}
	public void setSite10Name(String site10Name) {
		this.site10Name = site10Name;
	}
	public String getSite11Name() {
		return site11Name;
	}
	public void setSite11Name(String site11Name) {
		this.site11Name = site11Name;
	}
	public String getSite12Name() {
		return site12Name;
	}
	public void setSite12Name(String site12Name) {
		this.site12Name = site12Name;
	}
	public String getSite13Name() {
		return site13Name;
	}
	public void setSite13Name(String site13Name) {
		this.site13Name = site13Name;
	}
	public String getSite14Name() {
		return site14Name;
	}
	public void setSite14Name(String site14Name) {
		this.site14Name = site14Name;
	}
	public String getSite15Name() {
		return site15Name;
	}
	public void setSite15Name(String site15Name) {
		this.site15Name = site15Name;
	}
	public String getSite6Num() {
		return site6Num;
	}
	public void setSite6Num(String site6Num) {
		this.site6Num = site6Num;
	}
	public String getSite7Num() {
		return site7Num;
	}
	public void setSite7Num(String site7Num) {
		this.site7Num = site7Num;
	}
	public String getSite8Num() {
		return site8Num;
	}
	public void setSite8Num(String site8Num) {
		this.site8Num = site8Num;
	}
	public String getSite9Num() {
		return site9Num;
	}
	public void setSite9Num(String site9Num) {
		this.site9Num = site9Num;
	}
	public String getSite10Num() {
		return site10Num;
	}
	public void setSite10Num(String site10Num) {
		this.site10Num = site10Num;
	}
	public String getSite11Num() {
		return site11Num;
	}
	public void setSite11Num(String site11Num) {
		this.site11Num = site11Num;
	}
	public String getSite12Num() {
		return site12Num;
	}
	public void setSite12Num(String site12Num) {
		this.site12Num = site12Num;
	}
	public String getSite13Num() {
		return site13Num;
	}
	public void setSite13Num(String site13Num) {
		this.site13Num = site13Num;
	}
	public String getSite14Num() {
		return site14Num;
	}
	public void setSite14Num(String site14Num) {
		this.site14Num = site14Num;
	}
	public String getSite15Num() {
		return site15Num;
	}
	public void setSite15Num(String site15Num) {
		this.site15Num = site15Num;
	}
	public String getSite6Quality() {
		return site6Quality;
	}
	public void setSite6Quality(String site6Quality) {
		this.site6Quality = site6Quality;
	}
	public String getSite7Quality() {
		return site7Quality;
	}
	public void setSite7Quality(String site7Quality) {
		this.site7Quality = site7Quality;
	}
	public String getSite8Quality() {
		return site8Quality;
	}
	public void setSite8Quality(String site8Quality) {
		this.site8Quality = site8Quality;
	}
	public String getSite9Quality() {
		return site9Quality;
	}
	public void setSite9Quality(String site9Quality) {
		this.site9Quality = site9Quality;
	}
	public String getSite10Quality() {
		return site10Quality;
	}
	public void setSite10Quality(String site10Quality) {
		this.site10Quality = site10Quality;
	}
	public String getSite11Quality() {
		return site11Quality;
	}
	public void setSite11Quality(String site11Quality) {
		this.site11Quality = site11Quality;
	}
	public String getSite12Quality() {
		return site12Quality;
	}
	public void setSite12Quality(String site12Quality) {
		this.site12Quality = site12Quality;
	}
	public String getSite13Quality() {
		return site13Quality;
	}
	public void setSite13Quality(String site13Quality) {
		this.site13Quality = site13Quality;
	}
	public String getSite14Quality() {
		return site14Quality;
	}
	public void setSite14Quality(String site14Quality) {
		this.site14Quality = site14Quality;
	}
	public String getSite15Quality() {
		return site15Quality;
	}
	public void setSite15Quality(String site15Quality) {
		this.site15Quality = site15Quality;
	}
}