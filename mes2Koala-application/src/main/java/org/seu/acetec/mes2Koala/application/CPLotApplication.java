package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.CPLot;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author 阙宇翔
 * @version 2016/2/14
 */
public interface CPLotApplication extends GenericMES2Application<CPLot> {

    void createCheckedCPLot(CPLot cpLot);
    
    public void createCheckedCPLot(CPLot cpLot,boolean checkLotNo,boolean checkRCNumber);
    
    CPLot findByProductionId(Long id);
}
