package org.seu.acetec.mes2Koala.application;

import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.core.domain.CPNode;
import org.seu.acetec.mes2Koala.core.domain.FTNode;

import java.util.List;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
public interface CPNodeApplication extends GenericMES2Application<CPNode> {
    Page<CPNode> pageQueryCPNodesByCPProcessId(Long cpProcessId, int currentPage, int pageSize);

    public String createTransferStorage(String lotNo);

    List<CPNode> findEndedCPNodeByCPLotId(Long cpLotId);

    List<CPNode> findUnreachedCPNodeByCPLotId(Long cpLotId);

    List<CPNode> findToStartCPNodeByCPLotId(Long cpLotId);

    List<CPNode> findStartedCPNodeByCPLotId(Long cpLotId);




}
