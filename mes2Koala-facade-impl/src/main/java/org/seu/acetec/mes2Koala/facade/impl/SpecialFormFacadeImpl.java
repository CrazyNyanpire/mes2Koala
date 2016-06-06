package org.seu.acetec.mes2Koala.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.text.MessageFormat;
import javax.inject.Inject;
import javax.inject.Named;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.utils.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.impl.assembler.SpecialFormAssembler;
import org.seu.acetec.mes2Koala.facade.SpecialFormFacade;
import org.seu.acetec.mes2Koala.application.SpecialFormApplication;

import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class SpecialFormFacadeImpl implements SpecialFormFacade {

	@Inject
	private SpecialFormApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getSpecialForm(Long id) {
		return InvokeResult.success(SpecialFormAssembler.toDTO(application.get(id)));
	}
	
	public InvokeResult creatSpecialForm(SpecialFormDTO specialFormDTO) {
		application.create(SpecialFormAssembler.toEntity(specialFormDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateSpecialForm(SpecialFormDTO specialFormDTO) {
		application.update(SpecialFormAssembler.toEntity(specialFormDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeSpecialForm(Long id) {
		application.remove(application.get(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeSpecialForms(Long[] ids) {
		Set<SpecialForm> specialForms= new HashSet<SpecialForm>();
		for (Long id : ids) {
			specialForms.add(application.get(id));
		}
		application.removeAll(specialForms);
		return InvokeResult.success();
	}
	
	public List<SpecialFormDTO> findAllSpecialForm() {
		return SpecialFormAssembler.toDTOs(application.findAll());
	}
	
	public Page<SpecialFormDTO> pageQuerySpecialForm(SpecialFormDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _specialForm from SpecialForm _specialForm   where 1=1 ");
	   	if (queryVo.getCheckBoxForm() != null) {
		   	jpql.append(" and _specialForm.checkBoxForm is ?");
		   	conditionVals.add(queryVo.getCheckBoxForm());
	   	}	
	   	if (queryVo.getCheckSelfForm() != null) {
		   	jpql.append(" and _specialForm.checkSelfForm is ?");
		   	conditionVals.add(queryVo.getCheckSelfForm());
	   	}	
	   	if (queryVo.getFlowForm() != null) {
		   	jpql.append(" and _specialForm.flowForm is ?");
		   	conditionVals.add(queryVo.getFlowForm());
	   	}	
	   	if (queryVo.getIndexForm() != null) {
		   	jpql.append(" and _specialForm.indexForm is ?");
		   	conditionVals.add(queryVo.getIndexForm());
	   	}	
	   	if (queryVo.getLossForm() != null) {
		   	jpql.append(" and _specialForm.lossForm is ?");
		   	conditionVals.add(queryVo.getLossForm());
	   	}	
	   	if (queryVo.getReelcodeForm() != null) {
		   	jpql.append(" and _specialForm.reelcodeForm is ?");
		   	conditionVals.add(queryVo.getReelcodeForm());
	   	}	
	   	if (queryVo.getSizeForm() != null) {
		   	jpql.append(" and _specialForm.sizeForm is ?");
		   	conditionVals.add(queryVo.getSizeForm());
	   	}	
	   	if (queryVo.getSummaryForm() != null) {
		   	jpql.append(" and _specialForm.summaryForm is ?");
		   	conditionVals.add(queryVo.getSummaryForm());
	   	}	
        Page<SpecialForm> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<SpecialFormDTO>(pages.getStart(), pages.getResultCount(),pageSize, SpecialFormAssembler.toDTOs(pages.getData()));
	}

	@Override
	public Long getLastSpecialForm() {
		List<SpecialForm> aList = application.findAll();
		return aList.get(aList.size()-1).getId();
	}
	
	
}
