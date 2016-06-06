package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;

import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import org.seu.acetec.mes2Koala.facade.CPQDNFacade;
import org.openkoala.koala.commons.InvokeResult;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/CPQDN")
public class CPQDNController extends BaseController{
		
	@Inject
	private CPQDNFacade cPQDNFacade;
	private Map<String,Object> map;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(CPQDNDTO cPQDNDTO) {
		return cPQDNFacade.creatCPQDN(cPQDNDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(CPQDNDTO cPQDNDTO) {
		return cPQDNFacade.updateCPQDN(cPQDNDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(CPQDNDTO cPQDNDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<CPQDNDTO> all = cPQDNFacade.pageQueryCPQDN(cPQDNDTO, page, pagesize);
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
        return cPQDNFacade.removeCPQDNs(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return cPQDNFacade.getCPQDN(id);
	}
	
	@ResponseBody
	@RequestMapping("/getQDNChk/{id}")
	public String getQDNChk(@PathVariable Long id) {
		CPQDNDTO cpQDNDTO = cPQDNFacade.findCPQDN(id);
		if (cpQDNDTO == null) {
			return null ;
		}
		return cpQDNDTO.getQdnChk();
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
	public InvokeResult dispose(CPQDNDTO cpQDNDTO) {
		cpQDNDTO = this.lastModifyBase(cpQDNDTO);
		return cPQDNFacade.disposeCPQDN(cpQDNDTO);
	}
}
