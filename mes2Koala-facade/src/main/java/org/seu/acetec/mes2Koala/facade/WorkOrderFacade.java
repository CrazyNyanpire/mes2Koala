package org.seu.acetec.mes2Koala.facade;

import org.openkoala.koala.commons.InvokeResult;

public interface WorkOrderFacade {

    InvokeResult create(String workOrderNumber, Long[] ftLotIds, Long[] bomIds);

    InvokeResult getWorkOrderVo(String workOrderNumber, Long[] ftLotIds, Long[] bomIds);

    InvokeResult getExpectedWorkOrderNumber(Long[] ftLotIds);
}
