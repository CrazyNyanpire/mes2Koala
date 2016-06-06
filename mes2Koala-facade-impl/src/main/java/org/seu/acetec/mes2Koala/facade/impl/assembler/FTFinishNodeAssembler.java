package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class FTFinishNodeAssembler {
	
	public static FT_FinishDTO  toDTO(FTFinishNode fT_Finish){
		if (fT_Finish == null) {
			return null;
		}
    	FT_FinishDTO result  = new FT_FinishDTO();
	    	result.setId (fT_Finish.getId());
     	    	result.setVersion (fT_Finish.getVersion());
     	    	
     	    	result.setFtResultDTO(FTResultAssembler.toDTO(fT_Finish.getResult()));
     	    	result.setFtStatisticsDTO(FT_StatisticsAssembler.toDTO(fT_Finish.getStatistics()));
     	    return result;
	 }
	
	public static List<FT_FinishDTO>  toDTOs(Collection<FTFinishNode>  fT_Finishs){
		if (fT_Finishs == null) {
			return null;
		}
		List<FT_FinishDTO> results = new ArrayList<FT_FinishDTO>();
		for (FTFinishNode each : fT_Finishs) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static FTFinishNode toEntity(FT_FinishDTO  fT_FinishDTO){
	 	if (fT_FinishDTO == null) {
			return null;
		}
	 	FTFinishNode result  = new FTFinishNode();
        result.setId (fT_FinishDTO.getId());
         result.setVersion (fT_FinishDTO.getVersion());
         
         result.setResult(FTResultAssembler.toEntity(fT_FinishDTO.getFtResultDTO()));
         result.setStatistics(FT_StatisticsAssembler.toEntity(fT_FinishDTO.getFtStatisticsDTO()));
 	  	return result;
	 }
	
	public static List<FTFinishNode> toEntities(Collection<FT_FinishDTO> fT_FinishDTOs) {
		if (fT_FinishDTOs == null) {
			return null;
		}
		
		List<FTFinishNode> results = new ArrayList<FTFinishNode>();
		for (FT_FinishDTO each : fT_FinishDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
