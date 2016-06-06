package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class LabelAssembler {
	
	public static LabelDTO  toDTO(Label  label){
		if (label == null) {
			return null;
		}
    	LabelDTO result  = new LabelDTO();
	    	result.setId (label.getId());
     	    	result.setVersion (label.getVersion());
     	    	result.setLabelFullName ( label.getStoragePath() + label.getLabelName());
     	    	result.setLabelName(label.getLabelName());
     	    	result.setLabelType (label.getLabelType());
     	    	result.setTestType (label.getTestType());
     	    	result.setPackageType (label.getPackageType());
     	    	result.setTaxType(label.getTaxType());
     	    return result;
	 }
	
	public static List<LabelDTO>  toDTOs(Collection<Label>  labels){
		if (labels == null) {
			return null;
		}
		List<LabelDTO> results = new ArrayList<LabelDTO>();
		for (Label each : labels) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static Label  toEntity(LabelDTO  labelDTO){
	 	if (labelDTO == null) {
			return null;
		}
	 	Label result  = new Label();
        result.setId (labelDTO.getId());
         result.setVersion (labelDTO.getVersion());
         String labelFullName = labelDTO.getLabelFullName();
         //result.setStoragePath(labelFullName.substring(0, labelFullName.lastIndexOf('/')+1));
         result.setStoragePath(labelFullName == null ? "" : labelFullName.substring(0, labelFullName.lastIndexOf('/')+1));
         result.setLabelName (labelFullName == null ? "" : labelFullName.substring(labelFullName.lastIndexOf('/')+1));
         result.setLabelType (labelDTO.getLabelType());
         result.setTestType (labelDTO.getTestType());
         result.setPackageType (labelDTO.getPackageType());
         result.setTaxType(labelDTO.getTaxType());
 	  	return result;
	 }
	
	public static List<Label> toEntities(Collection<LabelDTO> labelDTOs) {
		if (labelDTOs == null) {
			return null;
		}
		
		List<Label> results = new ArrayList<Label>();
		for (LabelDTO each : labelDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
