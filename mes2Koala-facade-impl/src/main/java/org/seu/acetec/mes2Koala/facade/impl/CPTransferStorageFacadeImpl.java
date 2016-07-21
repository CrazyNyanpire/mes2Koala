package org.seu.acetec.mes2Koala.facade.impl;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

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
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPLotOptionLogApplication;
import org.seu.acetec.mes2Koala.application.CPNodeApplication;
import org.seu.acetec.mes2Koala.application.CPRuncardTemplateApplication;
import org.seu.acetec.mes2Koala.application.CustomerCPLotApplication;
import org.seu.acetec.mes2Koala.application.ProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.bean.SaveBaseBean;
import org.seu.acetec.mes2Koala.application.impl.CPProcessApplicationImpl;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPLotOptionLog;
import org.seu.acetec.mes2Koala.core.domain.CPNode;
import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import org.seu.acetec.mes2Koala.core.domain.CPProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.CPRuncard;
import org.seu.acetec.mes2Koala.core.domain.CPRuncardTemplate;
import org.seu.acetec.mes2Koala.core.domain.CPTestingNode;
import org.seu.acetec.mes2Koala.core.domain.CPWafer;
import org.seu.acetec.mes2Koala.core.domain.TestProgram;
import org.seu.acetec.mes2Koala.core.enums.CPNodeState;
import org.seu.acetec.mes2Koala.core.enums.TestTypeForWms;
import org.seu.acetec.mes2Koala.facade.CPLotFacade;
import org.seu.acetec.mes2Koala.facade.CPTransferStorageFacade;
import org.seu.acetec.mes2Koala.facade.CustomerCPLotFacade;
import org.seu.acetec.mes2Koala.facade.IncrementNumberFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPWaferDTO;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskTransferStorageDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPLotAssembler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
public class CPTransferStorageFacadeImpl implements CPTransferStorageFacade {

	@Inject
	private IncrementNumberFacade incrementNumberFacade;

	@Inject
	private CPLotApplication cpLotApplication;

	@Inject
	private CustomerCPLotApplication customerCPLotApplication;

	@Inject
	private CPNodeApplication cpNodeApplication;

	@Inject
	private CPProcessApplicationImpl processApplication;

	@Inject
	private CPRuncardTemplateApplication cpRuncardTemplateApplication;

	@Inject
	private ProductionScheduleApplication productionScheduleApplication;

	@Inject
	private CPLotOptionLogApplication cpLotOptionLogApplication;

	private static String REWORKED = "已重工";

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Override
	@Transactional
	public InvokeResult reworkLot(CPLotDTO cpLotDTO) {
		CPLot parentCpLot = cpLotApplication.get(cpLotDTO.getId());
		if (REWORKED.equals(parentCpLot.getCurrentState())) {
			 //throw new RuntimeException("此批次已重工建批");
		}
		SaveBaseBean sbb = new SaveBaseBean();
		BeanUtils.copyProperties(cpLotDTO, sbb);
		parentCpLot.setCurrentState(REWORKED);
		CPLot cpLot = new CPLot();
		cpLot.setSourceParentSeparationId(parentCpLot.getId());
		cpLot.setSourceParentSeparationNo(parentCpLot.getInternalLotNumber());
		BeanUtils.copyProperties(parentCpLot, cpLot, "id");
		List<CPWafer> waferList = new ArrayList<CPWafer>();
		CPWafer newCpWafer;
		for (CPWafer cpWafer : parentCpLot.getCpWafers()) {
			newCpWafer = new CPWafer();
			BeanUtils.copyProperties(cpWafer, newCpWafer, "id");
			newCpWafer.setLastModifyTimestamp(new Date());
			newCpWafer.setLastModifyEmployNo(cpLotDTO.getLastModifyEmployNo());
			newCpWafer.setCpLot(cpLot);
			waferList.add(newCpWafer);
		}
		cpLot.setCpWafers(waferList);
		cpLot.setQuantity((long) waferList.size());
		try {

			CPLot cpLotNo = cpLotApplication.findOne("select o from CPLot o where o.internalLotNumber like '"+parentCpLot
					.getInternalLotNumber()+"%' order by o.internalLotNumber desc");
			cpLot.setInternalLotNumber(this.createReworkLotNo(cpLotNo.getInternalLotNumber()));
			customerCPLotApplication.orderWithOutWMS(parentCpLot
					.getCustomerCPLot().getId(), cpLot);
			cpLotApplication.update(parentCpLot);
			cpLotApplication.create(cpLot);// 创建CPLot

			CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication
					.findByInternalProductId(cpLot.getCustomerCPLot()
							.getCpInfo().getId());
			CPRuncard cpRuncard = customerCPLotApplication
					.createCPRuncard(cpRuncardTemplate);
			cpRuncard.setCpLot(cpLot);
			BeanUtils.copyProperties(sbb, cpRuncard);
			cpRuncard.save();

			for (CPNode cpNode : cpLot.getCpProcess().getCpNodes()) {
				if (cpNode instanceof CPTestingNode) {
					productionScheduleApplication.createNewCpSchedule(null,
							(CPTestingNode) cpNode);
				}
			}
			// 操作记录
			cpLotOptionLogApplication.createCPNode(cpLot, cpLot.getCpProcess()
					.getNowNode(), "重工建批", "重工建批");
			cpLotOptionLogApplication.createCPNode(parentCpLot, parentCpLot
					.getCpProcess().getNowNode(), "重工建批", "重工建批");
			return InvokeResult.success();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw new RuntimeException("重工建批失败");
		}
	}

	private String createReworkLotNo(String lotNo) {
		int index = lotNo.indexOf("-R");
		if (index < 0) {
			return lotNo.concat("-R1");
		} else {
			String lotEnd = lotNo.substring(index);
			int reworkNo = Integer.valueOf(lotEnd.replace("-R", ""));
			reworkNo++;
			return lotNo.replace(lotEnd, "").concat("-R")
					.concat(String.valueOf(reworkNo));
		}
	}

	@SuppressWarnings("deprecation")
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

	@Override
	public Page<CPLotDTO> pageQuery(CPLotDTO cpLotDTO, int currentPage,
			int pageSize) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _cpLot from CPLot _cpLot where 1=1 ");
		jpql.append("and _cpLot.cpProcess.isTransferStorage = true ");
		if (cpLotDTO.getCreateTimestamp() != null) {
			jpql.append(" and _cpLot.createTimestamp between ? and ? ");
			conditionVals.add(cpLotDTO.getCreateTimestamp());
			conditionVals.add(cpLotDTO.getCreateTimestampEnd());
		}
		if (cpLotDTO.getLastModifyTimestamp() != null) {
			jpql.append(" and _cpLot.lastModifyTimestamp between ? and ? ");
			conditionVals.add(cpLotDTO.getLastModifyTimestamp());
			conditionVals.add(cpLotDTO.getLastModifyTimestampEnd());
		}
		if (cpLotDTO.getCreateEmployNo() != null
				&& !"".equals(cpLotDTO.getCreateEmployNo())) {
			jpql.append(" and _cpLot.createEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					cpLotDTO.getCreateEmployNo()));
		}
		if (cpLotDTO.getLastModifyEmployNo() != null
				&& !"".equals(cpLotDTO.getLastModifyEmployNo())) {
			jpql.append(" and _cpLot.lastModifyEmployNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					cpLotDTO.getLastModifyEmployNo()));
		}
		if (cpLotDTO.getHoldState() != null) {
			jpql.append(" and _cpLot.holdState like ?");
			conditionVals.add(cpLotDTO.getHoldState());
		}
		if (cpLotDTO.getCurrentState() != null) {
			jpql.append(" and _cpLot.currentState like ?");
			conditionVals.add(cpLotDTO.getCurrentState());
		}
		if (cpLotDTO.getDiskContent() != null
				&& !"".equals(cpLotDTO.getDiskContent())) {
			jpql.append(" and _cpLot.diskContent like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					cpLotDTO.getDiskContent()));
		}
		jpql.append(" and (_cpLot.showFlag = false or _cpLot.showFlag is null)");
		jpql.append(" order by _cpLot.createTimestamp desc");
		Page<CPLot> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		return new Page<>(pages.getStart(), pages.getResultCount(), pageSize,
				CPLotAssembler.toDTOs(pages.getData()));

	}

	@Override
	public InvokeResult createTransferStorage(String lotNo) {
		cpNodeApplication.createTransferStorage(lotNo);
		return InvokeResult.success();
	}
}
