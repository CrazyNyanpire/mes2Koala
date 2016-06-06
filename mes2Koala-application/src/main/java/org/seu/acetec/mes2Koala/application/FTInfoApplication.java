package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.FTInfo;

public interface FTInfoApplication extends GenericMES2Application<FTInfo> {

    /**
     * @param bomTemplateId
     * @return
     * @versino 2016/3/28 YuxiangQue
     */
    FTInfo findByBomTemplateId(Long bomTemplateId);


    /**
     * @param ftLotId
     * @return
     */
    FTInfo findByFTLotId(Long ftLotId);

    /**
     * 根据客户产品型号和客户编号(eg:1302-ASE-SU)查找内部产品，主要用于FT下单寻找InternalProduct
     *
     * @param customerProductNumber
     * @param customerNumber
     * @param productVersion
     * @return InternalProduct List列表
     * *@version 2016/1/9 Howard
     * @version 2016/3/27 YuxiangQue
     * @versino 2016/3/28 YuxiangQue
     */
    FTInfo findByCustomerProductNumberAndCustomerNumber(String customerProductNumber, String customerNumber, String productVersion);

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
    FTInfo findByCustomerProductNumberAndCustomerCode(String customerProductNumber, String customerCode);
}

