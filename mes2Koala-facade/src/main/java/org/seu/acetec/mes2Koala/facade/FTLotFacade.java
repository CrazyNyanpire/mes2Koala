package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.FTLotPageVo;

import java.util.List;

public interface FTLotFacade {

    InvokeResult getFTLot(Long id);

    InvokeResult updateFTLot(FTLotDTO fTLot);

    InvokeResult removeFTLot(Long id);

    InvokeResult removeFTLots(Long[] ids);

    List<FTLotDTO> findAllFTLot();

    Page<FTLotDTO> pageQueryFTLot(FTLotDTO fTLot, int currentPage, int pageSize);

    Page<FTLotPageVo> pageQueryFTPage(FTLotPageVo queryVo, int currentPage, int pageSize);

    InvokeResult getInternalLotFT(Long id);

    List getFTPageDTOs(Long[] idArray);

    InvokeResult createCheckedFTLot(FTLotDTO childFTLotDTO);

	InvokeResult getPlaceHold(Long id);

	InvokeResult getProcessTemplateByFtLotId(Long id);
}

