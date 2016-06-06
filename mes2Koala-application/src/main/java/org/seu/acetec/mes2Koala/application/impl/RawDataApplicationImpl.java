package org.seu.acetec.mes2Koala.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.seu.acetec.mes2Koala.application.RawDataApplication;
import org.seu.acetec.mes2Koala.core.domain.RawData;

@Named
@Transactional
public class RawDataApplicationImpl implements RawDataApplication {

	public RawData getRawData(Long id) {
		return RawData.get(RawData.class, id);
	}
	
	public void creatRawData(RawData rawData) {
		rawData.save();
	}
	
	public void updateRawData(RawData rawData) {
		rawData.save();
	}
	
	public void removeRawData(RawData rawData) {
		if(rawData != null){
			rawData.remove();
		}
	}
	
	public void removeRawDatas(Set<RawData> rawDatas) {
		for (RawData rawData : rawDatas) {
			rawData.remove();
		}
	}
	
	public List<RawData> findAllRawData() {
		return RawData.findAll(RawData.class);
	}
	
}
