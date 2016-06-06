package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.facade.dto.EmployeeDTO;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface AcetecAuthorizationFacade {

	public InvokeResult getAcetecAuthorization(Long id);
	
	public InvokeResult creatAcetecAuthorization(AcetecAuthorizationDTO acetecAuthorization);
	
	public InvokeResult updateAcetecAuthorization(AcetecAuthorizationDTO acetecAuthorization);
	
	public InvokeResult removeAcetecAuthorization(Long id);
	
	public InvokeResult removeAcetecAuthorizations(Long[] ids);
	
	public List<AcetecAuthorizationDTO> findAllAcetecAuthorization();
	
	public Page<AcetecAuthorizationDTO> pageQueryAcetecAuthorization(AcetecAuthorizationDTO acetecAuthorization, int currentPage, int pageSize);
	
	public EmployeeDTO findEmployeeByAcetecAuthorization(Long id);

	public Long getLastAcetecAuthorization();
	
	
	
}

