package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.FTIQCNode;
import org.seu.acetec.mes2Koala.core.domain.FTNode;
import org.seu.acetec.mes2Koala.core.domain.ReelDisk;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskTransferStorageDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReelDiskTransferStorageAssembler {

	public static ReelDiskTransferStorageDTO toDTO(ReelDisk reelDisk) {
		if (reelDisk == null) {
			return null;
		}
		ReelDiskTransferStorageDTO result = new ReelDiskTransferStorageDTO();
		result.setId(reelDisk.getId());
		result.setVersion(reelDisk.getVersion());
		result.setCreateTimestamp(reelDisk.getCreateTimestamp());
		result.setLastModifyTimestamp(reelDisk.getLastModifyTimestamp());
		result.setCreateEmployNo(reelDisk.getCreateEmployNo());
		result.setLastModifyEmployNo(reelDisk.getLastModifyEmployNo());
		result.setLogic(reelDisk.getLogic());
		result.setReelCode(reelDisk.getReelCode());
		result.setQuantity(reelDisk.getQuantity());
		result.setPartNumber(reelDisk.getPartNumber());

		result.setPackagingTime(reelDisk.getPackagingTime());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		result.setPackagingTimeStr(reelDisk.getPackagingTime() == null ? "null"
				: formatter.format(reelDisk.getPackagingTime()));

		result.setDateCode(reelDisk.getDateCode());
		result.setReelTime(reelDisk.getReelTime());
		result.setWflot(reelDisk.getWflot());
		result.setTime(reelDisk.getTime());
		result.setPoNumber(reelDisk.getPoNumber());
		result.setPoNumberBox(reelDisk.getPoNumberBox());
		result.setIsFull(reelDisk.getIsFull());
		result.setFtLotDTO(FTLotAssembler.toDTO(reelDisk.getFtLot()));

		result.setStatus(reelDisk.getStatus());
		result.setStorageTime(reelDisk.getStorageTime());
		result.setSpecialSign(reelDisk.getSpecialSign());
		result.setSpecialSignRemark(reelDisk.getSpecialSignRemark());
		result.setApprove(reelDisk.getApprove());

		result.setCustomerProductNumber(reelDisk.getFtLot().getCustomerFTLot()
				.getCustomerProductNumber());
		result.setShipmentProductNumber(reelDisk.getFtLot().getCustomerFTLot()
				.getFtInfo().getShipmentProductNumber());
		result.setCustomerPPO(reelDisk.getFtLot().getCustomerFTLot()
				.getCustomerPPO());
		result.setCustomerLotNumber(reelDisk.getFtLot().getCustomerFTLot()
				.getCustomerLotNumber());
		result.setCustomerProductRevision(reelDisk.getFtLot().getCustomerFTLot()
				.getFtInfo().getCustomerProductRevision());

		result.setCombinedLotDTO(FTLotAssembler.toDTO(reelDisk.getCombinedLot()));
		result.setParentSeparationDTO(ReelDiskTransferStorageAssembler
				.toDTO(reelDisk.getParentSeparation()));
		// result.setParentsIntegrationDTOs(ReelDiskAssembler.toPageVos(reelDisk.getParentIntegrationIds()));
		// result.setParentIntegrationIds(reelDisk.getParentIntegrationIds());
		result.setFromReelCode(reelDisk.getFromReelCode());
result.setLatPackageNo(reelDisk.getLatPackageNo());
		if (reelDisk.getCombinedLot() != null)
			result.setCombinedLotNumber(reelDisk.getCombinedLot()
					.getInternalLotNumber());

		result.setQuality(reelDisk.getQuality());
		result.setFailInfo(reelDisk.getFailInfo());
		result.setHoldSign(reelDisk.getHoldSign());
		result.setHoldSignRemark(reelDisk.getHoldSignRemark());
		result.setUnHoldSignRemark(reelDisk.getUnHoldSignRemark());
		result.setHoldDate(reelDisk.getHoldDate());
		result.setUnHoldDate(reelDisk.getUnHoldDate());
		result.setInternalLotId(reelDisk.getFtLot().getId());
		result.setFtResultDTO(FTResultAssembler.toDTO(reelDisk.getFtResult()));

		result.setPackageType(reelDisk.getFtLot().getCustomerFTLot()
				.getFtInfo().getPackageType());
		FTNode fTNode = (FTNode) reelDisk.getFtLot().getFtProcess()
				.getFtNodes().get(0);
		if (fTNode != null) {
			FTIQCNode fTIQCNode = FTIQCNode
					.get(FTIQCNode.class, fTNode.getId());
			if (fTIQCNode != null) {
				result.setMarkNo(fTIQCNode.getMark());
			} else {
				result.setMarkNo("");
			}
		}
		result.setRemark(reelDisk.getRemark());
		return result;
	}

	public static List<ReelDiskTransferStorageDTO> toDTOs(
			Collection<ReelDisk> reelDisks) {
		if (reelDisks == null) {
			return null;
		}
		List<ReelDiskTransferStorageDTO> results = new ArrayList<ReelDiskTransferStorageDTO>();
		for (ReelDisk each : reelDisks) {
			results.add(toDTO(each));
		}
		return results;
	}

}
