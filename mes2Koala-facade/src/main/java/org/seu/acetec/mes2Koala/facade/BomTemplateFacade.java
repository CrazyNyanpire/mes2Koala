package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.BomTemplateDTO;

import java.util.List;

public interface BomTemplateFacade {

    public InvokeResult getBomTemplate(Long id);

    public InvokeResult createBomTemplate(BomTemplateDTO bomTemplateDTO);

    public InvokeResult updateBomTemplate(BomTemplateDTO bomTemplateDTO);

    public InvokeResult removeBomTemplate(Long id);

    public InvokeResult removeBomTemplates(Long[] ids);

    public List<BomTemplateDTO> findAllBomTemplate();

    public Page<BomTemplateDTO> pageQueryBomTemplate(BomTemplateDTO bomTemplateDTO, int currentPage, int pageSize);

    public boolean fillBomDTOWithInternalProductID(BomTemplateDTO bomTemplateDTO);
}

