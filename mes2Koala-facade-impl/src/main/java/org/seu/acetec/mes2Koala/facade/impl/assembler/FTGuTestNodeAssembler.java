package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class FTGuTestNodeAssembler {
	
	public static FT_GuTestDTO  toDTO(FTGuTestNode fT_GuTestNode){
		if (fT_GuTestNode == null) {
			return null;
		}
    	FT_GuTestDTO result  = new FT_GuTestDTO();
	    	result.setId (fT_GuTestNode.getId());
     	    	result.setVersion (fT_GuTestNode.getVersion());
     	    	result.setCustomerName (fT_GuTestNode.getCustomerName());
     	    	result.setGoldenUnitsType (fT_GuTestNode.getGoldenUnitsType());
     	    	result.setGoldenUnitsNo (fT_GuTestNode.getGoldenUnitsNo());
     	    	result.setUseTimes (fT_GuTestNode.getUseTimes());
     	    	result.setProductNumber (fT_GuTestNode.getProductNumber());
     	    	result.setPe (fT_GuTestNode.getPe());
     	    	result.setStandardResult(fT_GuTestNode.getStandardResult());
     	    	result.setRecord(fT_GuTestNode.getRecord());
     	    	result.setNox(fT_GuTestNode.getNox());
     	    return result;
	 }
	
	public static List<FT_GuTestDTO>  toDTOs(Collection<FTGuTestNode> fT_GuTestNodes){
		if (fT_GuTestNodes == null) {
			return null;
		}
		List<FT_GuTestDTO> results = new ArrayList<FT_GuTestDTO>();
		for (FTGuTestNode each : fT_GuTestNodes) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static FTGuTestNode toEntity(FT_GuTestDTO  fT_GuTestDTO){
	 	if (fT_GuTestDTO == null) {
			return null;
		}
	 	FTGuTestNode result  = new FTGuTestNode();
        result.setId (fT_GuTestDTO.getId());
         result.setVersion (fT_GuTestDTO.getVersion());
         result.setCustomerName (fT_GuTestDTO.getCustomerName());
         result.setGoldenUnitsType (fT_GuTestDTO.getGoldenUnitsType());
         result.setGoldenUnitsNo (fT_GuTestDTO.getGoldenUnitsNo());
         result.setUseTimes (fT_GuTestDTO.getUseTimes());
         result.setProductNumber (fT_GuTestDTO.getProductNumber());
         result.setPe (fT_GuTestDTO.getPe());
         result.setStandardResult(fT_GuTestDTO.getStandardResult());
         result.setRecord(fT_GuTestDTO.getRecord());
         result.setNox(fT_GuTestDTO.getNox());
 	  	return result;
	 }
	
	public static List<FTGuTestNode> toEntities(Collection<FT_GuTestDTO> fT_GuTestDTOs) {
		if (fT_GuTestDTOs == null) {
			return null;
		}
		
		List<FTGuTestNode> results = new ArrayList<FTGuTestNode>();
		for (FT_GuTestDTO each : fT_GuTestDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
