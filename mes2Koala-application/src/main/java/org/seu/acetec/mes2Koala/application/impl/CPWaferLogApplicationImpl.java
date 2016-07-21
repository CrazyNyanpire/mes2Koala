package org.seu.acetec.mes2Koala.application.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;
import org.seu.acetec.mes2Koala.application.CPWaferLogApplication;
import org.seu.acetec.mes2Koala.core.domain.CPWaferLog;

@Named
@Transactional
public class CPWaferLogApplicationImpl extends
		GenericMES2ApplicationImpl<CPWaferLog> implements CPWaferLogApplication {

}
