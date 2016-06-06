package org.seu.acetec.mes2Koala.application;


import java.util.List;
import java.util.Set;
import  org.seu.acetec.mes2Koala.core.domain.DataCompensation;

public interface DataCompensationApplication {

	public DataCompensation getDataCompensation(Long id);
	
	public void creatDataCompensation(DataCompensation dataCompensation);
	
	public void updateDataCompensation(DataCompensation dataCompensation);
	
	public void removeDataCompensation(DataCompensation dataCompensation);
	
	public void removeDataCompensations(Set<DataCompensation> dataCompensations);
	
	public List<DataCompensation> findAllDataCompensation();
	
}

