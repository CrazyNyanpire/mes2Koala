package org.seu.acetec.mes2Koala.web.controller;

import net.sf.json.JSONArray;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.ReelDiskFacade;
import org.seu.acetec.mes2Koala.facade.dto.FT_ResultDTO;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskConvertDTO;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ReelDisk")
public class ReelDiskController {

    @Inject
    private ReelDiskFacade reelDiskFacade;

    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(ReelDiskDTO reelDiskDTO) {
        return reelDiskFacade.creatReelDisk(reelDiskDTO);
    }

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(ReelDiskDTO reelDiskDTO) {
        return reelDiskFacade.updateReelDisk(reelDiskDTO);
    }

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(ReelDiskDTO reelDiskDTO, @RequestParam int page, @RequestParam int pagesize) {
        Page<ReelDiskDTO> all = reelDiskFacade.pageQueryReelDisk(reelDiskDTO, page, pagesize);
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
        return reelDiskFacade.removeReelDisks(idArrs);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return reelDiskFacade.getReelDisk(id);
    }

    @ResponseBody
    @RequestMapping("/getAll/{ids}")
    public List<ReelDiskDTO> getAll(@PathVariable String ids) {
        String[] idArrs = ids.split(",");
        Long[] idsLong = new Long[idArrs.length];
        for (int i = 0; i < idArrs.length; i++) {
            idsLong[i] = Long.parseLong(idArrs[i]);
        }
        return reelDiskFacade.getReelDisks(idsLong);
    }
    
    @ResponseBody
    @RequestMapping("/getAllReel/{ids}")
    public List<ReelDiskConvertDTO> getAllReel(@PathVariable String ids) {
        String[] idArrs = ids.split(",");
        Long[] idsLong = new Long[idArrs.length];
        for (int i = 0; i < idArrs.length; i++) {
            idsLong[i] = Long.parseLong(idArrs[i]);
        }
        return reelDiskFacade.getConvertReelDisks(idsLong);
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
    @RequestMapping("/previewReelDisk")
    public List<ReelDiskDTO> previewReelDisk(@RequestParam Long ftLotId, @RequestParam int reelNumber, @RequestParam String binType) {
        return reelDiskFacade.previewReelDisk(ftLotId, reelNumber, binType);
    }

    @ResponseBody
    @RequestMapping("/createFailReelDisks")
    public InvokeResult createFailReelDisks(@RequestParam Long ftLotId, @RequestParam String failBins) {
        @SuppressWarnings("unchecked")
        List<FT_ResultDTO> failBins1 = (List<FT_ResultDTO>) JSONArray.toCollection(JSONArray.fromObject(failBins), FT_ResultDTO.class);
        return reelDiskFacade.createFailReelDisks(ftLotId, failBins1);
    }

    @ResponseBody
    @RequestMapping("/saveReelDisk")
    public InvokeResult saveReelDisk(@RequestParam String json) {
        //json = "[{\"id\":null,\"version\":0,\"reelCode\":\"V061601190011\",\"isFull\":null,\"reelTime\":null,\"poNumber\":null,\"lastModifyTimestamp\":null,\"lastModifyTimestampEnd\":null,\"logic\":null,\"packagingTime\":1453188458552,\"packagingTimeEnd\":null,\"packagingTimeStr\":null,\"lastModifyEmployNo\":null,\"createTimestamp\":null,\"createTimestampEnd\":null,\"time\":null,\"createEmployNo\":null,\"wflot\":null,\"poNumberBox\":null,\"quantity\":3000,\"dateCode\":\"160119\",\"partNumber\":\"VC5278-21\",\"combinedLotDTO\":null,\"parentsIntegrationDTOs\":[],\"parentSeparationDTO\":null,\"ftLotDTO\":{\"id\":66,\"version\":0,\"loss\":null,\"lastModifyTimestamp\":null,\"lastModifyTimestampEnd\":null,\"lastModifyEmployNo\":null,\"borrow\":null,\"createEmployNo\":null,\"logic\":null,\"state\":null,\"type\":null,\"createTimestamp\":null,\"createTimestampEnd\":null,\"qty\":null}},{\"id\":null,\"version\":0,\"reelCode\":\"V061601190012\",\"isFull\":null,\"reelTime\":null,\"poNumber\":null,\"lastModifyTimestamp\":null,\"lastModifyTimestampEnd\":null,\"logic\":null,\"packagingTime\":1453188458552,\"packagingTimeEnd\":null,\"packagingTimeStr\":null,\"lastModifyEmployNo\":null,\"createTimestamp\":null,\"createTimestampEnd\":null,\"time\":null,\"createEmployNo\":null,\"wflot\":null,\"poNumberBox\":null,\"quantity\":1600,\"dateCode\":\"160119\",\"partNumber\":\"VC5278-21\",\"combinedLotDTO\":null,\"parentsIntegrationDTOs\":[],\"parentSeparationDTO\":null,\"ftLotDTO\":{\"id\":66,\"version\":0,\"loss\":null,\"lastModifyTimestamp\":null,\"lastModifyTimestampEnd\":null,\"lastModifyEmployNo\":null,\"borrow\":null,\"createEmployNo\":null,\"logic\":null,\"state\":null,\"type\":null,\"createTimestamp\":null,\"createTimestampEnd\":null,\"qty\":null}}]";
    	JSONArray jsonarray = new JSONArray();
    	try{
        	jsonarray = JSONArray.fromObject(json);
        }catch(Exception e){
        	return InvokeResult.failure("ERR:保存异常");
        }
    	List<ReelDiskDTO> list = (List) JSONArray.toCollection(jsonarray, ReelDiskDTO.class);
        return reelDiskFacade.saveReelDisk(list);
    }

    /**
     * pass品分批
     *
     * @param reelId
     * @param separateQty
     * @return
     */
    @ResponseBody
    @RequestMapping("/separateReelDisk")
    public InvokeResult separateReelDisk(@RequestParam Long reelId, @RequestParam int separateQty) {
        return reelDiskFacade.separateReelDisk(reelId, separateQty);
    }

    /**
     * fail品分批
     *
     * @param reelDiskId
     * @param separateBins List<FTResult>
     * @return
     */
    @ResponseBody
    @RequestMapping("/separateFailReelDisk")
    public InvokeResult separateFailReelDisk(@RequestParam Long reelDiskId, @RequestParam String separateBins) {
        @SuppressWarnings("unchecked")
        List<FT_ResultDTO> list = (List<FT_ResultDTO>) JSONArray.toCollection(JSONArray.fromObject(separateBins), FT_ResultDTO.class);
        return reelDiskFacade.separateFailReelDisk(reelDiskId, list);
    }

    /**
     * 小样
     *
     * @param reelDiskIds
     * @return
     */
    @ResponseBody
    @RequestMapping("/sampleReelDisks")
    public InvokeResult sampleReelDisks(@RequestParam String reelDiskIds) {
        String[] value = reelDiskIds.split(",");
        Long[] idArr = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            idArr[i] = Long.parseLong(value[i]);
        }
        return reelDiskFacade.sampleReelDisks(idArr);
    }

    @ResponseBody
    @RequestMapping("/integrateReelDisk")
    public InvokeResult integrateReelDisk(@RequestParam Long reelId, @RequestParam Long toReelId) {
        return reelDiskFacade.integrateReelDisk(reelId, toReelId);
    }

    @ResponseBody
    @RequestMapping("/cancelIntegrate")
    public InvokeResult cancelIntegrate(@RequestParam Long reelId) {
        return InvokeResult.success();
    }

    @ResponseBody
    @RequestMapping("/findReelDiskByFTLotId/{ftLotId}")
    public List<ReelDiskDTO> findReelDiskByFTLotId(@PathVariable Long ftLotId) {
        return reelDiskFacade.findReelDiskByFTLotId(ftLotId);

    }

    @ResponseBody
    @RequestMapping("/findReelDiskByIntegratedLotId/{ftLotId}")
    public InvokeResult findReelDiskByCombinedFTLotId(@PathVariable Long ftLotId) {
        return reelDiskFacade.findReelDiskByIntegratedLotId(ftLotId);

    }
    
    @ResponseBody
    @RequestMapping("/gotoLot")
    public InvokeResult gotoLot(@RequestParam Long reelId, @RequestParam Long LotId) {
        return reelDiskFacade.gotoLot(reelId, LotId);
    }

    @ResponseBody
    @RequestMapping("/findReelDiskSettingByFTLotId/{ftLotId}")
    public InvokeResult findReelDiskSettingByFTLotId(@PathVariable Long ftLotId) {
        return reelDiskFacade.findReelDiskSettingByFTLotId(ftLotId);
    }
}
