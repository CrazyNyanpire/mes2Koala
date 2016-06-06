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
import org.seu.acetec.mes2Koala.facade.impl.assembler.FT_StatisticsAssembler;
import org.seu.acetec.mes2Koala.facade.FT_StatisticsFacade;
import org.seu.acetec.mes2Koala.application.FT_StatisticsApplication;

import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class FT_StatisticsFacadeImpl implements FT_StatisticsFacade {

	@Inject
	private FT_StatisticsApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getFT_Statistics(Long id) {
		return InvokeResult.success(FT_StatisticsAssembler.toDTO(application.get(id)));
	}
	
	public InvokeResult creatFT_Statistics(FT_StatisticsDTO fT_StatisticsDTO) {
		application.create(FT_StatisticsAssembler.toEntity(fT_StatisticsDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateFT_Statistics(FT_StatisticsDTO fT_StatisticsDTO) {
		application.update(FT_StatisticsAssembler.toEntity(fT_StatisticsDTO));
		return InvokeResult.success();
	}

	public InvokeResult removeFT_Statistics(Long id) {
		application.remove(application.get(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeFT_Statisticss(Long[] ids) {
		Set<FTStatistics> fT_Statisticss= new HashSet<FTStatistics>();
		for (Long id : ids) {
			fT_Statisticss.add(application.get(id));
		}
		application.removeAll(fT_Statisticss);
		return InvokeResult.success();
	}
	
	public List<FT_StatisticsDTO> findAllFT_Statistics() {
		return FT_StatisticsAssembler.toDTOs(application.findAll());
	}
	
	public Page<FT_StatisticsDTO> pageQueryFT_Statistics(FT_StatisticsDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _fT_Statistics from FT_Statistics _fT_Statistics   where 1=1 ");	
	   	if (queryVo.getOther() != null) {
	   		jpql.append(" and _fT_Statistics.other=?");
	   		conditionVals.add(queryVo.getOther());
	   	}	
        Page<FTStatistics> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<FT_StatisticsDTO>(pages.getStart(), pages.getResultCount(),pageSize, FT_StatisticsAssembler.toDTOs(pages.getData()));
	}
	
	
}
