package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class CPSBLTemplateAssembler {
	
	public static CPSBLTemplateDTO  toDTO(CPSBLTemplate  cPSBLTemplate){
		if (cPSBLTemplate == null) {
			return null;
		}
		        CPSBLTemplateDTO result  = new CPSBLTemplateDTO();
	    	    result.setId (cPSBLTemplate.getId());
     	    	result.setVersion (cPSBLTemplate.getVersion());
     	    	result.setCreateTimestamp (cPSBLTemplate.getCreateTimestamp());
     	    	result.setLastModifyTimestamp (cPSBLTemplate.getLastModifyTimestamp());
     	    	result.setCreateEmployNo (cPSBLTemplate.getCreateEmployNo());
     	    	result.setLastModifyEmployNo (cPSBLTemplate.getLastModifyEmployNo());
     	    	result.setLogic (cPSBLTemplate.getLogic());
     	    	result.setParentIntegrationIds (cPSBLTemplate.getParentIntegrationIds());
     	    	result.setRule (cPSBLTemplate.getRule());
     	    	result.setName (cPSBLTemplate.getName());
     	    	result.setLowerLimit (cPSBLTemplate.getLowerLimit());
     	    	result.setUpperLimit (cPSBLTemplate.getUpperLimit());
     	    	result.setLimitUnit (cPSBLTemplate.getLimitUnit());
     	    	result.setQuality (cPSBLTemplate.getQuality());
     	    	result.setSite (cPSBLTemplate.getSite());
     	    	result.setType (cPSBLTemplate.getType());
     	    	result.setNode (cPSBLTemplate.getNode());
     	    	result.setTestRange (cPSBLTemplate.getTestRange());
     	    	result.setControlType (cPSBLTemplate.getControlType());
     	    	result.setInternalProductDTO(InternalProductAssembler.toDTO(cPSBLTemplate.getInternalProduct()));
     	    return result;
	 }
	
	public static List<CPSBLTemplateDTO>  toDTOs(Collection<CPSBLTemplate>  cPSBLTemplates){
		if (cPSBLTemplates == null) {
			return null;
		}
		List<CPSBLTemplateDTO> results = new ArrayList<CPSBLTemplateDTO>();
		for (CPSBLTemplate each : cPSBLTemplates) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static CPSBLTemplate  toEntity(CPSBLTemplateDTO  cpSBLTemplateDTO){
	 	if (cpSBLTemplateDTO == null) {
			return null;
		}
	 	 CPSBLTemplate result  = new CPSBLTemplate();
         result.setId (cpSBLTemplateDTO.getId());
         result.setVersion (cpSBLTemplateDTO.getVersion());
         result.setCreateTimestamp (cpSBLTemplateDTO.getCreateTimestamp());
         result.setLastModifyTimestamp (cpSBLTemplateDTO.getLastModifyTimestamp());
         result.setCreateEmployNo (cpSBLTemplateDTO.getCreateEmployNo());
         result.setLastModifyEmployNo (cpSBLTemplateDTO.getLastModifyEmployNo());
         result.setLogic (cpSBLTemplateDTO.getLogic());
         result.setParentIntegrationIds (cpSBLTemplateDTO.getParentIntegrationIds());
         result.setRule (cpSBLTemplateDTO.getRule());
         result.setName (cpSBLTemplateDTO.getName());
         result.setLowerLimit (cpSBLTemplateDTO.getLowerLimit());
         result.setUpperLimit (cpSBLTemplateDTO.getUpperLimit());
         result.setLimitUnit (cpSBLTemplateDTO.getLimitUnit());
         result.setQuality (cpSBLTemplateDTO.getQuality());
         result.setSite (cpSBLTemplateDTO.getSite());
         result.setType (cpSBLTemplateDTO.getType());
         result.setNode (cpSBLTemplateDTO.getNode());
         result.setTestRange (cpSBLTemplateDTO.getTestRange());
         result.setControlType (cpSBLTemplateDTO.getControlType());
         result.setInternalProduct(InternalProductAssembler.toEntity(cpSBLTemplateDTO.getInternalProductDTO()));
 	  	return result;
	 }
	
	public static List<CPSBLTemplate> toEntities(Collection<CPSBLTemplateDTO> cpSBLTemplateDTOs) {
		if (cpSBLTemplateDTOs == null) {
			return null;
		}
		
		List<CPSBLTemplate> results = new ArrayList<CPSBLTemplate>();
		for (CPSBLTemplateDTO each : cpSBLTemplateDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
