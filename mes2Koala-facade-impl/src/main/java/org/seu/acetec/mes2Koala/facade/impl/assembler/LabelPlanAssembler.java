package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class LabelPlanAssembler {
	
	public static LabelPlanDTO  toDTO(LabelPlan  labelPlan){
		if (labelPlan == null) {
			return null;
		}
		LabelPlanDTO result  = new LabelPlanDTO();
	    	result.setId (labelPlan.getId());
     	    	result.setVersion (labelPlan.getVersion());
     	    	result.setLabelInsideDTO (LabelAssembler.toDTO(labelPlan.getLabelInside()));
     	    	result.setLabelOutsideDTO (LabelAssembler.toDTO(labelPlan.getLabelOutside()));
     	    	result.setLabelReelDTO (LabelAssembler.toDTO(labelPlan.getLabelReel()));
     	    	result.setInternalProductDTO (InternalProductAssembler.toDTO(labelPlan.getInternalProduct()));
     	    return result;
	 }
	
	public static List<LabelPlanDTO>  toDTOs(Collection<LabelPlan>  labelPlans){
		if (labelPlans == null) {
			return null;
		}
		List<LabelPlanDTO> results = new ArrayList<LabelPlanDTO>();
		for (LabelPlan each : labelPlans) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static LabelPlan  toEntity(LabelPlanDTO  labelPlanDTO){
	 	if (labelPlanDTO == null) {
			return null;
		}
	 	LabelPlan result  = new LabelPlan();
        result.setId (labelPlanDTO.getId());
         result.setVersion (labelPlanDTO.getVersion());
         //result.setLabelInside (LabelAssembler.toEntity(labelPlanDTO.getLabelInsideDTO()));
         result.setLabelOutside (LabelAssembler.toEntity(labelPlanDTO.getLabelOutsideDTO()));
         result.setLabelReel (LabelAssembler.toEntity(labelPlanDTO.getLabelReelDTO()));
		 //result.setFtInfo (InternalProductAssembler.toEntity(labelPlanDTO.getInternalProductDTO()));
		 //LHB
         InternalProduct internalProduct = new InternalProduct();
         Long internalProductId = labelPlanDTO.getInternalProductId();
         if(internalProductId!=null){
        	 internalProduct.setId(internalProductId);
        	 result.setInternalProduct (internalProduct);
         }      
         
         Label labelInside = new Label();
         Long labelInsideId = labelPlanDTO.getLabelInsideId();
         if(labelInsideId!=null){
        	 labelInside.setId(labelInsideId);
        	 result.setLabelInside(labelInside);
         } 
         
         Label labelOutside = new Label();
         Long labelOutsideId = labelPlanDTO.getLabelOutsideId();
         if(labelOutsideId!=null){
        	 labelOutside.setId(labelOutsideId);
        	 result.setLabelOutside(labelOutside);
         } 
         
         Label labelReel = new Label();
         Long labelReelId = labelPlanDTO.getLabelReelId();
         if(labelReelId!=null){
        	 labelReel.setId(labelReelId);
        	 result.setLabelReel(labelReel);
         } 
         
         return result;
	 }
	
	public static List<LabelPlan> toEntities(Collection<LabelPlanDTO> labelPlanDTOs) {
		if (labelPlanDTOs == null) {
			return null;
		}
		
		List<LabelPlan> results = new ArrayList<LabelPlan>();
		for (LabelPlanDTO each : labelPlanDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
