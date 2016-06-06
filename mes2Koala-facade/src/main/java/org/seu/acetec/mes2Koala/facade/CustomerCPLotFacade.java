package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.CustomerCPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.CPCustomerLotPageVo;

import java.util.List;

/**
 * @author 阙宇翔
 * @version 2016/2/27
 */
public interface CustomerCPLotFacade {

    InvokeResult getCustomerCPLot(Long id);

    InvokeResult createCustomerCPLot(CustomerCPLotDTO customerCPLotDTO);

    InvokeResult updateCustomerCPLot(CustomerCPLotDTO customerCPLotDTO);

    InvokeResult removeCustomerCPLot(Long id);

    InvokeResult removeCustomerCPLots(Long[] ids);

    List<CustomerCPLotDTO> findAllCustomerCPLot();

    Page<CPCustomerLotPageVo> pageQueryCustomerCPLot(CustomerCPLotDTO customerCPLotDTO, int currentPage, int pageSize);

    /**
     * 手动下单
     *
     * @param customerCPLotId
     * @param ftLotDTO
     * @return
     */
    InvokeResult order(Long customerCPLotId, CPLotDTO ftLotDTO);

    /**
     * 批量下单：循环遍历尝试自动合批后下单
     *
     * @param customerCPLotIds 需要批量下单的批次ids
     * @return 返回批量下单结果
     */
    InvokeResult batchOrder(Long[] customerCPLotIds);

    /**
     * 获取内部产品批号
     *
     * @param customerCPLotId 需要下单的客户批号
     * @return 成功时返回批号，失败时返回错误信息
     */
    InvokeResult getExpectedLotNumber(Long customerCPLotId);

    /**
     * 获取RC编号
     *
     * @param customerCPLotId 需要下单的客户批号
     * @return 成功是返回批号，失败时返回错误信息
     */
    InvokeResult getExpectedRCNumber(Long customerCPLotId);

    InvokeResult addWmsCustomerCPLots(String json);

    InvokeResult updateWmsCustomerCPLots(String wmsJson);

    InvokeResult deleteWmsCustomerCPLots(String wmsJson);

	InvokeResult getCPCustomerWafer(Long customerCPLotId);

	InvokeResult getCPVo(Long id);

	InvokeResult deleteOrder(CPLotDTO cpLotDTO);


    Long getCPinfoIdByCustomerCPLotId(Long id);
    
    public InvokeResult findPIDByCustomerCPLotId(Long id);
}
