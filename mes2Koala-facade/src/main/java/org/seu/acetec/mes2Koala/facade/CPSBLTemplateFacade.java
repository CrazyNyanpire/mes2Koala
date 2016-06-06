package org.seu.acetec.mes2Koala.facade;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.core.domain.CPSBLTemplate;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface CPSBLTemplateFacade {

	public InvokeResult getCPSBLTemplate(Long id);
	
	public InvokeResult creatCPSBLTemplate(CPSBLTemplateDTO cPSBLTemplate);
	
	public InvokeResult updateCPSBLTemplate(CPSBLTemplateDTO cPSBLTemplate);
	
	public InvokeResult removeCPSBLTemplate(Long id);
	
	public InvokeResult removeCPSBLTemplates(Long[] ids);
	
	public List<CPSBLTemplateDTO> findAllCPSBLTemplate();
	
	public Page<CPSBLTemplateDTO> pageQueryCPSBLTemplate(CPSBLTemplateDTO cPSBLTemplate, int currentPage, int pageSize);
	
	public InternalProductDTO findInternalProductByCPSBLTemplate(Long id);

	public InvokeResult bindSBLTemplates(Long internalProductId,
			List<CPSBLTemplateDTO> list);

	public List<CPSBLTemplateDTO> getCPSBLTemplatesByProduct(Long id);

	public JSONArray getCPSBLTemplatesByLotId(Long id);
	
}

