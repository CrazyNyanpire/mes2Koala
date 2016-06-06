package org.openkoala.security.org.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openkoala.security.org.facade.dto.OAUserDTO;

public class OAUserAssembler {

	public static OAUserDTO toDTO(Object[] objects) {
		if (objects == null) {
			return null;
		}
		OAUserDTO result = new OAUserDTO();
		result.setId(objects[0].toString());
		result.setName(objects[1].toString());
		result.setAccounts(objects[2].toString());
		result.setDeptId(Long.valueOf(objects[3].toString()));
		result.setDeptName(objects[4] != null ? objects[4].toString() : "");
		result.setEmail(objects[5] != null ? objects[5].toString() : "");
		result.setMobile(objects[6] != null ? objects[6].toString() : "");
		result.setSex(objects[7] != null ? objects[7].toString() : "");
		result.setUserPriv(objects[8] != null ? objects[8].toString() : "");
		return result;
	}

	public static List<OAUserDTO> toDTOs(Collection<Object[]> equipments) {
		if (equipments == null) {
			return null;
		}
		List<OAUserDTO> results = new ArrayList<OAUserDTO>();
		for (Object[] each : equipments) {
			results.add(toDTO(each));
		}
		return results;
	}
}
