package org.openkoala.security.org.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openkoala.security.org.facade.dto.OAUserDTO;
import org.openkoala.security.org.facade.dto.OAUserPrivDTO;

public class OAUserPrivAssembler {

	public static OAUserPrivDTO toDTO(Object[] objects) {
		if (objects == null) {
			return null;
		}
		OAUserPrivDTO result = new OAUserPrivDTO();
		result.setUserPriv(objects[0].toString());
		result.setPrivName(objects[1].toString());
		return result;
	}

	public static List<OAUserPrivDTO> toDTOs(Collection<Object[]> equipments) {
		if (equipments == null) {
			return null;
		}
		List<OAUserPrivDTO> results = new ArrayList<OAUserPrivDTO>();
		for (Object[] each : equipments) {
			results.add(toDTO(each));
		}
		return results;
	}
}
