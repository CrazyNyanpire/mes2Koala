package org.seu.acetec.mes2Koala.web.controller;

import net.sf.json.JSONObject;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.core.domain.CustomerFTLot;
import org.seu.acetec.mes2Koala.facade.CustomerFTLotFacade;
import org.seu.acetec.mes2Koala.facade.dto.CustomerFTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.RuncardSignDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.SpecialFormStatusVo;
import org.seu.acetec.mes2Koala.facade.service.FTRuncardfacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Created by LCN on 2016/3/5.
 */

@Controller
@RequestMapping(value = "/ueditor")
public class UEditorController {

    @Inject
    private FTRuncardfacade FTRuncardfacade;
    @Inject
    private CustomerFTLotFacade customerFTLotFacade;

    /**
     * 保存runcard信息
     *
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveRuncard", method = RequestMethod.POST)
    public InvokeResult saveRuncard(@RequestBody String json) {
        /**
         * json数据
         *        FTInfoId    FTInfo产品的id
         *        currentSite 当前站点
         *        totalSites  该流程的所有站点
         *        data        需要保存的数据
         */
        JSONObject jsonObject = JSONObject.fromObject(json);
        Long id = Long.valueOf(jsonObject.optString("ftinfoId"));
        String currentSite = jsonObject.optString("currentSite");
        String totalSites = jsonObject.optString("totalSites");
        currentSite = currentSite.replace("-", "_");
        String data = jsonObject.optString("data");

        boolean isFirstMatched = false;
        //只保存一个名为Test-*的数据
        String[] totalSitesArr = totalSites.split(",");
        for (int i = 0; i < totalSitesArr.length; i++) {
            totalSitesArr[i] = totalSitesArr[i].replace("-", "_");
            //如果是第一次匹配
            if (!isFirstMatched) {
                if (totalSitesArr[i].matches("Test_.+")) {
                    if (currentSite.equals(totalSitesArr[i])) {
                        currentSite = "siteTest";
                    }
                    isFirstMatched = true;
                    totalSitesArr[i] = "siteTest";
                }
            }
        }

        //保存数据
        FTRuncardfacade.saveRuncardInfo(id, currentSite, data);

        boolean isRuncardFinished = FTRuncardfacade.isRuncardFinished(id, totalSitesArr);

        if (isRuncardFinished) {
            FTRuncardfacade.sendEmailToPersons(id);
        }

        return InvokeResult.success("保存成功");
    }


    /**
     * 得到ue编辑器的页面
     *
     * @param model
     * @param ftinfoId
     * @param currentSite 当前的站点类型
     * @param totalSites  所有的站点类型
     * @return
     */
    @RequestMapping(value = "/getUEditorPage", method = RequestMethod.GET)
    public String getUEditorPage(Model model, @RequestParam String ftinfoId, @RequestParam String currentSite, @RequestParam String totalSites) {

        model.addAttribute("totalSites", totalSites);
        model.addAttribute("ftinfoId", ftinfoId);
        model.addAttribute("currentSite", currentSite);
        return "/ueditor/UEditor";
    }


    /**
     * 获取runcard对应站点的信息
     *
     * @param ftinfoId
     * @param currentSite
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRuncardInfo", method = RequestMethod.GET)
    public InvokeResult getRuncardInfo(@RequestParam String ftinfoId, @RequestParam String currentSite) {
        Long id = Long.parseLong(ftinfoId);
        String data = null;
        if (currentSite.matches("Test-.+")) {
            currentSite = "siteTest";
        }
        data = FTRuncardfacade.getRuncardInfoBySiteName(id, currentSite);

        return InvokeResult.success(data);
    }


    /***
     * 判断runcard是否全部填写完成
     *
     * @param ftinfoId   ftinfoid
     * @param totalSites 对应站点拼在一起的字符串  以 , 分隔
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isRuncardFinished", method = RequestMethod.GET)
    public InvokeResult isRuncardFinished(@RequestParam String ftinfoId, @RequestParam String totalSites) {
        Long id = Long.parseLong(ftinfoId);
        String[] totalSitesArr = totalSites.split(",");
        boolean isReplaced = false;
        //将 - 换成 _
        for (int i = 0; i < totalSitesArr.length; i++) {
            totalSitesArr[i] = totalSitesArr[i].replace("-", "_");
        }
        //把第一个Test_换成Test_TEST
        for (int i = 0; i < totalSitesArr.length; i++) {
            if (!isReplaced) {
                if (totalSitesArr[i].matches("Test_.+")) {
                    totalSitesArr[i] = "siteTest";
                    isReplaced = true;
                }
            }
        }
        boolean isFinished = FTRuncardfacade.isRuncardFinished(id, totalSitesArr);
        if (isFinished) {
            return InvokeResult.success("runcardinfo填写完成");
        }
        return InvokeResult.failure("runcardinfo没有填写完成");
    }


    @ResponseBody
    @RequestMapping(value = "/getRuncardFinishedStatus", method = RequestMethod.GET)
    public InvokeResult getRuncardFinishedStatus(@RequestParam String ftinfoId, @RequestParam String totalSites){
        Long id = Long.parseLong(ftinfoId);
        String[] totalSitesArr = totalSites.split(",");
        boolean isReplaced = false;
        //将 - 换成 _
        for (int i = 0; i < totalSitesArr.length; i++) {
            totalSitesArr[i] = totalSitesArr[i].replace("-", "_");
        }
        //把第一个Test_换成Test_TEST
        for (int i = 0; i < totalSitesArr.length; i++) {
            if (!isReplaced) {
                if (totalSitesArr[i].matches("Test_.+")) {
                    totalSitesArr[i] = "siteTest";
                    isReplaced = true;
                }
            }
        }

        return FTRuncardfacade.getRuncardFinishedStatus(id,totalSitesArr);


    }


    /**
     * 下单页面判断
     *
     * @param customerLotId
     * @param totalSites
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isRuncardFinished2", method = RequestMethod.GET)
    public InvokeResult isRuncardFinished2(@RequestParam String customerLotId, @RequestParam String totalSites) {
        Long id = Long.parseLong(customerLotId);
        String[] totalSitesArr = totalSites.split(",");
        boolean isReplaced = false;
        //将 - 换成 _
        for (int i = 0; i < totalSitesArr.length; i++) {
            totalSitesArr[i] = totalSitesArr[i].replace("-", "_");
        }
        //把第一个Test_换成Test_TEST
        for (int i = 0; i < totalSitesArr.length; i++) {
            if (!isReplaced) {
                if (totalSitesArr[i].matches("Test_.+")) {
                    totalSitesArr[i] = "siteTest";
                    isReplaced = true;
                }
            }
        }
        boolean isFinished = FTRuncardfacade.isRuncardFinished2(id, totalSitesArr);
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
        Long ftinfoId = Long.valueOf(jsonObject.optString("ftinfoId"));
        Long userId = Long.valueOf(jsonObject.optString("userId"));
        String opinion = jsonObject.optString("opinion");
        String note = jsonObject.optString("note");

        InvokeResult invokeResult = FTRuncardfacade.signRuncardInfo(ftinfoId, userId, opinion, note);

        return invokeResult;
    }


    /**
     * 判断runcardinfo 是否已经签核
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isRuncardInfoSigned", method = RequestMethod.GET)
    public InvokeResult isRuncardInfoSigned(@RequestParam Long ftinfoId) {
        boolean isSigned = FTRuncardfacade.isRuncardInfoSigned(ftinfoId);
        if (isSigned) {
            return InvokeResult.success("已经签核过");
        }
        return InvokeResult.failure("还没有签核");
    }


    /**
     * 判断runcardinfo 是否已经签核
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isRuncardInfoSigned2", method = RequestMethod.GET)
    public InvokeResult isRuncardInfoSigned2(@RequestParam Long ftinfoId) {
        Long id = FTRuncardfacade.convertFTLotIdtoFTinfoId(ftinfoId);
        boolean isSigned = FTRuncardfacade.isRuncardInfoSigned(id);
        if (isSigned) {
            return InvokeResult.success("已经签核过");
        }
        return InvokeResult.failure("还没有签核");
    }

    /**
     * 根据人员id返回部门信息    如果已经签核  返回上次签核的内容
     *
     * @param ftinfoId
     * @param employeeid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDepartmentByEmployeeId", method = RequestMethod.GET)
    public InvokeResult getDepartmentByEmployeeId(@RequestParam String ftinfoId, @RequestParam String employeeid) {
        RuncardSignDTO runcardSignDTO = FTRuncardfacade.getRuncardSignInfo(Long.parseLong(ftinfoId), Long.parseLong(employeeid));
        return InvokeResult.success(runcardSignDTO);
    }


    /**
     * 获得特殊表单
     *
     * @param ftinfoId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSpecialForm", method = RequestMethod.GET)
    public InvokeResult getSpecialForm(@RequestParam String ftinfoId, @RequestParam String formType) {
        Long id = Long.parseLong(ftinfoId);
        String data = FTRuncardfacade.getSpecialFormByFtinfoId(id, formType);
        return InvokeResult.success(data);
    }


    /**
     * 保存特殊表单
     *
     * @returnd
     */
    @ResponseBody
    @RequestMapping(value = "/saveSpecialForm", method = RequestMethod.POST)
    public InvokeResult saveSpecialForm(@RequestParam String json) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        Long id = Long.valueOf(jsonObject.optString("ftinfoId"));
        String formType = jsonObject.optString("formType");
        //TODO
        return InvokeResult.success();
    }


    /**
     * @param model
     * @param ftinfoId
     * @return
     */
    @RequestMapping(value = "/getSpecialFormPage", method = RequestMethod.GET)
    public String getSpecialFormPage(Model model, @RequestParam String ftinfoId) {
        model.addAttribute("ftinfoId", ftinfoId);
        return "/domain/FTInfo-specialForm";
    }


    @RequestMapping(value = "/getSpecialFormEditorPage", method = RequestMethod.GET)
    public String getSpecialFormEditorPage(Model model, @RequestParam String ftinfoId, @RequestParam String formType) {
        model.addAttribute("ftinfoId", ftinfoId);
        model.addAttribute("formType", formType);
        return "/ueditor/SpecialForm";
    }

    @ResponseBody
    @RequestMapping(value = "/saveSpecialFormStaus", method = RequestMethod.POST)
    public InvokeResult saveSpecialFormStaus(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        Long ftinfoId = jsonObject.optLong("ftinfoId");
        Boolean summarySheetStatus = jsonObject.optBoolean("summarySheetStatus");
        Boolean reelcodeSheetStatus = jsonObject.optBoolean("reelcodeSheetStatus");
        Boolean recordSheetStatus = jsonObject.optBoolean("recordSheetStatus");
        Boolean machineMaterialRecordSheetStatus = jsonObject.optBoolean("machineMaterialRecordSheetStatus");
        Boolean checkSheetStatus = jsonObject.optBoolean("checkSheetStatus");

        SpecialFormStatusVo specialFormStatusVo = new SpecialFormStatusVo();
        specialFormStatusVo.setSummarySheetStatus(summarySheetStatus);
        specialFormStatusVo.setReelcodeSheetStatus(reelcodeSheetStatus);
        specialFormStatusVo.setRecordSheetStatus(recordSheetStatus);
        specialFormStatusVo.setMachineMaterialRecordSheetStatus(machineMaterialRecordSheetStatus);
        specialFormStatusVo.setCheckSheetStatus(checkSheetStatus);
        return FTRuncardfacade.saveSpecialFormStatus(ftinfoId, specialFormStatusVo);
    }

    @ResponseBody
    @RequestMapping(value = "/getSpecialFormStatus", method = RequestMethod.GET)
    public InvokeResult getSpecialFormStatus(@RequestParam String ftinfoId) {
        Long id = Long.parseLong(ftinfoId);
        return FTRuncardfacade.getSpecialFormStatus(id);
    }

    @ResponseBody
    @RequestMapping(value = "/getRuncardInfoAfterReplaced", method = RequestMethod.GET)
    public InvokeResult getRuncardInfoAfterReplaced(@RequestParam String ftLotId) {
        Long id = Long.parseLong(ftLotId);
        return InvokeResult.success(FTRuncardfacade.getRuncardInfoAfterReplaced(id));
    }

    @ResponseBody
    @RequestMapping(value = "/getRuncardInfoByState", method = RequestMethod.GET)
    public InvokeResult getRuncardInfoByState( @RequestParam String ftLotId, @RequestParam String specialFormStr, @RequestParam String state) {

        String[] specialFormStrArr = specialFormStr.split(",");

        return FTRuncardfacade.getRuncardInfoByState(Long.parseLong(ftLotId), specialFormStrArr, Long.parseLong(state));
    }


    @RequestMapping(value = "/getPageForRuncard", method = RequestMethod.GET)
    public String getPageForRuncard(Model model, @RequestParam String ftLotId, @RequestParam String specialFormStr, @RequestParam String state) {
        model.addAttribute("ftLotId", ftLotId);
        model.addAttribute("specialFormStr", specialFormStr);
        model.addAttribute("state", state);
        return "/ueditor/ftTest/runcardPageForFTTest";
    }

}
