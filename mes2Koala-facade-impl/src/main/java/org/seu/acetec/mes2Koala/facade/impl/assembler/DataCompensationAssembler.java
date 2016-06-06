package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class DataCompensationAssembler {
	
	public static DataCompensationDTO  toDTO(DataCompensation  dataCompensation){
		if (dataCompensation == null) {
			return null;
		}
    	DataCompensationDTO result  = new DataCompensationDTO();
	    	result.setId (dataCompensation.getId());
     	    	result.setVersion (dataCompensation.getVersion());
     	    	result.setCreateTimestamp (dataCompensation.getCreateTimestamp());
     	    	result.setLastModifyTimestamp (dataCompensation.getLastModifyTimestamp());
     	    	result.setCreateEmployNo (dataCompensation.getCreateEmployNo());
     	    	result.setLastModifyEmployNo (dataCompensation.getLastModifyEmployNo());
     	    	result.setLogic (dataCompensation.getLogic());
     	    	result.setLotID (dataCompensation.getLotID());
     	    	result.setWaferID (dataCompensation.getWaferID());
     	    	result.setSmicProdID (dataCompensation.getSmicProdID());
     	    	result.setTestProgram (dataCompensation.getTestProgram());
     	    	result.setTesterID (dataCompensation.getTesterID());
     	    	result.setOperatorID (dataCompensation.getOperatorID());
     	    	result.setStartTime (dataCompensation.getStartTime());
     	    	result.setEndTime (dataCompensation.getEndTime());
     	    	result.setNotchSide (dataCompensation.getNotchSide());
     	    	result.setSortID (dataCompensation.getSortID());
     	    	result.setBinDefinitionFile (dataCompensation.getBinDefinitionFile());
     	    	result.setGridXmax (dataCompensation.getGridXmax());
     	    	result.setGridYmax (dataCompensation.getGridYmax());
     	    	result.setFabSite (dataCompensation.getFabSite());
     	    	result.setTestSite (dataCompensation.getTestSite());
     	    	result.setProbeCardID (dataCompensation.getProbeCardID());
     	    	result.setLoadBoardID (dataCompensation.getLoadBoardID());
     	    	result.setRomCode (dataCompensation.getRomCode());
     	    	result.setXDieSize (dataCompensation.getxDieSize());
     	    	result.setYDieSize (dataCompensation.getyDieSize());
     	    	result.setXStreet (dataCompensation.getxStreet());
     	    	result.setYStreet (dataCompensation.getyStreet());
     	    	result.setCustomerCode (dataCompensation.getCustomerCode());
     	    	result.setCustomerPartID (dataCompensation.getCustomerPartID());
     	    	result.setRawData (dataCompensation.getRawData());
     	    return result;
	 }
	
	public static List<DataCompensationDTO>  toDTOs(Collection<DataCompensation>  dataCompensations){
		if (dataCompensations == null) {
			return null;
		}
		List<DataCompensationDTO> results = new ArrayList<DataCompensationDTO>();
		for (DataCompensation each : dataCompensations) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static DataCompensation  toEntity(DataCompensationDTO  dataCompensationDTO){
	 	if (dataCompensationDTO == null) {
			return null;
		}
	 	DataCompensation result  = new DataCompensation();
        result.setId (dataCompensationDTO.getId());
         result.setVersion (dataCompensationDTO.getVersion());
         result.setCreateTimestamp (dataCompensationDTO.getCreateTimestamp());
         result.setLastModifyTimestamp (dataCompensationDTO.getLastModifyTimestamp());
         result.setCreateEmployNo (dataCompensationDTO.getCreateEmployNo());
         result.setLastModifyEmployNo (dataCompensationDTO.getLastModifyEmployNo());
         result.setLogic (dataCompensationDTO.getLogic());
         result.setLotID (dataCompensationDTO.getLotID());
         result.setWaferID (dataCompensationDTO.getWaferID());
         result.setSmicProdID (dataCompensationDTO.getSmicProdID());
         result.setTestProgram (dataCompensationDTO.getTestProgram());
         result.setTesterID (dataCompensationDTO.getTesterID());
         result.setOperatorID (dataCompensationDTO.getOperatorID());
         result.setStartTime (dataCompensationDTO.getStartTime());
         result.setEndTime (dataCompensationDTO.getEndTime());
         result.setNotchSide (dataCompensationDTO.getNotchSide());
         result.setSortID (dataCompensationDTO.getSortID());
         result.setBinDefinitionFile (dataCompensationDTO.getBinDefinitionFile());
         result.setGridXmax (dataCompensationDTO.getGridXmax());
         result.setGridYmax (dataCompensationDTO.getGridYmax());
         result.setFabSite (dataCompensationDTO.getFabSite());
         result.setTestSite (dataCompensationDTO.getTestSite());
         result.setProbeCardID (dataCompensationDTO.getProbeCardID());
         result.setLoadBoardID (dataCompensationDTO.getLoadBoardID());
         result.setRomCode (dataCompensationDTO.getRomCode());
         result.setxDieSize (dataCompensationDTO.getXDieSize());
         result.setyDieSize (dataCompensationDTO.getYDieSize());
         result.setxStreet (dataCompensationDTO.getXStreet());
         result.setyStreet (dataCompensationDTO.getYStreet());
         result.setCustomerCode (dataCompensationDTO.getCustomerCode());
         result.setCustomerPartID (dataCompensationDTO.getCustomerPartID());
         result.setRawData (dataCompensationDTO.getRawData());
 	  	return result;
	 }
	
	public static List<DataCompensation> toEntities(Collection<DataCompensationDTO> dataCompensationDTOs) {
		if (dataCompensationDTOs == null) {
			return null;
		}
		
		List<DataCompensation> results = new ArrayList<DataCompensation>();
		for (DataCompensationDTO each : dataCompensationDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
