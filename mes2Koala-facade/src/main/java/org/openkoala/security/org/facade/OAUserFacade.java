package org.openkoala.security.org.facade;

import java.util.List;

import org.openkoala.security.org.facade.dto.OAUserDTO;


public interface OAUserFacade {

	public List<OAUserDTO> findAll();
	
	public List<OAUserDTO> findByCondition(OAUserDTO oaUserDTO);
}
