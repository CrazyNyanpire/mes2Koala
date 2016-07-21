package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;

import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.seu.acetec.mes2Koala.facade.CPWaferFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/CPWafer")
public class CPWaferController extends BaseController{
		
	@Inject
	private CPWaferFacade cPWaferFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(CPWaferDTO cPWaferDTO) {
		return cPWaferFacade.creatCPWafer(cPWaferDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(CPWaferDTO cPWaferDTO) {
		return cPWaferFacade.updateCPWafer(cPWaferDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(CPWaferDTO cPWaferDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<CPWaferDTO> all = cPWaferFacade.pageQueryCPWafer(cPWaferDTO, page, pagesize);
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
        return cPWaferFacade.removeCPWafers(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return cPWaferFacade.getCPWafer(id);
	}
	
		@ResponseBody
	@RequestMapping("/findCpLotByCPWafer/{id}")
	public Map<String, Object> findCpLotByCPWafer(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", cPWaferFacade.findCpLotByCPWafer(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findCpCustomerWaferByCPWafer/{id}")
	public Map<String, Object> findCpCustomerWaferByCPWafer(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", cPWaferFacade.findCpCustomerWaferByCPWafer(id));
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
	@RequestMapping("/changeStatusPassed")
	public InvokeResult changeStatusPassed(@RequestParam String ids) {
		CPWaferDTO cPWaferDTO = new CPWaferDTO();
		cPWaferDTO = this.lastModifyBase(cPWaferDTO);
		return cPWaferFacade.changeStatusPassed(ids);
	}
	
	@ResponseBody
	@RequestMapping("/saveCheck")
	public InvokeResult saveCheck(@RequestParam String ids) {
		CPWaferDTO cPWaferDTO = new CPWaferDTO();
		cPWaferDTO = this.lastModifyBase(cPWaferDTO);
		return cPWaferFacade.saveCheck(ids,cPWaferDTO);
	}
	
	@ResponseBody
	@RequestMapping("/getCPWaferInfo")
	public InvokeResult getCPWaferInfo(@RequestParam Long cpLotId,@RequestParam Long nodeId) {
		return cPWaferFacade.getCPWaferInfoByNode(cpLotId,nodeId);
	}
	
	
	@ResponseBody
	@RequestMapping("/getCPWaferCheck")
	public InvokeResult getCPWaferCheck(@RequestParam Long cpLotId,@RequestParam Long nodeId) {
		return cPWaferFacade.getCPWaferCheck(cpLotId,nodeId);
	}
	
	
	@ResponseBody
	@RequestMapping("/getCPWaferYieldByLotId")
	public InvokeResult getCPWaferYieldByLotId(@RequestParam Long cpLotId) {
		List<CPWaferDTO> list = cPWaferFacade.getCPWaferYieldByLotId(cpLotId);
		return InvokeResult.success(list);
	}
	
}
