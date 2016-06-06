package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.FTCompostedTestApplication;
import org.seu.acetec.mes2Koala.core.domain.FTComposedTestNode;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class FTCompostedTestApplicationImpl extends GenericMES2ApplicationImpl<FTComposedTestNode> implements FTCompostedTestApplication {

}
