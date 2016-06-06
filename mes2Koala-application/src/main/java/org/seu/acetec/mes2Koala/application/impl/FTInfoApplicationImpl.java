package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.FTInfoApplication;
import org.seu.acetec.mes2Koala.core.domain.FTInfo;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.text.MessageFormat;

@Named
@Transactional
public class FTInfoApplicationImpl extends GenericMES2ApplicationImpl<FTInfo> implements FTInfoApplication {

    @Override
    public FTInfo findByFTLotId(Long ftLotId) {
        return findOne("select _fl.customerFTLot.ftInfo from FTLot _fl where _fl.id = ?", ftLotId);
    }

    /**
     * 根据客户产品型号以及客户编号找到内部产品型号
     *
     * @param customerProductNumber
     * @param customerNumber
     * @return
     * @version Howard
     * @version 2016/3/28 YuxiangQue
     */
    @Override
    public FTInfo findByCustomerProductNumberAndCustomerNumber(String customerProductNumber, String customerNumber, String productVersion) {
        return findOne("select _i " +
                        "from FTInfo _i " +
                        "where _i.customerProductNumber like ? and _i.customerDirect.number like ? and _i.customerProductRevision like ?",
                MessageFormat.format("%{0}%", customerProductNumber),
                MessageFormat.format("%{0}%", customerNumber),
                MessageFormat.format("%{0}%", productVersion));
    }

    @Override
    public FTInfo findByCustomerProductNumberAndCustomerCode(String customerProductNumber, String customerCode) {
        return findOne("select _i " +
                        "from FTInfo _i " +
                        "where _i.customerProductNumber like ? and _i.customerDirect.code like ?",
                MessageFormat.format("%{0}%", customerProductNumber),
                MessageFormat.format("%{0}%", customerCode));
    }

    @Override
    public FTInfo findByBomTemplateId(Long bomTemplateId) {
        return findOne("select e from BomTemplate o right join o.internalProduct e where o.id=?", bomTemplateId);
    }
}
