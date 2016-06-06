package org.seu.acetec.mes2Koala.facade.excelvo;

import org.seu.acetec.mes2Koala.facade.dto.LabelDTO;

public class LabelVoAssembler {
	
	public static LabelDTO toDTO(LabelVo labelVo) {
		LabelDTO labelDTO = new LabelDTO();
		
		labelDTO.setLabelName(labelVo.getLabelName());
		labelDTO.setLabelType(labelVo.getLabelType());
		labelDTO.setTestType(labelVo.getLabelType());
		labelDTO.setPackageType(labelVo.getPackageType());
		
		return labelDTO;
	}
	
	public static LabelVo toVo(LabelDTO labelDTO) {
		LabelVo labelVo = new LabelVo();
		
		labelVo.setLabelName(labelDTO.getLabelName());
		labelVo.setLabelType(labelDTO.getLabelType());
		labelVo.setTestType(labelDTO.getTestType());
		labelVo.setPackageType(labelDTO.getPackageType());
		
		return labelVo;
	}

}
