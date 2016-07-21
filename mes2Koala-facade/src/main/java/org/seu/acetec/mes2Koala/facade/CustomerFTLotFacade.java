package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.CustomerFTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.InternalProductDTO;

import java.util.List;

public interface CustomerFTLotFacade {

    CustomerFTLotDTO findCustomerFTLotByWmsID(String id);

    InvokeResult getCustomerFTLot(Long id);

    InvokeResult removeCustomerFTLots(Long[] ids);

    Page<CustomerFTLotDTO> pageQueryCustomerFTLot(CustomerFTLotDTO customerFTLot, int currentPage, int pageSize, String sortname, String sortOrder);

    CustomerFTLotDTO findParentIntegrationByCustomerLot(Long id);

    CustomerFTLotDTO findParentSeparationByCustomerLot(Long id);

    InternalProductDTO findInternalProductByCustomerLot(Long id);

    CustomerFTLotDTO findCustomerFTLotByCustomerLot(Long id);

    InvokeResult getExpectedLotNumber(Long customerFTLodId);

    InvokeResult getExpectedRCNumber(Long customerFTLodId);

    /**
     * 手动下单
     *
     * @param ftLotDTO
     * @return
     * @version 2016/3/22 YuxiangQue
     * @version 2016/3/27 YuxiangQue 添加参数customertFTLotId避免乐观锁的问题
     */
    InvokeResult order(Long customerFTLotId, FTLotDTO ftLotDTO,String operatorName);

    InvokeResult orderInBatches(Long[] customerFTLotId,String operatorName,FTLotDTO ftLotDTO);

    InvokeResult separateMany(Long parentCustomerFTLotId, List<Long> separationQuantities,String operatorName);

    InvokeResult undoSeparation(Long[] customerFTLotIds);

    InvokeResult integrateManual(Long[] customerFTLotIds,String operatorName);

    InvokeResult undoIntegration(Long customerFTLotId);

    InvokeResult addWmsCustomerFTLots(String json);

    InvokeResult updateWmsCustomerFTLots(String wmsJson);

    InvokeResult deleteWmsCustomerFTLos(String wmsJson);

    Long getFTinfoIdByCustomerFTLotId(Long id);
    
    InvokeResult deleteOrder(FTLotDTO ftLotDTO);
    
    public InvokeResult findPIDByCustomerFTLotId(Long id);

}

