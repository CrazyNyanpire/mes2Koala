package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface DataCompensationFacade {

	public InvokeResult getDataCompensation(Long id);
	
	public InvokeResult creatDataCompensation(DataCompensationDTO dataCompensation);
	
	public InvokeResult updateDataCompensation(DataCompensationDTO dataCompensation);
	
	public InvokeResult removeDataCompensation(Long id);
	
	public InvokeResult removeDataCompensations(Long[] ids);
	
	public List<DataCompensationDTO> findAllDataCompensation();
	
	public Page<DataCompensationDTO> pageQueryDataCompensation(DataCompensationDTO dataCompensation, int currentPage, int pageSize);
	

}

