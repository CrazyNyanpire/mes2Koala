package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;

import org.springframework.web.bind.WebDataBinder;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.seu.acetec.mes2Koala.facade.common.SenderMailUtils;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskTransferStorageDTO;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/Demo")
public class DemoController {

	@Inject
	private SenderMailUtils senderMailUtils;

	@ResponseBody
	@RequestMapping("/mail")
	public InvokeResult mail() {
		// senderMailUtils.sendMailHelper("MES2测试邮件发送", "MES2测试邮件发送",
		// "harlow.zhou@acetecsemi.com");
		InputStreamSource bar = new ByteArrayResource(this.exportExcel());
		senderMailUtils.sendMailHelper("MES2测试邮件发送", "MES2测试邮件发送",
				new String[] { "harlow.zhou@acetecsemi.com" }, null, null,
				"test.cvs", bar,false);
		return InvokeResult.success();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
		// CustomDateEditor 可以换成自己定义的编辑器。
		// 注册一个Date 类型的绑定器 。
		binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
	}

	private byte[] exportCvs() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream p = new PrintStream(out);
		for (int i = 0; i < 10; i++)
			p.println("This is ," + i + " ,line");
		return out.toByteArray();
	}

	@SuppressWarnings("unchecked")
	public byte[] exportExcel() {
		String[] titles = { "PPO", "Customer Lot", "产品型号", "LOT",
				"LAT PASS(数量)", "备注" };
		String title = "lat打印";
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
		// byte[] bsValue = this.createBarCode(latPackageNo);
		// 0, 1, 1, 5, 1, 0, 5, 1
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 1, 1, 5, (short) 1,
				0, (short) 5, 1);
		anchor.setAnchorType(2);

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
		return workbook.getBytes();

	}

}
