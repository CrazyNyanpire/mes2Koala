package org.seu.acetec.mes2Koala.web.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.facade.EmployeeFacade;
import org.openkoala.organisation.facade.dto.EmployeeDTO;
import org.seu.acetec.mes2Koala.facade.EQCSettingFacade;
import org.seu.acetec.mes2Koala.facade.ExcelFacade;
import org.seu.acetec.mes2Koala.facade.FTInfoFacade;
import org.seu.acetec.mes2Koala.facade.LabelFacade;
import org.seu.acetec.mes2Koala.facade.LabelPlanFacade;
import org.seu.acetec.mes2Koala.facade.ProcessTemplateFacade;
import org.seu.acetec.mes2Koala.facade.SBLTemplateFacade;
import org.seu.acetec.mes2Koala.facade.TestProgramTemplateFacade;
import org.seu.acetec.mes2Koala.facade.dto.EQCSettingDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTInfoDTO;
import org.seu.acetec.mes2Koala.facade.dto.LabelDTO;
import org.seu.acetec.mes2Koala.facade.dto.LabelPlanDTO;
import org.seu.acetec.mes2Koala.facade.dto.SBLTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.TestProgramTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.FTInfoPageVo;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import help.ExcelTemplateHelper;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/FTInfo")
public class FTInfoController {

    @Inject
    LabelFacade labelFacade;
    @Inject
    private FTInfoFacade fTInfoFacade;
    @Inject
    private SBLTemplateFacade sblTemplateFacade;
    @Inject
    private EQCSettingFacade eQCsettingFacade;
    @Inject
    private TestProgramTemplateFacade testProgramTemplateFacade;
    @Inject
    private LabelPlanFacade labelPlanFacade;
    @Inject
    private ProcessTemplateFacade processTemplateFacade;
    @Inject
    private EmployeeFacade employeeFacade;
    @Inject
    private ExcelFacade excelFacade;


    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(FTInfoDTO fTInfoDTO) {
        //根据id查出六个产品负责人
        EmployeeDTO keyProductionManagerDTO = employeeFacade.getEmployeeById(fTInfoDTO.getKeyProductionManagerDTO().getId());
        EmployeeDTO assistProductionManagerDTO = employeeFacade.getEmployeeById(fTInfoDTO.getAssistProductionManagerDTO().getId());
        EmployeeDTO keyQuantityManagerDTO = employeeFacade.getEmployeeById(fTInfoDTO.getKeyQuantityManagerDTO().getId());
        EmployeeDTO assistQuantityManagerDTO = employeeFacade.getEmployeeById(fTInfoDTO.getAssistQuantityManagerDTO().getId());
        EmployeeDTO keyTDEManagerDTO = employeeFacade.getEmployeeById(fTInfoDTO.getKeyTDEManagerDTO().getId());
        EmployeeDTO assistTDEManagerDTO = employeeFacade.getEmployeeById(fTInfoDTO.getAssistTDEManagerDTO().getId());

        fTInfoDTO.setKeyProductionManagerDTO(keyProductionManagerDTO);
        fTInfoDTO.setAssistProductionManagerDTO(assistProductionManagerDTO);
        fTInfoDTO.setKeyQuantityManagerDTO(keyQuantityManagerDTO);
        fTInfoDTO.setAssistQuantityManagerDTO(assistQuantityManagerDTO);
        fTInfoDTO.setKeyTDEManagerDTO(keyTDEManagerDTO);
        fTInfoDTO.setAssistTDEManagerDTO(assistTDEManagerDTO);

        return fTInfoFacade.creatFTInfo(fTInfoDTO);
    }

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(FTInfoDTO fTInfoDTO) {
        //根据id查出六个产品负责人
        EmployeeDTO keyProductionManagerDTO = employeeFacade.getEmployeeById(fTInfoDTO.getKeyProductionManagerDTO().getId());
        EmployeeDTO assistProductionManagerDTO = employeeFacade.getEmployeeById(fTInfoDTO.getAssistProductionManagerDTO().getId());
        EmployeeDTO keyQuantityManagerDTO = employeeFacade.getEmployeeById(fTInfoDTO.getKeyQuantityManagerDTO().getId());
        EmployeeDTO assistQuantityManagerDTO = employeeFacade.getEmployeeById(fTInfoDTO.getAssistProductionManagerDTO().getId());
        EmployeeDTO keyTDEManagerDTO = employeeFacade.getEmployeeById(fTInfoDTO.getKeyTDEManagerDTO().getId());
        EmployeeDTO assistTDEManagerDTO = employeeFacade.getEmployeeById(fTInfoDTO.getAssistTDEManagerDTO().getId());

        fTInfoDTO.setKeyProductionManagerDTO(keyProductionManagerDTO);

        fTInfoDTO.setAssistProductionManagerDTO(assistProductionManagerDTO);
        fTInfoDTO.setKeyQuantityManagerDTO(keyQuantityManagerDTO);
        fTInfoDTO.setAssistQuantityManagerDTO(assistQuantityManagerDTO);
        fTInfoDTO.setKeyTDEManagerDTO(keyTDEManagerDTO);
        fTInfoDTO.setAssistTDEManagerDTO(assistTDEManagerDTO);
        return fTInfoFacade.updateFTInfo(fTInfoDTO);
    }
    
    @ResponseBody
    @RequestMapping("/exportExcel")
    public InvokeResult exportExcel(@RequestParam String ids) {
        Long[] idArray = ExcelTemplateHelper.extractIdArray(ids, ",");
        return excelFacade.exportFTInfo(this.getClass(), idArray);
    }

    @ResponseBody
    @RequestMapping("/getProcessTemplateContentByProduct/{id}")
    public InvokeResult getTestNodeNamesByProduct(@PathVariable Long id) {
        return processTemplateFacade.findProcessTemplateContentByProduct(id);
    }

    @ResponseBody
    @RequestMapping("/findProcessTemplateByInternalProductId/{id}")
    public InvokeResult findProcessTemplateByProduct(@PathVariable Long id) {
        return processTemplateFacade.findProcessTemplateByInternalProductId(id);
    }


    @ResponseBody
    @RequestMapping("/findLabelsByInternalProductId/{id}")
    public List<LabelDTO> findLabelsByInternalProductId(@PathVariable Long id) {
        return labelFacade.findLabelsByInternalProductId(id);
    }

    @ResponseBody
    @RequestMapping("/getTestProgramByProduct/{id}")
    public List<TestProgramTemplateDTO> findTestProgramsByProduct(@PathVariable Long id) {
        return testProgramTemplateFacade.findTestProgramTemplatesByProduct(id);
    }

    @ResponseBody
    @RequestMapping("/getSBLTemplatesByProduct/{id}")
    public List<SBLTemplateDTO> getSBLTemplatesByProduct(@PathVariable Long id) {
        return fTInfoFacade.getSBLTemplatesByProduct(id);
    }

    @ResponseBody
    @RequestMapping("/getEQCSettingsByProduct/{id}")
    public List<EQCSettingDTO> getEQCSettingsByProduct(@PathVariable Long id) {
        return fTInfoFacade.getEQCSettingsByProduct(id);
    }

    @ResponseBody
    @RequestMapping("/getLabelPlanByProduct/{id}")
    public LabelPlanDTO findLabelPlanByProduct(@PathVariable Long id) {
        return labelPlanFacade.findLabelPlanByProduct(id);
    }

    @ResponseBody
    @RequestMapping("/bindProcess")
    public InvokeResult bindProcess(@RequestParam Long ftInfoId, @RequestParam Long processId) {
        return fTInfoFacade.bindProcess(ftInfoId, processId);
    }

    @ResponseBody
    @RequestMapping("/bindSBLTemplates")
    public InvokeResult bindSBLTemplates(Long internalProductId, @RequestParam("sblTemplates") String sblTemplates) {
        List<SBLTemplateDTO> list = (List) JSONArray.toCollection(JSONArray.fromObject(sblTemplates), SBLTemplateDTO.class);
        return sblTemplateFacade.bindSBLTemplates(internalProductId, list);
    }

    @ResponseBody
    @RequestMapping("/clearProcess/{id}")
    public InvokeResult clearProcess(@PathVariable Long id) {
        return fTInfoFacade.clearProcess(id);
    }

    //以/Label/getLabelByType代替
    @ResponseBody
    @RequestMapping("/getLabelByPackageType/{packageType}")
    public List<LabelDTO> getLabelByPackageType(@PathVariable String packageType) {
        return labelFacade.findFTLabelsByPackageType(packageType);
    }

    @ResponseBody
    @RequestMapping("/bindLabel")
    public InvokeResult bindLabel(@RequestParam Long ftInfoId, @RequestParam Long labelId) {
        return fTInfoFacade.bindLabel(ftInfoId, labelId);
    }

    @ResponseBody
    @RequestMapping("/bindLabels")
    public InvokeResult bindLabels(@RequestParam Long ftInfoId, @RequestParam String labelIds) {
        String[] ids = labelIds.split(",");
        Long[] idArray = new Long[ids.length];
        for (int index = 0; index < ids.length; index++) {
            idArray[index] = Long.parseLong(ids[index]);
        }
        //模拟process绑定过程
        return fTInfoFacade.bindLabels(ftInfoId, idArray);
    }

    @ResponseBody
    @RequestMapping("/clearLabel/{id}")
    public InvokeResult clearLabel(@PathVariable Long id) {
        return fTInfoFacade.clearLabel(id);
    }

    @ResponseBody
    @RequestMapping("/clearSBLTemplates/{id}")
    public InvokeResult clearSBLTemplates(@PathVariable Long id) {
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
    @RequestMapping("/clearEQCSettings/{id}")
    public InvokeResult clearEQCSettings(@PathVariable Long id) {
        List<EQCSettingDTO> eList = eQCsettingFacade.findEQCSettingsByProduct(id);
        if (eList != null && eList.get(0) != null) {
            Long[] idArrs = new Long[eList.size()];
            for (int i = 0; i < eList.size(); i++) {
                idArrs[i] = eList.get(i).getId();
            }
            eQCsettingFacade.removeEQCSettings(idArrs);
        }
        return InvokeResult.success();
    }

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(FTInfoDTO fTInfoDTO, @RequestParam int page, @RequestParam int pagesize, @RequestParam(required = false) String sortname, @RequestParam(required = false) String sortorder) {
        Page<FTInfoPageVo> all = fTInfoFacade.pageQueryFTInfo(fTInfoDTO, page, pagesize, sortname, sortorder);
        return all;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public InvokeResult remove(@RequestParam String ids) {
        String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            idArrs[i] = Long.parseLong(value[i]);
            clearSBLTemplates(idArrs[i]);
            clearEQCSettings(idArrs[i]);
        }
        return fTInfoFacade.removeFTInfos(idArrs);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return fTInfoFacade.getFTInfo(id);
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

}
