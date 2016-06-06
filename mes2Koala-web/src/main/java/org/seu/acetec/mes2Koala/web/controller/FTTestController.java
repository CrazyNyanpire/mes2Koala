package org.seu.acetec.mes2Koala.web.controller;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.FTTestFacade;
import org.seu.acetec.mes2Koala.facade.dto.FT_TestDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/FT_Test")
public class FTTestController {

    @Inject
    private FTTestFacade ftTestFacade;

    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(FT_TestDTO fT_TestDTO) {
        return ftTestFacade.creatFT_Test(fT_TestDTO);
    }

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(FT_TestDTO fT_TestDTO) {
        return ftTestFacade.updateFT_Test(fT_TestDTO);
    }

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(FT_TestDTO fT_TestDTO, @RequestParam int page, @RequestParam int pagesize) {
        Page<FT_TestDTO> all = ftTestFacade.pageQueryFT_Test(fT_TestDTO, page, pagesize);
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
        return ftTestFacade.removeFT_Tests(idArrs);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return ftTestFacade.getFT_Test(id);
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
