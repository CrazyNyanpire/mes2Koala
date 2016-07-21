package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class CPReworkAssembler {

	public static CPReworkDTO toDTO(CPRework cpRework) {
		if (cpRework == null) {
			return null;
		}
		CPReworkDTO result = new CPReworkDTO();
		result.setId(cpRework.getId());
		result.setVersion(cpRework.getVersion());
		result.setCreateTimestamp(cpRework.getCreateTimestamp());
		result.setLastModifyTimestamp(cpRework.getLastModifyTimestamp());
		result.setCreateEmployNo(cpRework.getCreateEmployNo());
		result.setLastModifyEmployNo(cpRework.getLastModifyEmployNo());
		result.setLogic(cpRework.getLogic());
		result.setReworkRCNo(cpRework.getReworkRCNo());
		result.setReworkCustomer(cpRework.getReworkCustomer());
		result.setReworkDate(cpRework.getReworkDate());
		result.setReworkDepartment(cpRework.getReworkDepartment());
		result.setReworkEquipment(cpRework.getReworkEquipment());
		result.setReworkLot(CPLotAssembler.toDTO(cpRework.getReworkLot()));
		result.setReworkNo(cpRework.getReworkNo());
		result.setReworkReworkQty(cpRework.getReworkReworkQty());
		result.setReworkTotalQty(cpRework.getReworkTotalQty());
		result.setReasonExplanation(cpRework.getReasonExplanation());
		result.setReasonOther(cpRework.getReasonOther());
		result.setReasonReasons(cpRework.getReasonReasons());
		result.setSummary(cpRework.getSummary());
		result.setLotNo(cpRework.getReworkLot().getInternalLotNumber());
		result.setReworkItems(CPReworkItemAssembler.toDTOs(cpRework
				.getReworkItems()));
		result.setApprovePerson(cpRework.getApprovePerson());
		result.setProduct(cpRework.getProduct());
		result.setReworkType(cpRework.getReworkType());
		result.setApprove(cpRework.getApprove());
		result.setApproveDate(cpRework.getApproveDate());
		result.setApproveRemark(cpRework.getApproveRemark());
		result.setReworkFailDut(cpRework.getReworkFailDut());
		result.setReworkPC(cpRework.getReworkPC());
		result.setReworkWaferId(cpRework.getReworkWaferId());
		result.setGist(cpRework.getGist());
		result.setTesterNo(cpRework.getTesterNo());
		return result;
	}

	public static List<CPReworkDTO> toDTOs(Collection<CPRework> cpReworks) {
		if (cpReworks == null) {
			return null;
		}
		List<CPReworkDTO> results = new ArrayList<CPReworkDTO>();
		for (CPRework each : cpReworks) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static CPRework toEntity(CPReworkDTO cpReworkDTO) {
		if (cpReworkDTO == null) {
			return null;
		}
		CPRework result = new CPRework();
		result.setId(cpReworkDTO.getId());
		result.setVersion(cpReworkDTO.getVersion());
		result.setCreateTimestamp(cpReworkDTO.getCreateTimestamp());
		result.setLastModifyTimestamp(cpReworkDTO.getLastModifyTimestamp());
		result.setCreateEmployNo(cpReworkDTO.getCreateEmployNo());
		result.setLastModifyEmployNo(cpReworkDTO.getLastModifyEmployNo());
		result.setLogic(cpReworkDTO.getLogic());
		result.setReworkRCNo(cpReworkDTO.getReworkRCNo());
		result.setReworkCustomer(cpReworkDTO.getReworkCustomer());
		result.setReworkDate(cpReworkDTO.getReworkDate());
		result.setReworkDepartment(cpReworkDTO.getReworkDepartment());
		result.setReworkEquipment(cpReworkDTO.getReworkEquipment());
		// result.setReworkLot(CPLotAssembler.toEntity(cpReworkDTO.getReworkLot()));
		result.setReworkNo(cpReworkDTO.getReworkNo());
		result.setReworkReworkQty(cpReworkDTO.getReworkReworkQty());
		result.setReworkTotalQty(cpReworkDTO.getReworkTotalQty());
		result.setReasonExplanation(cpReworkDTO.getReasonExplanation());
		result.setReasonOther(cpReworkDTO.getReasonOther());
		result.setReasonReasons(cpReworkDTO.getReasonReasons());
		result.setSummary(cpReworkDTO.getSummary());
		result.setReworkType(cpReworkDTO.getReworkType());
		result.setProduct(cpReworkDTO.getProduct());
		// result.setReworkItems(CPReworkItemAssembler.toEntities(cpReworkDTO
		// .getCpReworkItems()));
		result.setApprovePerson(cpReworkDTO.getApprovePerson());
		result.setReworkFailDut(cpReworkDTO.getReworkFailDut());
		result.setReworkPC(cpReworkDTO.getReworkPC());
		result.setReworkWaferId(cpReworkDTO.getReworkWaferId());
		result.setGist(cpReworkDTO.getGist());
		return result;
	}

	public static List<CPRework> toEntities(Collection<CPReworkDTO> cpReworkDTOs) {
		if (cpReworkDTOs == null) {
			return null;
		}

		List<CPRework> results = new ArrayList<CPRework>();
		for (CPReworkDTO each : cpReworkDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
