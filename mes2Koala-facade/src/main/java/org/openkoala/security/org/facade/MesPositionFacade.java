package org.openkoala.security.org.facade;

import java.util.List;

import org.openkoala.security.org.facade.dto.MesPositionDTO;

public interface MesPositionFacade {

	public List<MesPositionDTO> findAll();

	public List<MesPositionDTO> findByCondition(MesPositionDTO mesPositionDTO);
}
