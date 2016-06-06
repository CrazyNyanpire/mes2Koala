package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class FTIQCNodeAssembler {
	
	public static FT_IQCDTO  toDTO(FTIQCNode fT_IQC){
		if (fT_IQC == null) {
			return null;
		}
    	FT_IQCDTO result  = new FT_IQCDTO();
	    	result.setId (fT_IQC.getId());
     	    	result.setVersion (fT_IQC.getVersion());
     	    	
     	    	result.setMark (fT_IQC.getMark());
     	    	result.setGrossWeight (fT_IQC.getGrossWeight());
     	    	result.setNetWeight (fT_IQC.getNetWeight());
     	    	result.setReelCode (fT_IQC.getReelCode());
     	    	result.setFtResultDTO(FTResultAssembler.toDTO(fT_IQC.getResult()));
     	    return result;
	 }
	
	public static List<FT_IQCDTO>  toDTOs(Collection<FTIQCNode>  fT_IQCs){
		if (fT_IQCs == null) {
			return null;
		}
		List<FT_IQCDTO> results = new ArrayList<FT_IQCDTO>();
		for (FTIQCNode each : fT_IQCs) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static FTIQCNode toEntity(FT_IQCDTO  fT_IQCDTO){
	 	if (fT_IQCDTO == null) {
			return null;
		}
	 	FTIQCNode result  = new FTIQCNode();
        result.setId (fT_IQCDTO.getId());
         result.setVersion (fT_IQCDTO.getVersion());
              	 
         result.setMark (fT_IQCDTO.getMark());
         result.setGrossWeight (fT_IQCDTO.getGrossWeight());
         result.setNetWeight (fT_IQCDTO.getNetWeight());
         result.setReelCode (fT_IQCDTO.getReelCode());
         result.setResult(FTResultAssembler.toEntity(fT_IQCDTO.getFtResultDTO()));
 	  	return result;
	 }
	
	public static List<FTIQCNode> toEntities(Collection<FT_IQCDTO> fT_IQCDTOs) {
		if (fT_IQCDTOs == null) {
			return null;
		}
		
		List<FTIQCNode> results = new ArrayList<FTIQCNode>();
		for (FT_IQCDTO each : fT_IQCDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
