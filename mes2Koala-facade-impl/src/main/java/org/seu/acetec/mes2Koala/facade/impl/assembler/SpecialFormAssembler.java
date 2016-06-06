package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class SpecialFormAssembler {
	
	public static SpecialFormDTO  toDTO(SpecialForm  specialForm){
		if (specialForm == null) {
			return null;
		}
    	SpecialFormDTO result  = new SpecialFormDTO();
	    	result.setId (specialForm.getId());
     	    	result.setVersion (specialForm.getVersion());
     	        result.setCheckBoxForm (specialForm.isCheckBoxForm());
     	        result.setCheckSelfForm (specialForm.isCheckSelfForm());
     	        result.setFlowForm (specialForm.isFlowForm());
     	        result.setIndexForm (specialForm.isIndexForm());
     	        result.setLossForm (specialForm.isLossForm());
     	        result.setReelcodeForm (specialForm.isReelcodeForm());
     	        result.setSizeForm (specialForm.isSizeForm());
     	        result.setSummaryForm (specialForm.isSummaryForm());
     	    return result;
	 }
	
	public static List<SpecialFormDTO>  toDTOs(Collection<SpecialForm>  specialForms){
		if (specialForms == null) {
			return null;
		}
		List<SpecialFormDTO> results = new ArrayList<SpecialFormDTO>();
		for (SpecialForm each : specialForms) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static SpecialForm  toEntity(SpecialFormDTO  specialFormDTO){
	 	if (specialFormDTO == null) {
			return null;
		}
	 	SpecialForm result  = new SpecialForm();
        result.setId (specialFormDTO.getId());
         result.setVersion (specialFormDTO.getVersion());
         result.setCheckBoxForm (specialFormDTO.getCheckBoxForm());
         result.setCheckSelfForm (specialFormDTO.getCheckSelfForm());
         result.setFlowForm (specialFormDTO.getFlowForm());
         result.setIndexForm (specialFormDTO.getIndexForm());
         result.setLossForm (specialFormDTO.getLossForm());
         result.setReelcodeForm (specialFormDTO.getReelcodeForm());
         result.setSizeForm (specialFormDTO.getSizeForm());
         result.setSummaryForm (specialFormDTO.getSummaryForm());
 	  	return result;
	 }
	
	public static List<SpecialForm> toEntities(Collection<SpecialFormDTO> specialFormDTOs) {
		if (specialFormDTOs == null) {
			return null;
		}
		
		List<SpecialForm> results = new ArrayList<SpecialForm>();
		for (SpecialFormDTO each : specialFormDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
