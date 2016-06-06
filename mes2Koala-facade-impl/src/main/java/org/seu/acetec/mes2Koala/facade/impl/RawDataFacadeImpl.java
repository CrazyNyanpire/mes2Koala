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
import org.seu.acetec.mes2Koala.facade.impl.assembler.RawDataAssembler;
import org.seu.acetec.mes2Koala.facade.RawDataFacade;
import org.seu.acetec.mes2Koala.application.RawDataApplication;
import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class RawDataFacadeImpl implements RawDataFacade {

	@Inject
	private RawDataApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getRawData(Long id) {
		String jpql = "select o from RawData o right join o.internalProduct e where e.id=?";
	    List<RawData> rawDatas = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{id}).list();
		return InvokeResult.success(RawDataAssembler.toDTO(rawDatas.get(0)));
	}
	
	public InvokeResult creatRawData(RawDataDTO rawDataDTO) {
		application.creatRawData(RawDataAssembler.toEntity(rawDataDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateRawData(RawDataDTO rawDataDTO) {
		application.updateRawData(RawDataAssembler.toEntity(rawDataDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeRawData(Long id) {
		String jpql = "select o from RawData o right join o.internalProduct e where e.id=?";
	    List<RawData> rawDatas = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{id}).list();
        for (RawData rawData : rawDatas) {
        	application.removeRawData(rawData);
        }
		return InvokeResult.success();
	}
	
	public InvokeResult removeRawDatas(Long[] ids) {
		Set<RawData> rawDatas= new HashSet<RawData>();
		for (Long id : ids) {
			rawDatas.add(application.getRawData(id));
		}
		application.removeRawDatas(rawDatas);
		return InvokeResult.success();
	}
	
	public List<RawDataDTO> findAllRawData() {
		return RawDataAssembler.toDTOs(application.findAllRawData());
	}
	
	public Page<RawDataDTO> pageQueryRawData(RawDataDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _rawData from RawData _rawData   where 1=1 ");
	   	if (queryVo.getCreateTimestamp() != null) {
	   		jpql.append(" and _rawData.createTimestamp between ? and ? ");
	   		conditionVals.add(queryVo.getCreateTimestamp());
	   		conditionVals.add(queryVo.getCreateTimestampEnd());
	   	}	
	   	if (queryVo.getLastModifyTimestamp() != null) {
	   		jpql.append(" and _rawData.lastModifyTimestamp between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyTimestamp());
	   		conditionVals.add(queryVo.getLastModifyTimestampEnd());
	   	}	
	   	if (queryVo.getCreateEmployNo() != null && !"".equals(queryVo.getCreateEmployNo())) {
	   		jpql.append(" and _rawData.createEmployNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateEmployNo()));
	   	}		
	   	if (queryVo.getLastModifyEmployNo() != null && !"".equals(queryVo.getLastModifyEmployNo())) {
	   		jpql.append(" and _rawData.lastModifyEmployNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLastModifyEmployNo()));
	   	}		
	   	if (queryVo.getProductID() != null && !"".equals(queryVo.getProductID())) {
	   		jpql.append(" and _rawData.productID like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getProductID()));
	   	}		
	   	if (queryVo.getNotchSide() != null && !"".equals(queryVo.getNotchSide())) {
	   		jpql.append(" and _rawData.notchSide like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNotchSide()));
	   	}		
	   	if (queryVo.getBindefinitionFile() != null && !"".equals(queryVo.getBindefinitionFile())) {
	   		jpql.append(" and _rawData.bindefinitionFile like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getBindefinitionFile()));
	   	}		
	   	if (queryVo.getGridXmax() != null && !"".equals(queryVo.getGridXmax())) {
	   		jpql.append(" and _rawData.gridXmax like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getGridXmax()));
	   	}		
	   	if (queryVo.getFabSite() != null && !"".equals(queryVo.getFabSite())) {
	   		jpql.append(" and _rawData.fabSite like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFabSite()));
	   	}		
	   	if (queryVo.getXDieSize() != null && !"".equals(queryVo.getXDieSize())) {
	   		jpql.append(" and _rawData.xDieSize like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getXDieSize()));
	   	}		
	   	if (queryVo.getYDieSize() != null && !"".equals(queryVo.getYDieSize())) {
	   		jpql.append(" and _rawData.yDieSize like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getYDieSize()));
	   	}		
	   	if (queryVo.getCustomerCodeID() != null && !"".equals(queryVo.getCustomerCodeID())) {
	   		jpql.append(" and _rawData.customerCodeID like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerCodeID()));
	   	}		
        Page<RawData> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<RawDataDTO>(pages.getStart(), pages.getResultCount(),pageSize, RawDataAssembler.toDTOs(pages.getData()));
	}

	@Override
	public InternalProductDTO findInternalProductByRawData(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
