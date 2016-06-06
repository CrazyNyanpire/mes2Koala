package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.SampleShippingApplication;
import org.seu.acetec.mes2Koala.core.domain.SampleShipping;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class SampleShippingApplicationImpl extends GenericMES2ApplicationImpl<SampleShipping> implements SampleShippingApplication {

}
