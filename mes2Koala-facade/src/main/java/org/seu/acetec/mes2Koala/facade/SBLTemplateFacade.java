package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.SBLTemplateDTO;

import java.util.List;

public interface SBLTemplateFacade {

    InvokeResult getSBLTemplate(Long id);

    InvokeResult createSBLTemplate(SBLTemplateDTO sblTemplateDTO);

    void createSBLTemplates(List<SBLTemplateDTO> sblTemplateDTOs);

    InvokeResult updateSBLTemplate(SBLTemplateDTO sblTemplateDTO);

    InvokeResult removeSBLTemplate(Long id);

    InvokeResult removeSBLTemplates(Long[] ids);

    InvokeResult removeSBLTemplates(List<SBLTemplateDTO> ids);

    List<SBLTemplateDTO> findAllSBLTemplate();

    Page<SBLTemplateDTO> pageQuerySBLTemplate(SBLTemplateDTO sblTemplateDTO, int currentPage, int pageSize);

    List<SBLTemplateDTO> findSBLTemplatesDTOsByProductId(Long id);

    InvokeResult bindSBLTemplates(Long internalProductId, List<SBLTemplateDTO> list);
}

