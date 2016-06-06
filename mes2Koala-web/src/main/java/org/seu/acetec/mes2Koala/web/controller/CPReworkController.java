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
import org.seu.acetec.mes2Koala.facade.CPReworkFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/CPRework")
public class CPReworkController extends BaseController{

	@Inject
	private CPReworkFacade cpReworkFacade;

	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(@RequestParam String data, CPReworkDTO cpReworkDTO,@RequestParam Long id) {
		JSONObject jsonObjet = JSONObject.fromObject(data);
		JSONObject basic = jsonObjet.getJSONObject("basic");
		JSONObject reason = jsonObjet.getJSONObject("reason");
		cpReworkDTO = (CPReworkDTO) JSONObject.toBean(basic, CPReworkDTO.class);
		cpReworkDTO.setSummary(jsonObjet.getString("summary"));
		cpReworkDTO.setReasonExplanation(reason.getString("explanation"));
		cpReworkDTO.setReasonOther(reason.get("other") == null ? "" : reason.getString("other"));
		cpReworkDTO.setReasonReasons(reason.getString("reasons"));
		cpReworkDTO.setApprovePerson(jsonObjet.getString("approvePerson"));
		cpReworkDTO.setGist(reason.getString("gist"));
		JSONArray items = jsonObjet.getJSONArray("item");
		List<CPReworkItemDTO> reworkItems = (List<CPReworkItemDTO>) JSONArray
				.toCollection(items, CPReworkItemDTO.class);
		cpReworkDTO.setReworkItems(reworkItems);
		cpReworkDTO.setLotId(id);
		cpReworkDTO.setInternalLotId(id);
		cpReworkDTO.setReworkType("CP重工");
		cpReworkDTO = this.createBase(cpReworkDTO);
		return cpReworkFacade.creatCPRework(cpReworkDTO);
	}

	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(CPReworkDTO cpReworkDTO) {
		return cpReworkFacade.updateCPRework(cpReworkDTO);
	}

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(CPReworkDTO cpReworkDTO, @RequestParam int page,
			@RequestParam int pagesize) {
		Page<CPReworkDTO> all = cpReworkFacade.pageQueryCPRework(cpReworkDTO,
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
		return cpReworkFacade.removeCPReworks(idArrs);
	}

	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return cpReworkFacade.getCPRework(id);
	}
	
	@ResponseBody
	@RequestMapping("/createReworkNo/{id}")
	public InvokeResult createReworkNo(@PathVariable Long id) {
		return cpReworkFacade.createReworkNo(id);
	}

	@ResponseBody
	@RequestMapping("/findReworkItemsByCPRework/{id}")
	public Page findReworkItemsByCPRework(@PathVariable Long id,
			@RequestParam int page, @RequestParam int pagesize) {
		Page<CPReworkItemDTO> all = cpReworkFacade.findReworkItemsByCPRework(
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
	public InvokeResult approve(CPReworkDTO cpReworkDTO) {
		return cpReworkFacade.approveCPRework(cpReworkDTO);
	}

}
