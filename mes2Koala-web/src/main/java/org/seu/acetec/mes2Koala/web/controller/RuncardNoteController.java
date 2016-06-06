package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;
import org.springframework.web.bind.WebDataBinder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.RuncardNoteFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/RuncardNote")
public class RuncardNoteController {
		
	@Inject
	private RuncardNoteFacade runcardNoteFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(RuncardNoteDTO runcardNoteDTO) {
		return runcardNoteFacade.creatRuncardNote(runcardNoteDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(RuncardNoteDTO runcardNoteDTO) {
		return runcardNoteFacade.updateRuncardNote(runcardNoteDTO);
	}
	
	/**
	 * 区别于生成的update，该方法接收一个json字符串并解析为runcardNoteDTO并进行update
	 * @param json 通过post方法传入的json字符串
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateJson")
	public InvokeResult updateJson(@RequestParam String json){
		RuncardNoteDTO runcardNoteDTO = null;
		try{
			JSONObject jsonObject = JSONObject.fromObject(json);
			//将json字符串转化为RuncardNoteDTO
			runcardNoteDTO = (RuncardNoteDTO) JSONObject.toBean(jsonObject, RuncardNoteDTO.class);
		} catch (Exception e){
			e.printStackTrace();
			return InvokeResult.failure("无法解析Json包!");
		}
		return runcardNoteFacade.updateRuncardNote(runcardNoteDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(RuncardNoteDTO runcardNoteDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<RuncardNoteDTO> all = runcardNoteFacade.pageQueryRuncardNote(runcardNoteDTO, page, pagesize);
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
        return runcardNoteFacade.removeRuncardNotes(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return runcardNoteFacade.getRuncardNote(id);
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
