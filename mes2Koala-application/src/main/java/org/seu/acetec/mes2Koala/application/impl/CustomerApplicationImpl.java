package org.seu.acetec.mes2Koala.application.impl;

import java.util.List;

import org.seu.acetec.mes2Koala.application.CustomerApplication;
import org.seu.acetec.mes2Koala.core.domain.Customer;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class CustomerApplicationImpl extends GenericMES2ApplicationImpl<Customer> implements CustomerApplication {

    @Override
    public Customer findCustomerDirectByInternalProductId(Long internalProductId) {
        return findOne("select c from InternalProduct i right join i.customerDirect c where i.id = ?", internalProductId);
    }

    @Override
    public Customer findCustomerIndirectByInternalProductId(Long internalProductId) {
        return findOne("select c from InternalProduct i right join i.customerIndirect c where i.id = ?", internalProductId);
    }
    
}
