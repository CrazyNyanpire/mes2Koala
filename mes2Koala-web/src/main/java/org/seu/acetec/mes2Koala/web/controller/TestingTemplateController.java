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
import org.seu.acetec.mes2Koala.facade.TestingTemplateFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/TestingTemplate")
public class TestingTemplateController {
		
	@Inject
	private TestingTemplateFacade testingTemplateFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(TestingTemplateDTO testingTemplateDTO) {
		return testingTemplateFacade.creatTestingTemplate(testingTemplateDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(TestingTemplateDTO testingTemplateDTO) {
		return testingTemplateFacade.updateTestingTemplate(testingTemplateDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(TestingTemplateDTO testingTemplateDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<TestingTemplateDTO> all = testingTemplateFacade.pageQueryTestingTemplate(testingTemplateDTO, page, pagesize);
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
        return testingTemplateFacade.removeTestingTemplates(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return testingTemplateFacade.getTestingTemplate(id);
	}
	
	@ResponseBody
	@RequestMapping("/findTestingTemplate")
	public List<TestingTemplateDTO> queryAllTestingTemplate() {
		return testingTemplateFacade.findAllTestingTemplate();
	}
	
    @InitBinder    
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
        dateFormat.setLenient(false);    
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
        //CustomDateEditor 鍙互鎹㈡垚鑷繁瀹氫箟鐨勭紪杈戝櫒銆� 
        //娉ㄥ唽涓�釜Date 绫诲瀷鐨勭粦瀹氬櫒 銆�
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }
	
}
