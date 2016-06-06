package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class SystemDictionaryAssembler {
	
	public static SystemDictionaryDTO  toDTO(SystemDictionary  systemDictionary){
		if (systemDictionary == null) {
			return null;
		}
    	SystemDictionaryDTO result  = new SystemDictionaryDTO();
	    	result.setId (systemDictionary.getId());
     	    	result.setVersion (systemDictionary.getVersion());
     	    	result.setCreateTimestamp (systemDictionary.getCreateTimestamp());
     	    	result.setLastModifyTimestamp (systemDictionary.getLastModifyTimestamp());
     	    	result.setCreateEmployNo (systemDictionary.getCreateEmployNo());
     	    	result.setLastModifyEmployNo (systemDictionary.getLastModifyEmployNo());
     	    	result.setLogic (systemDictionary.getLogic());
     	    	result.setValue (systemDictionary.getValue());
     	    	result.setLabel (systemDictionary.getLabel());
     	    	result.setType (systemDictionary.getType());
     	    	result.setDescription (systemDictionary.getDescription());
     	    return result;
	 }
	
	public static List<SystemDictionaryDTO>  toDTOs(Collection<SystemDictionary>  systemDictionarys){
		if (systemDictionarys == null) {
			return null;
		}
		List<SystemDictionaryDTO> results = new ArrayList<SystemDictionaryDTO>();
		for (SystemDictionary each : systemDictionarys) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static SystemDictionary  toEntity(SystemDictionaryDTO  systemDictionaryDTO){
	 	if (systemDictionaryDTO == null) {
			return null;
		}
	 	SystemDictionary result  = new SystemDictionary();
        result.setId (systemDictionaryDTO.getId());
         result.setVersion (systemDictionaryDTO.getVersion());
         result.setCreateTimestamp (systemDictionaryDTO.getCreateTimestamp());
         result.setLastModifyTimestamp (systemDictionaryDTO.getLastModifyTimestamp());
         result.setCreateEmployNo (systemDictionaryDTO.getCreateEmployNo());
         result.setLastModifyEmployNo (systemDictionaryDTO.getLastModifyEmployNo());
         result.setLogic (systemDictionaryDTO.getLogic());
         result.setValue (systemDictionaryDTO.getValue());
         result.setLabel (systemDictionaryDTO.getLabel());
         result.setType (systemDictionaryDTO.getType());
         result.setDescription (systemDictionaryDTO.getDescription());
 	  	return result;
	 }
	
	public static List<SystemDictionary> toEntities(Collection<SystemDictionaryDTO> systemDictionaryDTOs) {
		if (systemDictionaryDTOs == null) {
			return null;
		}
		
		List<SystemDictionary> results = new ArrayList<SystemDictionary>();
		for (SystemDictionaryDTO each : systemDictionaryDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
