package org.openkoala.security.org.facade;

import java.util.List;

import org.openkoala.security.org.facade.dto.MesStaffsDTO;


public interface MesStaffsFacade {

	public List<MesStaffsDTO> findAll();
	
	public List<MesStaffsDTO> findAllDepartnent();
	
	public List<MesStaffsDTO> findAllPost();
	
	public List<MesStaffsDTO> findAllPostAndDepartment();
	
	public List<MesStaffsDTO> findByCondition(MesStaffsDTO mesStaffsDTO);
}
