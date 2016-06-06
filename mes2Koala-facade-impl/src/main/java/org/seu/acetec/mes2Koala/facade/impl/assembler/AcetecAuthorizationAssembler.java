package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.seu.acetec.mes2Koala.core.domain.AcetecAuthorization;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.openkoala.organisation.facade.impl.assembler.EmployeeAssembler;


public class AcetecAuthorizationAssembler {
	
	public static AcetecAuthorizationDTO  toDTO(AcetecAuthorization  acetecAuthorization){
		if (acetecAuthorization == null) {
			return null;
		}
		AcetecAuthorizationDTO result  = new AcetecAuthorizationDTO();
	    	result.setId (acetecAuthorization.getId());
     	    	result.setVersion (acetecAuthorization.getVersion());
     	    	result.setEmployeeDTO (EmployeeAssembler.toDTO(acetecAuthorization.getEmployee()));
     	    	result.setOpinion (acetecAuthorization.getOpinion());
     	    	result.setNote (acetecAuthorization.getNote());
     	    	if(acetecAuthorization.getLastModifyTimestamp()!=null&&acetecAuthorization.getOpinion()!=null
     	    			&&acetecAuthorization.getOpinion().equals("同意"))
     	    		result.setLastModifyTime(acetecAuthorization.getLastModifyTimestamp().toString());
     	    return result;
	 }
	
	public static List<AcetecAuthorizationDTO>  toDTOs(Collection<AcetecAuthorization>  acetecAuthorizations){
		if (acetecAuthorizations == null) {
			return null;
		}
		List<AcetecAuthorizationDTO> results = new ArrayList<AcetecAuthorizationDTO>();
		for (AcetecAuthorization each : acetecAuthorizations) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static AcetecAuthorization  toEntity(AcetecAuthorizationDTO  acetecAuthorizationDTO){
	 	if (acetecAuthorizationDTO == null) {
			return null;
		}
	 	AcetecAuthorization result  = new AcetecAuthorization();
        result.setId (acetecAuthorizationDTO.getId());
         result.setVersion (acetecAuthorizationDTO.getVersion());
         result.setEmployee (EmployeeAssembler.toEntity(acetecAuthorizationDTO.getEmployeeDTO()));
         result.setOpinion (acetecAuthorizationDTO.getOpinion());
         result.setNote (acetecAuthorizationDTO.getNote());
 	  	return result;
	 }
	
	public static List<AcetecAuthorization> toEntities(Collection<AcetecAuthorizationDTO> acetecAuthorizationDTOs) {
		if (acetecAuthorizationDTOs == null) {
			return null;
		}
		
		List<AcetecAuthorization> results = new ArrayList<AcetecAuthorization>();
		for (AcetecAuthorizationDTO each : acetecAuthorizationDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
