package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class ReworkAssembler {

	public static ReworkDTO toDTO(Rework rework) {
		if (rework == null) {
			return null;
		}
		ReworkDTO result = new ReworkDTO();
		result.setId(rework.getId());
		result.setVersion(rework.getVersion());
		result.setCreateTimestamp(rework.getCreateTimestamp());
		result.setLastModifyTimestamp(rework.getLastModifyTimestamp());
		result.setCreateEmployNo(rework.getCreateEmployNo());
		result.setLastModifyEmployNo(rework.getLastModifyEmployNo());
		result.setLogic(rework.getLogic());
		result.setCategory(rework.getCategory());
		result.setReworkRCNo(rework.getReworkRCNo());
		result.setReworkCustomer(rework.getReworkCustomer());
		result.setReworkDate(rework.getReworkDate());
		result.setReworkDepartment(rework.getReworkDepartment());
		result.setReworkEquipment(rework.getReworkEquipment());
		result.setReworkNo(rework.getReworkNo());
		result.setReworkReworkQty(rework.getReworkReworkQty());
		result.setReworkTotalQty(rework.getReworkTotalQty());
		result.setReasonExplanation(rework.getReasonExplanation());
		result.setReasonOther(rework.getReasonOther());
		result.setReasonReasons(rework.getReasonReasons());
		result.setSummary(rework.getSummary());
		result.setApprovePerson(rework.getApprovePerson());
		result.setReworkType(rework.getReworkType());
		result.setProduct(rework.getProduct());
		result.setApprove(rework.getApprove());
		result.setApproveDate(rework.getApproveDate());
		result.setApproveRemark(rework.getApproveRemark());
		result.setLotNo(rework.getLotNo());
		result.setStatus(rework.getStatus());
		
		return result;
	}

	public static List<ReworkDTO> toDTOs(Collection<Rework> reworks) {
		if (reworks == null) {
			return null;
		}
		List<ReworkDTO> results = new ArrayList<ReworkDTO>();
		for (Rework each : reworks) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static Rework toEntity(ReworkDTO reworkDTO) {
		if (reworkDTO == null) {
			return null;
		}
		Rework result = new Rework();
		result.setId(reworkDTO.getId());
		result.setVersion(reworkDTO.getVersion());
		result.setCreateTimestamp(reworkDTO.getCreateTimestamp());
		result.setLastModifyTimestamp(reworkDTO.getLastModifyTimestamp());
		result.setCreateEmployNo(reworkDTO.getCreateEmployNo());
		result.setLastModifyEmployNo(reworkDTO.getLastModifyEmployNo());
		result.setLogic(reworkDTO.getLogic());
		result.setReworkRCNo(reworkDTO.getReworkRCNo());
		result.setReworkCustomer(reworkDTO.getReworkCustomer());
		result.setReworkDate(reworkDTO.getReworkDate());
		result.setReworkDepartment(reworkDTO.getReworkDepartment());
		result.setReworkEquipment(reworkDTO.getReworkEquipment());
		result.setReworkNo(reworkDTO.getReworkNo());
		result.setReworkReworkQty(reworkDTO.getReworkReworkQty());
		result.setReworkTotalQty(reworkDTO.getReworkTotalQty());
		result.setReasonExplanation(reworkDTO.getReasonExplanation());
		result.setReasonOther(reworkDTO.getReasonOther());
		result.setReasonReasons(reworkDTO.getReasonReasons());
		result.setSummary(reworkDTO.getSummary());
		result.setApprovePerson(reworkDTO.getApprovePerson());
		result.setReworkType(reworkDTO.getReworkType());
		result.setProduct(reworkDTO.getProduct());
		result.setApprove(reworkDTO.getApprove());
		result.setApproveDate(reworkDTO.getApproveDate());
		result.setApproveRemark(reworkDTO.getApproveRemark());
		result.setLotNo(reworkDTO.getLotNo());
		result.setStatus(reworkDTO.getStatus());
		return result;
	}

	public static List<Rework> toEntities(Collection<ReworkDTO> reworkDTOs) {
		if (reworkDTOs == null) {
			return null;
		}

		List<Rework> results = new ArrayList<Rework>();
		for (ReworkDTO each : reworkDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
