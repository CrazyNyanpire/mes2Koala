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
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPLotOptionLogAssembler;
import org.seu.acetec.mes2Koala.facade.CPLotOptionLogFacade;
import org.seu.acetec.mes2Koala.application.CPLotOptionLogApplication;
import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class CPLotOptionLogFacadeImpl implements CPLotOptionLogFacade {

	@Inject
	private CPLotOptionLogApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getCPLotOptionLog(Long id) {
		return InvokeResult.success(CPLotOptionLogAssembler.toDTO(application.getCPLotOptionLog(id)));
	}
	
	public InvokeResult creatCPLotOptionLog(CPLotOptionLogDTO cPLotOptionLogDTO) {
		application.creatCPLotOptionLog(CPLotOptionLogAssembler.toEntity(cPLotOptionLogDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateCPLotOptionLog(CPLotOptionLogDTO cPLotOptionLogDTO) {
		application.updateCPLotOptionLog(CPLotOptionLogAssembler.toEntity(cPLotOptionLogDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeCPLotOptionLog(Long id) {
		application.removeCPLotOptionLog(application.getCPLotOptionLog(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeCPLotOptionLogs(Long[] ids) {
		Set<CPLotOptionLog> cPLotOptionLogs= new HashSet<CPLotOptionLog>();
		for (Long id : ids) {
			cPLotOptionLogs.add(application.getCPLotOptionLog(id));
		}
		application.removeCPLotOptionLogs(cPLotOptionLogs);
		return InvokeResult.success();
	}
	
	public List<CPLotOptionLogDTO> findAllCPLotOptionLog() {
		return CPLotOptionLogAssembler.toDTOs(application.findAllCPLotOptionLog());
	}
	
	public Page<CPLotOptionLogDTO> pageQueryCPLotOptionLog(CPLotOptionLogDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _cPLotOptionLog from CPLotOptionLog _cPLotOptionLog   where 1=1 ");
	   	if (queryVo.getCreateTimestamp() != null) {
	   		jpql.append(" and _cPLotOptionLog.createTimestamp between ? and ? ");
	   		conditionVals.add(queryVo.getCreateTimestamp());
	   		conditionVals.add(queryVo.getCreateTimestampEnd());
	   	}	
	   	if (queryVo.getLastModifyTimestamp() != null) {
	   		jpql.append(" and _cPLotOptionLog.lastModifyTimestamp between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyTimestamp());
	   		conditionVals.add(queryVo.getLastModifyTimestampEnd());
	   	}	
	   	if (queryVo.getCreateEmployNo() != null && !"".equals(queryVo.getCreateEmployNo())) {
	   		jpql.append(" and _cPLotOptionLog.createEmployNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateEmployNo()));
	   	}		
	   	if (queryVo.getLastModifyEmployNo() != null && !"".equals(queryVo.getLastModifyEmployNo())) {
	   		jpql.append(" and _cPLotOptionLog.lastModifyEmployNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLastModifyEmployNo()));
	   	}		
	   	if (queryVo.getOptType() != null && !"".equals(queryVo.getOptType())) {
	   		jpql.append(" and _cPLotOptionLog.optType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getOptType()));
	   	}		
	   	if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
	   		jpql.append(" and _cPLotOptionLog.remark like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
	   	}		
	   	if (queryVo.getFlownow() != null && !"".equals(queryVo.getFlownow())) {
	   		jpql.append(" and _cPLotOptionLog.flownow like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFlownow()));
	   	}
	   	if (queryVo.getLotId() != null) {
	   		jpql.append(" and _cPLotOptionLog.cpLot.id = ? ");
	   		conditionVals.add(queryVo.getLotId());
	   	}	
        Page<CPLotOptionLog> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<CPLotOptionLogDTO>(pages.getStart(), pages.getResultCount(),pageSize, CPLotOptionLogAssembler.toDTOs(pages.getData()));
	}

	@Override
	public CPLotDTO findCpLotByCPLotOptionLog(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
