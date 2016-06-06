package org.seu.acetec.mes2Koala.application.impl;

import java.util.List;
import java.util.Set;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import org.seu.acetec.mes2Koala.application.DataCompensationApplication;
import org.seu.acetec.mes2Koala.core.domain.DataCompensation;

@Named
@Transactional
public class DataCompensationApplicationImpl implements DataCompensationApplication {

	public DataCompensation getDataCompensation(Long id) {
		return DataCompensation.get(DataCompensation.class, id);
	}
	
	public void creatDataCompensation(DataCompensation dataCompensation) {
		dataCompensation.save();
	}
	
	public void updateDataCompensation(DataCompensation dataCompensation) {
		dataCompensation .save();
	}
	
	public void removeDataCompensation(DataCompensation dataCompensation) {
		if(dataCompensation != null){
			dataCompensation.remove();
		}
	}
	
	public void removeDataCompensations(Set<DataCompensation> dataCompensations) {
		for (DataCompensation dataCompensation : dataCompensations) {
			dataCompensation.remove();
		}
	}
	
	public List<DataCompensation> findAllDataCompensation() {
		return DataCompensation.findAll(DataCompensation.class);
	}
	
}
