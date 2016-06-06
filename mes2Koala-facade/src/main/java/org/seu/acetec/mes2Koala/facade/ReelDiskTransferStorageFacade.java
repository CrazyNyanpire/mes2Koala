package org.seu.acetec.mes2Koala.facade;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface ReelDiskTransferStorageFacade {

	public Page<ReelDiskTransferStorageDTO> pageQueryReelDisk(
			ReelDiskTransferStorageDTO reelDisk, int currentPage, int pageSize);

	public InvokeResult hold(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO);

	public InvokeResult unhold(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO);

	public InvokeResult changeStatus(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO);

	public InvokeResult specialSign(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO);

	public InvokeResult getReelDisk(Long id);

	public InvokeResult latPackage(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO);

	public InvokeResult reworkLot(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO);

	public void exportLatPrint(ServletOutputStream outputStream, String ids);

}
