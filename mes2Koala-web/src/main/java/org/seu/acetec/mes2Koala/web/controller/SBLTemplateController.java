package org.seu.acetec.mes2Koala.web.controller;

import net.sf.json.JSONArray;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.FTInfoFacade;
import org.seu.acetec.mes2Koala.facade.SBLTemplateFacade;
import org.seu.acetec.mes2Koala.facade.dto.SBLTemplateDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/SBLTemplate")
public class SBLTemplateController {

    @Inject
    private SBLTemplateFacade sblTemplateFacade;

    @Inject
    private FTInfoFacade fTInfoFacade;


    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(SBLTemplateDTO sblTemplateDTO) {
        return sblTemplateFacade.updateSBLTemplate(sblTemplateDTO);
    }

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(SBLTemplateDTO sblTemplateDTO, @RequestParam int page, @RequestParam int pagesize) {
        Page<SBLTemplateDTO> all = sblTemplateFacade.pageQuerySBLTemplate(sblTemplateDTO, page, pagesize);
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
        return sblTemplateFacade.removeSBLTemplates(idArrs);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return sblTemplateFacade.getSBLTemplate(id);
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
