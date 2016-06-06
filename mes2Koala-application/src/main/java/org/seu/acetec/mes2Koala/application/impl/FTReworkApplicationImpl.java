package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.FTReworkApplication;
import org.seu.acetec.mes2Koala.core.domain.FTRework;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class FTReworkApplicationImpl extends GenericMES2ApplicationImpl<FTRework> implements FTReworkApplication {

}
