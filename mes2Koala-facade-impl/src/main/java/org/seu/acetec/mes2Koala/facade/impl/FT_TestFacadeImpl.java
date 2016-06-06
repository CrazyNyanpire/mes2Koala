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
import org.seu.acetec.mes2Koala.facade.impl.assembler.FT_TestAssembler;
import org.seu.acetec.mes2Koala.facade.FTTestFacade;
import org.seu.acetec.mes2Koala.application.FTCompostedTestApplication;

import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class FT_TestFacadeImpl implements FTTestFacade {

	@Inject
	private FTCompostedTestApplication application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getFT_Test(Long id) {
		return InvokeResult.success(FT_TestAssembler.toDTO(application.get(id)));
	}
	
	public InvokeResult creatFT_Test(FT_TestDTO fT_TestDTO) {
		application.create(FT_TestAssembler.toEntity(fT_TestDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateFT_Test(FT_TestDTO fT_TestDTO) {
		application.update(FT_TestAssembler.toEntity(fT_TestDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeFT_Test(Long id) {
		application.remove(application.get(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeFT_Tests(Long[] ids) {
		Set<FTComposedTestNode> fT_Tests= new HashSet<FTComposedTestNode>();
		for (Long id : ids) {
			fT_Tests.add(application.get(id));
		}
		application.removeAll(fT_Tests);
		return InvokeResult.success();
	}
	
	public List<FT_TestDTO> findAllFT_Test() {
		return FT_TestAssembler.toDTOs(application.findAll());
	}
	
	public Page<FT_TestDTO> pageQueryFT_Test(FT_TestDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _fT_Test from FTComposedTestNode _fT_Test   where 1=1 ");

	   	if (queryVo.getFtNote() != null && !"".equals(queryVo.getFtNote())) {
	   		jpql.append(" and _fT_Test.ftNote like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFtNote()));
	   	}		
	   	if (queryVo.getRtNote() != null && !"".equals(queryVo.getRtNote())) {
	   		jpql.append(" and _fT_Test.rtNote like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRtNote()));
	   	}		
	   	if (queryVo.getEqcNote() != null && !"".equals(queryVo.getEqcNote())) {
	   		jpql.append(" and _fT_Test.eqcNote like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getEqcNote()));
	   	}		
	   	if (queryVo.getLatNote() != null && !"".equals(queryVo.getLatNote())) {
	   		jpql.append(" and _fT_Test.latNote like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLatNote()));
	   	}		
        Page<FTComposedTestNode> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<FT_TestDTO>(pages.getStart(), pages.getResultCount(),pageSize, FT_TestAssembler.toDTOs(pages.getData()));
	}
	
	
}
