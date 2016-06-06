package org.seu.acetec.mes2Koala.web.controller;

import help.ExcelTemplateHelper;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.ExcelFacade;
import org.seu.acetec.mes2Koala.facade.FTLotFacade;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.FTLotPageVo;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/FTLot")
public class FTLotController {

    @Inject
    private FTLotFacade ftLotFacade;

    @Inject
    private ExcelFacade excelFacade;

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(FTLotDTO fTLotDTO) {
        return ftLotFacade.updateFTLot(fTLotDTO);
    }

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(FTLotDTO fTLotDTO, @RequestParam int page, @RequestParam int pagesize) {
        Page<FTLotDTO> all = ftLotFacade.pageQueryFTLot(fTLotDTO, page, pagesize);
        return all;
    }

    @ResponseBody
    @RequestMapping("/ftPageJson")
    public Page ftPageJson(FTLotPageVo fTLotDTO, @RequestParam int page, @RequestParam int pagesize) {
        Page<FTLotPageVo> all = ftLotFacade.pageQueryFTPage(fTLotDTO, page, pagesize);
        return all;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public InvokeResult remove(@RequestParam String ids) {
        String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            idArrs[i] = Long.parseLong(value[i]);
        }
        return ftLotFacade.removeFTLots(idArrs);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return ftLotFacade.getFTLot(id);
    }

    @ResponseBody
    @RequestMapping("/exportExcel")
    public InvokeResult exportExcel(@RequestParam String ids) {
        Long[] idArray = ExcelTemplateHelper.extractIdArray(ids, ",");
        return excelFacade.exportFTLot(this.getClass(), idArray);
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
    @RequestMapping("/getPlaceHold/{id}")
    public InvokeResult getPlaceHold(@PathVariable Long id) {
        return ftLotFacade.getPlaceHold(id);
    }
    
    @ResponseBody
    @RequestMapping("/getProcessTemplateByFtLotId/{id}")
    public InvokeResult getProcessTemplateByFtLotId(@PathVariable Long id) {
        return ftLotFacade.getProcessTemplateByFtLotId(id);
    }
     
}
