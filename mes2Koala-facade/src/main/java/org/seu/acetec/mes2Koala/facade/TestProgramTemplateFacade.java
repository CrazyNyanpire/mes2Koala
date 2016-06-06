package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.AcetecAuthorizationDTO;
import org.seu.acetec.mes2Koala.facade.dto.TestProgramTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.TestProgramTemplatePageVo;

import java.util.List;

public interface TestProgramTemplateFacade {

    InvokeResult getTestProgramTemplate(Long id);

    InvokeResult createTestProgramTemplate(TestProgramTemplateDTO testProgram);

    InvokeResult updateTestProgramTemplate(TestProgramTemplateDTO testProgram);

    InvokeResult removeTestProgramTemplate(Long id);

    InvokeResult removeTestProgramTemplates(Long[] ids);

    List<TestProgramTemplateDTO> findAllTestProgramTemplate();

    Page<TestProgramTemplatePageVo> pageQueryTestProgramTemplate(TestProgramTemplateDTO testProgram, int currentPage, int pageSize);

    Page<AcetecAuthorizationDTO> findAcetecAuthorizationsByTestProgram(Long id, int currentPage, int pageSize);

    List<TestProgramTemplateDTO> findTestProgramTemplatesByProduct(Long id);

    InvokeResult checkTheAuthorizer(Long testProgreamTemplateId,Long userId);

	InvokeResult authorize(Long testProgramId, AcetecAuthorizationDTO acetecAuthorizationDTO);
}

