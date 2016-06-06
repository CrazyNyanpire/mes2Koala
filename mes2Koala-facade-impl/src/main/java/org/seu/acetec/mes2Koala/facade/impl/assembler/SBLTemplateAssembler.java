package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.SBLTemplate;
import org.seu.acetec.mes2Koala.core.enums.SBLQuality;
import org.seu.acetec.mes2Koala.facade.dto.SBLTemplateDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SBLTemplateAssembler {

    public static SBLTemplateDTO toDTO(SBLTemplate sblTemplate) {
        if (sblTemplate == null) {
            return null;
        }
        SBLTemplateDTO result = new SBLTemplateDTO();
        result.setId(sblTemplate.getId());
        result.setVersion(sblTemplate.getVersion());

        result.setBinType(sblTemplate.getBinType());
        result.setLowerLimit(sblTemplate.getLowerLimit());
        result.setUpperLimit(sblTemplate.getUpperLimit());
        result.setBinQuality(SBLQuality.getStringValue(sblTemplate.getBinQuality()));
        result.setSite(sblTemplate.getSite());
        result.setNodeName(sblTemplate.getNodeName());
        return result;
    }

    public static List<SBLTemplateDTO> toDTOs(Collection<SBLTemplate> sblTemplates) {
        if (sblTemplates == null) {
            return null;
        }
        List<SBLTemplateDTO> results = new ArrayList<>();
        for (SBLTemplate each : sblTemplates) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static SBLTemplate toEntity(SBLTemplateDTO sblTemplateDTO) {
        if (sblTemplateDTO == null) {
            return null;
        }
        SBLTemplate result = new SBLTemplate();
        result.setId(sblTemplateDTO.getId());
        result.setVersion(sblTemplateDTO.getVersion());

        result.setBinType(sblTemplateDTO.getBinType());
        result.setLowerLimit(sblTemplateDTO.getLowerLimit());
        result.setUpperLimit(sblTemplateDTO.getUpperLimit());
        result.setBinQuality(SBLQuality.fromStringValue(sblTemplateDTO.getBinQuality()));
        result.setSite(sblTemplateDTO.getSite());
        result.setNodeName(sblTemplateDTO.getNodeName());
        return result;
    }

    public static List<SBLTemplate> toEntities(Collection<SBLTemplateDTO> sblTemplateDTOs) {
        if (sblTemplateDTOs == null) {
            return null;
        }
        List<SBLTemplate> results = new ArrayList<>();
        for (SBLTemplateDTO each : sblTemplateDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
