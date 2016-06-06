package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;

import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.seu.acetec.mes2Koala.facade.common.SenderMailUtils;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/Demo")
public class DemoController {
		
	@Inject
	private SenderMailUtils senderMailUtils;
	
	@ResponseBody
	@RequestMapping("/mail")
	public InvokeResult mail() {
		senderMailUtils.sendMailHelper("MES2测试邮件发送", "MES2测试邮件发送", "harlow.zhou@acetecsemi.com");
		return InvokeResult.success();
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
