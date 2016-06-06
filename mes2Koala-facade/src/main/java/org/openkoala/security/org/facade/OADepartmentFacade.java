package org.openkoala.security.org.facade;

import java.util.List;

import org.openkoala.security.org.facade.dto.OADepartmentDTO;


public interface OADepartmentFacade {

	public List<OADepartmentDTO> findAll();
	
	public List<OADepartmentDTO> findByCondition(OADepartmentDTO oaUserDTO);
}
