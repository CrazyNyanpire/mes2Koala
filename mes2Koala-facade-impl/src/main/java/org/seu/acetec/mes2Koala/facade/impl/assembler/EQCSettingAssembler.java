package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.EQCSetting;
import org.seu.acetec.mes2Koala.facade.dto.EQCSettingDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EQCSettingAssembler {

	public static EQCSettingDTO toDTO(EQCSetting eqcSetting) {
		if (eqcSetting == null) {
			return null;
		}
		EQCSettingDTO result = new EQCSettingDTO();
		result.setId(eqcSetting.getId());
		result.setVersion(eqcSetting.getVersion());
		result.setQty(eqcSetting.getQty());
		result.setLowerLimit(eqcSetting.getLowerLimit());
		result.setUpperLimit(eqcSetting.getUpperLimit());
		result.setNodeName(eqcSetting.getNodeName());
		result.setInternalProductDTO(InternalProductAssembler.toDTO(eqcSetting.getInternalProduct()));
		return result;
	 }

	public static List<EQCSettingDTO> toDTOs(Collection<EQCSetting> eqcSettings) {
		if (eqcSettings == null) {
			return null;
		}
		List<EQCSettingDTO> results = new ArrayList<EQCSettingDTO>();
		for (EQCSetting each : eqcSettings) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static EQCSetting toEntity(EQCSettingDTO eqcSettingDTO) {
		if (eqcSettingDTO == null) {
			return null;
		}
		EQCSetting result = new EQCSetting();
		result.setId(eqcSettingDTO.getId());
		result.setVersion(eqcSettingDTO.getVersion());
		result.setQty(eqcSettingDTO.getQty());
		result.setLowerLimit(eqcSettingDTO.getLowerLimit());
		result.setUpperLimit(eqcSettingDTO.getUpperLimit());
		result.setNodeName(eqcSettingDTO.getNodeName());
		result.setInternalProduct(InternalProductAssembler.toEntity(eqcSettingDTO.getInternalProductDTO()));
 	  	return result;
	 }

	public static List<EQCSetting> toEntities(Collection<EQCSettingDTO> eqcSettingDTOs) {
		if (eqcSettingDTOs == null) {
			return null;
		}

		List<EQCSetting> results = new ArrayList<EQCSetting>();
		for (EQCSettingDTO each : eqcSettingDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
