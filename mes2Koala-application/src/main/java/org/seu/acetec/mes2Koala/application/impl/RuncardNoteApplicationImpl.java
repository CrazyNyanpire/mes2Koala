package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.RuncardNoteApplication;
import org.seu.acetec.mes2Koala.core.domain.RuncardNote;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class RuncardNoteApplicationImpl extends GenericMES2ApplicationImpl<RuncardNote> implements RuncardNoteApplication {

}
