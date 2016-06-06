package org.seu.acetec.mes2Koala.web.controller;

import net.sf.json.JSONArray;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.facade.EmployeeFacade;
import org.openkoala.organisation.facade.dto.EmployeeDTO;
import org.seu.acetec.mes2Koala.core.domain.CPSBLTemplate;
import org.seu.acetec.mes2Koala.core.domain.RawData;
import org.seu.acetec.mes2Koala.facade.*;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import help.ExcelTemplateHelper;
import help.FilenameHelper;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 阙宇翔
 * @version 2016/2/13
 */
@Controller
@RequestMapping("/CPInfo")
public class CPInfoController {

    @Inject
    ProcessTemplateFacade processTemplateFacade;

    @Inject
    private CPInfoFacade cpInfoFacade;

    @Inject
    private SBLTemplateFacade sblTemplateFacade;
    
    @Inject
    private CPSBLTemplateFacade cpSblTemplateFacade;

    @Inject
    private TestProgramTemplateFacade testProgramTemplateFacade;

    @Inject
    private LabelPlanFacade labelPlanFacade;
    
    @Inject
    private EmployeeFacade employeeFacade;
    
    @Inject
    private RawDataFacade rawDataFacade;

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
    
    /**
     * CPInfo
     *
     * @param cpInfoDTO
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(CPInfoDTO cpInfoDTO) {
    	EmployeeDTO keyProductionManagerDTO = employeeFacade.getEmployeeById(cpInfoDTO.getKeyProductionManagerDTO().getId());
        EmployeeDTO assistProductionManagerDTO = employeeFacade.getEmployeeById(cpInfoDTO.getAssistProductionManagerDTO().getId());
        EmployeeDTO keyQuantityManagerDTO = employeeFacade.getEmployeeById(cpInfoDTO.getKeyQuantityManagerDTO().getId());
        EmployeeDTO assistQuantityManagerDTO = employeeFacade.getEmployeeById(cpInfoDTO.getAssistQuantityManagerDTO().getId());
        EmployeeDTO keyTDEManagerDTO = employeeFacade.getEmployeeById(cpInfoDTO.getKeyTDEManagerDTO().getId());
        EmployeeDTO assistTDEManagerDTO = employeeFacade.getEmployeeById(cpInfoDTO.getAssistTDEManagerDTO().getId());

        cpInfoDTO.setKeyProductionManagerDTO(keyProductionManagerDTO);
        cpInfoDTO.setAssistProductionManagerDTO(assistProductionManagerDTO);
        cpInfoDTO.setKeyQuantityManagerDTO(keyQuantityManagerDTO);
        cpInfoDTO.setAssistQuantityManagerDTO(assistQuantityManagerDTO);
        cpInfoDTO.setKeyTDEManagerDTO(keyTDEManagerDTO);
        cpInfoDTO.setAssistTDEManagerDTO(assistTDEManagerDTO);
        return cpInfoFacade.createCPInfo(cpInfoDTO);
    }

    /**
     * CPInfo
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return cpInfoFacade.getCPInfo(id);
    }

    /**
     * CPInfo
     *
     * @param cpInfoDTO
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(CPInfoDTO cpInfoDTO) {
    	EmployeeDTO keyProductionManagerDTO = employeeFacade.getEmployeeById(cpInfoDTO.getKeyProductionManagerDTO().getId());
        EmployeeDTO assistProductionManagerDTO = employeeFacade.getEmployeeById(cpInfoDTO.getAssistProductionManagerDTO().getId());
        EmployeeDTO keyQuantityManagerDTO = employeeFacade.getEmployeeById(cpInfoDTO.getKeyQuantityManagerDTO().getId());
        EmployeeDTO assistQuantityManagerDTO = employeeFacade.getEmployeeById(cpInfoDTO.getAssistQuantityManagerDTO().getId());
        EmployeeDTO keyTDEManagerDTO = employeeFacade.getEmployeeById(cpInfoDTO.getKeyTDEManagerDTO().getId());
        EmployeeDTO assistTDEManagerDTO = employeeFacade.getEmployeeById(cpInfoDTO.getAssistTDEManagerDTO().getId());

        cpInfoDTO.setKeyProductionManagerDTO(keyProductionManagerDTO);
        cpInfoDTO.setAssistProductionManagerDTO(assistProductionManagerDTO);
        cpInfoDTO.setKeyQuantityManagerDTO(keyQuantityManagerDTO);
        cpInfoDTO.setAssistQuantityManagerDTO(assistQuantityManagerDTO);
        cpInfoDTO.setKeyTDEManagerDTO(keyTDEManagerDTO);
        cpInfoDTO.setAssistTDEManagerDTO(assistTDEManagerDTO);
        return cpInfoFacade.updateCPInfo(cpInfoDTO);
    }

    /**
     * CPInfo
     *
     * @param cpInfoDTO
     * @param page
     * @param pagesize
     * @return
     */
    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(CPInfoDTO cpInfoDTO, @RequestParam int page, @RequestParam int pagesize) {
        return cpInfoFacade.pageQueryCPInfo(cpInfoDTO, page, pagesize);
    }

    @ResponseBody
    @RequestMapping("/findProcessTemplateByInternalProductId/{id}")
    public InvokeResult findProcessTemplateByProduct(@PathVariable Long id) {
        return processTemplateFacade.findProcessTemplateByInternalProductId(id);
    }

    /**
     * SBLTemplate
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCPSBLTemplatesByProduct/{id}")
    public List<CPSBLTemplateDTO> getCPSBLTemplatesByProduct(@PathVariable Long id) {
        return cpSblTemplateFacade.getCPSBLTemplatesByProduct(id);
    }

    /**
     * CPInfo
     *
     * @param id
     * @param processTemplateId
     * @return
     */
    @ResponseBody
    @RequestMapping("/bindProcess")
    public InvokeResult bindProcess(@RequestParam Long id,
                                    @RequestParam Long processTemplateId) {
        return cpInfoFacade.updateProcessTemplate(id, processTemplateId);
    }

    @ResponseBody
    @RequestMapping("/bindSBLTemplates")
    public InvokeResult bindSBLTemplates(@RequestParam Long internalProductId, @RequestParam("sblTemplates") String sblTemplates) {
        List<CPSBLTemplateDTO> list = (List) JSONArray.toCollection(JSONArray.fromObject(sblTemplates), CPSBLTemplateDTO.class);
        return cpSblTemplateFacade.bindSBLTemplates(internalProductId, list);
    }

    /**
     * CPInfo
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/clearProcess/{id}")
    public InvokeResult clearProcess(@PathVariable Long id) {
        return cpInfoFacade.clearProcessTemplate(id);
    }

    /**
     * @param packageType
     * @return
     */
    @ResponseBody
    @RequestMapping("/getLabelByPackageType/{packageType}")
    public List<LabelDTO> getLabelsByPackageType(@PathVariable String packageType) {
        return cpInfoFacade.getLabelsByPackageType(packageType);
    }

    /**
     * 绑定Label
     *
     * @param id
     * @param labelId
     * @return
     */
    @ResponseBody
    @RequestMapping("/bindLabel")
    public InvokeResult bindLabel(@RequestParam Long id,
                                  @RequestParam Long labelId) {
        return cpInfoFacade.updateLabels(id, Arrays.asList(labelId));
    }

    /**
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/clearLabels/{id}")
    public InvokeResult clearLabels(@PathVariable Long id) {
        return cpInfoFacade.clearLabels(id);
    }

    /**
     * CPSBLTemplate
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/clearCPSBLTemplates/{id}")
    public InvokeResult clearCPSBLTemplates(@PathVariable Long id) {
        List<CPSBLTemplateDTO> list = cpSblTemplateFacade.getCPSBLTemplatesByProduct(id);
        if (list != null) {
        	if (list.get(0) != null){
        		Long[] idArrs = new Long[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    idArrs[i] = list.get(i).getId();
                }
                cpSblTemplateFacade.removeCPSBLTemplates(idArrs);
        	} 
        }
        return InvokeResult.success();
    }

    /**
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getLabelPlanByProduct/{id}")
    public LabelPlanDTO findLabelPlanByProduct(@PathVariable Long id) {
        return labelPlanFacade.findLabelPlanByProduct(id);
    }

    /**
     * TestProgramm
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTestProgramTemplateByProduct/{id}")
    public List<TestProgramTemplateDTO> findTestProgramsByProduct(@PathVariable Long id) {
        return testProgramTemplateFacade.findTestProgramTemplatesByProduct(id);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        //CustomDateEditor 可以换成自己定义的编辑器。
        //注册一个Date 类型的绑定器
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }

    /**
     * delete
     * @param
     * @return
     * @author HongYu
     */
    @ResponseBody
    @RequestMapping("/delete")
    public InvokeResult remove(@RequestParam String ids) {
        String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            idArrs[i] = Long.parseLong(value[i]);
            clearCPSBLTemplates(idArrs[i]);
            rawDataFacade.removeRawData(idArrs[i]);
        }
        return cpInfoFacade.removeCPInfos(idArrs);
    }

    @ResponseBody
    @RequestMapping("/clearSBL/{id}")
    public InvokeResult clearSBL(@PathVariable Long id) {
        List<SBLTemplateDTO> sList = sblTemplateFacade.findSBLTemplatesDTOsByProductId(id);
        if (sList != null && sList.get(0) != null) {
            Long[] idArrs = new Long[sList.size()];
            for (int i = 0; i < sList.size(); i++) {
                idArrs[i] = sList.get(i).getId();
            }
            sblTemplateFacade.removeSBLTemplates(idArrs);
        }
        return InvokeResult.success();
    }
    
    @ResponseBody
    @RequestMapping("/exportExcel")
    public InvokeResult exportExcel(HttpServletResponse response, @RequestParam String ids) {
    	Long[] idArray = ExcelTemplateHelper.extractIdArray(ids, ",");
		List cpinfoArray = new ArrayList();
		for (int i = 0; i < idArray.length; i++) {
			CPInfoDTO cpInfoDTO = (CPInfoDTO) cpInfoFacade.getCPInfo(idArray[i]).getData();
			cpinfoArray.add(cpInfoDTO);
		}
		String fileName = FilenameHelper.generateXlsFilename("CPInfo");
        try {
    		cpInfoFacade.exportExcel(cpinfoArray,fileName);
        	return InvokeResult.success("excel/export/" + fileName);
        } catch (Exception e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
    }
	
	@ResponseBody
	@RequestMapping("/uploadProdcutRequireFile")
	public InvokeResult uploadLogo(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) {
		//获取类文件所在的路径，为获取web应用路径做准备
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		//获取上传路径
		String path = classPath.substring(0, classPath.indexOf("WEB-INF")) + "upload/prodcutRequireFile";
		String fileName = new Date().getTime() + "";// 使用时间值作为上传文件的命名
		String suffix = file.getOriginalFilename();
		System.out.println(path);
		if (!uploadFile(file, path, fileName + suffix))
			return InvokeResult.failure("upload file failed.");

		return InvokeResult.success("upload/prodcutRequireFile/" + fileName + suffix);
	}
}
