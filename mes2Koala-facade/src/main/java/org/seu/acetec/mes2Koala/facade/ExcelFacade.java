package org.seu.acetec.mes2Koala.facade;

import java.util.List;

import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskDTO;
import org.seu.acetec.mes2Koala.facade.dto.ReworkDTO;

/**
 * @author yuxiangque
 * @version 2016/3/27
 */
public interface ExcelFacade {

    InvokeResult exportBomTemplate(Class<?> clazz, Long[] idArray);

    InvokeResult importBomTemplate(Class<?> clazz, String filename);

    InvokeResult exportLabel(Class<?> clazz, Long[] idArray);

    InvokeResult exportFTLot(Class<?> clazz, Long[] idArray);

    InvokeResult exportCustomer(Class<?> clazz, Long[] idArray);

    InvokeResult exportCPLot(Class<?> clazz, Long[] idArray);

	InvokeResult importReelCodeImpl(Class<?> clazz, String filename , Long ftProcessId);
	
	InvokeResult exportFTInfo(Class<?> clazz, Long[] idArray );
	
	List<ReelDiskDTO> importReelCode( Long ftProcessId, String filename );
	
	public InvokeResult exportReworkLot(ReworkDTO reworkDTO);
}
