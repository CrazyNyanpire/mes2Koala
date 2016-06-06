package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface RawDataFacade {

	public InvokeResult getRawData(Long id);
	
	public InvokeResult creatRawData(RawDataDTO rawData);
	
	public InvokeResult updateRawData(RawDataDTO rawData);
	
	public InvokeResult removeRawData(Long id);
	
	public InvokeResult removeRawDatas(Long[] ids);
	
	public List<RawDataDTO> findAllRawData();
	
	public Page<RawDataDTO> pageQueryRawData(RawDataDTO rawData, int currentPage, int pageSize);
	
	public InternalProductDTO findInternalProductByRawData(Long id);

	

	
}

