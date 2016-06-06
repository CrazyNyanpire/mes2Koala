package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;
import org.springframework.web.bind.WebDataBinder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.LabelPlanFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/LabelPlan")
public class LabelPlanController {
		
	@Inject
	private LabelPlanFacade labelPlanFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(LabelPlanDTO labelPlanDTO) {
		return labelPlanFacade.creatLabelPlan(labelPlanDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(LabelPlanDTO labelPlanDTO) {
		return labelPlanFacade.updateLabelPlan(labelPlanDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(LabelPlanDTO labelPlanDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<LabelPlanDTO> all = labelPlanFacade.pageQueryLabelPlan(labelPlanDTO, page, pagesize);
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
        return labelPlanFacade.removeLabelPlans(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return labelPlanFacade.getLabelPlan(id);
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
