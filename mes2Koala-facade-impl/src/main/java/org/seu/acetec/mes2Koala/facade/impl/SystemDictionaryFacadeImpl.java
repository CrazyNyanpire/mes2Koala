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
import org.seu.acetec.mes2Koala.facade.impl.assembler.SystemDictionaryAssembler;
import org.seu.acetec.mes2Koala.facade.SystemDictionaryFacade;
import org.seu.acetec.mes2Koala.application.SystemDictionaryApplication;

import org.seu.acetec.mes2Koala.core.domain.*;

@Named
public class SystemDictionaryFacadeImpl implements SystemDictionaryFacade {

	@Inject
	private SystemDictionaryApplication application;

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	public InvokeResult getSystemDictionary(Long id) {
		return InvokeResult.success(SystemDictionaryAssembler.toDTO(application.get(id)));
	}

	public InvokeResult creatSystemDictionary(SystemDictionaryDTO systemDictionaryDTO) {
		application.create(SystemDictionaryAssembler.toEntity(systemDictionaryDTO));
		return InvokeResult.success();
	}

	public InvokeResult updateSystemDictionary(SystemDictionaryDTO systemDictionaryDTO) {
		application.update(SystemDictionaryAssembler.toEntity(systemDictionaryDTO));
		return InvokeResult.success();
	}

	public InvokeResult removeSystemDictionary(Long id) {
		application.remove(application.get(id));
		return InvokeResult.success();
	}

	public InvokeResult removeSystemDictionarys(Long[] ids) {
		Set<SystemDictionary> systemDictionarys = new HashSet<SystemDictionary>();
		for (Long id : ids) {
			systemDictionarys.add(application.get(id));
		}
		application.removeAll(systemDictionarys);
		return InvokeResult.success();
	}

	public List<SystemDictionaryDTO> findAllSystemDictionary() {
		return SystemDictionaryAssembler.toDTOs(application.findAll());
	}

	public List<SystemDictionaryDTO> getByType(String type) {
		return SystemDictionaryAssembler.toDTOs(application.getByType(type));
	}

	public Page<SystemDictionaryDTO> pageQuerySystemDictionary(SystemDictionaryDTO queryVo, int currentPage,
			int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _systemDictionary from SystemDictionary _systemDictionary   where 1=1 ");
		if (queryVo.getCreateTimestamp() != null) {
			jpql.append(" and _systemDictionary.createTimestamp between ? and ? ");
			conditionVals.add(queryVo.getCreateTimestamp());
			conditionVals.add(queryVo.getCreateTimestampEnd());
		}
		if (queryVo.getLastModifyTimestamp() != null) {
			jpql.append(" and _systemDictionary.lastModifyTimestamp between ? and ? ");
			conditionVals.add(queryVo.getLastModifyTimestamp());
			conditionVals.add(queryVo.getLastModifyTimestampEnd());
		}
		if (queryVo.getCreateEmployNo() != null && !"".equals(queryVo.getCreateEmployNo())) {
			jpql.append(" and _systemDictionary.createEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateEmployNo()));
		}
		if (queryVo.getLastModifyEmployNo() != null && !"".equals(queryVo.getLastModifyEmployNo())) {
			jpql.append(" and _systemDictionary.lastModifyEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLastModifyEmployNo()));
		}
		if (queryVo.getValue() != null && !"".equals(queryVo.getValue())) {
			jpql.append(" and _systemDictionary.value like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getValue()));
		}
		if (queryVo.getLabel() != null && !"".equals(queryVo.getLabel())) {
			jpql.append(" and _systemDictionary.label like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLabel()));
		}
		if (queryVo.getType() != null && !"".equals(queryVo.getType())) {
			jpql.append(" and _systemDictionary.type like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getType()));
		}
		if (queryVo.getDescription() != null && !"".equals(queryVo.getDescription())) {
			jpql.append(" and _systemDictionary.description like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDescription()));
		}
		Page<SystemDictionary> pages = getQueryChannelService().createJpqlQuery(jpql.toString())
				.setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();

		return new Page<SystemDictionaryDTO>(pages.getStart(), pages.getResultCount(), pageSize,
				SystemDictionaryAssembler.toDTOs(pages.getData()));
	}

	/**
	 * 更新（增加）客户编码流水号
	 * 不提供减小操作，否则删除之前添加的客户会导致编码重复
	 */
	public InvokeResult updateCustomerSerialNumber(String dtoSerialNumberString) {
		SystemDictionary customerSerialNumber = application.getByType("CustomerSerialNumber").get(0);
		int serialNumber = Integer.parseInt(customerSerialNumber.getValue());
		int dtoSerialNumber = Integer.parseInt(dtoSerialNumberString.substring(dtoSerialNumberString.length()-2));
		if ( dtoSerialNumber < serialNumber)
			return InvokeResult.failure("Cannot update customer serial number!");

		serialNumber = serialNumber > 98 ? 0 : serialNumber;
		customerSerialNumber.setValue(String.format("%02d", serialNumber + 1));
		application.update(customerSerialNumber);
		return InvokeResult.success();
	}

	@Override
	public List<String> getValueByType(String type) {
		if ( type == null ) return null;

		List<SystemDictionaryDTO> systemDictionaryDTOs = getByType(type);
		List<String> values = new ArrayList<>();
		for ( SystemDictionaryDTO systemDictionaryDTO : systemDictionaryDTOs ) {
			values.add(systemDictionaryDTO.getValue());
		}
		return values;
	}

}
