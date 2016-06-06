package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.FTResult;
import org.seu.acetec.mes2Koala.core.domain.ReelDisk;

import java.util.List;
import java.util.Set;

public interface ReelDiskApplication extends GenericMES2Application<ReelDisk> {

    List<ReelDisk> findReelDisksByFTLotId(Long ftLotId);

    ReelDisk findReelDiskByCombinedLotId(Long ftLotId);

    void createFailReelDisks(Long ftLotId, List<FTResult> failBins);

    void separate(Long reelId, int separateQty);

    void separateFail(Long reelDiskId, List<FTResult> ftResults);

    /**
     * ReelDisk小样
     *
     * @param reelDiskIds
     */
    void sample(Long[] reelDiskIds);
	
	ReelDisk findReelDiskByReelcode(String Reelcode);

	void confirmMfgIn(String ids);
}

