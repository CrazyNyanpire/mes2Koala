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
import org.seu.acetec.mes2Koala.facade.impl.assembler.TestingTemplateAssembler;
import org.seu.acetec.mes2Koala.facade.TestingTemplateFacade;
import org.seu.acetec.mes2Koala.application.TestingTemplateApplication;

import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class TestingTemplateFacadeImpl implements TestingTemplateFacade {

	@Inject
	private TestingTemplateApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getTestingTemplate(Long id) {
		return InvokeResult.success(TestingTemplateAssembler.toDTO(application.get(id)));
	}
	
	public InvokeResult creatTestingTemplate(TestingTemplateDTO testingTemplateDTO) {
		try{
			application.create(TestingTemplateAssembler.toEntity(testingTemplateDTO));
		}catch(Exception e){
			return InvokeResult.failure("创建失败");
		}
		return InvokeResult.success();
	}
	
	public InvokeResult updateTestingTemplate(TestingTemplateDTO testingTemplateDTO) {
		try{
			application.update(TestingTemplateAssembler.toEntity(testingTemplateDTO));
		}catch(Exception e){
			return InvokeResult.failure("更新失败");
		}	
		return InvokeResult.success();
	}
	
	public InvokeResult removeTestingTemplate(Long id) {
		application.remove(application.get(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeTestingTemplates(Long[] ids) {
		Set<TestingTemplate> testingTemplates= new HashSet<TestingTemplate>();
		for (Long id : ids) {
			testingTemplates.add(application.get(id));
		}
		application.removeAll(testingTemplates);
		return InvokeResult.success();
	}
	
	public List<TestingTemplateDTO> findAllTestingTemplate() {
		return TestingTemplateAssembler.toDTOs(application.findAll());
	}
	
	public Page<TestingTemplateDTO> pageQueryTestingTemplate(TestingTemplateDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _testingTemplate from TestingTemplate _testingTemplate   where 1=1 ");
	   	if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
	   		jpql.append(" and _testingTemplate.name like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getName()));
	   	}		
	   	if (queryVo.getContent() != null && !"".equals(queryVo.getContent())) {
	   		jpql.append(" and _testingTemplate.content like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getContent()));
	   	}		
        Page<TestingTemplate> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<TestingTemplateDTO>(pages.getStart(), pages.getResultCount(),pageSize, TestingTemplateAssembler.toDTOs(pages.getData()));
	}
	
	
}
