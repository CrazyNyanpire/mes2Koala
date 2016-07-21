package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.sbl.SBLClient;
import org.seu.acetec.mes2Koala.facade.CPSBLTemplateFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/CPSBLTemplate")
public class CPSBLTemplateController {
		
	@Inject
	private CPSBLTemplateFacade cPSBLTemplateFacade;
	
	@Inject
	private SBLClient sblClient;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(CPSBLTemplateDTO cPSBLTemplateDTO) {
		return cPSBLTemplateFacade.creatCPSBLTemplate(cPSBLTemplateDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(CPSBLTemplateDTO cPSBLTemplateDTO) {
		return cPSBLTemplateFacade.updateCPSBLTemplate(cPSBLTemplateDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(CPSBLTemplateDTO cPSBLTemplateDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<CPSBLTemplateDTO> all = cPSBLTemplateFacade.pageQueryCPSBLTemplate(cPSBLTemplateDTO, page, pagesize);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public InvokeResult remove(@RequestParam String ids) {
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i ++) {
        	        					idArrs[i] = Long.parseLong(value[i]);
						        }
        return cPSBLTemplateFacade.removeCPSBLTemplates(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return cPSBLTemplateFacade.getCPSBLTemplate(id);
	}
	
    @ResponseBody
	@RequestMapping("/findInternalProductByCPSBLTemplate/{id}")
	public Map<String, Object> findInternalProductByCPSBLTemplate(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", cPSBLTemplateFacade.findInternalProductByCPSBLTemplate(id));
		return result;
	}
    
    @ResponseBody
	@RequestMapping("/findCPSBLTemplateByLotId/{id}")
	public Map<String, Object> findCPSBLTemplateByLotId(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONArray sbl = cPSBLTemplateFacade.getCPSBLTemplatesByLotId(id);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", sbl);
		String insertSbl = sblClient.insertCPSBL(jsonObject.toString());
		result.put("insertSbl", insertSbl);
		result.put("data", sbl);
		return result;
	}
    
    @ResponseBody
	@RequestMapping("/findCPSBLInfo")
	public Map<String, Object> findCPSBLInfo(@RequestParam String lotNo, @RequestParam String stationName) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sblInfo = sblClient.getErrorInfo(lotNo, stationName);
		result.put("sblInfo", sblInfo);
		return result;
	}

	
    @InitBinder    
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
        dateFormat.setLenient(false);    
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
        //CustomDateEditor 可以换成自己定义的编辑器。  
        //注册一个Date 类型的绑定器 。
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }
	
}
