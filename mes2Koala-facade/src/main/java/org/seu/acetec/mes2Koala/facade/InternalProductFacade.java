package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.core.domain.InternalProduct;
import org.seu.acetec.mes2Koala.facade.dto.CustomerDTO;
import org.seu.acetec.mes2Koala.facade.dto.InternalProductDTO;

import java.util.List;

public interface InternalProductFacade {

    InvokeResult getInternalProduct(Long id);

    InvokeResult createInternalProduct(InternalProductDTO internalProduct);

    InvokeResult updateInternalProduct(InternalProductDTO internalProduct);

    InvokeResult removeInternalProduct(Long id);

    InvokeResult removeInternalProducts(Long[] ids);

    List<InternalProductDTO> findAllInternalProduct();
    
    List<InternalProductDTO> findAllInternalProduct(String type);

    Page<InternalProductDTO> pageQueryInternalProduct(InternalProductDTO internalProduct, int currentPage, int pageSize);

    InvokeResult bindProcess(InternalProductDTO internalProductDTO);

	List<InternalProductDTO> findAllInternalProductByCus(String customerNumber, String lineType);

	public InvokeResult queryProductByVersion(String string, String string2);

	InvokeResult createPID(InternalProductDTO internalProductDTO);
	 
}

