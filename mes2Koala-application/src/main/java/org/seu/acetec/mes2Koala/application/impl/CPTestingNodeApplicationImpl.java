package org.seu.acetec.mes2Koala.application.impl;

import javax.inject.Named;

import org.seu.acetec.mes2Koala.application.CPTestingNodeApplication;
import org.seu.acetec.mes2Koala.core.domain.CPTestingNode;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class CPTestingNodeApplicationImpl extends GenericMES2ApplicationImpl<CPTestingNode> implements CPTestingNodeApplication {

}