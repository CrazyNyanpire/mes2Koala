package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.enums.TransferStorageState;

public class ReelDiskAssembler {
	
	public static ReelDiskDTO  toDTO(ReelDisk  reelDisk){
		if (reelDisk == null) {
			return null;
		}
    	ReelDiskDTO result  = new ReelDiskDTO();
	    	result.setId (reelDisk.getId());
     	    	result.setVersion (reelDisk.getVersion());
     	    	result.setCreateTimestamp (reelDisk.getCreateTimestamp());
     	    	result.setLastModifyTimestamp (reelDisk.getLastModifyTimestamp());
     	    	result.setCreateEmployNo (reelDisk.getCreateEmployNo());
     	    	result.setLastModifyEmployNo (reelDisk.getLastModifyEmployNo());
     	    	result.setLogic (reelDisk.getLogic());
     	    	result.setReelCode (reelDisk.getReelCode());
     	    	result.setQuantity (reelDisk.getQuantity());
     	    	result.setPartNumber (reelDisk.getPartNumber());
     	    	
     	    	result.setPackagingTime (reelDisk.getPackagingTime());
     	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     	    	result.setPackagingTimeStr(reelDisk.getPackagingTime()==null?"null":formatter.format(reelDisk.getPackagingTime()));
     	    	
     	    	result.setDateCode (reelDisk.getDateCode());
     	    	result.setReelTime (reelDisk.getReelTime());
     	    	result.setWflot (reelDisk.getWflot());
     	    	result.setTime (reelDisk.getTime());
     	    	result.setPoNumber (reelDisk.getPoNumber());
     	    	result.setPoNumberBox (reelDisk.getPoNumberBox());
     	    	result.setIsFull (reelDisk.getIsFull());
     	    	
     	    	result.setFtLotDTO(FTLotAssembler.toDTO(reelDisk.getFtLot()));
     	    	result.setCombinedLotDTO(FTLotAssembler.toDTO(reelDisk.getCombinedLot()));
     	    	result.setParentSeparationDTO(ReelDiskAssembler.toDTO(reelDisk.getParentSeparation()));
		//result.setParentsIntegrationDTOs(ReelDiskAssembler.toPageVos(reelDisk.getParentIntegrationIds()));
		result.setParentIntegrationIds(reelDisk.getParentIntegrationIds());
		result.setFromReelCode(reelDisk.getFromReelCode());
     	    	
     	    	if(reelDisk.getCombinedLot()!=null)
					result.setCombinedLotNumber(reelDisk.getCombinedLot().getInternalLotNumber());

		result.setQuality(reelDisk.getQuality());
     	    	result.setFailInfo(reelDisk.getFailInfo());
     	    	result.setFtResultDTO(FTResultAssembler.toDTO(reelDisk.getFtResult()));
     	    	
     	    return result;
	 }
	
	public static List<ReelDiskDTO>  toDTOs(Collection<ReelDisk>  reelDisks){
			if (reelDisks == null) {
				return null;
			}
			List<ReelDiskDTO> results = new ArrayList<ReelDiskDTO>();
			for (ReelDisk each : reelDisks) {
				results.add(toDTO(each));
			}
			return results;
	}
	
	 public static ReelDisk  toEntity(ReelDiskDTO  reelDiskDTO){
	 	if (reelDiskDTO == null) {
			return null;
		}
	 	ReelDisk result  = new ReelDisk();
        result.setId (reelDiskDTO.getId());
         result.setVersion (reelDiskDTO.getVersion());
         result.setCreateTimestamp (reelDiskDTO.getCreateTimestamp());
         result.setLastModifyTimestamp (reelDiskDTO.getLastModifyTimestamp());
         result.setCreateEmployNo (reelDiskDTO.getCreateEmployNo());
         result.setLastModifyEmployNo (reelDiskDTO.getLastModifyEmployNo());
         result.setLogic (reelDiskDTO.getLogic());
         result.setReelCode (reelDiskDTO.getReelCode());
         result.setQuantity (reelDiskDTO.getQuantity()==null?0:reelDiskDTO.getQuantity());
         result.setPartNumber (reelDiskDTO.getPartNumber());
         result.setPackagingTime (reelDiskDTO.getPackagingTime());
         result.setDateCode (reelDiskDTO.getDateCode());
         result.setReelTime (reelDiskDTO.getReelTime());
         result.setWflot (reelDiskDTO.getWflot());
         result.setTime (reelDiskDTO.getTime());
         result.setPoNumber (reelDiskDTO.getPoNumber());
         result.setPoNumberBox (reelDiskDTO.getPoNumberBox());
         result.setIsFull (reelDiskDTO.getIsFull());
         
         result.setFtLot(FTLotAssembler.toEntity(reelDiskDTO.getFtLotDTO()));
         result.setCombinedLot(FTLotAssembler.toEntity(reelDiskDTO.getCombinedLotDTO()));
         result.setParentSeparation(ReelDiskAssembler.toEntity(reelDiskDTO.getParentSeparationDTO()));
		 //result.setParentIntegrationIds(ReelDiskAssembler.toEntities(reelDiskDTO.getParentsIntegrationDTOs()));
		 result.setParentIntegrationIds(reelDiskDTO.getParentIntegrationIds());
		 result.setFromReelCode(reelDiskDTO.getFromReelCode());
         
         result.setQuality(reelDiskDTO.getQuality());
         result.setFailInfo(reelDiskDTO.getFailInfo());
         result.setFtResult(FTResultAssembler.toEntity(reelDiskDTO.getFtResultDTO()));
         
 	  	return result;
	 }
	
	public static List<ReelDisk> toEntities(Collection<ReelDiskDTO> reelDiskDTOs) {
		if (reelDiskDTOs == null) {
			return null;
		}
		
		List<ReelDisk> results = new ArrayList<ReelDisk>();
		for (ReelDiskDTO each : reelDiskDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
	  public static List<ReelDisk> wmsJsonToEntities(String wmsJson) {
	        if (wmsJson == null) {
	            return null;
	        }
	        List<ReelDisk> reeldisks = new ArrayList<ReelDisk>();
	        JSONArray jsonArray = JSONArray.fromObject(wmsJson);
	        List<JSONObject> jsonObjects = (List<JSONObject>) JSONArray.toCollection(jsonArray, JSONObject.class);
	        for (JSONObject object : jsonObjects) {
	        	reeldisks.add(wmsJsonToEntity(object));
	        }
	        return reeldisks;
	    }

	private static ReelDisk wmsJsonToEntity(JSONObject object) {
		ReelDisk reelDisk=new ReelDisk(); 
		reelDisk.setReelCode(object.optString("REELCODE"));
		reelDisk.setQuantity(object.optInt("QUANTITY")); 	
		reelDisk.setStatus(TransferStorageState.在库.toString());
		 
		return reelDisk;
	}
}
