package org.seu.acetec.mes2Koala.facade.impl;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.ReelDiskApplication;
import org.seu.acetec.mes2Koala.application.impl.FTProcessApplicationImpl;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.common.Mes2DateUtils;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.ReelDisk;
import org.seu.acetec.mes2Koala.facade.CustomerFTLotFacade;
import org.seu.acetec.mes2Koala.facade.FTLotFacade;
import org.seu.acetec.mes2Koala.facade.IncrementNumberFacade;
import org.seu.acetec.mes2Koala.facade.ReelDiskTransferStorageFacade;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskTransferStorageDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTLotAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.ReelDiskTransferStorageAssembler;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Named
@Transactional
public class ReelDiskTransferStorageFacadeImpl implements
		ReelDiskTransferStorageFacade {

	@Inject
	private ReelDiskApplication application;

	@Inject
	private IncrementNumberFacade incrementNumberFacade;

	@Inject
	private FTLotApplication ftLotApplication;

	@Inject
	private CustomerFTLotFacade customerFTLotFacade;

	@Inject
	private FTLotFacade ftLotFacade;

	@Inject
	private FTProcessApplicationImpl processApplication;

	@Inject
	private FTLotApplication fTLotApplication;

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	public Page<ReelDiskTransferStorageDTO> pageQueryReelDisk(
			ReelDiskTransferStorageDTO queryVo, int currentPage, int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _reelDisk from ReelDisk _reelDisk   where 1=1 ");
		jpql.append("and _reelDisk.status is not null ");
		if (queryVo.getIsCombined() == null || queryVo.getIsCombined() == false) {
			jpql.append(" and _reelDisk.combinedLot is null ");
		} else {
			jpql.append(" and (_reelDisk.combinedLot is not null  or (_reelDisk.fromReelCode is not null and _reelDisk.fromReelCode != '') ) ");
		}
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
		if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
			jpql.append(" and _reelDisk.status like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getStatus()));
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
		if (queryVo.getLogic() != null) {
			jpql.append(" and _reelDisk.logic=?");
			conditionVals.add(queryVo.getLogic());
		}
		if (queryVo.getCustomerLotNumber() != null
				&& !"".equals(queryVo.getCustomerLotNumber())) {
			jpql.append(" and _reelDisk.ftLot.customerFTLot.customerLotNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNumber()));
		}
		if (queryVo.getInternalLotNumber() != null
				&& !"".equals(queryVo.getInternalLotNumber() )) {
			jpql.append(" and _reelDisk.ftLot.internalLotNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getInternalLotNumber()));
		}
		if (queryVo.getQuality() != null && !"".equals(queryVo.getQuality())) {
			jpql.append(" and _reelDisk.quality like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getQuality()));
		}
		if (queryVo.getLatSign() != null && !"".equals(queryVo.getLatSign())) {
			if ("0".equals(queryVo.getLatSign())) {
				jpql.append(" and _reelDisk.latSign is null");
			} else {
				jpql.append(" and _reelDisk.latSign like ?");
				conditionVals.add(MessageFormat.format("%{0}%",
						queryVo.getLatSign()));
			}
		}
		if (queryVo.getLatPackageNo() != null
				&& !"".equals(queryVo.getLatPackageNo())) {
			jpql.append(" and _reelDisk.latPackageNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLatPackageNo()));
		}
		Page<ReelDisk> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();

		return new Page<ReelDiskTransferStorageDTO>(pages.getStart(),
				pages.getResultCount(), pageSize,
				ReelDiskTransferStorageAssembler.toDTOs(pages.getData()));
	}

	@Override
	public InvokeResult hold(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		ReelDisk reelDisk = application.get(reelDiskTransferStorageDTO.getId());
		reelDisk.setHoldSign("是");
		reelDisk.setHoldSignRemark(reelDiskTransferStorageDTO.getHoldSign());
		reelDisk.setHoldDate(new Date());
		reelDisk.setHoldPerson(reelDiskTransferStorageDTO
				.getLastModifyEmployNo());
		reelDisk.setLastModifyEmployNo(reelDiskTransferStorageDTO
				.getLastModifyEmployNo());
		application.update(reelDisk);
		return InvokeResult.success();
	}

	@Override
	public InvokeResult unhold(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		ReelDisk reelDisk = application.get(reelDiskTransferStorageDTO.getId());
		reelDisk.setHoldSign("否");
		reelDisk.setUnHoldSignRemark(reelDiskTransferStorageDTO
				.getUnHoldSignRemark());
		reelDisk.setUnHoldDate(new Date());
		reelDisk.setUnHoldPerson(reelDiskTransferStorageDTO
				.getLastModifyEmployNo());
		reelDisk.setLastModifyEmployNo(reelDiskTransferStorageDTO
				.getLastModifyEmployNo());
		application.update(reelDisk);
		return InvokeResult.success();
	}

	@Override
	public InvokeResult changeStatus(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		String[] ids = null;
		if (reelDiskTransferStorageDTO.getIds() != null) {
			ids = reelDiskTransferStorageDTO.getIds().split(",");
			for (String id : ids) {
				ReelDisk reelDisk = application.get(Long.valueOf(id));
				reelDisk.setStatus(reelDiskTransferStorageDTO.getStatus());
				reelDisk.setLastModifyEmployNo(reelDiskTransferStorageDTO
						.getLastModifyEmployNo());
				application.update(reelDisk);
			}
		}
		return InvokeResult.success();
	}

	@Override
	public InvokeResult specialSign(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		ReelDisk reelDisk = application.get(reelDiskTransferStorageDTO.getId());
		reelDisk.setSpecialSign(reelDiskTransferStorageDTO.getSpecialSign());
		reelDisk.setSpecialSignRemark(reelDiskTransferStorageDTO
				.getSpecialSignRemark());
		reelDisk.setLastModifyEmployNo(reelDiskTransferStorageDTO
				.getLastModifyEmployNo());
		application.update(reelDisk);
		return InvokeResult.success();
	}

	@Override
	public InvokeResult getReelDisk(Long id) {
		return InvokeResult.success(ReelDiskTransferStorageAssembler
				.toDTO(application.get(id)));
	}

	@Override
	public InvokeResult latPackage(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		if (reelDiskTransferStorageDTO.getIds() == null) {
			return InvokeResult.failure("未选择打包的ReelCode！");
		}
		String latPackageNo = Mes2DateUtils.getTodayTime("yyyyMMddHHmmss");
		for (String id : reelDiskTransferStorageDTO.getIds().split(",")) {
			ReelDisk reelDisk = application.get(Long.valueOf(id));
			reelDisk.setLatSign("1");
			reelDisk.setLatPackageTime(new Date());
			reelDisk.setLatPackageNo(latPackageNo);
			reelDisk.setLastModifyEmployNo(reelDiskTransferStorageDTO
					.getLastModifyEmployNo());
			application.update(reelDisk);
		}
		return InvokeResult.success();
	}

	@Override
	public InvokeResult reworkLot(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		// 1.获取母批实体
		String[] ids = reelDiskTransferStorageDTO.getIds().split(",");
		List<ReelDisk> reelDiskList = new ArrayList<ReelDisk>();
		FTLot ftLot = new FTLot();
		for (String id : ids) {
			ReelDisk reelDisk = application.get(Long.valueOf(id));
			if (ftLot.getId() != null
					&& !ftLot.getId().equals(reelDisk.getFtLot().getId())) {
				return InvokeResult.success("重工下单失败,选取的ReelCode属于不同的Lot!");
			}
			reelDiskList.add(reelDisk);
			ftLot = reelDisk.getFtLot();
		}
		FTLot parentLot = ftLot;
		// 2.保存重工的批次信息
		InvokeResult invokeResult;
		FTLotDTO reworkInternalDTO = FTLotAssembler.toDTO(ftLot);
		reworkInternalDTO.setCheckLotNo(false);
		if (ftLot.getInternalLotNumber().contains("-R")) {
			reworkInternalDTO.setInternalLotNumber(ftLot.getInternalLotNumber()
					.concat("R"));
		} else {
			reworkInternalDTO.setInternalLotNumber(ftLot.getInternalLotNumber()
					.concat("-R"));
		}
		try {
			invokeResult = customerFTLotFacade.getExpectedRCNumber(ftLot
					.getCustomerFTLot().getId());
			if (invokeResult.isHasErrors()) {
				return invokeResult;
			}
			reworkInternalDTO.setRcNumber(invokeResult.getData().toString());
			reworkInternalDTO.setId(null);
			reworkInternalDTO.setParentSeparationId(ftLot.getId());
			reworkInternalDTO.setMaterialType(ftLot.getCustomerFTLot()
					.getMaterialType());
			//getFTLotRework(parentLot, reelDiskTransferStorageDTO.getQty(),
			//		reworkInternalDTO);
			getFTLotRework(parentLot, (long)reelDiskTransferStorageDTO.getQuantity(),
					reworkInternalDTO);
			parentLot.setQty(parentLot.getQty() - reworkInternalDTO.getQty());
			invokeResult = ftLotFacade.createCheckedFTLot(reworkInternalDTO);
			if (invokeResult.isHasErrors()) {
				return invokeResult;
			}
			// 修改母批信息
			parentLot.setLogic(1);
			parentLot.setQty(0L);
			// 持久化操作
			// fTLotApplication.creatFTLot(childLots);
			fTLotApplication.update(parentLot);
			// this.logicDelReelDisk(reelDiskTransferStorageDTO);

			Long splitIds = (Long) invokeResult.getData();
			//0默认从第一个站点重工
			processApplication.createFTProcess(splitIds, ftLot.getId(), 0);

			// 改变ReelDisk状态，同时逻辑删除
			for (ReelDisk rd : reelDiskList) {
				rd.setStatus(ReelDisk.REEL_DISK_STATUS_REWORK);
				rd.setLogic(1);
				rd.setLastModifyTimestamp(reelDiskTransferStorageDTO
						.getLastModifyTimestamp());
				rd.setLastModifyEmployNo(reelDiskTransferStorageDTO
						.getLastModifyEmployNo());
				this.application.update(rd);
			}
			return InvokeResult.success(invokeResult.getData().toString());
		} catch (Exception e) {
			e.printStackTrace();
			return InvokeResult.failure("重工下单失败！");
		}
	}

	private void getFTLotRework(FTLot parentLot, Long qty,
			FTLotDTO childFTLotDTO) {
		BeanUtils.copyProperties(parentLot, childFTLotDTO, "internalLotNumber");
		childFTLotDTO.setId(null);
		childFTLotDTO.setQty((long) qty);
	}

	private void logicDelReelDisk(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		String[] ids = reelDiskTransferStorageDTO.getIds().split(",");
		for (String id : ids) {
			ReelDisk reelDisk = application.get(Long.valueOf(id));
			reelDisk.setLogic(1);
			reelDisk.setLastModifyEmployNo(reelDiskTransferStorageDTO
					.getLastModifyEmployNo());
			application.update(reelDisk);
		}
	}

	@Override
	public void exportLatPrint(ServletOutputStream outputStream,
			String latPackageNo) {
		String[] titles = { "PPO", "Customer Lot", "产品型号", "LOT",
				"LAT PASS(数量)", "备注" };
		String title = "lat打印";
		ReelDiskTransferStorageDTO reelDiskTransferStorageDTO = new ReelDiskTransferStorageDTO();
		reelDiskTransferStorageDTO.setLatPackageNo(latPackageNo);
		Page<ReelDiskTransferStorageDTO> page = this.pageQueryReelDisk(
				reelDiskTransferStorageDTO, 0, 100);
		this.exportExcel(title, titles, page.getData(), outputStream,
				latPackageNo);

	}

	public void exportExcel(String title, String[] titles,
			Collection<ReelDiskTransferStorageDTO> dataset,
			ServletOutputStream outputStream, String latPackageNo) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 生成一个边框样式
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		// 把字体应用到当前的样式
		style.setFont(font);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 产生表格标题行
		HSSFRow rowSign = sheet.createRow(0);
		HSSFCell cellSign = rowSign.createCell(0);
		cellSign.setCellStyle(style);
		cellSign.setCellValue("标示码：");
		// 条码位置
		sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
				// from 行
				0, // last row (0-based) to 行
				1, // first column (0-based) from 列
				5 // last column (0-based) to 列
		));
		// 图片
		// 有图片时，设置行高为60px;
		rowSign.setHeightInPoints(40);
		// 设置图片所在列宽度为80px,注意这里单位的一个换算
		// sheet.setColumnWidth(i, (short) (35.7 * 80));
		// sheet.autoSizeColumn(i);
		byte[] bsValue = this.createBarCode(latPackageNo);
		// 0, 1, 1, 5, 1, 0, 5, 1
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 1, 1, 5, (short) 1,
				0, (short) 5, 1);
		anchor.setAnchorType(2);
		patriarch.createPicture(anchor,
				workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_PNG));

		int dataIndex = 1;
		// 产生表格标题行
		HSSFRow row = sheet.createRow(dataIndex);
		if (titles != null) {
			for (short i = 0; i < titles.length; i++) {
				HSSFCell cell = row.createCell(Integer.valueOf(i));
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(titles[i]);
				cell.setCellValue(text);
			}
		}
		// 遍历集合数据，产生数据行
		int totalQty = 0;
		for (ReelDiskTransferStorageDTO reelDiskTransferStorageDTO : dataset) {
			dataIndex++;
			HSSFRow rowData = sheet.createRow(dataIndex);
			HSSFCell cell = rowData.createCell(0);
			cell.setCellStyle(style);
			cell.setCellValue(reelDiskTransferStorageDTO.getCustomerPPO());
			cell = rowData.createCell(1);
			cell.setCellStyle(style);
			cell.setCellValue(reelDiskTransferStorageDTO.getCustomerLotNumber());
			cell = rowData.createCell(2);
			cell.setCellStyle(style);
			cell.setCellValue(reelDiskTransferStorageDTO.getPartNumber());
			cell = rowData.createCell(3);
			cell.setCellStyle(style);
			cell.setCellValue(reelDiskTransferStorageDTO.getFtLotDTO()
					.getInternalLotNumber());
			cell = rowData.createCell(4);
			cell.setCellStyle(style);
			cell.setCellValue(reelDiskTransferStorageDTO.getQuantity());
			totalQty += reelDiskTransferStorageDTO.getQuantity();
		}
		dataIndex++;
		HSSFRow total = sheet.createRow(dataIndex);
		sheet.addMergedRegion(new CellRangeAddress(dataIndex, // first row
				// (0-based)
				// from 行
				dataIndex, // last row (0-based) to 行
				0, // first column (0-based) from 列
				3 // last column (0-based) to 列
		));
		cellSign = total.createCell(0);
		cellSign.setCellStyle(style);
		cellSign.setCellValue("合计：");
		cellSign = total.createCell(4);
		cellSign.setCellStyle(style);
		cellSign.setCellValue(totalQty);
		CellRangeAddress region = new CellRangeAddress(0, dataIndex, 0, 5);
		RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, region, sheet,
				workbook);
		RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, region, sheet,
				workbook);
		RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, region, sheet,
				workbook);
		RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, region, sheet,
				workbook);
		try {
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private byte[] createBarCode(String code) {
		byte[] b = null;
		try {
			// Create the barcode bean
			Code39Bean bean = new Code39Bean();

			final int dpi = 300;

			// Configure the barcode generator
			bean.setModuleWidth(UnitConv.in2mm(2.0f / dpi)); // makes the narrow
			bean.setBarHeight(4);
			bean.setWideFactor(3);
			bean.doQuietZone(false);
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			try {
				// Set up the canvas provider for monochrome JPEG output
				BitmapCanvasProvider canvas = new BitmapCanvasProvider(out,
						"image/png", dpi, BufferedImage.TYPE_BYTE_BINARY,
						false, 0);

				// Generate the barcode
				bean.generateBarcode(canvas, code);
				// Signal end of generation
				canvas.finish();

				b = out.toByteArray();
			} finally {
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return b;
	}
}
