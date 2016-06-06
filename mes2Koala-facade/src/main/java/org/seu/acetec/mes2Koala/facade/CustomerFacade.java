package org.seu.acetec.mes2Koala.facade;

import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface CustomerFacade {

	public InvokeResult getCustomer(Long id);
	
	public InvokeResult creatCustomer(CustomerDTO customer);
	
	public InvokeResult updateCustomer(CustomerDTO customer);
	
	public InvokeResult removeCustomer(Long id);
	
	public InvokeResult removeCustomers(Long[] ids);
	
	public List<CustomerDTO> findAllCustomer();
	
	public Page<CustomerDTO> pageQueryCustomer(CustomerDTO customer, int currentPage, int pageSize, String sortname, String sortorder);

	CustomerDTO findCustomerDirectByInternalProductId(Long internalProductId);

	CustomerDTO findCustomerIndirectByInternalProductId(Long internalProductId);
	
	public InvokeResult queryCustomerByNumber(String number);
 
}

