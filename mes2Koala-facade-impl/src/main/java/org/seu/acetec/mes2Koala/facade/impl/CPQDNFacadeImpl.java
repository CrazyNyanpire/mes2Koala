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
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPQDNAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTQDNAssembler;
import org.seu.acetec.mes2Koala.facade.CPQDNFacade;
import org.seu.acetec.mes2Koala.application.CPQDNApplication;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class CPQDNFacadeImpl implements CPQDNFacade {

	@Inject
	private CPQDNApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getCPQDN(Long id) {
		return InvokeResult.success(CPQDNAssembler.toDTO(application.getCPQDN(id)));
	}
	
	public InvokeResult creatCPQDN(CPQDNDTO cPQDNDTO) {
		application.creatCPQDN(CPQDNAssembler.toEntity(cPQDNDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateCPQDN(CPQDNDTO cPQDNDTO) {
		application.updateCPQDN(CPQDNAssembler.toEntity(cPQDNDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeCPQDN(Long id) {
		application.removeCPQDN(application.getCPQDN(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeCPQDNs(Long[] ids) {
		Set<CPQDN> cPQDNs= new HashSet<CPQDN>();
		for (Long id : ids) {
			cPQDNs.add(application.getCPQDN(id));
		}
		application.removeCPQDNs(cPQDNs);
		return InvokeResult.success();
	}
	
	public List<CPQDNDTO> findAllCPQDN() {
		return CPQDNAssembler.toDTOs(application.findAllCPQDN());
	}
	
	public Page<CPQDNDTO> pageQueryCPQDN(CPQDNDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _cPQDN from CPQDN _cPQDN   where 1=1 ");
	   	if (queryVo.getCreateTimestamp() != null) {
	   		jpql.append(" and _cPQDN.createTimestamp between ? and ? ");
	   		conditionVals.add(queryVo.getCreateTimestamp());
	   		conditionVals.add(queryVo.getCreateTimestampEnd());
	   	}	
	   	if (queryVo.getLastModifyTimestamp() != null) {
	   		jpql.append(" and _cPQDN.lastModifyTimestamp between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyTimestamp());
	   		conditionVals.add(queryVo.getLastModifyTimestampEnd());
	   	}	
	   	if (queryVo.getCreateEmployNo() != null && !"".equals(queryVo.getCreateEmployNo())) {
	   		jpql.append(" and _cPQDN.createEmployNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateEmployNo()));
	   	}		
	   	if (queryVo.getLastModifyEmployNo() != null && !"".equals(queryVo.getLastModifyEmployNo())) {
	   		jpql.append(" and _cPQDN.lastModifyEmployNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLastModifyEmployNo()));
	   	}		
	   	if (queryVo.getCustomerNote() != null && !"".equals(queryVo.getCustomerNote())) {
	   		jpql.append(" and _cPQDN.customerNote like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerNote()));
	   	}		
	   	if (queryVo.getCustomerSuggestion() != null && !"".equals(queryVo.getCustomerSuggestion())) {
	   		jpql.append(" and _cPQDN.customerSuggestion like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerSuggestion()));
	   	}		
	   	if (queryVo.getMethod() != null && !"".equals(queryVo.getMethod())) {
	   		jpql.append(" and _cPQDN.method like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getMethod()));
	   	}		
	   	if (queryVo.getNote() != null && !"".equals(queryVo.getNote())) {
	   		jpql.append(" and _cPQDN.note like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNote()));
	   	}		
	   	if (queryVo.getQASuggestion() != null && !"".equals(queryVo.getQASuggestion())) {
	   		jpql.append(" and _cPQDN.qASuggestion like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getQASuggestion()));
	   	}		
	   	if (queryVo.getReason() != null && !"".equals(queryVo.getReason())) {
	   		jpql.append(" and _cPQDN.reason like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getReason()));
	   	}		
	   	if (queryVo.getSuggestion() != null && !"".equals(queryVo.getSuggestion())) {
	   		jpql.append(" and _cPQDN.suggestion like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSuggestion()));
	   	}		
	   	if (queryVo.getAttachment() != null && !"".equals(queryVo.getAttachment())) {
	   		jpql.append(" and _cPQDN.attachment like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getAttachment()));
	   	}		
	   	if (queryVo.getQdnNo() != null && !"".equals(queryVo.getQdnNo())) {
	   		jpql.append(" and _cPQDN.qdnNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getQdnNo()));
	   	}		
	   	if (queryVo.getFlowNow() != null && !"".equals(queryVo.getFlowNow())) {
	   		jpql.append(" and _cPQDN.flowNow like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFlowNow()));
	   	}		
	   	if (queryVo.getCustomerDeal() != null && !"".equals(queryVo.getCustomerDeal())) {
	   		jpql.append(" and _cPQDN.customerDeal like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerDeal()));
	   	}		
	   	if (queryVo.getCustomerDealNote() != null && !"".equals(queryVo.getCustomerDealNote())) {
	   		jpql.append(" and _cPQDN.customerDealNote like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerDealNote()));
	   	}		
	   	if (queryVo.getDate() != null) {
	   		jpql.append(" and _cPQDN.date between ? and ? ");
	   		conditionVals.add(queryVo.getDate());
	   		conditionVals.add(queryVo.getDateEnd());
	   	}	
	   	if (queryVo.getDealDate() != null) {
	   		jpql.append(" and _cPQDN.dealDate between ? and ? ");
	   		conditionVals.add(queryVo.getDealDate());
	   		conditionVals.add(queryVo.getDealDateEnd());
	   	}	
	   	if (queryVo.getDealPerson() != null && !"".equals(queryVo.getDealPerson())) {
	   		jpql.append(" and _cPQDN.dealPerson like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDealPerson()));
	   	}		
	   	if (queryVo.getInternalDeal() != null && !"".equals(queryVo.getInternalDeal())) {
	   		jpql.append(" and _cPQDN.internalDeal like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getInternalDeal()));
	   	}		
	   	if (queryVo.getInternalDealNote() != null && !"".equals(queryVo.getInternalDealNote())) {
	   		jpql.append(" and _cPQDN.internalDealNote like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getInternalDealNote()));
	   	}		
	   	if (queryVo.getQaDeal() != null && !"".equals(queryVo.getQaDeal())) {
	   		jpql.append(" and _cPQDN.qaDeal like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getQaDeal()));
	   	}		
	   	if (queryVo.getTesterSys() != null && !"".equals(queryVo.getTesterSys())) {
	   		jpql.append(" and _cPQDN.testerSys like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTesterSys()));
	   	}		
	   	if (queryVo.getToPerson() != null && !"".equals(queryVo.getToPerson())) {
	   		jpql.append(" and _cPQDN.toPerson like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getToPerson()));
	   	}		
	   	if (queryVo.getType() != null && !"".equals(queryVo.getType())) {
	   		jpql.append(" and _cPQDN.type like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getType()));
	   	}		
	   	if (queryVo.getWorkPerson() != null && !"".equals(queryVo.getWorkPerson())) {
	   		jpql.append(" and _cPQDN.workPerson like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getWorkPerson()));
	   	}		
	   	if (queryVo.getIsFuture() != null) {
		   	jpql.append(" and _cPQDN.isFuture is ?");
		   	conditionVals.add(queryVo.getIsFuture());
	   	}	
	   	if (queryVo.getCustomerName() != null && !"".equals(queryVo.getCustomerName())) {
	   		jpql.append(" and _cPQDN.customerName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerName()));
	   	}		
	   	if (queryVo.getToDepartment() != null && !"".equals(queryVo.getToDepartment())) {
	   		jpql.append(" and _cPQDN.toDepartment like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getToDepartment()));
	   	}		
	   	if (queryVo.getFlow() != null && !"".equals(queryVo.getFlow())) {
	   		jpql.append(" and _cPQDN.flow like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getFlow()));
	   	}		
	   	if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
	   		jpql.append(" and _cPQDN.cpLot.internalLotNumber like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLotNo()));
	   	}
	   	if (queryVo.getCpLotDTO() != null) {
            if (queryVo.getCpLotDTO().getShipmentProductNumber() != null && !"".equals(queryVo.getCpLotDTO() .getShipmentProductNumber())) {
                jpql.append(" and _cPQDN.cpLot.shipmentProductNumber like ?");
                conditionVals.add(MessageFormat.format("%{0}%",queryVo.getCpLotDTO() .getShipmentProductNumber()));
            }
        }
        Page<CPQDN> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<CPQDNDTO>(pages.getStart(), pages.getResultCount(),pageSize, CPQDNAssembler.toDTOs(pages.getData()));
	}

	@Override
	public List<CPQDNDTO> findAllDoingQDNByLotId(Long id) {
        List<CPQDN> list = application.findAllDoingByCPLotId(id);
        return CPQDNAssembler.toDTOs(list);
	}
	
	public CPQDNDTO findCPQDN(Long id) {
		return CPQDNAssembler.toDTO(application.findByCPLotId(id));
	}
	
    @Override
    public InvokeResult disposeCPQDN(CPQDNDTO cpQDNDTO) {
        CPQDN cpQDN = application.getCPQDN(cpQDNDTO.getId());
        BeanUtils.copyProperties(cpQDNDTO, cpQDN);
        cpQDN.setQASuggestion(cpQDNDTO.getQASuggestion());
        cpQDN.setStatus(cpQDN.getStatus() + 1);
        application.updateCPQDN(cpQDN);
        return InvokeResult.success();
    }
}
