package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.CustomerLotApplication;
import org.seu.acetec.mes2Koala.core.domain.CustomerLot;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class CustomerLotApplicationImpl extends GenericMES2ApplicationImpl<CustomerLot> implements CustomerLotApplication {
}