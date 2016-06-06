package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class TestingTemplateAssembler {
	
	public static TestingTemplateDTO  toDTO(TestingTemplate  testingTemplate){
		if (testingTemplate == null) {
			return null;
		}
    	TestingTemplateDTO result  = new TestingTemplateDTO();
	    	result.setId (testingTemplate.getId());
     	    	result.setVersion (testingTemplate.getVersion());
     	    	result.setName (testingTemplate.getName());
     	    	result.setContent (testingTemplate.getContent());
     	    return result;
	 }
	
	public static List<TestingTemplateDTO>  toDTOs(Collection<TestingTemplate>  testingTemplates){
		if (testingTemplates == null) {
			return null;
		}
		List<TestingTemplateDTO> results = new ArrayList<TestingTemplateDTO>();
		for (TestingTemplate each : testingTemplates) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static TestingTemplate  toEntity(TestingTemplateDTO  testingTemplateDTO){
	 	if (testingTemplateDTO == null) {
			return null;
		}
	 	TestingTemplate result  = new TestingTemplate();
        result.setId (testingTemplateDTO.getId());
         result.setVersion (testingTemplateDTO.getVersion());
         result.setName (testingTemplateDTO.getName());
         result.setContent (testingTemplateDTO.getContent());
 	  	return result;
	 }
	
	public static List<TestingTemplate> toEntities(Collection<TestingTemplateDTO> testingTemplateDTOs) {
		if (testingTemplateDTOs == null) {
			return null;
		}
		
		List<TestingTemplate> results = new ArrayList<TestingTemplate>();
		for (TestingTemplateDTO each : testingTemplateDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
