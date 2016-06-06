package org.openkoala.security.org.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openkoala.security.org.facade.dto.MesPositionDTO;

public class MesPositionAssembler {

	public static MesPositionDTO toDTO(Object[] objects) {
		if (objects == null) {
			return null;
		}
		MesPositionDTO result = new MesPositionDTO();
		result.setId(Long.valueOf(objects[0].toString()));
		result.setPostCode(objects[1].toString());
		result.setPostName(objects[2].toString());
		result.setPostDept(objects[3] != null ? objects[3].toString() : "");
		return result;
	}

	public static List<MesPositionDTO> toDTOs(Collection<Object[]> equipments) {
		if (equipments == null) {
			return null;
		}
		List<MesPositionDTO> results = new ArrayList<MesPositionDTO>();
		for (Object[] each : equipments) {
			results.add(toDTO(each));
		}
		return results;
	}
}
