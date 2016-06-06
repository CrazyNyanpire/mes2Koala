package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface SpecialFormFacade {

	public InvokeResult getSpecialForm(Long id);
	
	public InvokeResult creatSpecialForm(SpecialFormDTO specialForm);
	
	public InvokeResult updateSpecialForm(SpecialFormDTO specialForm);
	
	public InvokeResult removeSpecialForm(Long id);
	
	public InvokeResult removeSpecialForms(Long[] ids);
	
	public List<SpecialFormDTO> findAllSpecialForm();
	
	public Page<SpecialFormDTO> pageQuerySpecialForm(SpecialFormDTO specialForm, int currentPage, int pageSize);

	public Long getLastSpecialForm();
	

}

