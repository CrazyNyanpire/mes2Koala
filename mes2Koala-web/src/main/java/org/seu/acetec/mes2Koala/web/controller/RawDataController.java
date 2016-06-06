package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;

import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.core.domain.RawData;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.RawDataFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/RawData")
public class RawDataController {
		
	@Inject
	private RawDataFacade rawDataFacade;
	
	@ResponseBody
	@RequestMapping("/add")
	public InvokeResult add(RawDataDTO rawDataDTO) {
		return rawDataFacade.creatRawData(rawDataDTO);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public InvokeResult update(RawDataDTO rawDataDTO) {
		return rawDataFacade.updateRawData(rawDataDTO);
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(RawDataDTO rawDataDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<RawDataDTO> all = rawDataFacade.pageQueryRawData(rawDataDTO, page, pagesize);
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
        return rawDataFacade.removeRawDatas(idArrs);
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return rawDataFacade.getRawData(id);
	}
	
    /**
     * getRawData
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRawData/{id}")
    public InvokeResult getRawData(@PathVariable Long id) {
        return rawDataFacade.getRawData(id);
    }
    
    /**
     * addRawData
     * @param rawData,id
     * @return
     */
    @ResponseBody
    @RequestMapping("/addRawData/{id}")
    public InvokeResult addRawData(@PathVariable Long id, @RequestParam("rawData") String rawData) {
    	RawDataDTO rawDataDto = new RawDataDTO();
    	String[] param  = rawData.split(",");
    	rawDataDto.setProductID(param[0]);
    	rawDataDto.setNotchSide(param[1]);
    	rawDataDto.setBindefinitionFile(param[2]);
    	rawDataDto.setGridXmax(param[3]);
    	rawDataDto.setFabSite(param[4]);
    	rawDataDto.setXDieSize(param[5]);
    	rawDataDto.setYDieSize(param[6]);
    	rawDataDto.setCustomerCodeID(param[7]);
    	InternalProductDTO internalProductDTO = new InternalProductDTO();
    	internalProductDTO.setId(id);
    	rawDataDto.setInternalProductDTO(internalProductDTO);
        return rawDataFacade.creatRawData(rawDataDto);
    }
    
	@ResponseBody
	@RequestMapping("/deleteRawData/{id}")
	public InvokeResult remove(@PathVariable Long id) {
		return rawDataFacade.removeRawData(id);
	}
	
	@ResponseBody
	@RequestMapping("/updateRawData/{id}")
	public InvokeResult updateRawData(@PathVariable Long id,RawDataDTO rawDataDto) {
    	InternalProductDTO internalProductDTO = new InternalProductDTO();
    	internalProductDTO.setId(id);
    	rawDataDto.setInternalProductDTO(internalProductDTO);
		return rawDataFacade.updateRawData(rawDataDto);
	}
    

}
