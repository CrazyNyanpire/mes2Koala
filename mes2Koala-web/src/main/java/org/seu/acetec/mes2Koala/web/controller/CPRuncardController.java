package org.seu.acetec.mes2Koala.web.controller;

import net.sf.json.JSONObject;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.RuncardSignDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.CPSpecialFormStatusVo;
import org.seu.acetec.mes2Koala.facade.service.CPRuncardfacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Created by LCN on 2016/4/27.
 */
@Controller
@RequestMapping(value = "/cpRuncardController")
public class CPRuncardController {
    @Inject
    private CPRuncardfacade cpRuncardfacade;


    /**
     * 保存runcard信息
     *
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveRuncard", method = RequestMethod.POST)
    public InvokeResult saveRuncard(@RequestBody String json) {

        JSONObject jsonObject = JSONObject.fromObject(json);
        Long id = Long.valueOf(jsonObject.optString("cpinfoId"));
        String currentSite = jsonObject.optString("currentSite");
        String totalSites = jsonObject.optString("totalSites");
        String data = jsonObject.optString("data");

        String[] totalSitesArr = totalSites.split(",");



        //保存数据
        cpRuncardfacade.saveRuncardInfo(id, currentSite, data);

        //保存站点信息
        cpRuncardfacade.saveTotalSitesOfRuncard(id,totalSites);

        boolean isRuncardFinished = cpRuncardfacade.isRuncardFinished(id, totalSitesArr);

        //如果站点全部保存, 发送邮件
        if (isRuncardFinished) {
            cpRuncardfacade.sendEmailToPersons(id);
        }

        return InvokeResult.success("保存成功");
    }


    /**
     * 获取runcard对应站点的信息
     *
     * @param cpinfoId
     * @param currentSite
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRuncardInfo", method = RequestMethod.GET)
    public InvokeResult getRuncardInfo(@RequestParam String cpinfoId, @RequestParam String currentSite) {
        Long id = Long.parseLong(cpinfoId);

        String data = cpRuncardfacade.getRuncardInfoBySiteName(id, currentSite);

        return InvokeResult.success(data);
    }


    /***
     * 判断runcard是否全部填写完成
     *
     * @param cpinfoId
     * @param totalSites 对应站点拼在一起的字符串  以 , 分隔
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isRuncardFinished", method = RequestMethod.GET)
    public InvokeResult isRuncardFinished(@RequestParam String cpinfoId, @RequestParam String totalSites) {
        Long id = Long.parseLong(cpinfoId);
        String[] totalSitesArr = totalSites.split(",");
        boolean isReplaced = false;


        boolean isFinished = cpRuncardfacade.isRuncardFinished(id, totalSitesArr);
        if (isFinished) {
            return InvokeResult.success("runcardinfo填写完成");
        }
        return InvokeResult.failure("runcardinfo没有填写完成");
    }


    /**
     * 对runcard进行授权
     *
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/signRuncardInfo", method = RequestMethod.POST)
    public InvokeResult signRuncardInfo(@RequestBody String json) {

        JSONObject jsonObject = JSONObject.fromObject(json);
        Long cpinfoId = Long.valueOf(jsonObject.optString("cpinfoId"));
        Long userId = Long.valueOf(jsonObject.optString("userId"));
        String opinion = jsonObject.optString("opinion");
        String note = jsonObject.optString("note");

        InvokeResult invokeResult = cpRuncardfacade.signRuncardInfo(cpinfoId, userId, opinion, note);

        return invokeResult;
    }


    /**
     * 判断runcardinfo 是否已经签核
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isRuncardInfoSigned", method = RequestMethod.GET)
    public InvokeResult isRuncardInfoSigned(@RequestParam Long cpinfoId) {
        boolean isSigned = cpRuncardfacade.isRuncardInfoSigned(cpinfoId);
        if (isSigned) {
            return InvokeResult.success("已经签核过");
        }
        return InvokeResult.failure("还没有签核");
    }


    /**
     * 根据人员id返回部门信息    如果已经签核  返回上次签核的内容
     *
     * @param cpinfoId
     * @param employeeid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDepartmentByEmployeeId", method = RequestMethod.GET)
    public InvokeResult getDepartmentByEmployeeId(@RequestParam String cpinfoId, @RequestParam String employeeid) {
        RuncardSignDTO runcardSignDTO = cpRuncardfacade.getRuncardSignInfo(Long.parseLong(cpinfoId), Long.parseLong(employeeid));
        return InvokeResult.success(runcardSignDTO);
    }


    /**
     * 获得特殊表单
     *
     * @param cpinfoId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSpecialForm", method = RequestMethod.GET)
    public InvokeResult getSpecialForm(@RequestParam String cpinfoId, @RequestParam String formType) {
        Long id = Long.parseLong(cpinfoId);
        String data = cpRuncardfacade.getSpecialFormByCPinfoId(id, formType);
        return InvokeResult.success(data);
    }

    /**
     * 保存特殊表单
     *
     * @returnd
     */
    @ResponseBody
    @RequestMapping(value = "/saveSpecialForm", method = RequestMethod.POST)
    public InvokeResult saveSpecialForm(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        Long id = Long.valueOf(jsonObject.optString("cpinfoId"));
        String formType = jsonObject.optString("formType");
        String data = jsonObject.optString("data");
        return cpRuncardfacade.saveSpecialForm(id, formType, data);
    }


    /**
     * @param model
     * @param cpinfoId
     * @return
     */
    @RequestMapping(value = "/getSpecialFormPage", method = RequestMethod.GET)
    public String getSpecialFormPage(Model model, @RequestParam String cpinfoId) {
        model.addAttribute("cpinfoId", cpinfoId);
        return "/domain/CPInfo-specialForm";
    }


    /**
     * 获得ue编辑器的页面
     * @param model
     * @param cpinfoId
     * @param formType
     * @return
     */
    @RequestMapping(value = "/getSpecialFormEditorPage", method = RequestMethod.GET)
    public String getSpecialFormEditorPage(Model model, @RequestParam String cpinfoId, @RequestParam String formType) {
        model.addAttribute("cpinfoId", cpinfoId);
        model.addAttribute("formType", formType);
        return "/ueditor/cpRuncard/SpecialForm";
    }


    /**
     * 保存特殊表单的打印状态
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveSpecialFormStaus", method = RequestMethod.POST)
    public InvokeResult saveSpecialFormStaus(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        Long cpinfoId = jsonObject.optLong("cpinfoId");
        Boolean MCP_Cover1SheetStatus = jsonObject.optBoolean("MCP_Cover1SheetStatus");
        Boolean Sheet1Status = jsonObject.optBoolean("Sheet1Status");
        Boolean Sheet2Status = jsonObject.optBoolean("Sheet2Status");
        Boolean CP1SheetStatus = jsonObject.optBoolean("CP1SheetStatus");
        Boolean CP2SheetStatus = jsonObject.optBoolean("CP2SheetStatus");
        Boolean Map_Shift1SheetStatus = jsonObject.optBoolean("Map_Shift1SheetStatus");

        CPSpecialFormStatusVo cpSpecialFormStatusVo = new CPSpecialFormStatusVo();
        cpSpecialFormStatusVo.setMCP_COVER1SheetStatus(MCP_Cover1SheetStatus);
        cpSpecialFormStatusVo.setSheet1Status(Sheet1Status);
        cpSpecialFormStatusVo.setSheet2Status(Sheet2Status);
        cpSpecialFormStatusVo.setCP2SheetStatus(CP2SheetStatus);
        cpSpecialFormStatusVo.setCP1SheetStatus(CP1SheetStatus);
        cpSpecialFormStatusVo.setMAP_SHIFT1Status(Map_Shift1SheetStatus);

        return cpRuncardfacade.saveSpecialFormStatus(cpinfoId, cpSpecialFormStatusVo);
    }


    /**
     * 获得特殊表单的打印状态
     * @param cpinfoId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSpecialFormStatus", method = RequestMethod.GET)
    public InvokeResult getSpecialFormStatus(@RequestParam String cpinfoId) {
        Long id = Long.parseLong(cpinfoId);
        return cpRuncardfacade.getSpecialFormStatus(id);
    }



    /**
     * 获得特殊表单的打印状态
     * @param cpLotId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSpecialFormStatusByCPLotId", method = RequestMethod.GET)
    public InvokeResult getSpecialFormStatusByCPLotId(@RequestParam String cpLotId) {
        Long id = Long.parseLong(cpLotId);
        return cpRuncardfacade.getSpecialFormStatusByCPLotId(id);
    }


    /**
     * 得到ue编辑器的页面
     *
     * @param model
     * @param cpinfoId
     * @param currentSite 当前的站点类型
     * @param totalSites  所有的站点类型
     * @return
     */
    @RequestMapping(value = "/getUEditorPage", method = RequestMethod.GET)
    public String getUEditorPage(Model model, @RequestParam String cpinfoId, @RequestParam String currentSite, @RequestParam String totalSites) {

        model.addAttribute("totalSites", totalSites);
        model.addAttribute("cpinfoId", cpinfoId);
        model.addAttribute("currentSite", currentSite);

        return "/ueditor/cpRuncard/UEditor";
    }


    /**
     * 获得runcard中各自站点的完成状态
     * @param cpinfoId
     * @param totalSites
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRuncardFinishedStatus", method = RequestMethod.GET)
    public InvokeResult getRuncardFinishedStatus(@RequestParam String cpinfoId, @RequestParam String totalSites){
        Long id = Long.parseLong(cpinfoId);
        String[] totalSitesArr = totalSites.split(",");

        return cpRuncardfacade.getRuncardFinishedStatus(id,totalSitesArr);
    }



    /***
     * 判断runcard是否全部填写完成
     * @param cpinfoId
     * @param totalSites 对应站点拼在一起的字符串  以 , 分隔
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isRuncardFinished2", method = RequestMethod.GET)
    public InvokeResult isRuncardFinished2(@RequestParam String cpinfoId, @RequestParam String totalSites) {
        Long id = Long.parseLong(cpinfoId);
        String[] totalSitesArr = totalSites.split(",");
        boolean isReplaced = false;


        boolean isFinished = cpRuncardfacade.isRuncardFinished2(id, totalSitesArr);
        if (isFinished) {
            return InvokeResult.success("runcardinfo填写完成");
        }
        return InvokeResult.failure("runcardinfo没有填写完成");
    }




    /**
     * 根据state数据展现不同的runcard数据
     * @param cpLotId
     * @param specialFormStr
     * @param state
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRuncardInfoByState", method = RequestMethod.GET)
    public InvokeResult getRuncardInfoByState( @RequestParam String cpLotId, @RequestParam String specialFormStr, @RequestParam String state) {

        String[] specialFormStrArr = specialFormStr.split(",");

        return cpRuncardfacade.getRuncardInfoByState(Long.parseLong(cpLotId), specialFormStrArr, Long.parseLong(state));
    }


    /**
     * 获得ue编辑器的页面  实际数据的请求在页面中完成
     * @param model
     * @param cpLotId
     * @param specialFormStr
     * @param state
     * @return
     */
    @RequestMapping(value = "/getPageForRuncard", method = RequestMethod.GET)
    public String getPageForRuncard(Model model, @RequestParam String cpLotId, @RequestParam String specialFormStr, @RequestParam String state) {
        model.addAttribute("cpLotId", cpLotId);
        model.addAttribute("specialFormStr", specialFormStr);
        model.addAttribute("state", state);
        return "/ueditor/cpTest/runcardPageForCPTest";
    }

}
