package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class FTReworkItemAssembler {

	public static FTReworkItemDTO toDTO(FTReworkItem fTReworkItem) {
		if (fTReworkItem == null) {
			return null;
		}
		FTReworkItemDTO result = new FTReworkItemDTO();
		result.setId(fTReworkItem.getId());
		result.setVersion(fTReworkItem.getVersion());
		result.setCreateTimestamp(fTReworkItem.getCreateTimestamp());
		result.setLastModifyTimestamp(fTReworkItem.getLastModifyTimestamp());
		result.setCreateEmployNo(fTReworkItem.getCreateEmployNo());
		result.setLastModifyEmployNo(fTReworkItem.getLastModifyEmployNo());
		result.setLogic(fTReworkItem.getLogic());
		result.setAccomplishDate(fTReworkItem.getAccomplishDate());
		result.setAttentionItem(fTReworkItem.getAttentionItem());
		result.setOperator(fTReworkItem.getOperator());
		result.setReworkFlow(fTReworkItem.getReworkFlow());
		// result.setFtReWork (fTReworkItem.getFtReWork());
		return result;
	}

	public static List<FTReworkItemDTO> toDTOs(
			Collection<FTReworkItem> fTReworkItems) {
		if (fTReworkItems == null) {
			return null;
		}
		List<FTReworkItemDTO> results = new ArrayList<FTReworkItemDTO>();
		for (FTReworkItem each : fTReworkItems) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static FTReworkItem toEntity(FTReworkItemDTO fTReworkItemDTO) {
		if (fTReworkItemDTO == null) {
			return null;
		}
		FTReworkItem result = new FTReworkItem();
		result.setId(fTReworkItemDTO.getId());
		result.setVersion(fTReworkItemDTO.getVersion());
		result.setCreateTimestamp(fTReworkItemDTO.getCreateTimestamp());
		result.setLastModifyTimestamp(fTReworkItemDTO.getLastModifyTimestamp());
		result.setCreateEmployNo(fTReworkItemDTO.getCreateEmployNo());
		result.setLastModifyEmployNo(fTReworkItemDTO.getLastModifyEmployNo());
		result.setLogic(fTReworkItemDTO.getLogic());
		result.setAccomplishDate(fTReworkItemDTO.getAccomplishDate());
		result.setAttentionItem(fTReworkItemDTO.getAttentionItem());
		result.setOperator(fTReworkItemDTO.getOperator());
		result.setReworkFlow(fTReworkItemDTO.getReworkFlow());
		// result.setFtReWork (fTReworkItemDTO.getFtReWork());
		return result;
	}

	public static List<FTReworkItem> toEntities(
			Collection<FTReworkItemDTO> fTReworkItemDTOs) {
		if (fTReworkItemDTOs == null) {
			return null;
		}

		List<FTReworkItem> results = new ArrayList<FTReworkItem>();
		for (FTReworkItemDTO each : fTReworkItemDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
