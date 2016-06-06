package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.FTNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTProcessDTO;

import java.util.List;

public interface FTProcessFacade {

    /**
     * @param id
     * @return
     */
    InvokeResult getFTProcess(Long id);

    /**
     * @param ftProcess
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<FTProcessDTO> pageQueryFTProcess(FTProcessDTO ftProcess, int currentPage, int pageSize);

    /**
     * @param ftProcessId
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<FTNodeDTO> pageQueryFTNodesByFTProcessId(Long ftProcessId, int currentPage, int pageSize);

    /**
     * @param ftLotId
     * @return
     * @version 2016/3/4 YuxiangQue
     */
    InvokeResult findFTProcessByFTLotId(Long ftLotId);

    /**
     * @param id
     * @return
     */
    List<String> findComposedTestNodeNamesByFTLotId(Long id);

    /**
     * @param id
     * @return
     */
    List<String> findComposedTestNodeNamesByProductId(Long id);

    /**
     *     
     * @param note
     * @return
     */
	InvokeResult updateFTNote(Long processId,String note);
}

