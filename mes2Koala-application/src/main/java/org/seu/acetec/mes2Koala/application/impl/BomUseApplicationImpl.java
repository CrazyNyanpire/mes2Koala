package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.BomUseApplication;
import org.seu.acetec.mes2Koala.core.domain.BomUse;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class BomUseApplicationImpl extends GenericMES2ApplicationImpl<BomUse> implements BomUseApplication {

}
