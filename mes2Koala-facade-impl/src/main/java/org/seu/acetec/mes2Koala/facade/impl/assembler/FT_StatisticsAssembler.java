package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class FT_StatisticsAssembler {
	
	public static FT_StatisticsDTO  toDTO(FTStatistics  fT_Statistics){
		if (fT_Statistics == null) {
			return null;
		}
    	FT_StatisticsDTO result  = new FT_StatisticsDTO();
	    	result.setId (fT_Statistics.getId());
     	    result.setVersion (fT_Statistics.getVersion());
     	    result.setYield(fT_Statistics.getYield());
     	    result.setLoss(fT_Statistics.getLoss());
     	    result.setBackUp(fT_Statistics.getBackUp());
     	    result.setOther(fT_Statistics.getOther());
     	    result.setResultSum(fT_Statistics.getResultSum());
     	    result.setMarkF(fT_Statistics.getMarkF());
     	    result.setFail(fT_Statistics.getFail());
     	    result.setLat(fT_Statistics.getLat());
     	    
     	    result.setSite1Name(fT_Statistics.getSite1Name());
     	    result.setSite1Num(fT_Statistics.getSite1Num());
     	    result.setSite1Quality(fT_Statistics.getSite1Quality());
     	    result.setSite2Name(fT_Statistics.getSite2Name());
     	    result.setSite2Num(fT_Statistics.getSite2Num());
     	    result.setSite2Quality(fT_Statistics.getSite2Quality());
     	    result.setSite3Name(fT_Statistics.getSite3Name());
     	    result.setSite3Num(fT_Statistics.getSite3Num());
     	    result.setSite3Quality(fT_Statistics.getSite3Quality());
     	    result.setSite4Name(fT_Statistics.getSite4Name());
     	    result.setSite4Num(fT_Statistics.getSite4Num());
     	    result.setSite4Quality(fT_Statistics.getSite4Quality());
     	    result.setSite5Name(fT_Statistics.getSite5Name());
     	    result.setSite5Num(fT_Statistics.getSite5Num());
     	    result.setSite5Quality(fT_Statistics.getSite5Quality());
     	    
     	    return result;
	 }
	
	public static List<FT_StatisticsDTO>  toDTOs(Collection<FTStatistics>  fT_Statisticss){
		if (fT_Statisticss == null) {
			return null;
		}
		List<FT_StatisticsDTO> results = new ArrayList<FT_StatisticsDTO>();
		for (FTStatistics each : fT_Statisticss) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static FTStatistics  toEntity(FT_StatisticsDTO  fT_StatisticsDTO){
	 	if (fT_StatisticsDTO == null) {
			return null;
		}
	 	FTStatistics result  = new FTStatistics();
        result.setId (fT_StatisticsDTO.getId());
        result.setVersion (fT_StatisticsDTO.getVersion());
 	    result.setYield(fT_StatisticsDTO.getYield());
 	    result.setLoss(fT_StatisticsDTO.getLoss());
 	    result.setBackUp(fT_StatisticsDTO.getBackUp());
 	    result.setOther(fT_StatisticsDTO.getOther());
 	    result.setResultSum(fT_StatisticsDTO.getResultSum());
 	    result.setMarkF(fT_StatisticsDTO.getMarkF());
 	    result.setFail(fT_StatisticsDTO.getFail());
 	    result.setLat(fT_StatisticsDTO.getLat());
 	    
 	    result.setSite1Name(fT_StatisticsDTO.getSite1Name());
 	    result.setSite1Num(fT_StatisticsDTO.getSite1Num());
 	    result.setSite1Quality(fT_StatisticsDTO.getSite1Quality());
 	    result.setSite2Name(fT_StatisticsDTO.getSite2Name());
 	    result.setSite2Num(fT_StatisticsDTO.getSite2Num());
 	    result.setSite2Quality(fT_StatisticsDTO.getSite2Quality());
 	    result.setSite3Name(fT_StatisticsDTO.getSite3Name());
 	    result.setSite3Num(fT_StatisticsDTO.getSite3Num());
 	    result.setSite3Quality(fT_StatisticsDTO.getSite3Quality());
 	    result.setSite4Name(fT_StatisticsDTO.getSite4Name());
 	    result.setSite4Num(fT_StatisticsDTO.getSite4Num());
 	    result.setSite4Quality(fT_StatisticsDTO.getSite4Quality());
 	    result.setSite5Name(fT_StatisticsDTO.getSite5Name());
 	    result.setSite5Num(fT_StatisticsDTO.getSite5Num());
 	    result.setSite5Quality(fT_StatisticsDTO.getSite5Quality());
        
 	  	return result;
	 }
	
	public static List<FTStatistics> toEntities(Collection<FT_StatisticsDTO> fT_StatisticsDTOs) {
		if (fT_StatisticsDTOs == null) {
			return null;
		}
		
		List<FTStatistics> results = new ArrayList<FTStatistics>();
		for (FT_StatisticsDTO each : fT_StatisticsDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
