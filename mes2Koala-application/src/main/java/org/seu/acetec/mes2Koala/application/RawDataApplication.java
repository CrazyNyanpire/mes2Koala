package org.seu.acetec.mes2Koala.application;


import java.util.List;
import java.util.Set;
import  org.seu.acetec.mes2Koala.core.domain.RawData;

public interface RawDataApplication {

	public RawData getRawData(Long id);
	
	public void creatRawData(RawData rawData);
	
	public void updateRawData(RawData rawData);
	
	public void removeRawData(RawData rawData);
	
	public void removeRawDatas(Set<RawData> rawDatas);
	
	public List<RawData> findAllRawData();
	
}

