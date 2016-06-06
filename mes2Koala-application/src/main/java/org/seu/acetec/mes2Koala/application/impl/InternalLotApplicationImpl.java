package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.InternalLotApplication;
import org.seu.acetec.mes2Koala.core.domain.InternalLot;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
@Named
@Transactional
public class InternalLotApplicationImpl extends GenericMES2ApplicationImpl<InternalLot> implements InternalLotApplication {
}
