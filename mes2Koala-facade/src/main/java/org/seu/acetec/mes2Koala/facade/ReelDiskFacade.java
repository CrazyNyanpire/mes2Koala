package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.FT_ResultDTO;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskConvertDTO;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskDTO;

import java.util.List;

public interface ReelDiskFacade {

    InvokeResult getReelDisk(Long id);

    List<ReelDiskDTO> getReelDisks(Long[] ids);
    
    List<ReelDiskConvertDTO> getConvertReelDisks(Long[] ids);

    InvokeResult creatReelDisk(ReelDiskDTO reelDisk);

    InvokeResult updateReelDisk(ReelDiskDTO reelDisk);

    InvokeResult removeReelDisk(Long id);

    InvokeResult removeReelDisks(Long[] ids);

    List<ReelDiskDTO> findAllReelDisk();

    Page<ReelDiskDTO> pageQueryReelDisk(ReelDiskDTO reelDisk, int currentPage, int pageSize);

    List<ReelDiskDTO> findReelDiskByFTLotId(Long ftLotId);

    InvokeResult findReelDiskByIntegratedLotId(Long ftLotId);

    List<ReelDiskDTO> previewReelDisk(Long ftLotId, int reelNumber, String binType);

    InvokeResult saveReelDisk(List<ReelDiskDTO> list);

    InvokeResult separateReelDisk(Long reelId, int separateQty);

    InvokeResult integrateReelDisk(Long reelId, Long toReelId);

    InvokeResult gotoLot(Long reelId, Long lotId);

    InvokeResult findReelDiskSettingByFTLotId(Long ftLotId);

    InvokeResult separateFailReelDisk(Long reelDiskId, List<FT_ResultDTO> list);

    InvokeResult createFailReelDisks(Long ftLotId, List<FT_ResultDTO> failBins);

    InvokeResult sampleReelDisks(Long[] reelDiskIds);

	InvokeResult mfgOut(Long[] idArr);

	InvokeResult confirmMfgIn(String ids);

	InvokeResult backtoLine(String reelDiskJson);

}


