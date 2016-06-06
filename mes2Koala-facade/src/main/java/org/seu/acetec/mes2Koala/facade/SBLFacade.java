package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.SBLDTO;

import java.util.List;

/**
 * @author 阙宇翔
 * @version 2016/3/4
 */
public interface SBLFacade {
    InvokeResult getSBL(Long id);

    InvokeResult createSBL(SBLDTO sbl);

    void createSBLs(List<SBLDTO> sbls);

    InvokeResult updateSBL(SBLDTO sbl);

    InvokeResult removeSBL(Long id);

    InvokeResult removeSBLs(Long[] ids);

    InvokeResult removeSBLs(List<SBLDTO> ids);

    List<SBLDTO> findAllSBL();

    Page<SBLDTO> pageQuerySBL(SBLDTO sbl, int currentPage, int pageSize);
}
