package org.seu.acetec.mes2Koala.facade;

import java.util.List;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface BomUseFacade {

    public InvokeResult getBomUse(Long id);

    public InvokeResult createBomUse(BomUseDTO bomUseDTO);

    public InvokeResult updateBomUse(BomUseDTO bomUseDTO);

    public InvokeResult removeBomUse(Long id);

    public InvokeResult removeBomUses(Long[] ids);

    public List<BomUseDTO> findAllBomUseList();

    public Page<BomUseDTO> pageQueryBomUse(BomUseDTO bomUseDTO, int currentPage, int pageSize);

    public BomTemplateDTO findBomTemplateByBomUseId(Long id);

}

