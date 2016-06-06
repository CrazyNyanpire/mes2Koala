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
import org.seu.acetec.mes2Koala.facade.impl.assembler.RuncardNoteAssembler;
import org.seu.acetec.mes2Koala.facade.RuncardNoteFacade;
import org.seu.acetec.mes2Koala.application.RuncardNoteApplication;

import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class RuncardNoteFacadeImpl implements RuncardNoteFacade {

	@Inject
	private RuncardNoteApplication  application;

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	public InvokeResult getRuncardNote(Long id) {
		return InvokeResult.success(RuncardNoteAssembler.toDTO(application.get(id)));
	}
	
	public InvokeResult creatRuncardNote(RuncardNoteDTO runcardNoteDTO) {
		application.create(RuncardNoteAssembler.toEntity(runcardNoteDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult updateRuncardNote(RuncardNoteDTO runcardNoteDTO) {
		application.update(RuncardNoteAssembler.toEntity(runcardNoteDTO));
		return InvokeResult.success();
	}
	
	public InvokeResult removeRuncardNote(Long id) {
		application.remove(application.get(id));
		return InvokeResult.success();
	}
	
	public InvokeResult removeRuncardNotes(Long[] ids) {
		Set<RuncardNote> runcardNotes= new HashSet<RuncardNote>();
		for (Long id : ids) {
			runcardNotes.add(application.get(id));
		}
		application.removeAll(runcardNotes);
		return InvokeResult.success();
	}
	
	public List<RuncardNoteDTO> findAllRuncardNote() {
		return RuncardNoteAssembler.toDTOs(application.findAll());
	}
	
	public Page<RuncardNoteDTO> pageQueryRuncardNote(RuncardNoteDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _runcardNote from RuncardNote _runcardNote   where 1=1 ");
	   	if (queryVo.getCreateTimestamp() != null) {
	   		jpql.append(" and _runcardNote.createTimestamp between ? and ? ");
	   		conditionVals.add(queryVo.getCreateTimestamp());
	   		conditionVals.add(queryVo.getCreateTimestampEnd());
	   	}	
	   	if (queryVo.getLastModifyTimestamp() != null) {
	   		jpql.append(" and _runcardNote.lastModifyTimestamp between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyTimestamp());
	   		conditionVals.add(queryVo.getLastModifyTimestampEnd());
	   	}	
	   	if (queryVo.getCreateEmployNo() != null && !"".equals(queryVo.getCreateEmployNo())) {
	   		jpql.append(" and _runcardNote.createEmployNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateEmployNo()));
	   	}		
	   	if (queryVo.getLastModifyEmployNo() != null && !"".equals(queryVo.getLastModifyEmployNo())) {
	   		jpql.append(" and _runcardNote.lastModifyEmployNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLastModifyEmployNo()));
	   	}		
	   	if (queryVo.getNodeName() != null && !"".equals(queryVo.getNodeName())) {
	   		jpql.append(" and _runcardNote.nodeName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNodeName()));
	   	}		
        Page<RuncardNote> pages = getQueryChannelService()
		   .createJpqlQuery(jpql.toString())
		   .setParameters(conditionVals)
		   .setPage(currentPage, pageSize)
		   .pagedList();
		   
        return new Page<RuncardNoteDTO>(pages.getStart(), pages.getResultCount(),pageSize, RuncardNoteAssembler.toDTOs(pages.getData()));
	}
	
	
}
