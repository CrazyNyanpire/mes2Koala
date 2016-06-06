package org.seu.acetec.mes2Koala.web.controller;

import help.ExcelTemplateHelper;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.ExcelFacade;
import org.seu.acetec.mes2Koala.facade.LabelFacade;
import org.seu.acetec.mes2Koala.facade.dto.LabelDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Label")
public class LabelController extends BaseController{

    @Inject
    ExcelFacade excelFacade;
    @Inject
    private LabelFacade labelFacade;

    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(LabelDTO labelDTO) {
    	this.createBase(labelDTO);
        return labelFacade.creatLabel(labelDTO);
    }

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(LabelDTO labelDTO) {
    	this.lastModifyBase(labelDTO);
        return labelFacade.updateLabel(labelDTO);
    }

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(LabelDTO labelDTO, @RequestParam int page, @RequestParam int pagesize) {
        Page<LabelDTO> all = labelFacade.pageQueryLabel(labelDTO, page, pagesize);
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
        return labelFacade.removeLabels(idArrs);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return labelFacade.getLabel(id);
    }

    @ResponseBody
    @RequestMapping("/findLabel")
    public List<LabelDTO> queryAllTestingTemplate() {
        return labelFacade.findAllLabel();
    }

    /**
     * 导出成Excel
     *
     * @param ids
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/exportExcel")
    public InvokeResult exportExcel(@RequestParam String ids, HttpServletRequest request) {
        Long[] idArray = ExcelTemplateHelper.extractIdArray(ids, ",");
        return excelFacade.exportLabel(this.getClass(), idArray);
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
    @RequestMapping("/getLabelByType")
    public List<LabelDTO> getLabelByType(LabelDTO labelDTO) {
        return labelFacade.getLabelByType(labelDTO);
    }

}
