package org.seu.acetec.mes2Koala.web.controller;

import javax.inject.Inject;

import org.openkoala.organisation.core.domain.Employee;
import org.openkoala.organisation.facade.EmployeeFacade;
import org.openkoala.organisation.facade.dto.EmployeeDTO;
import org.openkoala.security.application.SecurityAccessApplication;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.seu.acetec.mes2Koala.application.TestProgramApplication;
import org.seu.acetec.mes2Koala.application.TestProgramTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.AcetecAuthorization;
import org.seu.acetec.mes2Koala.core.domain.TestProgramTemplate;
import org.seu.acetec.mes2Koala.facade.common.SenderMailUtils;
import org.seu.acetec.mes2Koala.facade.dto.vo.TestProgramTemplatePageVo;
import org.seu.acetec.mes2Koala.facade.service.FTRuncardfacade;
import org.seu.acetec.mes2Koala.infra.EmsFetcher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.AcetecAuthorizationFacade;
import org.seu.acetec.mes2Koala.facade.TestProgramTemplateFacade;
import org.openkoala.koala.commons.InvokeResult;

@Controller
@RequestMapping("/TestProgramTemplate")
public class TestProgramTemplateController {

    @Inject
    private TestProgramTemplateFacade testProgramTemplateFacade;


    @Inject
    private AcetecAuthorizationFacade acetecAuthorizationFacade;

    @Inject
    private EmployeeFacade employeeFacade;

    @Inject
    private SecurityAccessApplication securityAccessApplication;

    @Inject
    private SenderMailUtils senderMailUtils;


    /**
     * 根据userId 查出它的employeeid
     *
     * @return
     */
    private Long getEmployeeIdByUserId(Long userId) {
        EmployeeUser employeeUser = securityAccessApplication.getActorById(userId);
        if (employeeUser.getEmployee() == null) {
            return null;
        }
        return employeeUser.getEmployee().getId();
    }

    @Async
    private void sendEmail(String subject, String content, String email) {
        senderMailUtils.sendMailHelper(subject, content, email);
    }


    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(TestProgramTemplateDTO testProgramTemplateDTO) {
        ArrayList<Long> aids = new ArrayList<Long>();
        for (Long each : testProgramTemplateDTO.getAcetecAuthorizationIds()) {
            AcetecAuthorizationDTO acetecAuthorizationDTO = new AcetecAuthorizationDTO();
            acetecAuthorizationDTO.setEmployeeId(each);
            acetecAuthorizationFacade.creatAcetecAuthorization(acetecAuthorizationDTO);
            Long aid = acetecAuthorizationFacade.getLastAcetecAuthorization();
            aids.add(aid);

            //发送邮件
            EmployeeDTO employeeDTO = employeeFacade.getEmployeeById(each);
            String email = employeeDTO.getEmail();

            String subject = "测试程序签核";
            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append("您好，你有测试程序需要签核!\n");
            sBuilder.append(testProgramTemplateDTO.toString());

            sendEmail(subject, sBuilder.toString(), email);
        }


        testProgramTemplateDTO.setAcetecAuthorizationIds(aids);
        return testProgramTemplateFacade.createTestProgramTemplate(testProgramTemplateDTO);
    }

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(TestProgramTemplateDTO testProgramTemplateDTO) {
        //删除对应的authorization
        Long tid = testProgramTemplateDTO.getId();
        Page page = testProgramTemplateFacade.findAcetecAuthorizationsByTestProgram(tid, 0, 20);
        List<AcetecAuthorizationDTO> alist = page.getData();
        Long[] aids0 = new Long[alist.size()];
        boolean flag = false;
        for (int i = 0; i < alist.size(); i++) {
            if ((alist.get(i).getOpinion() != null && !alist.get(i).getOpinion().equals(""))
                    || (alist.get(i).getNote() != null && !alist.get(i).getNote().equals(""))) {
                flag = true;
                break;
            }
            aids0[i] = alist.get(i).getId();
        }
        if (flag == false) {
            acetecAuthorizationFacade.removeAcetecAuthorizations(aids0);
            //添加对应的authorization
            ArrayList<Long> aids = new ArrayList<Long>();
            for (Long each : testProgramTemplateDTO.getAcetecAuthorizationIds()) {
                AcetecAuthorizationDTO acetecAuthorizationDTO = new AcetecAuthorizationDTO();
                acetecAuthorizationDTO.setEmployeeId(each);
                acetecAuthorizationFacade.creatAcetecAuthorization(acetecAuthorizationDTO);
                Long aid = acetecAuthorizationFacade.getLastAcetecAuthorization();
                aids.add(aid);

                //发送邮件
                
                EmployeeDTO employeeDTO = employeeFacade.getEmployeeById(each);
                String email = employeeDTO.getEmail();

                String subject = "测试程序签核";
                String content = "您好，你有测试程序需要签核!" + "版本型号为" + testProgramTemplateDTO.getProductVersion();

                sendEmail(subject, content, email);
                
            }
            testProgramTemplateDTO.setAcetecAuthorizationIds(aids);
            return testProgramTemplateFacade.updateTestProgramTemplate(testProgramTemplateDTO);
        }

        return InvokeResult.failure("ERR：已授权");
    }


    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(TestProgramTemplateDTO testProgramTemplateDTO, @RequestParam int page, @RequestParam int pagesize) {
        Page<TestProgramTemplatePageVo> all = testProgramTemplateFacade.pageQueryTestProgramTemplate(testProgramTemplateDTO, page, pagesize);
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
        List<AcetecAuthorizationDTO> alist = new ArrayList<AcetecAuthorizationDTO>();
        for (int i = 0; i < value.length; i++) {
            Page page = testProgramTemplateFacade.findAcetecAuthorizationsByTestProgram(idArrs[i], 0, 20);
            alist.addAll(page.getData());
        }
        Long[] aids = new Long[alist.size()];
        for (int i = 0; i < alist.size(); i++) {
            aids[i] = alist.get(i).getId();
        }
        acetecAuthorizationFacade.removeAcetecAuthorizations(aids);
        return testProgramTemplateFacade.removeTestProgramTemplates(idArrs);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return testProgramTemplateFacade.getTestProgramTemplate(id);
    }


    @ResponseBody
    @RequestMapping("/findAcetecAuthorizationsByTestProgramTemplate/{id}")
    public Page findAcetecAuthorizationsByTestProgram(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
        Page<AcetecAuthorizationDTO> all = testProgramTemplateFacade.findAcetecAuthorizationsByTestProgram(id, page, pagesize);
        return all;
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
    @RequestMapping("/findTestProgramsByProduct/{id}")
    public List<TestProgramTemplateDTO> findTestProgramTemplatesByProduct(@PathVariable Long id) {
        return testProgramTemplateFacade.findTestProgramTemplatesByProduct(id);
    }

    @ResponseBody
    @RequestMapping("/getUPHReal/{id}")
    public String getUPHReal(@PathVariable Long id) {
        InvokeResult iR = testProgramTemplateFacade.getTestProgramTemplate(id);
        TestProgramTemplateDTO tDTO = (TestProgramTemplateDTO) iR.getData();
        return ("" + tDTO.getUPHReality());
    }

    @ResponseBody
    @RequestMapping("/setUPHReal")
    public InvokeResult setUPHReal(@RequestParam Long id, @RequestParam int uph) {
        InvokeResult iR = get(id);
        TestProgramTemplateDTO tDTO = (TestProgramTemplateDTO) iR.getData();
        tDTO.setUPHReality(uph);
        List<Long> llist = new ArrayList<Long>();
        List<AcetecAuthorizationDTO> alist = tDTO.getAcetecAuthorizationDTOs();
        if(alist!=null&&alist.get(0)!=null)
        	llist.add(alist.get(0).getEmployeeDTO().getId());
        tDTO.setAcetecAuthorizationIds(llist);
        return update(tDTO);
    }


    @ResponseBody
    @RequestMapping("/checkTheAuthorizer")
    public InvokeResult checkTheAuthorizer(@RequestParam Long testProgramTemplateId, @RequestParam Long userId) {
        return testProgramTemplateFacade.checkTheAuthorizer(testProgramTemplateId, userId);
    }
    
    @ResponseBody
    @RequestMapping("/getTesterList")
    public List<String> getTesterList() {
    	return EmsFetcher.getTesterList();
    }
    
    /**
     * 未用到
     * @param testProgramId
     * @param acetecAuthorizationDTO
     * @return
     */
    @ResponseBody
    @RequestMapping("/authorize/{testProgramId}")
    public InvokeResult authorize(@PathVariable Long testProgramId, AcetecAuthorizationDTO acetecAuthorizationDTO) {
        return testProgramTemplateFacade.authorize( testProgramId, acetecAuthorizationDTO );
    }
}
