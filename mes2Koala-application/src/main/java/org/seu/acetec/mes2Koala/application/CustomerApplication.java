package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.Customer;

import java.util.List;
import java.util.Set;

public interface CustomerApplication extends GenericMES2Application<Customer> {

    Customer findCustomerDirectByInternalProductId(Long internalProductId);

    Customer findCustomerIndirectByInternalProductId(Long internalProductId);
 
}

