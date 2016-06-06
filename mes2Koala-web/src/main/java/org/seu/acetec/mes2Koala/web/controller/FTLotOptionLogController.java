package org.seu.acetec.mes2Koala.web.controller;

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
import org.seu.acetec.mes2Koala.facade.FTLotOptionLogFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/FTLotOptionLog")
public class FTLotOptionLogController {
		
	@Inject
	private FTLotOptionLogFacade fTLotOptionLogFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(FTLotOptionLogDTO fTLotOptionLogDTO) {
		return fTLotOptionLogFacade.creatFTLotOptionLog(fTLotOptionLogDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(FTLotOptionLogDTO fTLotOptionLogDTO) {
		return fTLotOptionLogFacade.updateFTLotOptionLog(fTLotOptionLogDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(FTLotOptionLogDTO fTLotOptionLogDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<FTLotOptionLogDTO> all = fTLotOptionLogFacade.pageQueryFTLotOptionLog(fTLotOptionLogDTO, page, pagesize);
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
        return fTLotOptionLogFacade.removeFTLotOptionLogs(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return fTLotOptionLogFacade.getFTLotOptionLog(id);
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
