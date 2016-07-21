package org.seu.acetec.mes2Koala.web.controller;

import net.sf.json.JSONArray;

import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.core.common.Mes2DateUtils;
import org.seu.acetec.mes2Koala.facade.TestDataReportOperationFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.TSKInfoDTO;
import org.seu.acetec.mes2Koala.facade.dto.TestData3360InfoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import help.FilenameHelper;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author 阙宇翔
 * @version 2016/2/14
 */
@Controller
@RequestMapping("/TestDataReport")
public class TestDataReportController extends BaseController {
	
	@Inject
	TestDataReportOperationFacade testDataReportOperationFacade;
    
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
    
	@ResponseBody
	@RequestMapping("/uploadCPDataCompensationFile")
	public InvokeResult uploadLogo(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) {
		//获取类文件所在的路径，为获取web应用路径做准备
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		//获取上传路径
		String path = classPath.substring(0, classPath.indexOf("WEB-INF")) + "upload/cpDataCompensationFile";
		String suffix = file.getOriginalFilename();
		System.out.println(path);
		if (!uploadFile(file, path, suffix))
			return InvokeResult.failure("upload file failed.");

		return InvokeResult.success("upload/cpDataCompensationFile/" + suffix);
	}
	
	@ResponseBody
    @RequestMapping("/getTskFileNames")
    public InvokeResult getTskFileNames(@RequestParam String upDown,@RequestParam String directory) {
        return testDataReportOperationFacade.getTskFileNames(upDown,directory);
    }
	
	@ResponseBody
    @RequestMapping("/tskExportExcel")
    public InvokeResult tskExportExcel(@RequestParam("tskInfo") String tskInfos) {
		List<TSKInfoDTO> list = (List) JSONArray.toCollection(JSONArray.fromObject(tskInfos), TSKInfoDTO.class);
		String fileName = FilenameHelper.generateXlsFilename("tsk");
	    testDataReportOperationFacade.tskExportExcel(list,fileName);
		return InvokeResult.success("excel/export/tsk/" + fileName);
    }
    
    @ResponseBody
    @RequestMapping("/resolveFile")
    public InvokeResult resolveFile(@RequestParam String upDown,@RequestParam String directoryName) {
        return testDataReportOperationFacade.resolveFile(upDown,directoryName);
    }
    
    @ResponseBody
    @RequestMapping("/mapCreate")
    public InvokeResult mapCreate(@RequestParam String upDown,@RequestParam String directoryName,@RequestParam String fileNameNum) {
    	String classPath = this.getClass().getClassLoader().getResource("").getPath();
    	String mapPath = classPath + "tskMapHtml";
    	return testDataReportOperationFacade.mapCreate(upDown,directoryName,fileNameNum,mapPath);
    }
    
    //@ResponseBody
    //@RequestMapping("/resolve3360File")
    //public InvokeResult resolve3360File(@RequestParam String directory,HttpServletRequest request) {
    //	StringBuffer url = request.getRequestURL();
    //	return testDataReportOperationFacade.resolve3360File(directory);
    //}
    
    @ResponseBody
    @RequestMapping("/resolve3360File")
    public InvokeResult resolve3360File(@RequestParam String customer,@RequestParam String testType,
    		                            @RequestParam String device,@RequestParam String lotID) {
    	return testDataReportOperationFacade.resolve3360File(customer,testType,device,lotID);
    }
    
    @ResponseBody
    @RequestMapping("/getHYNFileNames")
    public InvokeResult getHYNFileNames(@RequestParam String upDown,@RequestParam String directory) {
        return testDataReportOperationFacade.getHYNFileNames(upDown,directory);
    }
    
    @ResponseBody
    @RequestMapping("/get3360LotInfo")
    public InvokeResult get3360LotInfo(@RequestParam String customer,@RequestParam String testType,@RequestParam String device) {
        return testDataReportOperationFacade.get3360LotInfo(customer,testType,device);
    }
    
    @ResponseBody
    @RequestMapping("/exportInkList")
    public InvokeResult exportInkList(@RequestParam String upDown,@RequestParam String directoryName,@RequestParam("tskInfo") String tskInfos) {
		List<TSKInfoDTO> list = (List) JSONArray.toCollection(JSONArray.fromObject(tskInfos), TSKInfoDTO.class);
		String zipName = testDataReportOperationFacade.exportInkList(upDown,directoryName,list);
		return InvokeResult.success("excel/export/inkList/" + zipName);
    }
    
	@ResponseBody
    @RequestMapping("/exportExcel3360")
    public InvokeResult exportExcel3360(@RequestParam("info3360") String info3360s) {
		List<TestData3360InfoDTO> list = (List) JSONArray.toCollection(JSONArray.fromObject(info3360s), TestData3360InfoDTO.class);
		String fileName = FilenameHelper.generateXlsFilename("3360");
	    testDataReportOperationFacade.exportExcel3360(list,fileName);
		return InvokeResult.success("excel/export/3360/" + fileName);
    }
	
	@ResponseBody
    @RequestMapping("/exportHYN1CP1")
    public InvokeResult exportHYN1CP1(@RequestParam String upDown,@RequestParam String directoryName,@RequestParam("tskInfo") String tskInfos) {
		List<TSKInfoDTO> list = (List) JSONArray.toCollection(JSONArray.fromObject(tskInfos), TSKInfoDTO.class);
		String filename = testDataReportOperationFacade.exportHYNCP1Excel(upDown,directoryName,list);
		return InvokeResult.success("excel/export/"+filename);
    }
	
	@ResponseBody
    @RequestMapping("/exportHYN1CP2")
    public InvokeResult exportHYN1CP2(@RequestParam String upDown,@RequestParam String directoryName,@RequestParam("tskInfo") String tskInfos) {
		List<TSKInfoDTO> list = (List) JSONArray.toCollection(JSONArray.fromObject(tskInfos), TSKInfoDTO.class);
		String filename = testDataReportOperationFacade.exportHYNCP2Excel(upDown,directoryName,list);
		return InvokeResult.success("excel/export/"+filename);
    }
	
	@ResponseBody
    @RequestMapping("/exportHYN1CP4")
    public InvokeResult exportHYN1CP4(@RequestParam String upDown,@RequestParam String directoryName,@RequestParam("tskInfo") String tskInfos) {
		List<TSKInfoDTO> list = (List) JSONArray.toCollection(JSONArray.fromObject(tskInfos), TSKInfoDTO.class);
		String filename = testDataReportOperationFacade.exportHYNCP4Excel(upDown,directoryName,list);
		return InvokeResult.success("excel/export/"+filename);
    }
}
