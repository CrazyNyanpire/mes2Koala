package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class RawDataAssembler {
	
	public static RawDataDTO  toDTO(RawData  rawData){
		if (rawData == null) {
			return null;
		}
    	    RawDataDTO result  = new RawDataDTO();
	        result.setId (rawData.getId());
     	    result.setVersion (rawData.getVersion());
     	    result.setCreateTimestamp (rawData.getCreateTimestamp());
     	    result.setLastModifyTimestamp (rawData.getLastModifyTimestamp());
     	    result.setCreateEmployNo (rawData.getCreateEmployNo());
     	    result.setLastModifyEmployNo (rawData.getLastModifyEmployNo());
     	    result.setLogic (rawData.getLogic());
     	    result.setProductID (rawData.getProductID());
     	    result.setNotchSide (rawData.getNotchSide());
     	    result.setBindefinitionFile (rawData.getBindefinitionFile());
     	    result.setGridXmax (rawData.getGridXmax());
     	    result.setFabSite (rawData.getFabSite());
     	    result.setXDieSize (rawData.getxDieSize());
     	    result.setYDieSize (rawData.getyDieSize());
     	    result.setCustomerCodeID (rawData.getCustomerCodeID());
     	    result.setInternalProductDTO(InternalProductAssembler.toDTO(rawData.getInternalProduct()));
     	    return result;
	 }
	
	public static List<RawDataDTO>  toDTOs(Collection<RawData>  rawDatas){
		if (rawDatas == null) {
			return null;
		}
		List<RawDataDTO> results = new ArrayList<RawDataDTO>();
		for (RawData each : rawDatas) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static RawData  toEntity(RawDataDTO  rawDataDTO){
	 	if (rawDataDTO == null) {
			return null;
		}
	 	 RawData result  = new RawData();
         result.setId (rawDataDTO.getId());
         result.setVersion (rawDataDTO.getVersion());
         result.setCreateTimestamp (rawDataDTO.getCreateTimestamp());
         result.setLastModifyTimestamp (rawDataDTO.getLastModifyTimestamp());
         result.setCreateEmployNo (rawDataDTO.getCreateEmployNo());
         result.setLastModifyEmployNo (rawDataDTO.getLastModifyEmployNo());
         result.setLogic (rawDataDTO.getLogic());
         result.setProductID (rawDataDTO.getProductID());
         result.setNotchSide (rawDataDTO.getNotchSide());
         result.setBindefinitionFile (rawDataDTO.getBindefinitionFile());
         result.setGridXmax (rawDataDTO.getGridXmax());
         result.setFabSite (rawDataDTO.getFabSite());
         result.setxDieSize (rawDataDTO.getXDieSize());
         result.setyDieSize (rawDataDTO.getYDieSize());
         result.setCustomerCodeID (rawDataDTO.getCustomerCodeID());
         result.setInternalProduct(InternalProductAssembler.toEntity(rawDataDTO.getInternalProductDTO()));
 	  	 return result;
	 }
	
	public static List<RawData> toEntities(Collection<RawDataDTO> rawDataDTOs) {
		if (rawDataDTOs == null) {
			return null;
		}
		
		List<RawData> results = new ArrayList<RawData>();
		for (RawDataDTO each : rawDataDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
