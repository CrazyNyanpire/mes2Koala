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

public class ReelDiskConvertAssembler {
	
	public static ReelDiskConvertDTO  toDTO(ReelDisk  reelDisk){
		if (reelDisk == null) {
			return null;
		}
		ReelDiskConvertDTO result  = new ReelDiskConvertDTO();
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
     	    	result.setParentSeparationDTO(ReelDiskConvertAssembler.toDTO(reelDisk.getParentSeparation()));
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
	
	public static List<ReelDiskConvertDTO>  toDTOs(Collection<ReelDisk>  reelDisks){
			if (reelDisks == null) {
				return null;
			}
			List<ReelDiskConvertDTO> results = new ArrayList<ReelDiskConvertDTO>();
			for (ReelDisk each : reelDisks) {
				results.add(toDTO(each));
			}
			return results;
	}
	
}
