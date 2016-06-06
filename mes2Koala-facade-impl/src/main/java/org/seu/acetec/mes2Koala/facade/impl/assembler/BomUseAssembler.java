package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.BomUse;
import org.seu.acetec.mes2Koala.facade.dto.BomUseDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BomUseAssembler {

    public static BomUseDTO toDTO(BomUse bomUse) {
        if (bomUse == null) {
            return null;
        }
        BomUseDTO result = new BomUseDTO();
        result.setId(bomUse.getId());
        result.setVersion(bomUse.getVersion());
        result.setCreateTimestamp(bomUse.getCreateTimestamp());
        result.setLastModifyTimestamp(bomUse.getLastModifyTimestamp());
        result.setCreateEmployNo(bomUse.getCreateEmployNo());
        result.setLastModifyEmployNo(bomUse.getLastModifyEmployNo());
        result.setLogic(bomUse.getLogic());

        // TODO AcetecLot
        result.setBomTemplateDTO(BomTemplateAssembler.toDTO(bomUse.getBomTemplate()));
        result.setSelected(bomUse.isSelected());
        return result;
    }

    public static List<BomUseDTO> toDTOs(Collection<BomUse> bomUses) {
        if (bomUses == null) {
            return null;
        }
        List<BomUseDTO> results = new ArrayList<BomUseDTO>();
        for (BomUse each : bomUses) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static BomUse toEntity(BomUseDTO bomUseDTO) {
        if (bomUseDTO == null) {
            return null;
        }
        BomUse result = new BomUse();
        result.setId(bomUseDTO.getId());
        result.setVersion(bomUseDTO.getVersion());
        result.setCreateTimestamp(bomUseDTO.getCreateTimestamp());
        result.setLastModifyTimestamp(bomUseDTO.getLastModifyTimestamp());
        result.setCreateEmployNo(bomUseDTO.getCreateEmployNo());
        result.setLastModifyEmployNo(bomUseDTO.getLastModifyEmployNo());
        result.setLogic(bomUseDTO.getLogic());

        // TODO AcetecLot
        result.setBomTemplate(BomTemplateAssembler.toEntity(bomUseDTO.getBomTemplateDTO()));
        result.setSelected(bomUseDTO.getSelected());
        return result;
    }

    public static List<BomUse> toEntities(Collection<BomUseDTO> bomSettingDTOs) {
        if (bomSettingDTOs == null) {
            return null;
        }

        List<BomUse> results = new ArrayList<BomUse>();
        for (BomUseDTO each : bomSettingDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
