package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface SystemDictionaryFacade {

	InvokeResult getSystemDictionary(Long id);

	InvokeResult creatSystemDictionary(SystemDictionaryDTO systemDictionary);

	InvokeResult updateSystemDictionary(SystemDictionaryDTO systemDictionary);

	InvokeResult removeSystemDictionary(Long id);

	InvokeResult removeSystemDictionarys(Long[] ids);

	List<SystemDictionaryDTO> findAllSystemDictionary();

	Page<SystemDictionaryDTO> pageQuerySystemDictionary(SystemDictionaryDTO systemDictionary, int currentPage, int pageSize);

	List<SystemDictionaryDTO> getByType(String type);
	
	List<String> getValueByType(String type); 
	
	InvokeResult updateCustomerSerialNumber(String dtoSerialNumber);
	

}

