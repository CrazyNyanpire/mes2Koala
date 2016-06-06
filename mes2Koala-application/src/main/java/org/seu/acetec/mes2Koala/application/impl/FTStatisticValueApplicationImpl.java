package org.seu.acetec.mes2Koala.application.impl;

import javax.inject.Named;

import org.seu.acetec.mes2Koala.application.FTStatisticValueApplication;
import org.seu.acetec.mes2Koala.application.FTStatisticsApplication;
import org.seu.acetec.mes2Koala.core.domain.FTStatisticValue;
import org.seu.acetec.mes2Koala.core.domain.FTStatistics;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class FTStatisticValueApplicationImpl extends GenericMES2ApplicationImpl<FTStatisticValue> implements FTStatisticValueApplication{

}
