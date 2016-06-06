package org.seu.acetec.mes2Koala.application.impl;

import com.google.common.base.Strings;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.IncrementNumberApplication;
import org.seu.acetec.mes2Koala.application.ReelDiskApplication;
import org.seu.acetec.mes2Koala.application.WMSClientApplication;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.enums.StorageType;
import org.seu.acetec.mes2Koala.core.enums.TestTypeForWms;
import org.seu.acetec.mes2Koala.core.enums.TransferStorageState;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Named
@Transactional
public class ReelDiskApplicationImpl extends GenericMES2ApplicationImpl<ReelDisk> implements ReelDiskApplication {

    @Inject
    FTLotApplication ftLotApplication;

    @Inject
    IncrementNumberApplication incrementNumberApplication;
    @Inject
    WMSClientApplication wmsClientApplication;

    @Override
    public List<ReelDisk> findReelDisksByFTLotId(Long ftLotId) {
        return find("select o from ReelDisk o right join o.ftLot e where e.id=?", ftLotId);
    }

    @Override
    public ReelDisk findReelDiskByCombinedLotId(Long ftLotId) {
        return findOne("select o from ReelDisk o right join o.combinedLot e where e.id=?", ftLotId);
    }

    @Override
    public ReelDisk findReelDiskByReelcode(String Reelcode) {
        return findOne("select o from ReelDisk o where o.reelCode=?", Reelcode);
    }

    @Override
    public void createFailReelDisks(Long ftLotId, List<FTResult> failBins) {
        FTLot ftLot = ftLotApplication.get(ftLotId);
        CustomerFTLot customerLot = ftLot.getCustomerFTLot();
        FTInfo ftInfo = customerLot.getFtInfo();
        String dateCode = customerLot.getDateCode();
        
        String partNumber = ftInfo.getCustomerProductNumber();

        // 找到Finish站点的结果
        List<FTNode> ftNodes = ftLot.getFtProcess().getFtNodes();
        FTResult finishResult = null;
        for (FTNode ftNode : ftNodes) {
            if (ftNode instanceof FTFinishNode) {
                finishResult = ftNode.getResult();
                break;
            }
        }
        assert finishResult != null;
        int finishLoss = Strings.isNullOrEmpty(finishResult.getLoss()) ? 0 : Integer.parseInt(finishResult.getLoss());
        int finishOther = Strings.isNullOrEmpty(finishResult.getOther()) ? 0 : Integer.parseInt(finishResult.getOther());
        int finishBins[] = FTResult.getBins(finishResult);
        
        List<SBL> slist = new ArrayList<>();
        for(FTNode fnode:ftNodes){
        	if(fnode.getName().startsWith("Finish")){
        		slist = fnode.getSbls();
        	}
        }
        
        HashMap<String, Integer> hmap = new HashMap<>();
        for(SBL sbl:slist){
        	String nodeSite = sbl.getNodeName()+sbl.getSite();
        	if(hmap.containsKey(nodeSite)){
        		int i1 = hmap.get(nodeSite);
        		int i2 = Integer.parseInt(sbl.getType());
        		finishBins[i1-1] += finishBins[i2-1];
        		finishBins[i2-1] = 0;
        	}else{
        		hmap.put(nodeSite, Integer.parseInt(sbl.getType()));
        	}
        }
        
        for (FTResult result : failBins) {
            finishLoss -= Integer.parseInt(result.getLoss());
            finishOther -= Integer.parseInt(result.getOther());
            if (finishLoss < 0) {
                throw new RuntimeException("loss数据");
            }
            if (finishOther < 0) {
                throw new RuntimeException("other数据");
            }
            int[] bins = FTResult.getBins(result);
            for (int binIndex = 0; binIndex < 20; binIndex++) {
                if (finishBins[binIndex] != -1 && bins[binIndex] != -1) {
                    finishBins[binIndex] -= bins[binIndex];
                    if (finishBins[binIndex] < 0) {
                        throw new RuntimeException("bin数据");
                    }
                }
            }
        }
        List<ReelDisk> reelDisks = new ArrayList<>();
        for (FTResult failBin : failBins) {

            String reelCode = incrementNumberApplication.nextReelCode(ftInfo);
            ReelDisk reelDisk = new ReelDisk();
            reelDisk.setReelCode(reelCode);
            reelDisk.setPartNumber(partNumber);
            //reelDisk.setDateCode(new SimpleDateFormat("yyMMdd").format(new Date()));
            reelDisk.setDateCode(dateCode);
            reelDisk.setQuality(ReelDisk.REEL_DISK_QUALITY_FAIL);
            reelDisk.setIsFull("");
            reelDisk.setLogic(0);
            reelDisk.setFromReelCode("");
            reelDisk.setFtResult(failBin);
            reelDisk.setFtLot(ftLot);
            reelDisk.setPackagingTime(new Date());
            int quantity = 0;
            int[] failBinValues = FTResult.getBins(failBin);
            for (int binIndex = 0; binIndex < 20; binIndex++) {
                quantity += (finishBins[binIndex] != -1 && failBinValues[binIndex] != -1) ? failBinValues[binIndex] : 0;
            }
            reelDisk.setQuantity(quantity);
            reelDisks.add(reelDisk);
        }
        createAll(reelDisks);
    }

    @Override
    public void separate(Long reelDiskId, int separateQuantity) {
        ReelDisk originalReelDisk = get(reelDiskId);
        originalReelDisk.setQuantity(originalReelDisk.getQuantity() - separateQuantity);
        originalReelDisk.setIsFull("否");
        update(originalReelDisk);

        FTLot ftLot = originalReelDisk.getFtLot();
        FTInfo ftInfo = ftLot.getCustomerFTLot().getFtInfo();
        String targetReelCode = incrementNumberApplication.nextReelCode(ftInfo);

        ReelDisk targetReelDisk = new ReelDisk();
        BeanUtils.copyProperties(originalReelDisk, targetReelDisk);

        targetReelDisk.setId(null);
        targetReelDisk.setReelCode(targetReelCode);
        targetReelDisk.setQuantity(separateQuantity);
        targetReelDisk.setIsFull("否");
        targetReelDisk.setLogic(0);
        targetReelDisk.setFromReelCode("");
        create(targetReelDisk);
    }

    @Override
    public void separateFail(Long reelDiskId, List<FTResult> ftResults) {
        ReelDisk reelDisk = get(reelDiskId);
        FTResult ftResult = reelDisk.getFtResult();

        // 检查输入数据是否合法
        int loss = Integer.parseInt(ftResult.getLoss());
        int other = Integer.parseInt(ftResult.getOther());
        int[] binValues = FTResult.getBins(ftResult);
        for (FTResult ftResult1 : ftResults) {
            // loss
            int lossTemp = Integer.parseInt(ftResult1.getLoss() != null ? ftResult1.getLoss() : "0");
            if (lossTemp < 0)
                throw new RuntimeException("lossTemp < 0");
            loss -= lossTemp;
            if (loss < 0)
                throw new RuntimeException("loss < 0");
            // other
            int otherTemp = Integer.parseInt(ftResult1.getOther() != null ? ftResult1.getOther() : "0");
            if (otherTemp < 0)
                throw new RuntimeException("otherTemp < 0");
            other -= otherTemp;
            if (other < 0)
                throw new RuntimeException("other < 0");
            // bins
            int[] binValues1 = FTResult.getBins(ftResult1);
            for (int binIndex = 0; binIndex < 20; binIndex++) {
                int binValue1 = binValues1[binIndex];
                // 不可以分批的bin跳过
                if (binValues[binIndex] == -1) {
                    continue;
                }
                // 先检查
                if (binValue1 < 0)
                    throw new RuntimeException("binValue < 0");


                binValues[binIndex] -= binValue1;
                if (binValues[binIndex] < 0) {
                    throw new RuntimeException("binValues[binIndex]  < 0");
                }
            }
        }
        if (loss < 0) {  // >=0
            throw new RuntimeException("loss  < 0");
        }
        if (other < 0) { // >= 0
            throw new RuntimeException("other  < 0");
        }
        for (int binIndex = 0; binIndex < 20; binIndex++) {
            if (binValues[binIndex] < 0 && binValues[binIndex] != -1) {
                throw new RuntimeException("binValues[binIndex] < 0 && binValues[binIndex] != -1");
            }
        }

        // 创建所有子批
        String reelCode = reelDisk.getReelCode();
        FTLot ftLot = reelDisk.getFtLot();
        FTInfo ftInfo = ftLot.getCustomerFTLot().getFtInfo();
        String productNumber = ftInfo.getCustomerProductNumber();

        int remainQuantity = reelDisk.getQuantity();
        int remainOtherQuantity = Integer.parseInt(ftResult.getOther());
        int remainLossQuantity = Integer.parseInt(ftResult.getLoss());
        int[] binValues1 = FTResult.getBins(ftResult);
        //
        for (FTResult ftResultDTO : ftResults) {

            String reelCode1 = incrementNumberApplication.nextReelCode(ftInfo);

            // Quantity
            int lossQuantity = Integer.parseInt(ftResultDTO.getLoss() != null ? ftResultDTO.getLoss() : "0");
            remainLossQuantity -= loss;
            int otherQuantity = Integer.parseInt(ftResultDTO.getOther() != null ? ftResultDTO.getOther() : "0");
            remainOtherQuantity -= otherQuantity;
            int binsQuantity = 0;
            int[] binsTemp = FTResult.getBins(ftResultDTO);


            for (int binIndex = 0; binIndex < 20; binIndex++) {
                int subBin = binsTemp[binIndex];
                if (subBin != -1) {
                    binsQuantity += subBin;
                    // 更新原始批次的bin
                    binValues1[binIndex] -= subBin;
                }
            }
            FTResult.setBins(ftResult, binValues1);

            int quantity = lossQuantity + otherQuantity + binsQuantity;
            remainQuantity -= quantity;
            ReelDisk reelDiskSeparated = new ReelDisk();
            reelDiskSeparated.setLogic(0);
            reelDiskSeparated.setIsFull("否");
            reelDiskSeparated.setFtLot(ftLot);

            Date date = new Date();
            reelDiskSeparated.setPackagingTime(date);
            reelDiskSeparated.setDateCode(new SimpleDateFormat("yyMMdd").format(date));
            reelDiskSeparated.setFromReelCode("");
            reelDiskSeparated.setQuality(ReelDisk.REEL_DISK_QUALITY_FAIL);
            reelDiskSeparated.setQuantity(quantity);
            reelDiskSeparated.setReelCode(reelCode1);
            reelDiskSeparated.setPartNumber(productNumber);
            reelDiskSeparated.setFtResult(ftResultDTO);
            reelDiskSeparated.setFromReelCode(reelCode);
            create(reelDiskSeparated);
        }
        ftResult.setLoss(String.valueOf(remainLossQuantity)); // 更新原始批次的loss
        ftResult.setOther(String.valueOf(remainOtherQuantity)); // 更新原始批次的other
        reelDisk.setQuantity(remainQuantity); // 更新原始批次的quantity
        update(reelDisk);
    }

    /**
     * TODO
     *
     * @param reelDiskIds
     * @version 2016/3/29  YuxiangQue
     */
    @Override
    public void sample(Long[] reelDiskIds) {
        List<ReelDisk> reelDisks = new ArrayList<ReelDisk>();
        for (Long reelDiskId : reelDiskIds) {
            ReelDisk reelDisk = get(reelDiskId);
            reelDisk.setStatus("在库");
            reelDisks.add(reelDisk);
        }
        updateAll(reelDisks);
    }
 
   
    @Override
    public void confirmMfgIn(String ids) {
    	List<ReelDisk> reelDisks = new ArrayList<ReelDisk>();
		String[] storageIds=ids.split(",");
		for(String sid:storageIds)
		{
			ReelDisk reelDisk =findReelDiskByStorageId(sid);
			reelDisk.setStatus(TransferStorageState.出库已签核.toString());
			reelDisks.add(reelDisk);
		}
		updateAll(reelDisks);
    }

    private ReelDisk findReelDiskByStorageId(String sid) {
        return findOne("select o from ReelDisk o where o.wmsStorageId=?", sid);
    }

}
