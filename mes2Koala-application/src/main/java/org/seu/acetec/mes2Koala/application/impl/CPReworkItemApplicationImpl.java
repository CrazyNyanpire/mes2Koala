package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.CPReworkItemApplication;
import org.seu.acetec.mes2Koala.core.domain.CPReworkItem;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class CPReworkItemApplicationImpl extends GenericMES2ApplicationImpl<CPReworkItem> implements CPReworkItemApplication {

}
