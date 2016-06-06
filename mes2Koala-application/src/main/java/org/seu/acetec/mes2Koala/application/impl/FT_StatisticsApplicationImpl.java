package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.FT_StatisticsApplication;
import org.seu.acetec.mes2Koala.core.domain.FTStatistics;
import org.seu.acetec.mes2Koala.core.domain.FT_Statistics;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class FT_StatisticsApplicationImpl extends GenericMES2ApplicationImpl<FTStatistics> implements FT_StatisticsApplication {

}
