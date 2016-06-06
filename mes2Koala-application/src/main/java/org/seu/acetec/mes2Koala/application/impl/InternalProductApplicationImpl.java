package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.InternalProductApplication;
import org.seu.acetec.mes2Koala.core.domain.InternalProduct;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class InternalProductApplicationImpl extends GenericMES2ApplicationImpl<InternalProduct> implements InternalProductApplication {




//    @Override
//    public InternalProduct findByCustomerProductNumberAndCustomerNumber(String customerProductNumber, String customerNumber) {
//        List<Object> conditionVals = new ArrayList<Object>();
//        StringBuilder jpql = new StringBuilder("select _internalProduct " +
//                "from InternalProduct _internalProduct   where 1=1 ");
//        if (customerProductNumber != null && !"".equals(customerProductNumber)) {
//            jpql.append(" and _internalProduct.customerProductNumber like ?");
//            conditionVals.add(MessageFormat.format("%{0}%", customerProductNumber));
//        }
//        if (customerNumber != null && !"".equals(customerNumber)) {
//            jpql.append(" and _internalProduct.customerDirect.number like ?");
//            conditionVals.add(MessageFormat.format("%{0}%", customerNumber));
//        }
//        return (InternalProduct) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
//    }
//
//    @Override
//    public InternalProduct findByCustomerProductNumberAndCustomerCode(String customerProductNumber, String customerCode) {
//        List<Object> conditionVals = new ArrayList<Object>();
//        StringBuilder jpql = new StringBuilder("select _internalProduct from InternalProduct _internalProduct   where 1=1 ");
//        if (customerProductNumber != null && !"".equals(customerProductNumber)) {
//            jpql.append(" and _internalProduct.customerProductNumber like ?");
//            conditionVals.add(MessageFormat.format("%{0}%", customerProductNumber));
//        }
//        if (customerCode != null && !"".equals(customerCode)) {
//            jpql.append(" and _internalProduct.customerDirect.code like ?");
//            conditionVals.add(MessageFormat.format("%{0}%", customerCode));
//        }
//        return (InternalProduct) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
//    }
}
