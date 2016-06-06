package org.openkoala.security.org.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openkoala.security.org.facade.dto.OADepartmentDTO;

public class OADepartmentAssembler {

	public static OADepartmentDTO toDTO(Object[] objects) {
		if (objects == null) {
			return null;
		}
		OADepartmentDTO result = new OADepartmentDTO();
		result.setId(Long.valueOf(objects[0].toString()));
		result.setDeptName(objects[1].toString());
		result.setDeptNo(objects[2].toString());
		result.setDeptParentId(objects[3] != null ? Long.valueOf(objects[3]
				.toString()) : null);
		result.setDepParentNo(objects[4] != null ? objects[4].toString() : null);
		return result;
	}

	public static List<OADepartmentDTO> toDTOs(Collection<Object[]> equipments) {
		if (equipments == null) {
			return null;
		}
		List<OADepartmentDTO> results = new ArrayList<OADepartmentDTO>();
		for (Object[] each : equipments) {
			results.add(toDTO(each));
		}
		return results;
	}
}
