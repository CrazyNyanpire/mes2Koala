package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface LabelPlanFacade {

	public InvokeResult getLabelPlan(Long id);
	
	public InvokeResult creatLabelPlan(LabelPlanDTO labelPlan);
	
	public InvokeResult updateLabelPlan(LabelPlanDTO labelPlan);
	
	public InvokeResult removeLabelPlan(Long id);
	
	public InvokeResult removeLabelPlans(Long[] ids);
	
	public List<LabelPlanDTO> findAllLabelPlan();
	
	public Page<LabelPlanDTO> pageQueryLabelPlan(LabelPlanDTO labelPlan, int currentPage, int pageSize);
	
	public LabelPlanDTO findLabelPlanByProduct(Long id);

	
}

