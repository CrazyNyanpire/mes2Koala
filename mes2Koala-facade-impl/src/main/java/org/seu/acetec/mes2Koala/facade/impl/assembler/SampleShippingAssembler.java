package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class SampleShippingAssembler {

	public static SampleShippingDTO toDTO(SampleShipping sampleShipping) {
		if (sampleShipping == null) {
			return null;
		}
		SampleShippingDTO result = new SampleShippingDTO();
		result.setId(sampleShipping.getId());
		result.setVersion(sampleShipping.getVersion());
		result.setCreateTimestamp(sampleShipping.getCreateTimestamp());
		result.setLastModifyTimestamp(sampleShipping.getLastModifyTimestamp());
		result.setCreateEmployNo(sampleShipping.getCreateEmployNo());
		result.setLastModifyEmployNo(sampleShipping.getLastModifyEmployNo());
		result.setLogic(sampleShipping.getLogic());
		result.setQty(sampleShipping.getQty());
		result.setQtyTotal(sampleShipping.getQtyTotal());
		result.setQuality(sampleShipping.getQuality());
		result.setNote(sampleShipping.getNote());
		result.setFTLotDTO(FTLotAssembler.toDTO(sampleShipping.getFtLot()));
		result.setReelDiskDTO(ReelDiskAssembler.toDTO(sampleShipping
				.getReelDisk()));
		return result;
	}

	public static List<SampleShippingDTO> toDTOs(
			Collection<SampleShipping> sampleShippings) {
		if (sampleShippings == null) {
			return null;
		}
		List<SampleShippingDTO> results = new ArrayList<SampleShippingDTO>();
		for (SampleShipping each : sampleShippings) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static SampleShipping toEntity(SampleShippingDTO SampleShippingDTO) {
		if (SampleShippingDTO == null) {
			return null;
		}
		SampleShipping result = new SampleShipping();
		result.setId(SampleShippingDTO.getId());
		result.setVersion(SampleShippingDTO.getVersion());
		result.setCreateTimestamp(SampleShippingDTO.getCreateTimestamp());
		result.setLastModifyTimestamp(SampleShippingDTO
				.getLastModifyTimestamp());
		result.setCreateEmployNo(SampleShippingDTO.getCreateEmployNo());
		result.setLastModifyEmployNo(SampleShippingDTO.getLastModifyEmployNo());
		result.setLogic(SampleShippingDTO.getLogic());
		result.setQty(SampleShippingDTO.getQty());
		result.setQtyTotal(SampleShippingDTO.getQtyTotal());
		result.setQuality(SampleShippingDTO.getQuality());
		result.setNote(SampleShippingDTO.getNote());
		// result.setFtLot
		// (FTLotAssembler.toEntity(SampleShippingDTO.getFTLotDTO()));
		// result.setReelDisk(SampleShippingDTO.getReelDiskDTO());
		return result;
	}

	public static List<SampleShipping> toEntities(
			Collection<SampleShippingDTO> SampleShippingDTOs) {
		if (SampleShippingDTOs == null) {
			return null;
		}

		List<SampleShipping> results = new ArrayList<SampleShipping>();
		for (SampleShippingDTO each : SampleShippingDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
