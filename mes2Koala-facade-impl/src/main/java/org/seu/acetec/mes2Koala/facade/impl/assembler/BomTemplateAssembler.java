package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.BomTemplate;
import org.seu.acetec.mes2Koala.facade.dto.BomTemplateDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BomTemplateAssembler {

    public static BomTemplateDTO toDTO(BomTemplate bomTemplate) {
        if (bomTemplate == null) {
            return null;
        }
        BomTemplateDTO result = new BomTemplateDTO();
        result.setId(bomTemplate.getId());
        result.setVersion(bomTemplate.getVersion());

//        result.setLevel(bomTemplate.getLevel());
        result.setBomId(bomTemplate.getBomId());
        result.setModelNumber(bomTemplate.getModelNumber());
        result.setNumber(bomTemplate.getNumber());
        result.setRevision(bomTemplate.getRevision());
        result.setDescription(bomTemplate.getDescription());
        result.setCustomerCode(bomTemplate.getInternalProduct() == null ? "null" : bomTemplate.getInternalProduct().getCustomerDirect().getCode());
        result.setUm(bomTemplate.getUm());
        result.setQuantity(bomTemplate.getQuantity());
        result.setTheoryQuantity(bomTemplate.getTheoryQuantity());
//        result.setItemDescription(bomTemplate.getItemDescription());
        result.setManufacturerName(bomTemplate.getManufacturerName());

        result.setInternalProductDTO(InternalProductAssembler.toDTO(bomTemplate.getInternalProduct()));
        return result;
    }

    public static List<BomTemplateDTO> toDTOs(Collection<BomTemplate> bomTemplates) {
        if (bomTemplates == null) {
            return null;
        }
        List<BomTemplateDTO> results = new ArrayList<BomTemplateDTO>();
        for (BomTemplate each : bomTemplates) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static BomTemplate toEntity(BomTemplateDTO bomTemplateDTO) {
        if (bomTemplateDTO == null) {
            return null;
        }
        BomTemplate result = new BomTemplate();
        result.setId(bomTemplateDTO.getId());
        result.setVersion(bomTemplateDTO.getVersion());

//        result.setLevel(bomTemplateDTO.getLevel());
        result.setBomId(bomTemplateDTO.getBomId());
        result.setModelNumber(bomTemplateDTO.getModelNumber());
        result.setNumber(bomTemplateDTO.getNumber());
        result.setRevision(bomTemplateDTO.getRevision());
        result.setDescription(bomTemplateDTO.getDescription());
        result.setUm(bomTemplateDTO.getUm());
        result.setQuantity(bomTemplateDTO.getQuantity() == null ? "0" : bomTemplateDTO.getQuantity());
        result.setTheoryQuantity(bomTemplateDTO.getTheoryQuantity() == null ? "0" : bomTemplateDTO.getTheoryQuantity());
//        result.setItemDescription(bomTemplateDTO.getItemDescription());
        result.setManufacturerName(bomTemplateDTO.getManufacturerName());

        result.setInternalProduct(InternalProductAssembler.toEntity(bomTemplateDTO.getInternalProductDTO()));
        return result;
    }

    public static List<BomTemplate> toEntities(Collection<BomTemplateDTO> bomTemplateDTOs) {
        if (bomTemplateDTOs == null) {
            return null;
        }
        List<BomTemplate> results = new ArrayList<BomTemplate>();
        for (BomTemplateDTO each : bomTemplateDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
