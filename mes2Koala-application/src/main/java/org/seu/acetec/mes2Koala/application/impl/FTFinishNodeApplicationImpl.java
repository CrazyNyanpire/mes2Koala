package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.FTFinishNodeApplication;
import org.seu.acetec.mes2Koala.core.domain.FTFinishNode;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class FTFinishNodeApplicationImpl extends GenericMES2ApplicationImpl<FTFinishNode> implements FTFinishNodeApplication {

}
