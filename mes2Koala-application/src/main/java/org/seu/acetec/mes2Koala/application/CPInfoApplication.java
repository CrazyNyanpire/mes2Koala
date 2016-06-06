package org.seu.acetec.mes2Koala.application;

import java.util.List;

import org.seu.acetec.mes2Koala.core.domain.CPInfo;
import org.seu.acetec.mes2Koala.core.domain.RawData;

/**
 * @author 阙宇翔
 * @version 2016/02/13
 */
public interface CPInfoApplication extends GenericMES2Application<CPInfo> {
    /**
     * @param bomTemplateId
     * @return
     * @versino 2016/3/28 YuxiangQue
     */
    CPInfo findByBomTemplateId(Long bomTemplateId);


    CPInfo findByCPLotId(Long ftLotId);

    /**
     * 根据客户产品型号和客户编号(eg:1302-ASE-SU)查找内部产品，主要用于FT下单寻找InternalProduct
     *
     * @param customerProductNumber
     * @param customerNumber
     * @return InternalProduct List列表
     * *@version 2016/1/9 Howard
     * @version 2016/3/27 YuxiangQue
     * @versino 2016/3/28 YuxiangQue
     */
    CPInfo findByCustomerProductNumberAndCustomerNumber(String customerProductNumber, String customerNumber);

    /**
     * 根据客户产品型号和客户编码（eg:1502）查找内部产品，主要用于Bom单管理
     *
     * @param customerProductNumber 客户产品型号
     * @param customerCode          客户编码
     * @return InternalProductDTO List列表
     * @version 2015/12/26 Howard
     * @version 2016/3/27 YuxiangQue
     * @versino 2016/3/28 YuxiangQue
     */
    CPInfo findByCustomerProductNumberAndCustomerCode(String customerProductNumber, String customerCode);

    
    public List<CPInfo> findPIDByCustomerProductNumberAndCustomerNumber(String customerProductNumber, String customerNumber);
}
