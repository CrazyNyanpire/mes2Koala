package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;
import org.springframework.web.bind.WebDataBinder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.SpecialFormFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/SpecialForm")
public class SpecialFormController {
		
	@Inject
	private SpecialFormFacade specialFormFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(SpecialFormDTO specialFormDTO) {
		return specialFormFacade.creatSpecialForm(specialFormDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(SpecialFormDTO specialFormDTO) {
		
		if(specialFormDTO.getCheckBoxForm()==null)
			specialFormDTO.setCheckBoxForm(false);
		if(specialFormDTO.getCheckSelfForm()==null)
			specialFormDTO.setCheckSelfForm(false);
		if(specialFormDTO.getFlowForm()==null)
			specialFormDTO.setFlowForm(false);
		if(specialFormDTO.getIndexForm()==null)
			specialFormDTO.setIndexForm(false);
		if(specialFormDTO.getLossForm()==null)
			specialFormDTO.setLossForm(false);
		if(specialFormDTO.getReelcodeForm()==null)
			specialFormDTO.setReelcodeForm(false);
		if(specialFormDTO.getSizeForm()==null)
			specialFormDTO.setSizeForm(false);
		if(specialFormDTO.getSummaryForm()==null)
			specialFormDTO.setSummaryForm(false);		
		
		return specialFormFacade.updateSpecialForm(specialFormDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(SpecialFormDTO specialFormDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<SpecialFormDTO> all = specialFormFacade.pageQuerySpecialForm(specialFormDTO, page, pagesize);
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
        return specialFormFacade.removeSpecialForms(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return specialFormFacade.getSpecialForm(id);
	}
	
//	@ResponseBody
//	@RequestMapping("/findSpecialForm")
//	public List<SpecialFormDTO> queryAllSpecialForm() {
//		return specialFormFacade.findAllSpecialForm();
//	}
		
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
