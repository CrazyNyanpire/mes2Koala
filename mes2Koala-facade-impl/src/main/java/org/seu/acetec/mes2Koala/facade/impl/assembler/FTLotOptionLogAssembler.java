package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class FTLotOptionLogAssembler {

	public static FTLotOptionLogDTO toDTO(FTLotOptionLog fTLotOptionLog) {
		if (fTLotOptionLog == null) {
			return null;
		}
		FTLotOptionLogDTO result = new FTLotOptionLogDTO();
		result.setId(fTLotOptionLog.getId());
		result.setVersion(fTLotOptionLog.getVersion());
		result.setCreateTimestamp(fTLotOptionLog.getCreateTimestamp());
		result.setLastModifyTimestamp(fTLotOptionLog.getLastModifyTimestamp());
		result.setCreateEmployNo(fTLotOptionLog.getCreateEmployNo());
		result.setLastModifyEmployNo(fTLotOptionLog.getLastModifyEmployNo());
		result.setLogic(fTLotOptionLog.getLogic());
		result.setOptType(fTLotOptionLog.getOptType());
		result.setRemark(fTLotOptionLog.getRemark());
		result.setFlownow(fTLotOptionLog.getFlownow());
		result.setFtLotDTO(FTLotAssembler.toDTO(fTLotOptionLog.getFtLot()));
		return result;
	}

	public static List<FTLotOptionLogDTO> toDTOs(
			Collection<FTLotOptionLog> fTLotOptionLogs) {
		if (fTLotOptionLogs == null) {
			return null;
		}
		List<FTLotOptionLogDTO> results = new ArrayList<FTLotOptionLogDTO>();
		for (FTLotOptionLog each : fTLotOptionLogs) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static FTLotOptionLog toEntity(FTLotOptionLogDTO fTLotOptionLogDTO) {
		if (fTLotOptionLogDTO == null) {
			return null;
		}
		FTLotOptionLog result = new FTLotOptionLog();
		result.setId(fTLotOptionLogDTO.getId());
		result.setVersion(fTLotOptionLogDTO.getVersion());
		result.setCreateTimestamp(fTLotOptionLogDTO.getCreateTimestamp());
		result.setLastModifyTimestamp(fTLotOptionLogDTO
				.getLastModifyTimestamp());
		result.setCreateEmployNo(fTLotOptionLogDTO.getCreateEmployNo());
		result.setLastModifyEmployNo(fTLotOptionLogDTO.getLastModifyEmployNo());
		result.setLogic(fTLotOptionLogDTO.getLogic());
		result.setOptType(fTLotOptionLogDTO.getOptType());
		result.setRemark(fTLotOptionLogDTO.getRemark());
		result.setFlownow(fTLotOptionLogDTO.getFlownow());
		result.setFtLot(FTLotAssembler.toEntity(fTLotOptionLogDTO.getFtLotDTO()));
		return result;
	}

	public static List<FTLotOptionLog> toEntities(
			Collection<FTLotOptionLogDTO> fTLotOptionLogDTOs) {
		if (fTLotOptionLogDTOs == null) {
			return null;
		}

		List<FTLotOptionLog> results = new ArrayList<FTLotOptionLog>();
		for (FTLotOptionLogDTO each : fTLotOptionLogDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
