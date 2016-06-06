package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.TestingTemplateDTO;

import java.util.List;

public interface TestingTemplateFacade {

    public InvokeResult getTestingTemplate(Long id);

    public InvokeResult creatTestingTemplate(TestingTemplateDTO testingTemplate);

    public InvokeResult updateTestingTemplate(TestingTemplateDTO testingTemplate);

    public InvokeResult removeTestingTemplate(Long id);

    public InvokeResult removeTestingTemplates(Long[] ids);

    public List<TestingTemplateDTO> findAllTestingTemplate();

    public Page<TestingTemplateDTO> pageQueryTestingTemplate(TestingTemplateDTO testingTemplate, int currentPage, int pageSize);
}

