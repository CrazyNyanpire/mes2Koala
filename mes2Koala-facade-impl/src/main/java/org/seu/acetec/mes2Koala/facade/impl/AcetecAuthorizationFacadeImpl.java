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
import org.openkoala.organisation.application.BaseApplication;
import org.openkoala.organisation.core.domain.Employee;
import org.openkoala.organisation.facade.dto.EmployeeDTO;
import org.openkoala.organisation.facade.impl.assembler.EmployeeAssembler;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.impl.assembler.AcetecAuthorizationAssembler;
import org.seu.acetec.mes2Koala.facade.AcetecAuthorizationFacade;
import org.seu.acetec.mes2Koala.application.AcetecAuthorizationApplication;

import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class AcetecAuthorizationFacadeImpl implements AcetecAuthorizationFacade {

	@Inject
	private AcetecAuthorizationApplication  application;

    @Inject
    private BaseApplication baseApplication;
		
	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getAcetecAuthorization(Long id) {
		return InvokeResult.success(AcetecAuthorizationAssembler.toDTO(application.get(id)));
	}
	
	public InvokeResult creatAcetecAuthorization(AcetecAuthorizationDTO acetecAuthorizationDTO) {
		Employee employee = baseApplication.getEntity(Employee.class,acetecAuthorizationDTO.getEmployeeId());
		acetecAuthorizationDTO.setEmployeeDTO(EmployeeAssembler.toDTO(employee));
		application.create(AcetecAuthorizationAssembler.toEntity(acetecAuthorizationDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateAcetecAuthorization(AcetecAuthorizationDTO acetecAuthorizationDTO) {
		Employee employee = baseApplication.getEntity(Employee.class,acetecAuthorizationDTO.getEmployeeId());
		acetecAuthorizationDTO.setEmployeeDTO(EmployeeAssembler.toDTO(employee));
		int version = application.get(acetecAuthorizationDTO.getId()).getVersion();
		acetecAuthorizationDTO.setVersion(version);
		application.update(AcetecAuthorizationAssembler.toEntity(acetecAuthorizationDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeAcetecAuthorization(Long id) {
		application.remove(application.get(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeAcetecAuthorizations(Long[] ids) {
		Set<AcetecAuthorization> acetecAuthorizations= new HashSet<AcetecAuthorization>();
		for (Long id : ids) {
			acetecAuthorizations.add(application.get(id));
		}
		application.removeAll(acetecAuthorizations);
		return InvokeResult.success();
	}
	
	public List<AcetecAuthorizationDTO> findAllAcetecAuthorization() {
		return AcetecAuthorizationAssembler.toDTOs(application.findAll());
	}
	
	//LHB:找到最新添加的authorization的ID
	public Long getLastAcetecAuthorization(){
		List<AcetecAuthorization> aList = application.findAll();
		return aList.get(aList.size()-1).getId();
	}
	
	public Page<AcetecAuthorizationDTO> pageQueryAcetecAuthorization(AcetecAuthorizationDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _acetecAuthorization from AcetecAuthorization _acetecAuthorization   where 1=1 ");
	   	if (queryVo.getOpinion() != null && !"".equals(queryVo.getOpinion())) {
	   		jpql.append(" and _acetecAuthorization.opinion like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getOpinion()));
	   	}		
	   	if (queryVo.getNote() != null && !"".equals(queryVo.getNote())) {
	   		jpql.append(" and _acetecAuthorization.note like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNote()));
	   	}		
        Page<AcetecAuthorization> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<AcetecAuthorizationDTO>(pages.getStart(), pages.getResultCount(),pageSize, AcetecAuthorizationAssembler.toDTOs(pages.getData()));
	}

	@Override
	public EmployeeDTO findEmployeeByAcetecAuthorization(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
