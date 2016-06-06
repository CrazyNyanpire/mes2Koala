package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.ProcessApplication;
import org.seu.acetec.mes2Koala.core.domain.Process;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
@Named
@Transactional
public class ProcessApplicationImpl extends GenericMES2ApplicationImpl<Process> implements ProcessApplication {
}
