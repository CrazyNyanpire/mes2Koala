package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.enums.CPWaferCheck;
import org.seu.acetec.mes2Koala.core.enums.CPWaferState;

public class CPWaferAssembler {

	public static CPWaferDTO toDTO(CPWafer cPWafer) {
		if (cPWafer == null) {
			return null;
		}
		CPWaferDTO result = new CPWaferDTO();
		result.setId(cPWafer.getId());
		result.setVersion(cPWafer.getVersion());
		result.setCreateTimestamp(cPWafer.getCreateTimestamp());
		result.setLastModifyTimestamp(cPWafer.getLastModifyTimestamp());
		result.setCreateEmployNo(cPWafer.getCreateEmployNo());
		result.setLastModifyEmployNo(cPWafer.getLastModifyEmployNo());
		result.setLogic(cPWafer.getLogic());
		result.setMap(cPWafer.getMap());
		if (cPWafer.getState() != null) {
			result.setState(CPWaferState.getIntValue(cPWafer.getState()));
		} else {
			result.setState(CPWaferState.getIntValue(CPWaferState.UNPASS));
		}
		result.setInternalWaferCode(cPWafer.getInternalWaferCode());
		result.setPass(cPWafer.getPass());
		result.setFail(cPWafer.getFail());
		result.setCustomerOffset(cPWafer.getCustomerOffset());
		result.setInternalOffset(cPWafer.getInternalOffset());
		if (cPWafer.getCpCustomerWafer() != null) {
			result.setCustomerWaferCode(cPWafer.getCpCustomerWafer()
					.getWaferCode());
			result.setCustomerWaferIndex(cPWafer.getCpCustomerWafer()
					.getWaferIndex());
		}
		if (cPWafer.getCpWaferCheck() != null) {
			result.setIsCheck(CPWaferCheck.getIntValue(cPWafer
					.getCpWaferCheck()));
		} else {
			result.setIsCheck(CPWaferCheck.getIntValue(CPWaferCheck.UNCHECKED));
		}
		return result;
	}

	public static List<CPWaferDTO> toDTOs(Collection<CPWafer> cPWafers) {
		if (cPWafers == null) {
			return null;
		}
		List<CPWaferDTO> results = new ArrayList<CPWaferDTO>();
		for (CPWafer each : cPWafers) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static CPWafer toEntity(CPWaferDTO cPWaferDTO) {
		if (cPWaferDTO == null) {
			return null;
		}
		CPWafer result = new CPWafer();
		result.setId(cPWaferDTO.getId());
		result.setVersion(cPWaferDTO.getVersion());
		result.setCreateTimestamp(cPWaferDTO.getCreateTimestamp());
		result.setLastModifyTimestamp(cPWaferDTO.getLastModifyTimestamp());
		result.setCreateEmployNo(cPWaferDTO.getCreateEmployNo());
		result.setLastModifyEmployNo(cPWaferDTO.getLastModifyEmployNo());
		result.setLogic(cPWaferDTO.getLogic());
		result.setMap(cPWaferDTO.getMap());
		result.setState(CPWaferState.fromIntValue(cPWaferDTO.getState()));
		result.setInternalWaferCode(cPWaferDTO.getInternalWaferCode());
		result.setPass(cPWaferDTO.getPass());
		result.setFail(cPWaferDTO.getFail());
		result.setCustomerOffset(cPWaferDTO.getCustomerOffset());
		result.setInternalOffset(cPWaferDTO.getInternalOffset());
		return result;
	}

	public static List<CPWafer> toEntities(Collection<CPWaferDTO> cPWaferDTOs) {
		if (cPWaferDTOs == null) {
			return null;
		}

		List<CPWafer> results = new ArrayList<CPWafer>();
		for (CPWaferDTO each : cPWaferDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
