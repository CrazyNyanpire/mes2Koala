package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class FT_StatisticValueAssembler {
	
	public static FT_StatisticValueDTO  toDTO(FTStatisticValue  fTStatisticValue){
		if (fTStatisticValue == null) {
			return null;
		}
		FT_StatisticValueDTO result  = new FT_StatisticValueDTO();
	    	result.setId (fTStatisticValue.getId());
     	    result.setVersion (fTStatisticValue.getVersion());
     	    result.setName(fTStatisticValue.getName());
     	    result.setQty(fTStatisticValue.getQty());
     	    result.setQuality(fTStatisticValue.getQuality());
     	    result.setSite(fTStatisticValue.getSite());
     	    result.setNodename(fTStatisticValue.getNodename());
     	    return result;
	 }
	
	public static List<FT_StatisticValueDTO>  toDTOs(Collection<FTStatisticValue>  fT_Statisticss){
		if (fT_Statisticss == null) {
			return null;
		}
		List<FT_StatisticValueDTO> results = new ArrayList<FT_StatisticValueDTO>();
		for (FTStatisticValue each : fT_Statisticss) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static FTStatisticValue  toEntity(FT_StatisticValueDTO  FT_StatisticValueDTO){
	 	if (FT_StatisticValueDTO == null) {
			return null;
		}
	 	FTStatisticValue result  = new FTStatisticValue();
        result.setId (FT_StatisticValueDTO.getId());
        result.setVersion (FT_StatisticValueDTO.getVersion()); 
 	    result.setName(FT_StatisticValueDTO.getName());
 	    result.setQty(FT_StatisticValueDTO.getQty());
 	    result.setQuality(FT_StatisticValueDTO.getQuality());
 	    result.setSite(FT_StatisticValueDTO.getSite());
 	    result.setNodename(FT_StatisticValueDTO.getNodename());
        
 	  	return result;
	 }
	
	public static List<FTStatisticValue> toEntities(Collection<FT_StatisticValueDTO> fT_StatisticsDTOs) {
		if (fT_StatisticsDTOs == null) {
			return null;
		}
		
		List<FTStatisticValue> results = new ArrayList<FTStatisticValue>();
		for (FT_StatisticValueDTO each : fT_StatisticsDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
