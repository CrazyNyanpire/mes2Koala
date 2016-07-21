package org.seu.acetec.mes2Koala.web.controller;

import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.ExcelFacade;
import org.seu.acetec.mes2Koala.facade.FTLotNodeOptionFacade;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTProcessDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTQDNDTO;
import org.seu.acetec.mes2Koala.facade.dto.SampleShippingDTO;
import org.seu.acetec.mes2Koala.infra.EmsFetcher;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import help.ExcelTemplateHelper;
import help.FilenameHelper;

import javax.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/FTLotNodeOption")
public class FTLotNodeOptionController extends BaseController {

    @Inject
    ExcelFacade excelFacade;
    
    @Inject
    EmsFetcher emsFetcher;
    
    @Inject
    private FTLotNodeOptionFacade ftLotNodeOptionFacade;

    private static boolean uploadFile(MultipartFile multipartFile, String path, String filename) {
        File targetFile = new File(path, filename);
        if (!targetFile.exists()) {
            if (targetFile.mkdirs()) {
                try {
                    multipartFile.transferTo(targetFile);
                    return true;
                } catch (IOException e) {
                }
            }
        }
        return false;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
        // CustomDateEditor 可以换成自己定义的编辑器。
        // 注册一个Date 类型的绑定器 。
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }

    @ResponseBody
    @RequestMapping("/hold")
    public InvokeResult hold(FTQDNDTO fTQDNDTO) {
        fTQDNDTO = this.createBase(fTQDNDTO);
        return ftLotNodeOptionFacade.hold(fTQDNDTO);
    }

    @ResponseBody
    @RequestMapping("/unhold")
    public InvokeResult unhold(FTLotDTO fTLotDTO) {
        fTLotDTO = this.createBase(fTLotDTO);
        return ftLotNodeOptionFacade.unhold(fTLotDTO);
    }

    @ResponseBody
    @RequestMapping("/futureHold")
    public InvokeResult futureHold(FTQDNDTO fTQDNDTO) {
        fTQDNDTO = this.createBase(fTQDNDTO);
        return ftLotNodeOptionFacade.futureHold(fTQDNDTO);
    }

    @ResponseBody
    @RequestMapping("/getFTResultByLotId/{id}")
    public InvokeResult getFTResultByLotId(@PathVariable Long id,
                                           @RequestParam String labelType) {
        return ftLotNodeOptionFacade.getFTResultByLotId(id, labelType);
    }

    @ResponseBody
    @RequestMapping("/sampleShipping")
    public InvokeResult sampleShipping(SampleShippingDTO sampleShippingDTO) {
        sampleShippingDTO = this.createBase(sampleShippingDTO);
        return ftLotNodeOptionFacade.sampleShipping(sampleShippingDTO);
    }

    @ResponseBody
    @RequestMapping("/optionLog")
    public InvokeResult optionLog(FTLotDTO fTLotDTO) {
        return InvokeResult.success();
    }

    @ResponseBody
    @RequestMapping("/splitLot")
    public InvokeResult splitLot(FTLotDTO fTLotDTO) {
        String[] value = fTLotDTO.getSplitQty().split(",");
        Long[] separateQuantities = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            separateQuantities[i] = Long.parseLong(value[i]);
        }
        return ftLotNodeOptionFacade.separate(fTLotDTO.getId(), separateQuantities);

//        InvokeResult invokeResult = ftLotNodeOptionFacade.splitLot(fTLotDTO);
//        if (invokeResult.isHasErrors()) {
//            return invokeResult;
//        }
//
//        return InvokeResult.success();
    }

    @ResponseBody
    @RequestMapping("/mergeLot")
    public InvokeResult mergeLot(FTLotDTO fTLotDTO) {
        String[] value = fTLotDTO.getMergeIds().split(",");
        Long[] integrateFTLotIds = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            integrateFTLotIds[i] = Long.parseLong(value[i]);
        }
        return ftLotNodeOptionFacade.integrate(integrateFTLotIds);
//        InvokeResult invokeResult = ftLotNodeOptionFacade.mergeLot(fTLotDTO);
//        if (invokeResult.isHasErrors()) {
//            return invokeResult;
//        }
//
//        return InvokeResult.success();
    }

    /**
     * 入站
     *
     * @param processId
     * @return
     */
    @ResponseBody
    @RequestMapping("/startFTNode/{processId}")
    public InvokeResult startNode(@PathVariable Long processId) {
    	//专用于操作日志
    	FTProcessDTO ftProcessDTO = new FTProcessDTO();
    	createBase(ftProcessDTO);
        return ftLotNodeOptionFacade.startFTNode(processId, ftProcessDTO);
    }

    /**
     * 出站
     *
     * @param currentNode 当前所在站点
     * @param processId
     * @return
     */
    @ResponseBody
    @RequestMapping("/endFTNode")
    public InvokeResult endFTNode(@RequestParam Long processId, FTNodeDTO currentNode) {
    	this.createBase(currentNode);
        return ftLotNodeOptionFacade.endFTNode(processId, currentNode);
    }

    /**
     * 良率放行
     *
     * @param processId
     * @return
     */
    @ResponseBody
    @RequestMapping("/endFailTestNode")
    public InvokeResult endFailTestNode(@RequestParam Long processId) {
    	FTProcessDTO ftProcessDTO = new FTProcessDTO();
    	createBase(ftProcessDTO);
        return ftLotNodeOptionFacade.endFailTestNode(processId, ftProcessDTO);
    }

    /**
     * 保存站点信息
     *
     * @param processId
     * @param ftNodeDTO
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveFTNode")
    public InvokeResult saveFTNode(@RequestParam Long processId, FTNodeDTO ftNodeDTO) {
    	this.createBase(ftNodeDTO);
        return ftLotNodeOptionFacade.saveUncheckedFTNode(processId, ftNodeDTO);
    }

    @ResponseBody
    @RequestMapping("/saveUncheckedFTNode")
    public InvokeResult saveUncheckedFTNode(@RequestParam Long processId, FTNodeDTO ftNodeDTO) {
    	this.createBase(ftNodeDTO);
        return ftLotNodeOptionFacade.saveUncheckedFTNode(processId, ftNodeDTO);
    }

    /**
     * 数量统计
     *
     * @param processId
     * @return
     */
    @ResponseBody
    @RequestMapping("/quantityStatistics/{processId}")
    public InvokeResult quantityStatistics(@PathVariable Long processId) {
        return ftLotNodeOptionFacade.quantityStatistics(processId);
    }

    /**
     * 检索当前站点Sbl信息
     *
     * @param id
     * @return
     * @version 2016/3/17 HongYu
     */
    @ResponseBody
    @RequestMapping("/getCurrentSblByInternalLot/{id}")
    public InvokeResult getCurrentSblByInternalLot(@PathVariable Long id) {
        return ftLotNodeOptionFacade.findCurrentSblByLotId(id);
    }

    /**
     * 帮助前端获取烤箱编号
     * LHB
     */
    @ResponseBody
    @RequestMapping("/getOven")
    public List<String> getOven() {
        List<String> result = new ArrayList<String>();
        final String QUERY_ALL_OVEN = emsFetcher.getEmsAddress() + "/Equipment/pageJson.koala?page=0&pagesize=10000000&equipmentCategory=oven";
        String ovenInfo = emsFetcher.fetch(QUERY_ALL_OVEN);
        int index1 = 0, index2 = 0;
        while(true){
        	index1 = ovenInfo.indexOf("equipmentNo",index1);
        	if(index1 < 0)
        		break;
        	index1 += 14;
        	index2 = ovenInfo.indexOf("\",", index1);
        	result.add(ovenInfo.substring(index1,index2));
        }
        return result;
    }

    /**
     * 上传并解析ReelCode
     * LHB
     */
    @ResponseBody
    @RequestMapping("/importReelCode")
    public InvokeResult importReelCode(@RequestParam(value = "excel", required = false) MultipartFile multipartFile,@RequestParam(value = "pid", required = false) Long ftProcessId) {
        //获取模板路径与导入文件的路径
        String importPath = ExcelTemplateHelper.importPath(this.getClass());
        String filename = FilenameHelper.generateXlsFilename(
                FilenameHelper.extractFilenameNoExtensionByFileName(multipartFile.getOriginalFilename()));
        if (!uploadFile(multipartFile, importPath, filename))
            return InvokeResult.failure("上传Excel失败");
//        return excelFacade.importReelCodeImpl(this.getClass(), filename,374L);
        InvokeResult temp = ftLotNodeOptionFacade.checkImportedReelCode(filename, ftProcessId);
        if ( temp.isHasErrors() ){
        	return temp;
        } else {
        	return InvokeResult.success(filename);
        }
    }
}
