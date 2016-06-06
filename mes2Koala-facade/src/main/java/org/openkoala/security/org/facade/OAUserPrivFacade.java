package org.openkoala.security.org.facade;

import java.util.List;

import org.openkoala.security.org.facade.dto.OAUserPrivDTO;


public interface OAUserPrivFacade {

	public List<OAUserPrivDTO> findAll();
	
	public List<OAUserPrivDTO> findByCondition(OAUserPrivDTO oaUserPrivDTO);
}
