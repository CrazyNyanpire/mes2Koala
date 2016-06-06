package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class CPReworkItemAssembler {

	public static CPReworkItemDTO toDTO(CPReworkItem cpReworkItem) {
		if (cpReworkItem == null) {
			return null;
		}
		CPReworkItemDTO result = new CPReworkItemDTO();
		result.setId(cpReworkItem.getId());
		result.setVersion(cpReworkItem.getVersion());
		result.setCreateTimestamp(cpReworkItem.getCreateTimestamp());
		result.setLastModifyTimestamp(cpReworkItem.getLastModifyTimestamp());
		result.setCreateEmployNo(cpReworkItem.getCreateEmployNo());
		result.setLastModifyEmployNo(cpReworkItem.getLastModifyEmployNo());
		result.setLogic(cpReworkItem.getLogic());
		result.setAccomplishDate(cpReworkItem.getAccomplishDate());
		result.setAttentionItem(cpReworkItem.getAttentionItem());
		result.setOperator(cpReworkItem.getOperator());
		result.setReworkFlow(cpReworkItem.getReworkFlow());
		// result.setCpReWork (cpReworkItem.getCpReWork());
		result.setGist(cpReworkItem.getGist());
		return result;
	}

	public static List<CPReworkItemDTO> toDTOs(
			Collection<CPReworkItem> cpReworkItems) {
		if (cpReworkItems == null) {
			return null;
		}
		List<CPReworkItemDTO> results = new ArrayList<CPReworkItemDTO>();
		for (CPReworkItem each : cpReworkItems) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static CPReworkItem toEntity(CPReworkItemDTO cpReworkItemDTO) {
		if (cpReworkItemDTO == null) {
			return null;
		}
		CPReworkItem result = new CPReworkItem();
		result.setId(cpReworkItemDTO.getId());
		result.setVersion(cpReworkItemDTO.getVersion());
		result.setCreateTimestamp(cpReworkItemDTO.getCreateTimestamp());
		result.setLastModifyTimestamp(cpReworkItemDTO.getLastModifyTimestamp());
		result.setCreateEmployNo(cpReworkItemDTO.getCreateEmployNo());
		result.setLastModifyEmployNo(cpReworkItemDTO.getLastModifyEmployNo());
		result.setLogic(cpReworkItemDTO.getLogic());
		result.setAccomplishDate(cpReworkItemDTO.getAccomplishDate());
		result.setAttentionItem(cpReworkItemDTO.getAttentionItem());
		result.setOperator(cpReworkItemDTO.getOperator());
		result.setReworkFlow(cpReworkItemDTO.getReworkFlow());
		// result.setCpReWork (cpReworkItemDTO.getCpReWork());
		result.setGist(cpReworkItemDTO.getGist());
		return result;
	}

	public static List<CPReworkItem> toEntities(
			Collection<CPReworkItemDTO> cpReworkItemDTOs) {
		if (cpReworkItemDTOs == null) {
			return null;
		}

		List<CPReworkItem> results = new ArrayList<CPReworkItem>();
		for (CPReworkItemDTO each : cpReworkItemDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
