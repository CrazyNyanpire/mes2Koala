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
import org.seu.acetec.mes2Koala.facade.impl.assembler.DataCompensationAssembler;
import org.seu.acetec.mes2Koala.facade.DataCompensationFacade;
import org.seu.acetec.mes2Koala.application.DataCompensationApplication;

import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class DataCompensationFacadeImpl implements DataCompensationFacade {

	@Inject
	private DataCompensationApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getDataCompensation(Long id) {
		return InvokeResult.success(DataCompensationAssembler.toDTO(application.getDataCompensation(id)));
	}
	
	public InvokeResult creatDataCompensation(DataCompensationDTO dataCompensationDTO) {
		application.creatDataCompensation(DataCompensationAssembler.toEntity(dataCompensationDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateDataCompensation(DataCompensationDTO dataCompensationDTO) {
		application.updateDataCompensation(DataCompensationAssembler.toEntity(dataCompensationDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeDataCompensation(Long id) {
		application.removeDataCompensation(application.getDataCompensation(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeDataCompensations(Long[] ids) {
		Set<DataCompensation> dataCompensations= new HashSet<DataCompensation>();
		for (Long id : ids) {
			dataCompensations.add(application.getDataCompensation(id));
		}
		application.removeDataCompensations(dataCompensations);
		return InvokeResult.success();
	}
	
	public List<DataCompensationDTO> findAllDataCompensation() {
		return DataCompensationAssembler.toDTOs(application.findAllDataCompensation());
	}
	
	public Page<DataCompensationDTO> pageQueryDataCompensation(DataCompensationDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _dataCompensation from DataCompensation _dataCompensation   where 1=1 ");
	   	if (queryVo.getCreateTimestamp() != null) {
	   		jpql.append(" and _dataCompensation.createTimestamp between ? and ? ");
	   		conditionVals.add(queryVo.getCreateTimestamp());
	   		conditionVals.add(queryVo.getCreateTimestampEnd());
	   	}	
	   	if (queryVo.getLastModifyTimestamp() != null) {
	   		jpql.append(" and _dataCompensation.lastModifyTimestamp between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyTimestamp());
	   		conditionVals.add(queryVo.getLastModifyTimestampEnd());
	   	}	
	   	if (queryVo.getCreateEmployNo() != null && !"".equals(queryVo.getCreateEmployNo())) {
	   		jpql.append(" and _dataCompensation.createEmployNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateEmployNo()));
	   	}		
	   	if (queryVo.getLastModifyEmployNo() != null && !"".equals(queryVo.getLastModifyEmployNo())) {
	   		jpql.append(" and _dataCompensation.lastModifyEmployNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLastModifyEmployNo()));
	   	}		
	   	if (queryVo.getLotID() != null && !"".equals(queryVo.getLotID())) {
	   		jpql.append(" and _dataCompensation.lotID like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLotID()));
	   	}		
	   	if (queryVo.getWaferID() != null && !"".equals(queryVo.getWaferID())) {
	   		jpql.append(" and _dataCompensation.waferID like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getWaferID()));
	   	}		
	   	if (queryVo.getSmicProdID() != null && !"".equals(queryVo.getSmicProdID())) {
	   		jpql.append(" and _dataCompensation.smicProdID like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSmicProdID()));
	   	}		
	   	if (queryVo.getTestProgram() != null && !"".equals(queryVo.getTestProgram())) {
	   		jpql.append(" and _dataCompensation.testProgram like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTestProgram()));
	   	}		
	   	if (queryVo.getTesterID() != null && !"".equals(queryVo.getTesterID())) {
	   		jpql.append(" and _dataCompensation.testerID like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTesterID()));
	   	}		
	   	if (queryVo.getOperatorID() != null && !"".equals(queryVo.getOperatorID())) {
	   		jpql.append(" and _dataCompensation.operatorID like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getOperatorID()));
	   	}		
	   	if (queryVo.getStartTime() != null && !"".equals(queryVo.getStartTime())) {
	   		jpql.append(" and _dataCompensation.startTime like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getStartTime()));
	   	}		
	   	if (queryVo.getEndTime() != null && !"".equals(queryVo.getEndTime())) {
	   		jpql.append(" and _dataCompensation.endTime like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getEndTime()));
	   	}		
	   	if (queryVo.getNotchSide() != null && !"".equals(queryVo.getNotchSide())) {
	   		jpql.append(" and _dataCompensation.notchSide like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNotchSide()));
	   	}		
	   	if (queryVo.getSortID() != null && !"".equals(queryVo.getSortID())) {
	   		jpql.append(" and _dataCompensation.sortID like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSortID()));
	   	}		
	   	if (queryVo.getBinDefinitionFile() != null && !"".equals(queryVo.getBinDefinitionFile())) {
	   		jpql.append(" and _dataCompensation.binDefinitionFile like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getBinDefinitionFile()));
	   	}		
	   	if (queryVo.getGridXmax() != null && !"".equals(queryVo.getGridXmax())) {
	   		jpql.append(" and _dataCompensation.gridXmax like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getGridXmax()));
	   	}		
	   	if (queryVo.getGridYmax() != null && !"".equals(queryVo.getGridYmax())) {
	   		jpql.append(" and _dataCompensation.gridYmax like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getGridYmax()));
	   	}		
	   	if (queryVo.getFabSite() != null && !"".equals(queryVo.getFabSite())) {
	   		jpql.append(" and _dataCompensation.fabSite like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFabSite()));
	   	}		
	   	if (queryVo.getTestSite() != null && !"".equals(queryVo.getTestSite())) {
	   		jpql.append(" and _dataCompensation.testSite like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTestSite()));
	   	}		
	   	if (queryVo.getProbeCardID() != null && !"".equals(queryVo.getProbeCardID())) {
	   		jpql.append(" and _dataCompensation.probeCardID like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getProbeCardID()));
	   	}		
	   	if (queryVo.getLoadBoardID() != null && !"".equals(queryVo.getLoadBoardID())) {
	   		jpql.append(" and _dataCompensation.loadBoardID like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLoadBoardID()));
	   	}		
	   	if (queryVo.getRomCode() != null && !"".equals(queryVo.getRomCode())) {
	   		jpql.append(" and _dataCompensation.romCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRomCode()));
	   	}		
	   	if (queryVo.getXDieSize() != null && !"".equals(queryVo.getXDieSize())) {
	   		jpql.append(" and _dataCompensation.xDieSize like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getXDieSize()));
	   	}		
	   	if (queryVo.getYDieSize() != null && !"".equals(queryVo.getYDieSize())) {
	   		jpql.append(" and _dataCompensation.yDieSize like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getYDieSize()));
	   	}		
	   	if (queryVo.getXStreet() != null && !"".equals(queryVo.getXStreet())) {
	   		jpql.append(" and _dataCompensation.xStreet like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getXStreet()));
	   	}		
	   	if (queryVo.getYStreet() != null && !"".equals(queryVo.getYStreet())) {
	   		jpql.append(" and _dataCompensation.yStreet like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getYStreet()));
	   	}		
	   	if (queryVo.getCustomerCode() != null && !"".equals(queryVo.getCustomerCode())) {
	   		jpql.append(" and _dataCompensation.customerCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerCode()));
	   	}		
	   	if (queryVo.getCustomerPartID() != null && !"".equals(queryVo.getCustomerPartID())) {
	   		jpql.append(" and _dataCompensation.customerPartID like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerPartID()));
	   	}		
	   	if (queryVo.getRawData() != null && !"".equals(queryVo.getRawData())) {
	   		jpql.append(" and _dataCompensation.rawData like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRawData()));
	   	}		
        Page<DataCompensation> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<DataCompensationDTO>(pages.getStart(), pages.getResultCount(),pageSize, DataCompensationAssembler.toDTOs(pages.getData()));
	}
	
	
}
