package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;

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
import org.seu.acetec.mes2Koala.facade.FTQDNFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/FTQDN")
public class FTQDNController extends BaseController{
		
	@Inject
	private FTQDNFacade fTQDNFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(FTQDNDTO fTQDNDTO) {
		return fTQDNFacade.creatFTQDN(fTQDNDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(FTQDNDTO fTQDNDTO) {
		return fTQDNFacade.updateFTQDN(fTQDNDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(FTQDNDTO fTQDNDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<FTQDNDTO> all = fTQDNFacade.pageQueryFTQDN(fTQDNDTO, page, pagesize);
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
        return fTQDNFacade.removeFTQDNs(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return fTQDNFacade.getFTQDN(id);
	}
	
		@ResponseBody
	@RequestMapping("/findFtLotByFTQDN/{id}")
	public Map<String, Object> findFtLotByFTQDN(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", fTQDNFacade.findFtLotByFTQDN(id));
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
	
    
	@ResponseBody
	@RequestMapping("/dispose")
	public InvokeResult dispose(FTQDNDTO fTQDNDTO) {
		fTQDNDTO = this.lastModifyBase(fTQDNDTO);
		return fTQDNFacade.disposeFTQDN(fTQDNDTO);
	}
}
