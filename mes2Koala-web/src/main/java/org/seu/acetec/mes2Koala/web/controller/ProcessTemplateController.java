package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.springframework.web.bind.WebDataBinder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import help.FilenameHelper;

import org.apache.commons.io.IOUtils;
import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.dto.vo.EmployeeVo;
import org.seu.acetec.mes2Koala.facade.AcetecAuthorizationFacade;
import org.seu.acetec.mes2Koala.facade.ProcessTemplateFacade;
import org.seu.acetec.mes2Koala.facade.SpecialFormFacade;
import org.seu.acetec.mes2Koala.facade.SystemDictionaryFacade;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.facade.EmployeeFacade;

@Controller
@RequestMapping("/ProcessTemplate")
public class ProcessTemplateController extends BaseController{
	
	@Inject
	private EmployeeFacade empolyeeFacade;
	
	@Inject
	private SystemDictionaryFacade systemDictionaryFacade;

    @Inject
    private ProcessTemplateFacade processTemplateFacade;

    @Inject
    private AcetecAuthorizationFacade acetecAuthorizationFacade;

    @Inject
    private SpecialFormFacade specialFormFacade;

    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(ProcessTemplateDTO processTemplateDTO) {

    	//获取创建人
    	createBase(processTemplateDTO);
    	
    	//没办法我只能写在controller层了...
    	//从字典表中取出授权者的工号，并根据工号获取id
        List<String> sns = systemDictionaryFacade.getValueByType("processAuthorizerSN");
        List<EmployeeVo> employeeVos = empolyeeFacade.findEmployeeByAccount(sns);
        
        ArrayList<Long> aids = new ArrayList<Long>();
        for ( EmployeeVo each : employeeVos ) {
            AcetecAuthorizationDTO acetecAuthorizationDTO = new AcetecAuthorizationDTO();
            acetecAuthorizationDTO.setEmployeeId(each.getId());
            acetecAuthorizationFacade.creatAcetecAuthorization(acetecAuthorizationDTO);
            Long aid = acetecAuthorizationFacade.getLastAcetecAuthorization();
            aids.add(aid);
        }
        processTemplateDTO.setAcetecAuthorizationIds(aids);
        //
        processTemplateDTO.setAllowState("未签核");
        //
        SpecialFormDTO sp = new SpecialFormDTO();
        sp.setCheckBoxForm(false);
        sp.setCheckSelfForm(false);
        sp.setFlowForm(false);
        sp.setIndexForm(false);
        sp.setLossForm(false);
        sp.setReelcodeForm(false);
        sp.setSizeForm(false);
        sp.setSummaryForm(false);
        specialFormFacade.creatSpecialForm(sp);
        Long sid = specialFormFacade.getLastSpecialForm();
        sp.setId(sid);
        processTemplateDTO.setSpecialFormDTO(sp);

        return processTemplateFacade.creatProcessTemplate(processTemplateDTO);
    }

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(ProcessTemplateDTO processTemplateDTO) {
    	
    	lastModifyBase(processTemplateDTO);
    	
        Long tid = processTemplateDTO.getId();
        //Page page = processTemplateFacade.findAcetecAuthorizationsByProcessTemplate(tid, 0, 20);
        //List<AcetecAuthorizationDTO> alist = page.getData();
        boolean flag = false;
        
        //未签核、未通过的process也可以修改        
        InvokeResult iR = processTemplateFacade.getProcessTemplate(tid);
        ProcessTemplateDTO pDTO = (ProcessTemplateDTO) iR.getData();
        if(pDTO.getAllowState().equals("未签核")||pDTO.getAllowState().equals("未通过")){
        	
        }else{
        	flag = true;
        }
        /*
        for (int i = 0; i < alist.size(); i++) {
            if ((alist.get(i).getOpinion() != null && !alist.get(i).getOpinion().equals(""))
                    || (alist.get(i).getNote() != null && !alist.get(i).getNote().equals(""))) {
                flag = true;
                break;
            }
        }
        */
        if (flag == false) {
            return processTemplateFacade.updateProcessTemplate(processTemplateDTO);
        }
        return InvokeResult.failure("ERR：已签核或正在签核");
    }

    @ResponseBody
    @RequestMapping("/updateAllowState/{id}")
    public void updateAllowState(@PathVariable Long id) {
        processTemplateFacade.updateAllowState(id);
        return;
    }




    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(ProcessTemplateDTO processTemplateDTO, @RequestParam int page, @RequestParam int pagesize, @RequestParam(required = false) String sortname, @RequestParam(required = false) String sortorder) {
        Page<ProcessTemplateDTO> all = processTemplateFacade.pageQueryProcessTemplate(processTemplateDTO, page, pagesize,sortname,sortorder);
        return all;
    }


    @ResponseBody
    @RequestMapping(value = "/signProcessTemplate",method = RequestMethod.POST)
    public InvokeResult signProcessTemplate(@RequestBody String json){
        JSONObject jsonObject = JSONObject.fromObject(json);
        Long processId = Long.valueOf(jsonObject.optString("processTemplateId"));
        Long userId = Long.valueOf(jsonObject.optString("userId"));
        String opinion = jsonObject.optString("opinion");
        String note = jsonObject.optString("note");
        return processTemplateFacade.signProcessTemplate(processId,userId,opinion,note);
    }


    @ResponseBody
    @RequestMapping(value = "/getSignInfo",method = RequestMethod.GET)
    public InvokeResult signProcessTemplate(@RequestParam String processId, @RequestParam String userId){
        return processTemplateFacade.getProcessTemplateSignInfo(Long.parseLong(processId),Long.parseLong(userId));
    }


    @ResponseBody
    @RequestMapping("/delete")
    public InvokeResult remove(@RequestParam String ids) {
        String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            idArrs[i] = Long.parseLong(value[i]);
        }
        for(int i = 0; i < value.length; i++){
        	InvokeResult iR = processTemplateFacade.getProcessTemplate(idArrs[i]);
        	ProcessTemplateDTO pDTO = (ProcessTemplateDTO) iR.getData();
        	if(pDTO.getAllowState().equals("已签核")||pDTO.getAllowState().equals("签核中"))
        		return InvokeResult.failure("ERR：已签核或正在签核");
        }
            
        List<AcetecAuthorizationDTO> alist = new ArrayList<AcetecAuthorizationDTO>();
        for (int i = 0; i < value.length; i++) {
            Page page = processTemplateFacade.findAcetecAuthorizationsByProcessTemplate(idArrs[i], 0, 20);
            alist.addAll(page.getData());
        }
        Long[] aids = new Long[alist.size()];
        for (int i = 0; i < alist.size(); i++) {
            aids[i] = alist.get(i).getId();
        }
        acetecAuthorizationFacade.removeAcetecAuthorizations(aids);

        //       
        Long[] sids = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            Long sid = processTemplateFacade.findSpecialFormByProcessTemplate(idArrs[i]);
            sids[i] = sid;
        }


        processTemplateFacade.removeProcessTemplates(idArrs);

        return specialFormFacade.removeSpecialForms(sids);

    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return processTemplateFacade.getProcessTemplate(id);
    }

    @ResponseBody
    @RequestMapping("/findTestingTemplateByProcessTemplate/{id}")
    public Map<String, Object> findTestingTemplateByProcessTemplate(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", processTemplateFacade.findTestingTemplateByProcessTemplate(id));
        return result;
    }


    @ResponseBody
    @RequestMapping("/findAcetecAuthorizationsByProcessTemplate/{id}")
    public Page findAcetecAuthorizationsByProcessTemplate(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
        Page<AcetecAuthorizationDTO> all = processTemplateFacade.findAcetecAuthorizationsByProcessTemplate(id, page, pagesize);
        return all;
    }

    @ResponseBody
    @RequestMapping("/findRuncardNoteByProcessTemplate/{id}")
    public InvokeResult findRuncardNoteByProcessTemplate(@PathVariable Long id) {
        return processTemplateFacade.findRuncardNoteByProcessTemplate(id);
    }

    /**
     * get方法访问链接，传入需要生成Runcard的id，返回生成文件的全路径。
     *
     * @param id        需要生成Runcard的ProcessTemplate记录id
     * @param request   用于取请求访问的当前地址
     * @param responseA 返回容器（主要用于指定返回内容为pdf）
     * @return 失败时返回错误信息，成功时返回文件的全路径
     */
    @ResponseBody
    @RequestMapping(value = "/checkRuncard/{id}", method = RequestMethod.GET)
    public InvokeResult checkRuncard(@PathVariable Long id, HttpServletRequest request, HttpServletResponse responseA) {
        //获取类文件所在的路径，为获取web应用路径做准备
        String classPath = this.getClass().getClassLoader().getResource("").getPath();

        //获取web路径并且指定模板文件所在路径
        String templatePath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/";
        String templateFullPath = templatePath + "FTRuncardTemplate.xlsx";
//		String templateFullPath = templatePath + "BGARuncardTemplate.xlsx";

        //根据模板文件所在路径指定输出路径
        String targetPath = templatePath + "export/";
        String targetFileName = FilenameHelper.generateXlsxFilename("runcard_export");
        String targetFullPath = targetPath + targetFileName;

        try (InputStream is = new FileInputStream(templateFullPath)) {
            try (OutputStream os = new FileOutputStream(targetFullPath)) {
                processTemplateFacade.checkRuncard(id, is, os);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return InvokeResult.failure(e.getMessage());
        }
        return InvokeResult.success("excel/export/" + targetFileName);

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
    @RequestMapping("/findProcessByHandler/{handlerType}")
    public List<ProcessTemplateDTO> findProcessByHandler(@PathVariable String handlerType) {
        return processTemplateFacade.findProcessByHandler(handlerType);
    }


}
