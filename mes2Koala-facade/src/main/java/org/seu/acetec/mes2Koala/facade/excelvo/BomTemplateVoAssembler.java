package org.seu.acetec.mes2Koala.facade.excelvo;

import org.seu.acetec.mes2Koala.core.domain.BomTemplate;
import org.seu.acetec.mes2Koala.facade.dto.BomTemplateDTO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yuxiangque
 * @version 2015/12/11
 */
public class BomTemplateVoAssembler {

    public static BomTemplateVo toVo(BomTemplate bomTemplate) {
        if (bomTemplate == null)
            return null;
        BomTemplateVo bomTemplateVo = new BomTemplateVo();
//        bomTemplateVo.setLevel(bomTemplate.getLevel());
        bomTemplateVo.setBomId(bomTemplate.getBomId());
        bomTemplateVo.setModelNumber(bomTemplate.getModelNumber());
        bomTemplateVo.setNumber(bomTemplate.getNumber());
        bomTemplateVo.setRevision(bomTemplate.getRevision());
        bomTemplateVo.setDescription(bomTemplate.getDescription());
        //bomTemplateVo.setCustomerCode(bomTemplate.getCustomerCode());
        bomTemplateVo.setCustomerCode(bomTemplate.getInternalProduct() == null ? "null" : bomTemplate.getInternalProduct().getCustomerDirect().getCode());
        bomTemplateVo.setUm(bomTemplate.getUm());
        bomTemplateVo.setQuantity(bomTemplate.getQuantity() == null ? 0 : Double.valueOf(bomTemplate.getQuantity()));
        bomTemplateVo.setTheoryQuantity(bomTemplate.getTheoryQuantity() == null ? 0 : Double.valueOf(bomTemplate.getTheoryQuantity()));
//        bomTemplateVo.setItemDescription(bomTemplate.getItemDescription());
        bomTemplateVo.setManufacturerName(bomTemplate.getManufacturerName());
        return bomTemplateVo;

    }

    public static BomTemplateVo toVo(BomTemplateDTO bomTemplateDTO) {
        if (bomTemplateDTO == null)
            return null;
        BomTemplateVo bomTemplateVo = new BomTemplateVo();
//        bomTemplateVo.setLevel(bomTemplate.getLevel());
        bomTemplateVo.setBomId(bomTemplateDTO.getBomId());
        bomTemplateVo.setModelNumber(bomTemplateDTO.getModelNumber());
        bomTemplateVo.setNumber(bomTemplateDTO.getNumber());
        bomTemplateVo.setRevision(bomTemplateDTO.getRevision());
        bomTemplateVo.setDescription(bomTemplateDTO.getDescription());
        //bomTemplateVo.setCustomerCode(bomTemplate.getCustomerCode());
        bomTemplateVo.setCustomerCode(bomTemplateDTO.getInternalProductDTO() == null ? "null" : bomTemplateDTO.getInternalProductDTO().getCustomerDirectDTO().getCode());
        bomTemplateVo.setUm(bomTemplateDTO.getUm());
        bomTemplateVo.setQuantity(bomTemplateDTO.getQuantity() == null ? 0 : Double.valueOf(bomTemplateDTO.getQuantity()));
        bomTemplateVo.setTheoryQuantity(bomTemplateDTO.getTheoryQuantity() == null ? 0 : Double.valueOf(bomTemplateDTO.getTheoryQuantity()));
//        bomTemplateVo.setItemDescription(bomTemplate.getItemDescription());
        bomTemplateVo.setManufacturerName(bomTemplateDTO.getManufacturerName());
        return bomTemplateVo;
    }

    public static List<BomTemplateVo> toVos(Collection<BomTemplateDTO> bomTemplateDTOs) {
        if (bomTemplateDTOs == null)
            return null;
        List<BomTemplateVo> bomTemplateVos = new ArrayList<BomTemplateVo>();
        for (BomTemplateDTO bomTemplateDTO : bomTemplateDTOs) {
            bomTemplateVos.add(toVo(bomTemplateDTO));
        }
        return bomTemplateVos;
    }

    public static BomTemplateDTO toDTO(BomTemplateVo bomTemplateVo) {
        if (bomTemplateVo == null)
            return null;
        BomTemplateDTO bomTemplateDTO = new BomTemplateDTO();
//        bomTemplate.setLevel(bomTemplateVo.getLevel());
        bomTemplateDTO.setBomId(bomTemplateVo.getBomId());
        bomTemplateDTO.setModelNumber(bomTemplateVo.getModelNumber());
        bomTemplateDTO.setNumber(bomTemplateVo.getNumber());
        bomTemplateDTO.setRevision(bomTemplateVo.getRevision());
        bomTemplateDTO.setDescription(bomTemplateVo.getDescription());
        bomTemplateDTO.setCustomerCode(bomTemplateVo.getCustomerCode());
        bomTemplateDTO.setUm(bomTemplateVo.getUm());
        bomTemplateDTO.setQuantity(new DecimalFormat("#.######").format(bomTemplateVo.getQuantity()));
        bomTemplateDTO.setTheoryQuantity(new DecimalFormat("#.######").format(bomTemplateVo.getTheoryQuantity()));
//        bomTemplate.setItemDescription(bomTemplateVo.getItemDescription());
        bomTemplateDTO.setManufacturerName(bomTemplateVo.getManufacturerName());
        return bomTemplateDTO;
    }


}
