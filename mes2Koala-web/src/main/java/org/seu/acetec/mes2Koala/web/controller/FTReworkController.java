package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.FTReworkFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/FTRework")
public class FTReworkController extends BaseController{

	@Inject
	private FTReworkFacade fTReworkFacade;

	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(@RequestParam String data, FTReworkDTO fTReworkDTO,@RequestParam Long id) {
		JSONObject jsonObjet = JSONObject.fromObject(data);
		JSONObject basic = jsonObjet.getJSONObject("basic");
		JSONObject reason = jsonObjet.getJSONObject("reason");
		fTReworkDTO = (FTReworkDTO) JSONObject.toBean(basic, FTReworkDTO.class);
		fTReworkDTO.setSummary(jsonObjet.getString("summary"));
		fTReworkDTO.setReasonExplanation(reason.getString("explanation"));
		fTReworkDTO.setReasonOther(reason.get("other") == null ? "" : reason.getString("other"));
		fTReworkDTO.setReasonReasons(reason.getString("reasons"));
		fTReworkDTO.setApprovePerson(jsonObjet.getString("approvePerson"));
		JSONArray items = jsonObjet.getJSONArray("item");
		List<FTReworkItemDTO> reworkItems = (List<FTReworkItemDTO>) JSONArray
				.toCollection(items, FTReworkItemDTO.class);
		fTReworkDTO.setReworkItems(reworkItems);
		fTReworkDTO.setLotId(id);
		fTReworkDTO.setInternalLotId(id);
		fTReworkDTO.setReworkType("FT重工");
		fTReworkDTO = this.createBase(fTReworkDTO);
		return fTReworkFacade.creatFTRework(fTReworkDTO);
	}

	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(FTReworkDTO fTReworkDTO) {
		return fTReworkFacade.updateFTRework(fTReworkDTO);
	}

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(FTReworkDTO fTReworkDTO, @RequestParam int page,
			@RequestParam int pagesize) {
		Page<FTReworkDTO> all = fTReworkFacade.pageQueryFTRework(fTReworkDTO,
				page, pagesize);
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
		return fTReworkFacade.removeFTReworks(idArrs);
	}

	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return fTReworkFacade.getFTRework(id);
	}
	
	@ResponseBody
	@RequestMapping("/createReworkNo/{id}")
	public InvokeResult createReworkNo(@PathVariable Long id) {
		return fTReworkFacade.createReworkNo(id);
	}

	@ResponseBody
	@RequestMapping("/findReworkItemsByFTRework/{id}")
	public Page findReworkItemsByFTRework(@PathVariable Long id,
			@RequestParam int page, @RequestParam int pagesize) {
		Page<FTReworkItemDTO> all = fTReworkFacade.findReworkItemsByFTRework(
				id, page, pagesize);
		return all;
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
	public InvokeResult approve(FTReworkDTO fTReworkDTO) {
		return fTReworkFacade.approveFTRework(fTReworkDTO);
	}

}
