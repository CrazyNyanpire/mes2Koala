package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class FTQDNAssembler {

	public static FTQDNDTO toDTO(FTQDN fTQDN) {
		if (fTQDN == null) {
			return null;
		}
		FTQDNDTO result = new FTQDNDTO();
		result.setId(fTQDN.getId());
		result.setVersion(fTQDN.getVersion());
		result.setCreateTimestamp(fTQDN.getCreateTimestamp());
		result.setLastModifyTimestamp(fTQDN.getLastModifyTimestamp());
		result.setCreateEmployNo(fTQDN.getCreateEmployNo());
		result.setLastModifyEmployNo(fTQDN.getLastModifyEmployNo());
		result.setLogic(fTQDN.getLogic());
		result.setCustomerNote(fTQDN.getCustomerNote());
		result.setCustomerSuggestion(fTQDN.getCustomerSuggestion());
		result.setMethod(fTQDN.getMethod());
		result.setNote(fTQDN.getNote());
		result.setQASuggestion(fTQDN.getQASuggestion());
		result.setReason(fTQDN.getReason());
		result.setSuggestion(fTQDN.getSuggestion());
		result.setAttachment(fTQDN.getAttachment());
		result.setQdnNo(fTQDN.getQdnNo());
		result.setFlowNow(fTQDN.getFlowNow());
		result.setCustomerDeal(fTQDN.getCustomerDeal());
		result.setCustomerDealNote(fTQDN.getCustomerDealNote());
		result.setDate(fTQDN.getDate());
		result.setDealDate(fTQDN.getDealDate());
		result.setDealPerson(fTQDN.getDealPerson());
		result.setFailSample(fTQDN.getFailSample());
		result.setFailTotal(fTQDN.getFailTotal());
		result.setInternalDeal(fTQDN.getInternalDeal());
		result.setInternalDealNote(fTQDN.getInternalDealNote());
		result.setNote(fTQDN.getNote());
		result.setQADeal(fTQDN.getQADeal());
		result.setStatus(fTQDN.getStatus());
		result.setTesterSys(fTQDN.getTesterSys());
		result.setToPerson(fTQDN.getToPerson());
		result.setType(fTQDN.getType());
		result.setWorkPerson(fTQDN.getWorkPerson());
		result.setFtLotDTO(FTLotAssembler.toDTO(fTQDN.getFtLot()));
		result.setIsFuture(fTQDN.getIsFuture());
		result.setSampleQty(fTQDN.getSampleQty());
		result.setTotalQty(fTQDN.getTotalQty());
		result.setQdnBin(fTQDN.getQdnBin());
		result.setCustomerName(fTQDN.getCustomerName());
		result.setQdnBinName(fTQDN.getQdnBinName());
		result.setFlow(fTQDN.getFlow());
		result.setToDepartment(fTQDN.getToDepartment());
		result.setLotNo(fTQDN.getFtLot()
				.getInternalLotNumber());
		return result;
	}

	public static List<FTQDNDTO> toDTOs(Collection<FTQDN> fTQDNs) {
		if (fTQDNs == null) {
			return null;
		}
		List<FTQDNDTO> results = new ArrayList<FTQDNDTO>();
		for (FTQDN each : fTQDNs) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static FTQDN toEntity(FTQDNDTO ftQDNDTO) {
		if (ftQDNDTO == null) {
			return null;
		}
		FTQDN result = new FTQDN();
		result.setId(ftQDNDTO.getId());
		result.setVersion(ftQDNDTO.getVersion());
		result.setCreateTimestamp(ftQDNDTO.getCreateTimestamp());
		result.setLastModifyTimestamp(ftQDNDTO.getLastModifyTimestamp());
		result.setCreateEmployNo(ftQDNDTO.getCreateEmployNo());
		result.setLastModifyEmployNo(ftQDNDTO.getLastModifyEmployNo());
		result.setLogic(ftQDNDTO.getLogic());
		result.setCustomerNote(ftQDNDTO.getCustomerNote());
		result.setCustomerSuggestion(ftQDNDTO.getCustomerSuggestion());
		result.setMethod(ftQDNDTO.getMethod());
		result.setNote(ftQDNDTO.getNote());
		result.setQASuggestion(ftQDNDTO.getQaSuggestion());
		result.setReason(ftQDNDTO.getReason());
		result.setSuggestion(ftQDNDTO.getSuggestion());
		result.setAttachment(ftQDNDTO.getAttachment());
		result.setQdnNo(ftQDNDTO.getQdnNo());
		result.setFlowNow(ftQDNDTO.getFlowNow());
		result.setCustomerDeal(ftQDNDTO.getCustomerDeal());
		result.setCustomerDealNote(ftQDNDTO.getCustomerDealNote());
		result.setDate(ftQDNDTO.getDate());
		result.setDealDate(ftQDNDTO.getDealDate());
		result.setDealPerson(ftQDNDTO.getDealPerson());
		result.setFailSample(ftQDNDTO.getFailSample());
		result.setFailTotal(ftQDNDTO.getFailTotal());
		result.setInternalDeal(ftQDNDTO.getInternalDeal());
		result.setInternalDealNote(ftQDNDTO.getInternalDealNote());
		result.setNote(ftQDNDTO.getNote());
		result.setQADeal(ftQDNDTO.getQADeal());
		result.setStatus(ftQDNDTO.getStatus());
		result.setTesterSys(ftQDNDTO.getTesterSys());
		result.setToPerson(ftQDNDTO.getToPerson());
		result.setType(ftQDNDTO.getType());
		result.setWorkPerson(ftQDNDTO.getWorkPerson());
		result.setFtLot(FTLotAssembler.toEntity(ftQDNDTO.getFtLotDTO()));
		result.setIsFuture(ftQDNDTO.getIsFuture());
		result.setSampleQty(ftQDNDTO.getSampleQty());
		result.setTotalQty(ftQDNDTO.getTotalQty());
		result.setQdnBin(ftQDNDTO.getQdnBin());
		result.setCustomerName(ftQDNDTO.getCustomerName());
		result.setQdnBinName(ftQDNDTO.getQdnBinName());
		result.setToDepartment(ftQDNDTO.getToDepartment());
		return result;
	}

	public static List<FTQDN> toEntities(Collection<FTQDNDTO> fTQDNDTOs) {
		if (fTQDNDTOs == null) {
			return null;
		}

		List<FTQDN> results = new ArrayList<FTQDN>();
		for (FTQDNDTO each : fTQDNDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
