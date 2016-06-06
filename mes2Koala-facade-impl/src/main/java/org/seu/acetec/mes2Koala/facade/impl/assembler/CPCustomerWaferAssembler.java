package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class CPCustomerWaferAssembler {

	public static CPCustomerWaferDTO toDTO(CPCustomerWafer cPWafer) {
		if (cPWafer == null) {
			return null;
		}
		CPCustomerWaferDTO result = new CPCustomerWaferDTO();
		result.setId(cPWafer.getId());
		result.setVersion(cPWafer.getVersion());
		result.setCreateTimestamp(cPWafer.getCreateTimestamp());
		result.setLastModifyTimestamp(cPWafer.getLastModifyTimestamp());
		result.setCreateEmployNo(cPWafer.getCreateEmployNo());
		result.setLastModifyEmployNo(cPWafer.getLastModifyEmployNo());
		result.setLogic(cPWafer.getLogic());
		result.setWaferCode(cPWafer.getWaferCode());
		result.setWaferIndex(cPWafer.getWaferIndex());
		return result;
	}

	public static List<CPCustomerWaferDTO> toDTOs(Collection<CPCustomerWafer> cPWafers) {
		if (cPWafers == null) {
			return null;
		}
		List<CPCustomerWaferDTO> results = new ArrayList<CPCustomerWaferDTO>();
		for (CPCustomerWafer each : cPWafers) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static CPCustomerWafer toEntity(CPCustomerWaferDTO cPWaferDTO) {
		if (cPWaferDTO == null) {
			return null;
		}
		CPCustomerWafer result = new CPCustomerWafer();
		result.setId(cPWaferDTO.getId());
		result.setVersion(cPWaferDTO.getVersion());
		result.setCreateTimestamp(cPWaferDTO.getCreateTimestamp());
		result.setLastModifyTimestamp(cPWaferDTO.getLastModifyTimestamp());
		result.setCreateEmployNo(cPWaferDTO.getCreateEmployNo());
		result.setLastModifyEmployNo(cPWaferDTO.getLastModifyEmployNo());
		result.setLogic(cPWaferDTO.getLogic());

		return result;
	}

	public static List<CPCustomerWafer> toEntities(Collection<CPCustomerWaferDTO> cPWaferDTOs) {
		if (cPWaferDTOs == null) {
			return null;
		}

		List<CPCustomerWafer> results = new ArrayList<CPCustomerWafer>();
		for (CPCustomerWaferDTO each : cPWaferDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
