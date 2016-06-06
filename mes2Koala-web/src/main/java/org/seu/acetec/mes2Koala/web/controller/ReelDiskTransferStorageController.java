package org.seu.acetec.mes2Koala.web.controller;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.FTLotFacade;
import org.seu.acetec.mes2Koala.facade.FTProcessFacade;
import org.seu.acetec.mes2Koala.facade.ReelDiskFacade;
import org.seu.acetec.mes2Koala.facade.ReelDiskTransferStorageFacade;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskTransferStorageDTO;
import org.seu.acetec.mes2Koala.core.common.Mes2DateUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/ReelDiskTransferStorage")
public class ReelDiskTransferStorageController extends BaseController {

	@Inject
	private ReelDiskFacade reelDiskFacade;

	@Inject
	private ReelDiskTransferStorageFacade reelDiskTransferStorageFacade;

	@Inject
	private FTLotFacade ftLotFacade;

	@Inject
	private FTProcessFacade FTProcessFacade;

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(ReelDiskTransferStorageDTO reelDiskDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		Page<ReelDiskTransferStorageDTO> all = reelDiskTransferStorageFacade
				.pageQueryReelDisk(reelDiskDTO, page, pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return reelDiskTransferStorageFacade.getReelDisk(id);
	}

	@ResponseBody
	@RequestMapping("/hold")
	public InvokeResult hold(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		reelDiskTransferStorageDTO = this
				.createBase(reelDiskTransferStorageDTO);
		return reelDiskTransferStorageFacade.hold(reelDiskTransferStorageDTO);
	}

	@ResponseBody
	@RequestMapping("/unhold")
	public InvokeResult unhold(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		reelDiskTransferStorageDTO = this
				.createBase(reelDiskTransferStorageDTO);
		return reelDiskTransferStorageFacade.unhold(reelDiskTransferStorageDTO);
	}

	@ResponseBody
	@RequestMapping("/changeStatus")
	public InvokeResult changeStatus(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		reelDiskTransferStorageDTO = this
				.createBase(reelDiskTransferStorageDTO);
		return reelDiskTransferStorageFacade
				.changeStatus(reelDiskTransferStorageDTO);
	}

	@ResponseBody
	@RequestMapping("/specialSign")
	public InvokeResult specialSign(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		reelDiskTransferStorageDTO = this
				.createBase(reelDiskTransferStorageDTO);
		return reelDiskTransferStorageFacade
				.specialSign(reelDiskTransferStorageDTO);
	}

	@ResponseBody
	@RequestMapping("/latPackage")
	public InvokeResult latPackage(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		reelDiskTransferStorageDTO = this
				.createBase(reelDiskTransferStorageDTO);
		return reelDiskTransferStorageFacade
				.latPackage(reelDiskTransferStorageDTO);
	}

	@ResponseBody
	@RequestMapping("/reworkLot")
	public InvokeResult reworkLot(
			ReelDiskTransferStorageDTO reelDiskTransferStorageDTO) {
		reelDiskTransferStorageDTO = this
				.createBase(reelDiskTransferStorageDTO);
		InvokeResult invokeResult = reelDiskTransferStorageFacade
				.reworkLot(reelDiskTransferStorageDTO);
		if (invokeResult.isHasErrors()) {
			return invokeResult;
		}
		return InvokeResult.success();
	}

	/**
	 * 导出成Excel
	 *
	 * @param response
	 * @param latPackageNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/latPrint")
	public InvokeResult latPrint(HttpServletResponse response,
			@RequestParam String latPackageNo) {
		// "20160322001"
		if (latPackageNo == null || "".equals(latPackageNo)) {
			return InvokeResult.failure("请选择打包过后的Lat！");
		}
		response.setContentType("application/binary;charset=ISO8859_1");
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = new String(
					("lat-" + Mes2DateUtils.getTodayTime("yyyy-MM-dd_HHmmss"))
							.getBytes(),
					"ISO8859_1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName + ".xls");// 组装附件名称和格式
			reelDiskTransferStorageFacade.exportLatPrint(outputStream,
					latPackageNo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return InvokeResult.success();
	}

	/**
	 * 中转库出库
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/shipping")
	public InvokeResult shipping(@RequestParam String ids) {

		String[] value = ids.split(",");
		Long[] idArr = new Long[value.length];
		for (int i = 0; i < value.length; i++) {
			idArr[i] = Long.parseLong(value[i]);
		}
		return reelDiskFacade.mfgOut(idArr);
	}

	/**
	 * WMS入库审核之后，修改中转库状态为出库已签核
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/confirmMfgIn")
	public InvokeResult confirmMfgIn(String ids) {
		InvokeResult invokeResult = reelDiskFacade.confirmMfgIn(ids);
		if (invokeResult.isHasErrors()) {
			return invokeResult;
		}
		return InvokeResult.success();
	}
	/**
	 * 返线
	 * @param reelDiskJson 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/backtoLine")
	public InvokeResult backtoLine(String json) {
		try {
			json = URLDecoder.decode(json, "UTF-8");
			InvokeResult invokeResult = reelDiskFacade.backtoLine(json);
			if (invokeResult.isHasErrors()) {
				return invokeResult;
			}  
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		}
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
}
