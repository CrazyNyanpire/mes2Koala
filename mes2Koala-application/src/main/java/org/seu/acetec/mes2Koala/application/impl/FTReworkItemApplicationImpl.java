package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.FTReworkItemApplication;
import org.seu.acetec.mes2Koala.core.domain.FTReworkItem;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class FTReworkItemApplicationImpl extends GenericMES2ApplicationImpl<FTReworkItem> implements FTReworkItemApplication {

}
