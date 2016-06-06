package org.seu.acetec.mes2Koala.facade.impl;

import net.sf.json.JSONArray;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.*;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.enums.StorageType;
import org.seu.acetec.mes2Koala.core.enums.TestTypeForWms;
import org.seu.acetec.mes2Koala.core.enums.TransferStorageState;
import org.seu.acetec.mes2Koala.facade.ExcelFacade;
import org.seu.acetec.mes2Koala.facade.FTLotFacade;
import org.seu.acetec.mes2Koala.facade.FTProcessFacade;
import org.seu.acetec.mes2Koala.facade.ReelDiskFacade;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTResultAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FT_StatisticsAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.ReelDiskAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.ReelDiskConvertAssembler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.inject.Inject;
import javax.inject.Named;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Named
public class ReelDiskFacadeImpl implements ReelDiskFacade {

	@Inject
	ExcelFacade excelFacade;

	@Inject
	FTLotApplication ftLotApplication;

	@Inject
	FTInfoApplication ftInfoApplication;

	@Inject
	FTProcessApplication FTProcessApplication;

	@Inject
	FTNodeApplication ftNodeApplication;
	@Inject
	IncrementNumberApplication incrementNumberApplication;
	@Inject
	private ReelDiskApplication application;
	@Inject
	private FTLotFacade ftLotFacade;
	@Inject
	private FTProcessFacade ftProcessFacade;
	private QueryChannelService queryChannel;
	@Inject
	WMSClientApplication wmsClientApplication;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	public InvokeResult getReelDisk(Long id) {
		return InvokeResult
				.success(ReelDiskAssembler.toDTO(application.get(id)));
	}

	@Override
	public List<ReelDiskDTO> getReelDisks(Long[] ids) {
		List<ReelDisk> reelDisks = new ArrayList<>();
		for (Long id : ids) {
			reelDisks.add(application.get(id));
		}
		return ReelDiskAssembler.toDTOs(reelDisks);
	}
	
	@Override
	public List<ReelDiskConvertDTO> getConvertReelDisks(Long[] ids){
		List<ReelDisk> reelDisks = new ArrayList<>();
		for (Long id : ids) {
			reelDisks.add(application.get(id));
		}
		List<ReelDiskConvertDTO> rlistDTO = ReelDiskConvertAssembler.toDTOs(reelDisks);
		for(ReelDiskConvertDTO rDTO:rlistDTO){
			Long ftId = rDTO.getFtLotDTO().getId();
			FTProcess ftProcess = FTProcessApplication.findFTProcessByFTLotId(ftId);
			List<FTNode> nodelist = ftProcess.getFtNodes();
			FTStatistics statistics = new FTStatistics();
			for(FTNode fNode:nodelist){
				if(fNode.getName().startsWith("Finish")){
					
					statistics = fNode.getStatistics();
					
					statistics.setOther(rDTO.getFtResultDTO().getOther());
					statistics.setLoss(rDTO.getFtResultDTO().getLoss());
					statistics.setYield("0");
					statistics.setBackUp("0");
					statistics.setResultSum("0");
					statistics.setMarkF("0");
					statistics.setFail("0");
					statistics.setFail("0");
					
					statistics.setSite1Num("0");
					statistics.setSite2Num("0");
					statistics.setSite3Num("0");
					statistics.setSite4Num("0");
					statistics.setSite5Num("0");
					statistics.setSite6Num("0");
					statistics.setSite7Num("0");
					statistics.setSite8Num("0");
					statistics.setSite9Num("0");
					statistics.setSite10Num("0");
					statistics.setSite11Num("0");
					statistics.setSite12Num("0");
					statistics.setSite13Num("0");
					statistics.setSite14Num("0");
					statistics.setSite15Num("0");				
				}
			}
			List<String> binlist = rDTO.getFtResultDTO().getBin();
			for(int i=0;i<20;i++){
				String k = "" + (i + 1);
				if(Integer.parseInt(binlist.get(i))<0){
					
				}else if(statistics.getSite1Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite1Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite1Num(numStr);
				}else if(statistics.getSite2Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite2Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite2Num(numStr);
				}else if(statistics.getSite3Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite3Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite3Num(numStr);
				}else if(statistics.getSite4Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite4Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite4Num(numStr);
				}else if(statistics.getSite5Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite5Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite5Num(numStr);
				}else if(statistics.getSite6Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite6Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite6Num(numStr);
				}else if(statistics.getSite7Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite7Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite7Num(numStr);
				}else if(statistics.getSite8Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite8Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite8Num(numStr);
				}else if(statistics.getSite9Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite9Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite9Num(numStr);
				}else if(statistics.getSite10Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite10Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite10Num(numStr);
				}else if(statistics.getSite11Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite11Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite11Num(numStr);
				}else if(statistics.getSite12Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite12Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite12Num(numStr);
				}else if(statistics.getSite13Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite13Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite13Num(numStr);
				}else if(statistics.getSite14Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite14Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite14Num(numStr);
				}else if(statistics.getSite15Name().contains(k)){
					int num = Integer.parseInt(statistics.getSite15Num());
					num += Integer.parseInt(binlist.get(i));
					String numStr = "" + num;
					statistics.setSite15Num(numStr);
				}
				rDTO.setFtStatisticsDTO(FT_StatisticsAssembler.toDTO(statistics));
			}			
		}
		return rlistDTO;
	}

	public InvokeResult creatReelDisk(ReelDiskDTO reelDiskDTO) {
		application.create(ReelDiskAssembler.toEntity(reelDiskDTO));
		return InvokeResult.success();
	}

	public InvokeResult updateReelDisk(ReelDiskDTO reelDiskDTO) {
		/*
        ReelDisk reelDisk = application.get(reelDiskDTO.getId());
		reelDiskDTO.setVersion(reelDisk.getVersion());
		BeanUtils.copyProperties(reelDiskDTO, reelDisk);
		application.update(reelDisk);
		 */
		ReelDisk reelDisk = application.get(reelDiskDTO.getId());
		reelDiskDTO.setVersion(reelDisk.getVersion());
		ReelDisk reelDisk1 = ReelDiskAssembler.toEntity(reelDiskDTO);
		application.update(reelDisk1);
		return InvokeResult.success();
	}

	public InvokeResult removeReelDisk(Long id) {
		application.remove(application.get(id));
		return InvokeResult.success();
	}

	public InvokeResult removeReelDisks(Long[] ids) {
		Set<ReelDisk> reelDisks = new HashSet<ReelDisk>();
		for (Long id : ids) {
			reelDisks.add(application.get(id));
		}
		application.removeAll(reelDisks);
		return InvokeResult.success();
	}

	public List<ReelDiskDTO> findAllReelDisk() {
		return ReelDiskAssembler.toDTOs(application.findAll());
	}

	public Page<ReelDiskDTO> pageQueryReelDisk(ReelDiskDTO queryVo,
			int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _reelDisk from ReelDisk _reelDisk   where 1=1 ");
		if (queryVo.getCreateTimestamp() != null) {
			jpql.append(" and _reelDisk.createTimestamp between ? and ? ");
			conditionVals.add(queryVo.getCreateTimestamp());
			conditionVals.add(queryVo.getCreateTimestampEnd());
		}
		if (queryVo.getLastModifyTimestamp() != null) {
			jpql.append(" and _reelDisk.lastModifyTimestamp between ? and ? ");
			conditionVals.add(queryVo.getLastModifyTimestamp());
			conditionVals.add(queryVo.getLastModifyTimestampEnd());
		}
		if (queryVo.getCreateEmployNo() != null
				&& !"".equals(queryVo.getCreateEmployNo())) {
			jpql.append(" and _reelDisk.createEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCreateEmployNo()));
		}
		if (queryVo.getLastModifyEmployNo() != null
				&& !"".equals(queryVo.getLastModifyEmployNo())) {
			jpql.append(" and _reelDisk.lastModifyEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLastModifyEmployNo()));
		}
		if (queryVo.getReelCode() != null && !"".equals(queryVo.getReelCode())) {
			jpql.append(" and _reelDisk.reelCode like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReelCode()));
		}
		if (queryVo.getQuantity() != null) {
			jpql.append(" and _reelDisk.quantity=?");
			conditionVals.add(queryVo.getQuantity());
		}
		if (queryVo.getPartNumber() != null
				&& !"".equals(queryVo.getPartNumber())) {
			jpql.append(" and _reelDisk.partNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNumber()));
		}
		if (queryVo.getPackagingTime() != null) {
			jpql.append(" and _reelDisk.packagingTime between ? and ? ");
			conditionVals.add(queryVo.getPackagingTime());
			conditionVals.add(queryVo.getPackagingTimeEnd());
		}
		if (queryVo.getDateCode() != null && !"".equals(queryVo.getDateCode())) {
			jpql.append(" and _reelDisk.dateCode like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getDateCode()));
		}
		if (queryVo.getReelTime() != null && !"".equals(queryVo.getReelTime())) {
			jpql.append(" and _reelDisk.reelTime like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getReelTime()));
		}
		if (queryVo.getWflot() != null && !"".equals(queryVo.getWflot())) {
			jpql.append(" and _reelDisk.wflot like ?");
			conditionVals
			.add(MessageFormat.format("%{0}%", queryVo.getWflot()));
		}
		if (queryVo.getTime() != null && !"".equals(queryVo.getTime())) {
			jpql.append(" and _reelDisk.time like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTime()));
		}
		if (queryVo.getPoNumber() != null && !"".equals(queryVo.getPoNumber())) {
			jpql.append(" and _reelDisk.poNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPoNumber()));
		}
		if (queryVo.getPoNumberBox() != null
				&& !"".equals(queryVo.getPoNumberBox())) {
			jpql.append(" and _reelDisk.poNumberBox like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPoNumberBox()));
		}
		if (queryVo.getIsFull() != null && !"".equals(queryVo.getIsFull())) {
			jpql.append(" and _reelDisk.isFull like ?");
			conditionVals
			.add(MessageFormat.format("%{0}%", queryVo.getIsFull()));
		}
		if (queryVo.getQuality() != null && !"".equals(queryVo.getQuality())) {
			jpql.append(" and _reelDisk.quality like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getQuality()));
		}
		if (queryVo.getLogic() != null) {
			jpql.append(" and _reelDisk.logic=?");
			conditionVals.add(queryVo.getLogic());
		}
		if (queryVo.getFtLotDTO() != null) {
            if (queryVo.getFtLotDTO().getInternalLotNumber() != null && !"".equals(queryVo.getFtLotDTO().getInternalLotNumber())) {
                jpql.append(" and _reelDisk.ftLot.internalLotNumber like ?");
                conditionVals.add(MessageFormat.format("%{0}%",queryVo.getFtLotDTO().getInternalLotNumber()));
            }
        }
		Page<ReelDisk> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();

		return new Page<ReelDiskDTO>(pages.getStart(), pages.getResultCount(),
				pageSize, ReelDiskAssembler.toDTOs(pages.getData()));
	}

	@Override
	public List<ReelDiskDTO> findReelDiskByFTLotId(Long ftLotId) {
		return ReelDiskAssembler.toDTOs(application
				.findReelDisksByFTLotId(ftLotId));
	}

	@Override
	public InvokeResult findReelDiskByIntegratedLotId(Long ftLotId) {
		ReelDisk reelDisk = application.findReelDiskByCombinedLotId(ftLotId);
		return reelDisk != null ? InvokeResult.success(ReelDiskAssembler.toDTO(reelDisk)) : InvokeResult.failure("");
	}

	private ReelDiskDTO findReelCodeByCombinedLotId(Long ftLotId) {
		return ReelDiskAssembler.toDTO(application.findReelDiskByCombinedLotId(ftLotId));
	}

	/**
	 * 全
	 *
	 * @param ftLotId
	 * @param reelNumber
	 * @param binType
	 * @return
	 */
	@Transactional
	@Override
	public List<ReelDiskDTO> previewReelDisk(Long ftLotId, int reelNumber,
			String binType) {
		List<ReelDiskDTO> result = new ArrayList<>();

		// 如果已经生成PASS品ReelCode，则不返回值
		List<ReelDisk> rList = application.findReelDisksByFTLotId(ftLotId);
		String binStr = "" + binType;
		if (rList != null && rList.size() > 0 && rList.get(0) != null) {
			for (ReelDisk each : rList) {
				if (each.getQuality().equals(ReelDisk.REEL_DISK_QUALITY_PASS)
						&& each.getFailInfo() != null
						&& each.getFailInfo().equals(binStr)) {
					return result;
				}
			}
		}

		// 判断是否导入ReelCode
		List<ReelDiskDTO> resultTemp = getImportReelcode(ftLotId);
		if (resultTemp != null && resultTemp.size() > 0
				&& resultTemp.get(0) != null) {
			return resultTemp;
		}

		FTLot internalLotDTO = ftLotApplication.get(ftLotId);
		if (internalLotDTO == null) {
			return result;
		}
		FTProcessDTO FTProcessDTO = (FTProcessDTO) ftProcessFacade
				.findFTProcessByFTLotId(internalLotDTO.getId()).getData();
		if (FTProcessDTO == null) {
			return result;
		}
		// String sblStr = FTProcessDTO.getSblTemplate();
		List<FTNodeDTO> fDTOList = FTProcessDTO.getFtNodeDTOs();
		FTNodeDTO ftNodeDTO = new FTNodeDTO();
		for (FTNodeDTO aFDTOList : fDTOList) {
			if (aFDTOList.getFtState() == 1 || aFDTOList.getFtState() == 2) {
				ftNodeDTO = aFDTOList;
				break;
			}
		}
		//List<String> bList = ftNodeDTO.getFtFinishDTO().getFtResultDTO().getBin();
		FT_StatisticsDTO fDTO = ftNodeDTO.getFtFinishDTO().getFtStatisticsDTO();
		
		int qty = 0;
		// String parentIntegration = "";
		String fromReelCode = "";
		ReelDiskDTO reelDTO = findReelCodeByCombinedLotId(ftLotId);
		if (reelDTO != null) {
			qty = reelDTO.getQuantity();
			// parentIntegration = reelDTO.getId().toString();
			fromReelCode = reelDTO.getReelCode() + "," + qty;
		}

		//qty += Integer.parseInt(bList.get(binType - 1));
		if(fDTO.getSite1Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite1Num());
		}else if(fDTO.getSite2Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite2Num());
		}else if(fDTO.getSite3Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite3Num());
		}else if(fDTO.getSite4Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite4Num());
		}else if(fDTO.getSite5Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite5Num());
		}else if(fDTO.getSite6Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite6Num());
		}else if(fDTO.getSite7Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite7Num());
		}else if(fDTO.getSite8Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite8Num());
		}else if(fDTO.getSite9Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite9Num());
		}else if(fDTO.getSite10Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite10Num());
		}else if(fDTO.getSite11Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite11Num());
		}else if(fDTO.getSite12Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite12Num());
		}else if(fDTO.getSite13Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite13Num());
		}else if(fDTO.getSite14Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite14Num());
		}else if(fDTO.getSite15Name().equals(binType)){
			qty += Integer.parseInt(fDTO.getSite15Num());
		}
				
		FTInfo ftInfoDTO = internalLotDTO.getCustomerFTLot().getFtInfo();

		Date packagingTime = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		//String dateCode = df.format(packagingTime);
		String dateCode = internalLotDTO.getCustomerFTLot().getDateCode();
		String partNumber = ftInfoDTO.getCustomerProductNumber();

		int flag = 0;

		while (qty > 0) {
			ReelDiskDTO reelDiskDTO = new ReelDiskDTO();
			FTLotDTO ftLotDTO = new FTLotDTO();
			ftLotDTO.setId(ftLotId);

			String reelCode = incrementNumberApplication
					.nextReelCode(ftInfoDTO);
			reelDiskDTO.setFailInfo(binStr);
			reelDiskDTO.setReelCode(reelCode);
			reelDiskDTO.setFtLotDTO(ftLotDTO);
			reelDiskDTO.setPartNumber(partNumber);
			reelDiskDTO.setPackagingTime(packagingTime);
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			reelDiskDTO.setPackagingTimeStr(formatter.format(reelDiskDTO
					.getPackagingTime()));
			reelDiskDTO.setDateCode(dateCode);
			reelDiskDTO.setQuality(ReelDisk.REEL_DISK_QUALITY_PASS);
			reelDiskDTO.setLogic(0);
			// reelDiskDTO.setParentIntegrationIds(parentIntegration);
			if (flag == 0) {
				reelDiskDTO.setParentSeparationDTO(reelDTO);
			}
			flag = 1;
			// parentIntegration = "";
			reelDiskDTO.setFromReelCode(fromReelCode);
			fromReelCode = "";

			if (qty >= reelNumber) {
				reelDiskDTO.setQuantity(reelNumber);
				reelDiskDTO.setIsFull("是");
			} else {
				reelDiskDTO.setQuantity(qty);
				reelDiskDTO.setIsFull("否");
			}
			result.add(reelDiskDTO);
			qty -= reelNumber;
		}
		return result;
	}

	public List<ReelDiskDTO> getImportReelcode(Long ftLotId) {
		FTProcess process = FTProcessApplication
				.findFTProcessByFTLotId(ftLotId);
		List<FTNode> ftNodes = process.getFtNodes();
		Collections.sort(ftNodes);
		for (int index = 0; index < ftNodes.size(); index++) {
			FTNode ftNode = ftNodes.get(index);
			if (ftNode.getName().startsWith("IQC")) {
				if (ftNode instanceof FTIQCNode) {
					FTIQCNode ftIQCNode = (FTIQCNode) ftNode;
					String importReelCode = ftIQCNode.getReelCode();
					return excelFacade.importReelCode(ftLotId, importReelCode);
				}
			}
		}
		return null;
	}

	/**
	 * @param ftLotId
	 * @param failBinDTOs
	 * @return
	 */
	@Transactional
	@Override
	public InvokeResult createFailReelDisks(Long ftLotId,
			List<FT_ResultDTO> failBinDTOs) {
		try {
			createLatReelDisks(ftLotId);
			List<FTResult> failBins = FTResultAssembler.toEntities(failBinDTOs);
			application.createFailReelDisks(ftLotId, failBins);
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			return InvokeResult.failure(ex.getMessage());
		}
	}

	/**
	 * LAT
	 *
	 */	
	public void createLatReelDisks(Long ftLotId) {		

		// 如果已经生成ReelCode
		List<ReelDisk> rList = application.findReelDisksByFTLotId(ftLotId);
		if (rList != null && rList.size() > 0 && rList.get(0) != null) {
			return;
		}

		FTLot internalLotDTO = ftLotApplication.get(ftLotId);
		if (internalLotDTO == null) {
			return;
		}
		FTProcessDTO FTProcessDTO = (FTProcessDTO) ftProcessFacade
				.findFTProcessByFTLotId(internalLotDTO.getId()).getData();
		if (FTProcessDTO == null) {
			return;
		}

		List<FTNodeDTO> fDTOList = FTProcessDTO.getFtNodeDTOs();
		FTNodeDTO ftNodeDTO = new FTNodeDTO();
		for (FTNodeDTO aFDTOList : fDTOList) {
			if (aFDTOList.getFtState() == 1 || aFDTOList.getFtState() == 2) {
				ftNodeDTO = aFDTOList;
				break;
			}
		}
		//List<String> bList = ftNodeDTO.getFtFinishDTO().getFtResultDTO().getBin();
		FT_StatisticsDTO fDTO = ftNodeDTO.getFtFinishDTO().getFtStatisticsDTO();
		
		int qty = 0;		
		qty = Integer.parseInt(fDTO.getLat());		
		FTInfo ftInfoDTO = internalLotDTO.getCustomerFTLot().getFtInfo();

		String dateCode = internalLotDTO.getCustomerFTLot().getDateCode();
		String partNumber = ftInfoDTO.getCustomerProductNumber();

		Date packagingTime = new Date();
		ReelDiskDTO reelDiskDTO = new ReelDiskDTO();
		FTLotDTO ftLotDTO = new FTLotDTO();
		ftLotDTO.setId(ftLotId);

		reelDiskDTO.setFailInfo("");
		reelDiskDTO.setReelCode("");
		reelDiskDTO.setFtLotDTO(ftLotDTO);
		reelDiskDTO.setPartNumber(partNumber);
		reelDiskDTO.setPackagingTime(packagingTime);
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		reelDiskDTO.setPackagingTimeStr(formatter.format(reelDiskDTO
				.getPackagingTime()));
		reelDiskDTO.setDateCode(dateCode);
		reelDiskDTO.setQuality(ReelDisk.REEL_DISK_QUALITY_LAT);
		reelDiskDTO.setLogic(0);
		reelDiskDTO.setQuantity(qty);
		reelDiskDTO.setIsFull("");
			
		creatReelDisk(reelDiskDTO);		
		return;
	}	
	
	/**
	 * 小样
	 *
	 * @param reelDiskIds
	 * @return
	 */
	@Transactional
	@Override
	public InvokeResult sampleReelDisks(Long[] reelDiskIds) {
		try {
			application.sample(reelDiskIds);
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			return InvokeResult.failure("小样失败");
		}
	}

	@Override
	public InvokeResult saveReelDisk(List<ReelDiskDTO> list) {
		if(list!=null && list.size() > 0 && list.get(0)!=null){		
			try{
				createLatReelDisks(list.get(0).getFtLotDTO().getId());	
			}catch(Exception e){}
		}
		for (int i = 0; i < list.size(); i++) {
			creatReelDisk(list.get(i));
		}
		return InvokeResult.success();
	}

	@Transactional
	@Override
	public InvokeResult separateReelDisk(Long reelId, int separateQty) {
		try {
			application.separate(reelId, separateQty);
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			return InvokeResult.failure(ex.getMessage());
		}
	}

	/**
	 * @param reelDiskId   源 Fail ReelDisk 的Id
	 * @param ftResultDTOs 生成目标的集合，存储loss, other以及bin
	 * @return
	 */
	@Transactional
	@Override
	public InvokeResult separateFailReelDisk(Long reelDiskId,
			List<FT_ResultDTO> ftResultDTOs) {
		try {
			List<FTResult> ftResults = FTResultAssembler
					.toEntities(ftResultDTOs);
			application.separateFail(reelDiskId, ftResults);
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
			return InvokeResult.failure(ex.getMessage());
		}
	}

	@Override
	public InvokeResult integrateReelDisk(Long reelId, Long toReelId) {
		ReelDiskDTO reelDiskDTO = (ReelDiskDTO) getReelDisk(reelId).getData();
		ReelDiskDTO toReelDiskDTO = (ReelDiskDTO) getReelDisk(toReelId)
				.getData();

		if (Objects.equals(reelDiskDTO.getFtLotDTO().getId(), toReelDiskDTO
				.getFtLotDTO().getId()))
			return InvokeResult.failure("同一批ReelCode不允许合盘");

		reelDiskDTO.setCombinedLotDTO(toReelDiskDTO.getFtLotDTO());
		reelDiskDTO.setLogic(1);
		updateReelDisk(reelDiskDTO);

		int qty = reelDiskDTO.getQuantity();
		int qty1 = toReelDiskDTO.getQuantity();
		String reelCode = toReelDiskDTO.getReelCode();
		String partNumber = reelDiskDTO.getPartNumber();
		String partNumber1 = toReelDiskDTO.getPartNumber();
		String dateCode = reelDiskDTO.getDateCode();
		String dateCode1 = toReelDiskDTO.getDateCode();

		if (!partNumber.equals(partNumber1))
			partNumber1 += "/" + partNumber;
		if (!dateCode.equals(dateCode1))
			dateCode1 += "/" + dateCode;

		FTLot iDTO = ftLotApplication.get(toReelDiskDTO.getFtLotDTO().getId());
		FTInfo fDTO = iDTO.getCustomerFTLot().getFtInfo();
		int reelQty = Integer.parseInt(fDTO.getReelQty());

		toReelDiskDTO.setPartNumber(partNumber1);
		toReelDiskDTO.setDateCode(dateCode1);
		toReelDiskDTO.setPackagingTime(new Date());
		// toReelDiskDTO.setParentSeparationDTO(reelDiskDTO);
		String fromReel = toReelDiskDTO.getFromReelCode();
		if (fromReel == null)
			fromReel = "";
		if (fromReel.length() > 0)
			fromReel += ";";
		fromReel += reelDiskDTO.getReelCode() + "," + reelDiskDTO.getQuantity();
		// toReelDiskDTO.setFromReelCode(reelDiskDTO.getReelCode() + "," +
		// reelDiskDTO.getQuantity());
		toReelDiskDTO.setFromReelCode(fromReel);
		String parentIntegrations = toReelDiskDTO.getParentIntegrationIds();
		if (parentIntegrations == null)
			parentIntegrations = "";
		if (parentIntegrations.length() > 0)
			parentIntegrations += ",";
		parentIntegrations += ("" + reelId);
		toReelDiskDTO.setParentIntegrationIds(parentIntegrations);

		toReelDiskDTO.setParentSeparationDTO(reelDiskDTO);
		toReelDiskDTO.setLogic(0);

		if (qty + qty1 <= reelQty) {
			toReelDiskDTO.setQuantity(qty + qty1);
			toReelDiskDTO.setIsFull(qty + qty1 < reelQty ? "否" : "是");
			updateReelDisk(toReelDiskDTO);
		} else {
			toReelDiskDTO.setQuantity(reelQty);
			toReelDiskDTO.setIsFull("是");
			updateReelDisk(toReelDiskDTO);
			qty1 = qty + qty1 - reelQty;
			toReelDiskDTO.setId(null);
			toReelDiskDTO.setVersion(0);
			toReelDiskDTO.setQuantity(qty1);
			toReelDiskDTO.setIsFull("否");
			toReelDiskDTO.setFromReelCode(toReelDiskDTO.getReelCode() + ","
					+ qty1);
			toReelDiskDTO.setParentSeparationDTO(null);
			String reelCode1 = incrementNumberApplication.nextReelCode(fDTO);
			toReelDiskDTO.setReelCode(reelCode1);
			creatReelDisk(toReelDiskDTO);
		}

		/*
		 *
		 * String[] value = reelIds.split(",");
		 * 
		 * ReelDiskDTO reelDiskDTO = (ReelDiskDTO)
		 * getReelDisk(Long.parseLong(value[0])).getData(); String reelCode =
		 * reelDiskDTO.getReelCode(); reelDiskDTO.setParentSeparationDTO(null);
		 * 
		 * InternalLotDTO iDTO =
		 * ftLotFacade.findInternalLotByFTLot(reelDiskDTO.getFtLotDTO
		 * ().getId()); Long pid =
		 * iDTO.getCustomerLotDTO().getInternalProductDTO().getId(); FTInfoDTO
		 * fDTO = (FTInfoDTO) ftInfoFacade.getFTInfo(pid).getData();
		 * 
		 * int reelQty = Integer.parseInt(fDTO.getReelQty());
		 * 
		 * String type = "reelCodeNum" +
		 * iDTO.getCustomerLotDTO().getInternalProductDTO
		 * ().getCustomerProductNumber();
		 * 
		 * int qty = 0; List<ReelDiskDTO> rList = new ArrayList<ReelDiskDTO>();
		 * String rStr = ""; for (int i = 0; i < value.length; i ++) { Long rid
		 * = Long.parseLong(value[i]); ReelDiskDTO rDTO = (ReelDiskDTO)
		 * getReelDisk(rid).getData(); qty += rDTO.getQuantity();
		 * 
		 * ReelDiskDTO rrDTO = new ReelDiskDTO();
		 * rrDTO.setId(rid);rrDTO.setQuantity(0); rList.add(rrDTO);
		 * 
		 * rStr += rid.toString() + ","; rDTO.setLogic(1); updateReelDisk(rDTO);
		 * if(qty>=reelQty){ reelDiskDTO.setId(null); String incrementNumber =
		 * getOrCreateReelCodeIncrementNumber(type); String reelCode1 =
		 * reelCode.substring(0, reelCode.length()-3)+incrementNumber;
		 * reelDiskDTO.setReelCode(reelCode1); reelDiskDTO.setQuantity(reelQty);
		 * reelDiskDTO.setIsFull("是"); reelDiskDTO.setLogic(null);
		 * //reelDiskDTO.setParentsIntegrationDTOs(rList); rStr =
		 * rStr.substring(0, rStr.length()-1);
		 * reelDiskDTO.setParentIntegrationIds(rStr); qty -= reelQty; //rList =
		 * new ArrayList<ReelDiskDTO>(); rStr = ""; if(qty>0){ //
		 * rList.add(rrDTO); rStr += rid.toString() + ","; }
		 * creatReelDisk(reelDiskDTO); } } if(qty>0){ reelDiskDTO.setId(null);
		 * String incrementNumber = getOrCreateReelCodeIncrementNumber(type);
		 * String reelCode1 = reelCode.substring(0,
		 * reelCode.length()-3)+incrementNumber;
		 * reelDiskDTO.setReelCode(reelCode1); reelDiskDTO.setQuantity(qty);
		 * reelDiskDTO.setIsFull("否"); reelDiskDTO.setLogic(null);
		 * //reelDiskDTO.setParentsIntegrationDTOs(rList); rStr =
		 * rStr.substring(0, rStr.length()-1);
		 * reelDiskDTO.setParentIntegrationIds(rStr);
		 * creatReelDisk(reelDiskDTO); }
		 */
		return InvokeResult.success();
	}

	@Override
	public InvokeResult gotoLot(Long reelId, Long lotId) {
		ReelDiskDTO rDTO = (ReelDiskDTO) getReelDisk(reelId).getData();
		FTLotDTO iDTO = (FTLotDTO) ftLotFacade.getFTLot(lotId).getData();
		FTLotDTO fDTO = new FTLotDTO();
		fDTO.setId(iDTO.getId());
		rDTO.setCombinedLotDTO(fDTO);
		rDTO.setLogic(1);
		return updateReelDisk(rDTO);
	}

	/**
	 * 获取ReelDisk的配置信息
	 *
	 * @param ftLotId
	 * @return
	 */
	@Override
	public InvokeResult findReelDiskSettingByFTLotId(Long ftLotId) {
		final FTInfo ftInfo = ftInfoApplication.findByFTLotId(ftLotId);
		String fixCode = ftInfo.getReelFixCode();
		String reelQty = ftInfo.getReelQty();

		// 这里要获取Finish站点的SBL信息
		Map<String, Object> data = new HashMap<>();
		data.put("fixCode", fixCode);
		data.put("reelQty", reelQty);
		return InvokeResult.success(data);
	}
	 /**
     * 中转库出库
     * 1、修改在中转库的状态为出库未签核状态；
     * 2、调用WMS接口，生成入库申请
     * 3、WMS审批之后在中转库的状态为出库已签核
     *
     * @param 中转库每个盘的id
     * @author Eva
     * @version 2016-4-20
     * @return  
     */
	@Override
	public InvokeResult mfgOut(Long[] idArr) {
	 
		try{
			List<ReelDisk> reelDisks = new ArrayList<ReelDisk>();
			List<FTStorage> ftstorages=new ArrayList<FTStorage>();
			StringBuilder sb=new StringBuilder();
			for (Long reelDiskId : idArr) {
				ReelDisk reelDisk = application.get(reelDiskId);
				if(reelDisk.getStatus().equals(TransferStorageState.在库.toString()))
				{
					FTStorage ftstorage=ConvertToFTStorage(reelDisk);
					if(ftstorage!=null){
						ftstorages.add(ftstorage);
						reelDisk.setStatus(TransferStorageState.出库未签核.toString());
						reelDisks.add(reelDisk); }
					else
					{
						sb.append(reelDisk.getReelCode()+",");
					}
				} 
			}
			if(reelDisks.size()>0)
			{
				application.updateAll(reelDisks);
				JSONArray obj=JSONArray.fromObject(ftstorages); 
				wmsClientApplication.mfgInWMS(TestTypeForWms.getStringValue(TestTypeForWms.FT), StorageType.getValue(StorageType.FINISH_STORAGE), obj.toString());			 
			}
			if(sb.length()>0)
			{
				sb.deleteCharAt(sb.length()-1);
				sb.append(" 合批信息错误！");
			}
			return  InvokeResult.success("共出库成功"+reelDisks.size()+"盘,不在库的产品无法出库！"+sb.toString());
		} catch (RuntimeException ex) {
			ex.printStackTrace(); 
			return InvokeResult.failure(ex.getMessage());
		} 

	}
	private FTStorage ConvertToFTStorage(ReelDisk reelDisk) {
		FTStorage ftstorage=new FTStorage();
		ftstorage.setID(UUID.randomUUID().toString());
		ftstorage.setACETEC_LOT(reelDisk.getFtLot().getInternalLotNumber()); 
		ftstorage.setCUS_LOT(reelDisk.getFtLot().getCustomerFTLot().getCustomerLotNumber());
		ftstorage.setCUS_PPO(reelDisk.getFtLot().getCustomerFTLot().getCustomerPPO());
		ftstorage.setQUALITY(reelDisk.getQuality());
		ftstorage.setQUANTITY(reelDisk.getQuantity());
		ftstorage.setREELCODE(reelDisk.getReelCode());
		ftstorage.setTEST_ID(reelDisk.getFtLot().getWmsTestId());
		ftstorage.setNUMBER(reelDisk.getReelCode());
		ftstorage.setSPECIAL_CONTROL(reelDisk.getSpecialSign());
		ftstorage.setDATECODE(reelDisk.getDateCode()); 
		ftstorage.setIS_MERGE("01");
		if(reelDisk.getParentIntegrationIds()!=null&&reelDisk.getParentIntegrationIds().length()>0){
			ftstorage.setIS_MERGE("02");
			List<FTStorage> ftstorageChildList=new ArrayList<FTStorage> ();
			String[] mergerReeldiskIds=reelDisk.getParentIntegrationIds().split(",");
			int childQty=0;
			for(String mergerId:mergerReeldiskIds)
			{
				ReelDisk reelDiskMerger = application.get(Long.parseLong(mergerId));
				FTStorage ftstorageChild=new FTStorage();
				ftstorageChild.setID(UUID.randomUUID().toString());
				ftstorageChild.setACETEC_LOT(reelDiskMerger.getFtLot().getInternalLotNumber()); 
				ftstorageChild.setCUS_LOT(reelDiskMerger.getFtLot().getCustomerFTLot().getCustomerLotNumber());
				ftstorageChild.setCUS_PPO(reelDiskMerger.getFtLot().getCustomerFTLot().getCustomerPPO());
				ftstorageChild.setQUALITY(reelDiskMerger.getQuality());
				ftstorageChild.setQUANTITY(reelDiskMerger.getQuantity());
				ftstorageChild.setREELCODE(ftstorage.getREELCODE());
				ftstorageChild.setTEST_ID(reelDiskMerger.getFtLot().getWmsTestId());
				ftstorageChild.setNUMBER(reelDiskMerger.getReelCode());
				ftstorageChild.setSPECIAL_CONTROL(reelDiskMerger.getSpecialSign());
				ftstorageChild.setDATECODE(reelDiskMerger.getDateCode()); 
				reelDiskMerger.setWmsStorageId(ftstorageChild.getID());
				ftstorageChildList.add(ftstorageChild);
				childQty+=ftstorageChild.getQUANTITY();
			}
			int cqty=ftstorage.getQUANTITY()-childQty;
			if(cqty<=0)
			{
				return null;
			}
			FTStorage ftstorageChild=new FTStorage();
			ftstorageChild.setID(UUID.randomUUID().toString());
			ftstorageChild.setACETEC_LOT(ftstorage.getACETEC_LOT()); 
			ftstorageChild.setCUS_LOT(ftstorage.getCUS_LOT());
			ftstorageChild.setCUS_PPO(ftstorage.getCUS_PPO());
			ftstorageChild.setQUALITY(ftstorage.getQUALITY());
			ftstorageChild.setQUANTITY(ftstorage.getQUANTITY()-childQty);
			ftstorageChild.setREELCODE(ftstorage.getREELCODE());
			ftstorageChild.setTEST_ID(ftstorage.getTEST_ID());
			ftstorageChild.setNUMBER(ftstorage.getNUMBER());
			ftstorageChild.setSPECIAL_CONTROL(ftstorage.getSPECIAL_CONTROL());
			ftstorageChild.setDATECODE(ftstorage.getDATECODE());  
			ftstorageChildList.add(ftstorageChild);

			ftstorage.setCHILDES(ftstorageChildList);
		} 

		reelDisk.setWmsStorageId(ftstorage.getID());
		return ftstorage;
	}
	@Override
	public InvokeResult confirmMfgIn(String ids) {
		try {
			application.confirmMfgIn(ids);
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace(); 
			return InvokeResult.failure(ex.getMessage());
		}
	}

	@Override
	public InvokeResult backtoLine(String reelDiskJson) {
		try {
			List<ReelDisk> reelDiskList=new ArrayList<ReelDisk>();
			List<ReelDisk> reelDisks = ReelDiskAssembler.wmsJsonToEntities(reelDiskJson);
			for (ReelDisk reelDisk : reelDisks) {
				int backQty=reelDisk.getQuantity();
				reelDisk=application.findOne("select _reelDisk from ReelDisk _reelDisk where _reelDisk.reelCode='"+reelDisk.getReelCode()+"'");
				reelDisk.setQuantity(backQty);
				reelDisk.setStatus(TransferStorageState.在库.toString());
				reelDiskList.add(reelDisk);
			}
			application.updateAll(reelDiskList);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return InvokeResult.failure(ex.getMessage());
		}
		return InvokeResult.success();
	}

}
