package org.openkoala.security.org.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openkoala.security.org.facade.dto.MesStaffsDTO;

public class MesStaffsAssembler {

	public static MesStaffsDTO toDTO(Object[] objects) {
		if (objects == null) {
			return null;
		}
		MesStaffsDTO result = new MesStaffsDTO();
		result.setId(objects[0].toString());
		result.setName(objects[1].toString());
		result.setAccounts(objects[2].toString());
		result.setDeptName(objects[3] != null ? objects[3].toString() : "");
		result.setUserPriv(objects[4] != null ? objects[4].toString() : "");
		result.setEmail(objects[5] != null ? objects[5].toString() : "");
		result.setMobile(objects[6] != null ? objects[6].toString() : "");
		result.setSex(objects[7] != null ? objects[7].toString() : "");

		return result;
	}

	public static List<MesStaffsDTO> toDTOs(Collection<Object[]> equipments) {
		if (equipments == null) {
			return null;
		}
		List<MesStaffsDTO> results = new ArrayList<MesStaffsDTO>();
		for (Object[] each : equipments) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static MesStaffsDTO toPostDTO(Object[] objects) {
		if (objects == null) {
			return null;
		}
		MesStaffsDTO result = new MesStaffsDTO();
		result.setUserPriv(objects[0] != null ? objects[0].toString() : "");
		return result;
	}

	public static List<MesStaffsDTO> toPostDTOs(Collection<Object[]> equipments) {
		if (equipments == null) {
			return null;
		}
		List<MesStaffsDTO> results = new ArrayList<MesStaffsDTO>();
		for (Object[] each : equipments) {
			results.add(toPostAndDepartmentDTO(each));
		}
		return results;
	}

	public static MesStaffsDTO toDepartmentDTO(String objects) {
		if (objects == null) {
			return null;
		}
		MesStaffsDTO result = new MesStaffsDTO();
		result.setDeptName(objects != null ? objects.toString() : "");
		return result;
	}

	public static List<MesStaffsDTO> toDepartmentDTOs(
			Collection<String> equipments) {
		if (equipments == null) {
			return null;
		}
		List<MesStaffsDTO> results = new ArrayList<MesStaffsDTO>();
		for (String each : equipments) {
			results.add(toDepartmentDTO(each.toString()));
		}
		return results;
	}

	public static MesStaffsDTO toPostAndDepartmentDTO(Object[] objects) {
		if (objects == null) {
			return null;
		}
		MesStaffsDTO result = new MesStaffsDTO();
		result.setDeptName(objects[0] != null ? objects[0].toString() : "");
		result.setUserPriv(objects[1] != null ? objects[1].toString() : "");
		return result;
	}

	public static List<MesStaffsDTO> toPostAndDepartmentDTOs(
			Collection<Object[]> equipments) {
		if (equipments == null) {
			return null;
		}
		List<MesStaffsDTO> results = new ArrayList<MesStaffsDTO>();
		for (Object[] each : equipments) {
			results.add(toPostAndDepartmentDTO(each));
		}
		return results;
	}
}
