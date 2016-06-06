package org.seu.acetec.mes2Koala.facade;

import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface FTReworkFacade {

	public InvokeResult getFTRework(Long id);

	public InvokeResult creatFTRework(FTReworkDTO fTRework);

	public InvokeResult updateFTRework(FTReworkDTO fTRework);

	public InvokeResult removeFTRework(Long id);

	public InvokeResult removeFTReworks(Long[] ids);

	public List<FTReworkDTO> findAllFTRework();

	public Page<FTReworkDTO> pageQueryFTRework(FTReworkDTO fTRework,
			int currentPage, int pageSize);

	public Page<FTReworkItemDTO> findReworkItemsByFTRework(Long id,
			int currentPage, int pageSize);


	public InvokeResult approveFTRework(FTReworkDTO fTReworkDTO);

	InvokeResult createReworkNo(Long id);
}
