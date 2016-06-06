package org.seu.acetec.mes2Koala.web.controller;

import net.sf.json.JSONArray;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.EQCSettingFacade;
import org.seu.acetec.mes2Koala.facade.dto.EQCSettingDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/EQCSetting")
public class EQCSettingController {

    @Inject
    private EQCSettingFacade eqcSettingFacade;

    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(@RequestParam("eqcSettings") String eqcSettings) {
        List<EQCSettingDTO> list = (List) JSONArray.toCollection(JSONArray.fromObject(eqcSettings), EQCSettingDTO.class);
        return eqcSettingFacade.replaceAll(list);
    }

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(EQCSettingDTO eQCsettingDTO) {
        return eqcSettingFacade.updateEQCSetting(eQCsettingDTO);
    }

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(EQCSettingDTO eQCsettingDTO, @RequestParam int page, @RequestParam int pagesize) {
        Page<EQCSettingDTO> all = eqcSettingFacade.pageQueryEQCSetting(eQCsettingDTO, page, pagesize);
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
        return eqcSettingFacade.removeEQCSettings(idArrs);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return eqcSettingFacade.getEQCSetting(id);
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
