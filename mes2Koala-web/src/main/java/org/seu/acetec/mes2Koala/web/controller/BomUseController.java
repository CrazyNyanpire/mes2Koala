package org.seu.acetec.mes2Koala.web.controller;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.BomUseFacade;
import org.seu.acetec.mes2Koala.facade.dto.BomUseDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/BomUse")
public class BomUseController {

    @Inject
    private BomUseFacade bomUseFacade;

    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(BomUseDTO bomUseDTO) {
        return bomUseFacade.createBomUse(bomUseDTO);
    }

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(BomUseDTO bomUseDTO) {
        return bomUseFacade.updateBomUse(bomUseDTO);
    }

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(BomUseDTO bOMListDTO, @RequestParam int page, @RequestParam int pagesize) {
        Page<BomUseDTO> all = bomUseFacade.pageQueryBomUse(bOMListDTO, page, pagesize);
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
        return bomUseFacade.removeBomUses(idArrs);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return bomUseFacade.getBomUse(id);
    }

    @ResponseBody
    @RequestMapping("/findBomByBomUse/{id}")
    public Map<String, Object> findBomByBomUse(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", bomUseFacade.findBomTemplateByBomUseId(id));
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

}
