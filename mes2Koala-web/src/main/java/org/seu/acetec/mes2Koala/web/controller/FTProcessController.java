package org.seu.acetec.mes2Koala.web.controller;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.FTProcessFacade;
import org.seu.acetec.mes2Koala.facade.dto.FTProcessDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/FTProcess")
public class FTProcessController {

    @Inject
    private FTProcessFacade ftProcessFacade;

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(FTProcessDTO FTProcessDTO, @RequestParam int page,
                         @RequestParam int pagesize) {
        Page<FTProcessDTO> all = ftProcessFacade.pageQueryFTProcess(FTProcessDTO, page, pagesize);
        return all;
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return ftProcessFacade.getFTProcess(id);
    }

    @ResponseBody
    @RequestMapping("/pageQueryFTNodesByFTProcessId/{id}")
    public Page pageQueryFTNodesByFTProcessId(@PathVariable Long id,
                                              @RequestParam int page, @RequestParam int pagesize) {
        return ftProcessFacade.pageQueryFTNodesByFTProcessId(id, page, pagesize);
    }

    /**
     * 获取所有测试站点的名称
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/findComposedTestNodeNamesByLotId/{id}")
    public List<String> findComposedTestNodeNamesByLotId(@PathVariable Long id) {
        return ftProcessFacade.findComposedTestNodeNamesByFTLotId(id);
    }

    @ResponseBody
    @RequestMapping("/findComposedTestNodeNamesByProductId/{id}")
    public List<String> findComposedTestNodeNamesByProductId(@PathVariable Long id) {
        return ftProcessFacade.findComposedTestNodeNamesByProductId(id);
    }

    @ResponseBody
    @RequestMapping("/findFTProcessByFTLotId/{id}")
    public InvokeResult findFTProcessByFTLotId(@PathVariable Long id) {
        return ftProcessFacade.findFTProcessByFTLotId(id);
    }

    @ResponseBody
    @RequestMapping("/updateFTNote")
    public InvokeResult updateFTNote(@RequestParam Long processId,@RequestParam String note) {
        return ftProcessFacade.updateFTNote(processId,note);
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
