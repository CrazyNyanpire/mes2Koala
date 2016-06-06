package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.SpecialFormApplication;
import org.seu.acetec.mes2Koala.core.domain.SpecialForm;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class SpecialFormApplicationImpl extends GenericMES2ApplicationImpl<SpecialForm> implements SpecialFormApplication {

}
