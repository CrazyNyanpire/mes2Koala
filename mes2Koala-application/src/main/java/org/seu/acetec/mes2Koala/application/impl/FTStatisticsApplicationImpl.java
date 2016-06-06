package org.seu.acetec.mes2Koala.application.impl;

import javax.inject.Named;

import org.seu.acetec.mes2Koala.application.FTStatisticsApplication;
import org.seu.acetec.mes2Koala.core.domain.FTStatistics;
import org.springframework.transaction.annotation.Transactional;

@Named
@Transactional
public class FTStatisticsApplicationImpl extends GenericMES2ApplicationImpl<FTStatistics> implements FTStatisticsApplication{

}
