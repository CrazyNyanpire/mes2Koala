package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.CPNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPProcessDTO;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
public interface CPProcessFacade {

    /**
     * @param id
     * @return
     */
    InvokeResult getCPProcess(Long id);

    /**
     * @param cpProcess
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<CPProcessDTO> pageQueryCPProcess(CPProcessDTO cpProcess, int currentPage, int pageSize);

    /**
     * @param id
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<CPNodeDTO> pageQueryCPNodesByCPProcessId(Long id, int currentPage, int pageSize);

    /**
     * @param id
     * @return
     */
    InvokeResult findCPProcessByCPLotId(Long id);
}
