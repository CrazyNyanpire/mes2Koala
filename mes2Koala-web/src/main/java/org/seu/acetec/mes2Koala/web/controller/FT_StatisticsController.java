package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;
import org.springframework.web.bind.WebDataBinder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.FT_StatisticsFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/FT_Statistics")
public class FT_StatisticsController {
		
	@Inject
	private FT_StatisticsFacade fT_StatisticsFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(FT_StatisticsDTO fT_StatisticsDTO) {
		return fT_StatisticsFacade.creatFT_Statistics(fT_StatisticsDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(FT_StatisticsDTO fT_StatisticsDTO) {
		return fT_StatisticsFacade.updateFT_Statistics(fT_StatisticsDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(FT_StatisticsDTO fT_StatisticsDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<FT_StatisticsDTO> all = fT_StatisticsFacade.pageQueryFT_Statistics(fT_StatisticsDTO, page, pagesize);
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
        return fT_StatisticsFacade.removeFT_Statisticss(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return fT_StatisticsFacade.getFT_Statistics(id);
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
