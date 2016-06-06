package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.TestingTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.TestingTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@Transactional
public class TestingTemplateApplicationImpl extends GenericMES2ApplicationImpl<TestingTemplate> implements TestingTemplateApplication {

}
