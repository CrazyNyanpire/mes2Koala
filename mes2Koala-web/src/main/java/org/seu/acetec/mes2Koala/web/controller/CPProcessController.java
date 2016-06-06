package org.seu.acetec.mes2Koala.web.controller;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.CPProcessFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPProcessDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
@Controller
@RequestMapping("/CPProcess")
public class CPProcessController {

    @Inject
    private CPProcessFacade cpProcessFacade;

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(CPProcessDTO FTProcessDTO, @RequestParam int page,
                         @RequestParam int pagesize) {
        Page<CPProcessDTO> all = cpProcessFacade.pageQueryCPProcess(FTProcessDTO, page, pagesize);
        return all;
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return cpProcessFacade.getCPProcess(id);
    }

    @ResponseBody
    @RequestMapping("/pageQueryCPNodesByCPProcessId/{id}")
    public Page pageQueryCPNodesByCPProcessId(@PathVariable Long id,
                                              @RequestParam int page, @RequestParam int pagesize) {
        return cpProcessFacade.pageQueryCPNodesByCPProcessId(id, page, pagesize);
    }

    @ResponseBody
    @RequestMapping("/findCPProcessByCPLotId/{id}")
    public InvokeResult findCPProcessByCPLotId(@PathVariable Long id) {
        return cpProcessFacade.findCPProcessByCPLotId(id);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
        // CustomDateEditor 可以换成自己定义的编辑器。
        // 注册一个Date 类型的绑定器 。
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }
}
