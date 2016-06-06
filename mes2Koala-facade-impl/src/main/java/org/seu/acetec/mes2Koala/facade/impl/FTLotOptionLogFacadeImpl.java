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
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTLotOptionLogAssembler;
import org.seu.acetec.mes2Koala.facade.FTLotOptionLogFacade;
import org.seu.acetec.mes2Koala.application.FTLotOptionLogApplication;

import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class FTLotOptionLogFacadeImpl implements FTLotOptionLogFacade {

	@Inject
	private FTLotOptionLogApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getFTLotOptionLog(Long id) {
		return InvokeResult.success(FTLotOptionLogAssembler.toDTO(application.getFTLotOptionLog(id)));
	}
	
	public InvokeResult creatFTLotOptionLog(FTLotOptionLogDTO fTLotOptionLogDTO) {
		application.creatFTLotOptionLog(FTLotOptionLogAssembler.toEntity(fTLotOptionLogDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateFTLotOptionLog(FTLotOptionLogDTO fTLotOptionLogDTO) {
		application.updateFTLotOptionLog(FTLotOptionLogAssembler.toEntity(fTLotOptionLogDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeFTLotOptionLog(Long id) {
		application.removeFTLotOptionLog(application.getFTLotOptionLog(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeFTLotOptionLogs(Long[] ids) {
		Set<FTLotOptionLog> fTLotOptionLogs= new HashSet<FTLotOptionLog>();
		for (Long id : ids) {
			fTLotOptionLogs.add(application.getFTLotOptionLog(id));
		}
		application.removeFTLotOptionLogs(fTLotOptionLogs);
		return InvokeResult.success();
	}
	
	public List<FTLotOptionLogDTO> findAllFTLotOptionLog() {
		return FTLotOptionLogAssembler.toDTOs(application.findAllFTLotOptionLog());
	}
	
	public Page<FTLotOptionLogDTO> pageQueryFTLotOptionLog(FTLotOptionLogDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _fTLotOptionLog from FTLotOptionLog _fTLotOptionLog   left join _fTLotOptionLog.ftLot  where 1=1 ");
	   	if (queryVo.getCreateTimestamp() != null) {
	   		jpql.append(" and _fTLotOptionLog.createTimestamp between ? and ? ");
	   		conditionVals.add(queryVo.getCreateTimestamp());
	   		conditionVals.add(queryVo.getCreateTimestampEnd());
	   	}	
	   	if (queryVo.getLastModifyTimestamp() != null) {
	   		jpql.append(" and _fTLotOptionLog.lastModifyTimestamp between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyTimestamp());
	   		conditionVals.add(queryVo.getLastModifyTimestampEnd());
	   	}	
	   	if (queryVo.getCreateEmployNo() != null && !"".equals(queryVo.getCreateEmployNo())) {
	   		jpql.append(" and _fTLotOptionLog.createEmployNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateEmployNo()));
	   	}		
	   	if (queryVo.getLastModifyEmployNo() != null && !"".equals(queryVo.getLastModifyEmployNo())) {
	   		jpql.append(" and _fTLotOptionLog.lastModifyEmployNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLastModifyEmployNo()));
	   	}		
	   	if (queryVo.getOptType() != null && !"".equals(queryVo.getOptType())) {
	   		jpql.append(" and _fTLotOptionLog.optType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getOptType()));
	   	}		
	   	if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
	   		jpql.append(" and _fTLotOptionLog.remark like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
	   	}		
	   	if (queryVo.getFlownow() != null && !"".equals(queryVo.getFlownow())) {
	   		jpql.append(" and _fTLotOptionLog.flownow like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFlownow()));
	   	}		
	   	if (queryVo.getInternalLotId() != null) {
	   		jpql.append(" and _fTLotOptionLog.ftLot.id = ? ");
	   		conditionVals.add(queryVo.getInternalLotId());
	   	}	
        Page<FTLotOptionLog> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<FTLotOptionLogDTO>(pages.getStart(), pages.getResultCount(),pageSize, FTLotOptionLogAssembler.toDTOs(pages.getData()));
	}
	
	public FTLotDTO findFtLotByFTLotOptionLog(Long id) {
		return null;
	}

	
}
