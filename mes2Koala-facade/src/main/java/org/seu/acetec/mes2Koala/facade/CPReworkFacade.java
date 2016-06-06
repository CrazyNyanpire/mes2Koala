package org.seu.acetec.mes2Koala.facade;

import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface CPReworkFacade {

	public InvokeResult getCPRework(Long id);

	public InvokeResult creatCPRework(CPReworkDTO cpRework);

	public InvokeResult updateCPRework(CPReworkDTO cpRework);

	public InvokeResult removeCPRework(Long id);

	public InvokeResult removeCPReworks(Long[] ids);

	public List<CPReworkDTO> findAllCPRework();

	public Page<CPReworkDTO> pageQueryCPRework(CPReworkDTO cpRework,
			int currentPage, int pageSize);

	public Page<CPReworkItemDTO> findReworkItemsByCPRework(Long id,
			int currentPage, int pageSize);


	public InvokeResult approveCPRework(CPReworkDTO cpReworkDTO);

	InvokeResult createReworkNo(Long id);
}
