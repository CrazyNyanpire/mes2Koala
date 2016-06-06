package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class CPQDNAssembler {
	
	  public static CPQDNDTO  toDTO(CPQDN  cPQDN){
		    if (cPQDN == null) {
		  	  return null;
		    }
    	    CPQDNDTO result  = new CPQDNDTO();
	    	result.setId (cPQDN.getId());
     	    result.setVersion (cPQDN.getVersion());
     	    result.setCreateTimestamp (cPQDN.getCreateTimestamp());
     	    result.setLastModifyTimestamp (cPQDN.getLastModifyTimestamp());
     	    result.setCreateEmployNo (cPQDN.getCreateEmployNo());
     	    result.setLastModifyEmployNo (cPQDN.getLastModifyEmployNo());
     	    result.setLogic (cPQDN.getLogic());
     	    result.setCustomerNote (cPQDN.getCustomerNote());
     	    result.setCustomerSuggestion (cPQDN.getCustomerSuggestion());
     	    result.setMethod (cPQDN.getMethod());
     	    result.setNote (cPQDN.getNote());
     	    result.setQASuggestion (cPQDN.getQASuggestion());
     	    result.setReason (cPQDN.getReason());
     	    result.setSuggestion (cPQDN.getSuggestion());
     	    result.setAttachment (cPQDN.getAttachment());
     	    result.setQdnNo (cPQDN.getQdnNo());
     	    result.setFlowNow (cPQDN.getFlowNow());
     	    result.setCustomerDeal (cPQDN.getCustomerDeal());
     	    result.setCustomerDealNote (cPQDN.getCustomerDealNote());
     	    result.setDate (cPQDN.getDate());
     	    result.setDealDate (cPQDN.getDealDate());
     	    result.setDealPerson (cPQDN.getDealPerson());
     	    result.setFailQty (cPQDN.getFailQty());
     	    result.setInternalDeal (cPQDN.getInternalDeal());
     	    result.setInternalDealNote (cPQDN.getInternalDealNote());
     	    result.setQaDeal (cPQDN.getQaDeal());
     	    result.setStatus (cPQDN.getStatus());
     	    result.setTesterSys (cPQDN.getTesterSys());
     	    result.setToPerson (cPQDN.getToPerson());
     	    result.setType (cPQDN.getType());
     	    result.setWorkPerson (cPQDN.getWorkPerson());
     	    result.setCpLotDTO (CPLotAssembler.toDTO(cPQDN.getCpLot()));
     	    result.setIsFuture (cPQDN.getIsFuture());
     	    result.setCustomerName (cPQDN.getCustomerName());
     	    result.setToDepartment (cPQDN.getToDepartment());
     	    result.setFlow (cPQDN.getFlow());
     	    result.setRiskLot(cPQDN.getRiskLot());
     	    result.setQdnInfo(cPQDN.getQdnInfo());
     	    result.setQdnChk(cPQDN.getQdnChk());
     	    result.setLotNo(cPQDN.getCpLot().getInternalLotNumber());
     	  return result;
	 }
	
	public static List<CPQDNDTO>  toDTOs(Collection<CPQDN>  cPQDNs){
		if (cPQDNs == null) {
			return null;
		}
		List<CPQDNDTO> results = new ArrayList<CPQDNDTO>();
		for (CPQDN each : cPQDNs) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static CPQDN  toEntity(CPQDNDTO  cPQDNDTO){
	 	if (cPQDNDTO == null) {
			return null;
		}
	 	 CPQDN result  = new CPQDN();
         result.setId (cPQDNDTO.getId());
         result.setVersion (cPQDNDTO.getVersion());
         result.setCreateTimestamp (cPQDNDTO.getCreateTimestamp());
         result.setLastModifyTimestamp (cPQDNDTO.getLastModifyTimestamp());
         result.setCreateEmployNo (cPQDNDTO.getCreateEmployNo());
         result.setLastModifyEmployNo (cPQDNDTO.getLastModifyEmployNo());
         result.setLogic (cPQDNDTO.getLogic());
         result.setCustomerNote (cPQDNDTO.getCustomerNote());
         result.setCustomerSuggestion (cPQDNDTO.getCustomerSuggestion());
         result.setMethod (cPQDNDTO.getMethod());
         result.setNote (cPQDNDTO.getNote());
         result.setQASuggestion (cPQDNDTO.getQASuggestion());
         result.setReason (cPQDNDTO.getReason());
         result.setSuggestion (cPQDNDTO.getSuggestion());
         result.setAttachment (cPQDNDTO.getAttachment());
         result.setQdnNo (cPQDNDTO.getQdnNo());
         result.setFlowNow (cPQDNDTO.getFlowNow());
         result.setCustomerDeal (cPQDNDTO.getCustomerDeal());
         result.setCustomerDealNote (cPQDNDTO.getCustomerDealNote());
         result.setDate (cPQDNDTO.getDate());
         result.setDealDate (cPQDNDTO.getDealDate());
         result.setDealPerson (cPQDNDTO.getDealPerson());
         result.setFailQty (cPQDNDTO.getFailQty());
         result.setInternalDeal (cPQDNDTO.getInternalDeal());
         result.setInternalDealNote (cPQDNDTO.getInternalDealNote());
         result.setQaDeal (cPQDNDTO.getQaDeal());
         result.setStatus (cPQDNDTO.getStatus());
         result.setTesterSys (cPQDNDTO.getTesterSys());
         result.setToPerson (cPQDNDTO.getToPerson());
         result.setType (cPQDNDTO.getType());
         result.setWorkPerson (cPQDNDTO.getWorkPerson());
         result.setCpLot (CPLotAssembler.toEntity(cPQDNDTO.getCpLotDTO()));
         result.setIsFuture (cPQDNDTO.getIsFuture());
         result.setCustomerName (cPQDNDTO.getCustomerName());
         result.setToDepartment (cPQDNDTO.getToDepartment());
         result.setFlow (cPQDNDTO.getFlow());
	     result.setRiskLot(cPQDNDTO.getRiskLot());
	     result.setQdnInfo(cPQDNDTO.getQdnInfo());
	     result.setQdnChk(cPQDNDTO.getQdnChk());
 	  	return result;
	 }
	
	public static List<CPQDN> toEntities(Collection<CPQDNDTO> cPQDNDTOs) {
		if (cPQDNDTOs == null) {
			return null;
		}
		
		List<CPQDN> results = new ArrayList<CPQDN>();
		for (CPQDNDTO each : cPQDNDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
