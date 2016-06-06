package org.seu.acetec.mes2Koala.facade;

import javax.servlet.ServletOutputStream;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface CPTransferStorageFacade {

	public Page<CPLotDTO> pageQuery(CPLotDTO cpLotDTO, int currentPage,
			int pageSize);

	public InvokeResult reworkLot(CPLotDTO cpLotDTO);
	
	public InvokeResult createTransferStorage(String lotNo);


}
