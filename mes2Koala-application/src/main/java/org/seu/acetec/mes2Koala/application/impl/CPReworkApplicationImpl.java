package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.CPReworkApplication;
import org.seu.acetec.mes2Koala.core.domain.CPRework;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class CPReworkApplicationImpl extends GenericMES2ApplicationImpl<CPRework> implements CPReworkApplication {

}
