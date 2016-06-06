package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.FT_ResultDTO;

import java.util.List;

public interface FTResultFacade {

    InvokeResult getFTResult(Long id);

    InvokeResult createFTResult(FT_ResultDTO fT_Result);

    InvokeResult updateFTResult(FT_ResultDTO fT_Result);

    InvokeResult removeFTResult(Long id);

    InvokeResult removeFTResults(Long[] ids);

    List<FT_ResultDTO> findAllFTResult();

    Page<FT_ResultDTO> pageQueryFT_Result(FT_ResultDTO fT_Result, int currentPage, int pageSize);

}

