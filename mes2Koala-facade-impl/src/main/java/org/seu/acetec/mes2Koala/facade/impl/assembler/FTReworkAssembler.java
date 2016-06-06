package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class FTReworkAssembler {

	public static FTReworkDTO toDTO(FTRework fTRework) {
		if (fTRework == null) {
			return null;
		}
		FTReworkDTO result = new FTReworkDTO();
		result.setId(fTRework.getId());
		result.setVersion(fTRework.getVersion());
		result.setCreateTimestamp(fTRework.getCreateTimestamp());
		result.setLastModifyTimestamp(fTRework.getLastModifyTimestamp());
		result.setCreateEmployNo(fTRework.getCreateEmployNo());
		result.setLastModifyEmployNo(fTRework.getLastModifyEmployNo());
		result.setLogic(fTRework.getLogic());
		result.setReworkRCNo(fTRework.getReworkRCNo());
		result.setReworkCustomer(fTRework.getReworkCustomer());
		result.setReworkDate(fTRework.getReworkDate());
		result.setReworkDepartment(fTRework.getReworkDepartment());
		result.setReworkEquipment(fTRework.getReworkEquipment());
		result.setReworkLot(FTLotAssembler.toDTO(fTRework.getReworkLot()));
		result.setReworkNo(fTRework.getReworkNo());
		result.setReworkReworkQty(fTRework.getReworkReworkQty());
		result.setReworkTotalQty(fTRework.getReworkTotalQty());
		result.setReasonExplanation(fTRework.getReasonExplanation());
		result.setReasonOther(fTRework.getReasonOther());
		result.setReasonReasons(fTRework.getReasonReasons());
		result.setSummary(fTRework.getSummary());
		result.setLotNo(fTRework.getReworkLot().getInternalLotNumber());
		result.setReworkItems(FTReworkItemAssembler.toDTOs(fTRework
				.getReworkItems()));
		result.setApprovePerson(fTRework.getApprovePerson());
		result.setProduct(fTRework.getProduct());
		result.setReworkType(fTRework.getReworkType());
		result.setApprove(fTRework.getApprove());
		result.setApproveDate(fTRework.getApproveDate());
		result.setApproveRemark(fTRework.getApproveRemark());
		return result;
	}

	public static List<FTReworkDTO> toDTOs(Collection<FTRework> fTReworks) {
		if (fTReworks == null) {
			return null;
		}
		List<FTReworkDTO> results = new ArrayList<FTReworkDTO>();
		for (FTRework each : fTReworks) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static FTRework toEntity(FTReworkDTO fTReworkDTO) {
		if (fTReworkDTO == null) {
			return null;
		}
		FTRework result = new FTRework();
		result.setId(fTReworkDTO.getId());
		result.setVersion(fTReworkDTO.getVersion());
		result.setCreateTimestamp(fTReworkDTO.getCreateTimestamp());
		result.setLastModifyTimestamp(fTReworkDTO.getLastModifyTimestamp());
		result.setCreateEmployNo(fTReworkDTO.getCreateEmployNo());
		result.setLastModifyEmployNo(fTReworkDTO.getLastModifyEmployNo());
		result.setLogic(fTReworkDTO.getLogic());
		result.setReworkRCNo(fTReworkDTO.getReworkRCNo());
		result.setReworkCustomer(fTReworkDTO.getReworkCustomer());
		result.setReworkDate(fTReworkDTO.getReworkDate());
		result.setReworkDepartment(fTReworkDTO.getReworkDepartment());
		result.setReworkEquipment(fTReworkDTO.getReworkEquipment());
		//result.setReworkLot(FTLotAssembler.toEntity(fTReworkDTO.getReworkLot()));
		result.setReworkNo(fTReworkDTO.getReworkNo());
		result.setReworkReworkQty(fTReworkDTO.getReworkReworkQty());
		result.setReworkTotalQty(fTReworkDTO.getReworkTotalQty());
		result.setReasonExplanation(fTReworkDTO.getReasonExplanation());
		result.setReasonOther(fTReworkDTO.getReasonOther());
		result.setReasonReasons(fTReworkDTO.getReasonReasons());
		result.setSummary(fTReworkDTO.getSummary());
		result.setReworkType(fTReworkDTO.getReworkType());
		result.setProduct(fTReworkDTO.getProduct());
//		result.setReworkItems(FTReworkItemAssembler.toEntities(fTReworkDTO
//				.getFtReworkItems()));
		result.setApprovePerson(fTReworkDTO.getApprovePerson());
		return result;
	}

	public static List<FTRework> toEntities(Collection<FTReworkDTO> fTReworkDTOs) {
		if (fTReworkDTOs == null) {
			return null;
		}

		List<FTRework> results = new ArrayList<FTRework>();
		for (FTReworkDTO each : fTReworkDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
