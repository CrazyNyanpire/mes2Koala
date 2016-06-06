package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.EQCSettingDTO;

import java.util.List;

public interface EQCSettingFacade {

    InvokeResult getEQCSetting(Long id);

    InvokeResult createEQCSetting(EQCSettingDTO eqcSettingDTO);

    InvokeResult createEQCSettings(List<EQCSettingDTO> eqcSettingDTOs);

    InvokeResult updateEQCSetting(EQCSettingDTO eqcSettingDTO);

    InvokeResult removeEQCSetting(Long id);

    InvokeResult removeEQCSettings(List<EQCSettingDTO> eqcSettingDTOs);

    InvokeResult removeEQCSettings(Long[] ids);

    List<EQCSettingDTO> findAllEQCSetting();

    Page<EQCSettingDTO> pageQueryEQCSetting(EQCSettingDTO eqcSettingDTO, int currentPage, int pageSize);

    List<EQCSettingDTO> findEQCSettingsByProduct(Long id);

    InvokeResult replaceAll(List<EQCSettingDTO> list);
}

