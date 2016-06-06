package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.LabelDTO;

import java.util.List;

public interface LabelFacade {

    public InvokeResult getLabel(Long id);

    public InvokeResult creatLabel(LabelDTO label);

    public InvokeResult updateLabel(LabelDTO label);

    public InvokeResult removeLabel(Long id);

    public InvokeResult removeLabels(Long[] ids);

    public List<LabelDTO> findAllLabel();

    public Page<LabelDTO> pageQueryLabel(LabelDTO label, int currentPage, int pageSize);

    public List<LabelDTO> getLabelByType(LabelDTO labelDTO);

    public List<LabelDTO> findFTLabelsByPackageType(String packageType);

    List<LabelDTO> findLabelsByInternalProductId(Long id);
}

