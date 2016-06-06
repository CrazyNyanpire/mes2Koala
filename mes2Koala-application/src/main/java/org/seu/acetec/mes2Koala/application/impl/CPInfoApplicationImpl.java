package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.CPInfoApplication;
import org.seu.acetec.mes2Koala.core.domain.CPInfo;
import org.seu.acetec.mes2Koala.core.domain.RawData;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

import java.util.List;
import java.util.Set;
import java.text.MessageFormat;

/**
 * @author 阙宇翔
 * @version 2016/02/13
 */
@Named
@Transactional
public class CPInfoApplicationImpl extends GenericMES2ApplicationImpl<CPInfo> implements CPInfoApplication {

    @Override
    public CPInfo findByBomTemplateId(Long bomTemplateId) {
        return findOne("select e from BomTemplate o right join o.internalProduct e where o.id=?", bomTemplateId);
    }

    @Override
    public CPInfo findByCPLotId(Long cpLotId) {
        //return findOne("select _fl.customerLot.internalProduct from CPLot _fl where _fl.id = ?", cpLotId);
    	return findOne("select _fl.customerCPLot.cpInfo from CPLot _fl where _fl.id = ?", cpLotId);
    }

    @Override
    public CPInfo findByCustomerProductNumberAndCustomerNumber(String customerProductNumber, String customerNumber) {
        return findOne("select _i " +
                        "from CPInfo _i " +
                        "where _i.customerProductNumber like ? and _i.customerDirect.number like ?",
                MessageFormat.format("%{0}%", customerProductNumber),
                MessageFormat.format("%{0}%", customerNumber));
    }
    
    @Override
    public List<CPInfo> findPIDByCustomerProductNumberAndCustomerNumber(String customerProductNumber, String customerNumber) {
        return find("select _i " +
                        "from CPInfo _i " +
                        "where _i.customerProductNumber like ? and _i.customerDirect.number like ?",
                MessageFormat.format("%{0}%", customerProductNumber),
                MessageFormat.format("%{0}%", customerNumber));
    }

    @Override
    public CPInfo findByCustomerProductNumberAndCustomerCode(String customerProductNumber, String customerCode) {
        return findOne("select _i " +
                        "from CPInfo _i " +
                        "where _i.customerProductNumber like ? and _i.customerDirect.code like ?",
                MessageFormat.format("%{0}%", customerProductNumber),
                MessageFormat.format("%{0}%", customerCode));
    }
}
