package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.FTResultApplication;
import org.seu.acetec.mes2Koala.core.domain.FTResult;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class FTResultApplicationImpl extends GenericMES2ApplicationImpl<FTResult> implements FTResultApplication {

}
