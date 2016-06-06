package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface ReworkFacade {

	public InvokeResult getRework(Long id);

	public InvokeResult creatRework(ReworkDTO rework);

	public InvokeResult updateRework(ReworkDTO rework);

	public InvokeResult removeRework(Long id);

	public InvokeResult removeReworks(Long[] ids);

	public List<ReworkDTO> findAllRework();

	public Page<ReworkDTO> pageQueryRework(ReworkDTO rework, int currentPage,
			int pageSize);

	public InvokeResult approve(ReworkDTO rework);
}
