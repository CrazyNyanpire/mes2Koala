package org.seu.acetec.mes2Koala.web.controller;

import help.ExcelTemplateHelper;

import javax.inject.Inject;

import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.ExcelFacade;
import org.seu.acetec.mes2Koala.facade.ReworkFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/Rework")
public class ReworkController extends BaseController {

	@Inject
	private ReworkFacade reworkFacade;

	@Inject
	private ExcelFacade excelFacade;

	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(ReworkDTO reworkDTO) {
		return reworkFacade.creatRework(reworkDTO);
	}

	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(ReworkDTO reworkDTO) {
		return reworkFacade.updateRework(reworkDTO);
	}

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(ReworkDTO reworkDTO, @RequestParam int page,
			@RequestParam int pagesize) {
		Page<ReworkDTO> all = reworkFacade.pageQueryRework(reworkDTO, page,
				pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public InvokeResult remove(@RequestParam String ids) {
		String[] value = ids.split(",");
		Long[] idArrs = new Long[value.length];
		for (int i = 0; i < value.length; i++) {
			idArrs[i] = Long.parseLong(value[i]);
		}
		return reworkFacade.removeReworks(idArrs);
	}

	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return reworkFacade.getRework(id);
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

	@ResponseBody
	@RequestMapping("/approve")
	public InvokeResult approve(ReworkDTO reworkDTO) {
		reworkDTO = this.createBase(reworkDTO);
		return reworkFacade.approve(reworkDTO);
	}

	@ResponseBody
	@RequestMapping("/exportExcel")
	public InvokeResult exportExcel(ReworkDTO reworkDTO) {
		return excelFacade.exportReworkLot(reworkDTO);
	}

}
