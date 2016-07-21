package org.seu.acetec.mes2Koala.facade.impl.runcard;


import help.FilenameHelper;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.application.BaseApplication;
import org.openkoala.organisation.core.domain.Employee;
import org.openkoala.security.application.SecurityAccessApplication;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.seu.acetec.mes2Koala.application.*;
import org.seu.acetec.mes2Koala.core.common.ResourcesUtil;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.facade.common.SenderMailUtils;
import org.seu.acetec.mes2Koala.facade.dto.RuncardSignDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.SpecialFormStatusVo;
import org.seu.acetec.mes2Koala.facade.service.FTRuncardfacade;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LCN on 2016/3/10.
 */
@Named
@Transactional
public class FTRuncardfacadeImpl implements FTRuncardfacade {
    @Inject
    private FTInfoApplication ftInfoApplication;
    @Inject
    private BaseApplication baseApplication;
    @Inject
    private AcetecAuthorizationApplication acetecAuthorizationApplication;
    @Inject
    private FTRuncardTemplateApplication ftRuncardTemplateApplication;
    @Inject
    private SecurityAccessApplication securityAccessApplication;
    @Inject
    private SenderMailUtils senderMailUtils;
    @Inject
    private PlaceHolderApplication placeHolderApplication;
    @Inject
    private FTLotApplication ftLotApplication;
    @Inject
    private CustomerFTLotApplication customerFTLotApplication;
    @Inject
    private FTNodeApplication ftNodeApplication;


    /**
     * 验证是否已经创建runcard
     * 如果没有则创建runcard信息
     *
     * @param id ftinfo id
     * @return
     */
    private FTRuncardTemplate validateRuncardTemplate(Long id) {
        FTRuncardTemplate FTRuncardTemplate = ftRuncardTemplateApplication.findByInternalProductId(id);
        if (FTRuncardTemplate == null) {
            FTRuncardTemplate = new FTRuncardTemplate();
        }
        return FTRuncardTemplate;
    }


    /**
     * 创建ftruncard的模板
     * @return
     */
    private FTRuncardTemplate createFTRuncardTemplate() {
        FTRuncardTemplate ftRuncardTemplate = new FTRuncardTemplate();
        //特殊表单也在此时创建
        SpecialFormTemplate specialFormTemplate = new SpecialFormTemplate();
        specialFormTemplate.setRecordSheet(readRuncardinfoFromFiles("specialform/recordSheet"));
        specialFormTemplate.setReelcodeSheet(readRuncardinfoFromFiles("specialform/reelcodeSheet"));
        specialFormTemplate.setSummarySheet(readRuncardinfoFromFiles("specialform/summarySheet"));
        specialFormTemplate.setFirstSheet(readRuncardinfoFromFiles("specialform/firstSheet"));
        specialFormTemplate.setMachineMaterialRecordSheet(readRuncardinfoFromFiles("specialform/machineMaterialSheet"));
        specialFormTemplate.setCheckSheet(readRuncardinfoFromFiles("specialform/checkSheet"));
        ftRuncardTemplate.setSpecialFormTemplate(specialFormTemplate);
        //6个部门对应的签核信息
        ftRuncardTemplate.setKeyProductionAuthorization(new AcetecAuthorization());
        ftRuncardTemplate.setAssistProductionAuthorization(new AcetecAuthorization());

        ftRuncardTemplate.setKeyQuantityAuthorization(new AcetecAuthorization());
        ftRuncardTemplate.setAssistQuantityAuthorization(new AcetecAuthorization());

        ftRuncardTemplate.setKeyTDEAuthorization(new AcetecAuthorization());
        ftRuncardTemplate.setAssistTDEAuthorization(new AcetecAuthorization());

        return ftRuncardTemplate;
    }

    /**
     * 保存runcard
     *
     * @param id          ftinfoid
     * @param currentSite 对应的站点
     * @param data        对应的数据
     */
    public void saveRuncardInfo(Long id, String currentSite, String data) {
        FTInfo ftInfo = ftInfoApplication.get(id);

        FTRuncardTemplate ftRuncardTemplate = ftRuncardTemplateApplication.findByInternalProductId(id);

        if (ftRuncardTemplate == null) {
            ftRuncardTemplate = createFTRuncardTemplate();
            ftRuncardTemplate.setInternalProduct(ftInfo);
        }
        switch (currentSite) {
            case "IQC":
                ftRuncardTemplate.setIQC(data);
                break;
            case "OQC":
                ftRuncardTemplate.setOQC(data);
                break;
            case "FVI":
                ftRuncardTemplate.setFVI(data);
                break;
            case "FQC":
                ftRuncardTemplate.setFQC(data);
                break;
            case "Baking":
                ftRuncardTemplate.setBaking(data);
                break;
            case "Packing":
                ftRuncardTemplate.setPacking(data);
                break;
            case "siteTest":
                ftRuncardTemplate.setSiteTest(data);
                break;
            default:
                break;
        }
        ftRuncardTemplate.save();
    }


    /**
     * 值为null时转化为空字符串。不为null时，添加换行符输出
     *
     * @param data
     * @return
     */
    private String convertData(String data) {
        if (data == null) {
            data = "";
            return data;
        }
        return data + "<br/><br/>";
    }


    /**
     * 根据customerLotId获得runcardinfo的数据
     * @param customerLotId
     * @return
     */
    private String getRuncardInfoByCustomerLotId(Long customerLotId) {
        CustomerFTLot customerFTLot = customerFTLotApplication.get(customerLotId);
        return getRuncardInfo(customerFTLot.getFtInfo().getId());
    }


    /**
     * 根据ftinfo的id返回runcard信息
     *
     * @param id
     * @return
     */
    @Override
    public String getRuncardInfo(Long id) {

        FTRuncardTemplate FTRuncardTemplate = ftRuncardTemplateApplication.findByInternalProductId(id);
        if (FTRuncardTemplate == null)
            return "";
        /**
         * 取出延迟加载的所有属性
         */
        StringBuffer sb = new StringBuffer();
        //首页
        sb.append(FTRuncardTemplate.getSpecialFormTemplate().getFirstSheet());
        sb.append("<br/><br/>");
        //flow
        sb.append(convertData(FTRuncardTemplate.getIQC()));

        sb.append(convertData(FTRuncardTemplate.getBaking()));

        sb.append(convertData(FTRuncardTemplate.getSiteTest()));

        sb.append(convertData(FTRuncardTemplate.getFVI()));

        sb.append(convertData(FTRuncardTemplate.getPacking()));

        sb.append(convertData(FTRuncardTemplate.getFQC()));

        sb.append(convertData(FTRuncardTemplate.getOQC()));

        if (FTRuncardTemplate.getSpecialFormTemplate().getSummarySheetStatus()) {
            //summary
            sb.append(FTRuncardTemplate.getSpecialFormTemplate().getSummarySheet());
            sb.append("<br/><br/>");
        }

        if (FTRuncardTemplate.getSpecialFormTemplate().getRecordSheetStatus()) {
            //记录表
            sb.append(FTRuncardTemplate.getSpecialFormTemplate().getRecordSheet());
            sb.append("<br/><br/>");
        }

        if (FTRuncardTemplate.getSpecialFormTemplate().getReelcodeSheetStatus()) {
            //reelcode
            sb.append(FTRuncardTemplate.getSpecialFormTemplate().getReelcodeSheet());
            sb.append("<br/><br/>");
        }

        if (FTRuncardTemplate.getSpecialFormTemplate().getMachineMaterialRecordSheetStatus()) {
            //reelcode
            sb.append(FTRuncardTemplate.getSpecialFormTemplate().getMachineMaterialRecordSheet());
            sb.append("<br/><br/>");
        }


        if (FTRuncardTemplate.getSpecialFormTemplate().getCheckSheetStatus()) {
            //reelcode
            sb.append(FTRuncardTemplate.getSpecialFormTemplate().getCheckSheet());
            sb.append("<br/><br/>");
        }
        return sb.toString();
    }


    /**
     * 读取文件中的runcardinfo信息
     *
     * @param name
     * @return
     */
    private String readRuncardinfoFromFiles(String name) {
        InputStream in = null;
        try {
            in = new FileInputStream(this.getClass().getResource("/").getPath() + "ftinfo/" + name + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return FilenameHelper.readFileToString(in);
    }


    @Override
    public String getIQC(Long id) {
        String IQC = validateRuncardTemplate(id).getIQC();
        if (IQC == null) {
            IQC = readRuncardinfoFromFiles("IQC");
        }
        return IQC;
    }

    @Override
    public String getBaking(Long id) {
        String baking = validateRuncardTemplate(id).getBaking();
        if (baking == null) {
            baking = readRuncardinfoFromFiles("Baking");
        }
        return baking;
    }


    @Override
    public String getFVI(Long id) {
        String fvi = validateRuncardTemplate(id).getFVI();
        if (fvi == null) {
            fvi = readRuncardinfoFromFiles("FVI");
        }
        return fvi;
    }

    @Override
    public String getFQC(Long id) {
        String fqc = validateRuncardTemplate(id).getFQC();
        if (fqc == null) {
            fqc = readRuncardinfoFromFiles("FQC");
        }
        return fqc;
    }

    @Override
    public String getPacking(Long id) {
        String packing = validateRuncardTemplate(id).getPacking();
        if (packing == null) {
            packing = readRuncardinfoFromFiles("PACKING");
        }
        return packing;

    }

    @Override
    public String getOQC(Long id) {
        String oqc = validateRuncardTemplate(id).getOQC();
        if (oqc == null) {
            oqc = readRuncardinfoFromFiles("OQC");
        }
        return oqc;
    }

    @Override
    public String getSiteTest(Long id) {
        String siteTest = validateRuncardTemplate(id).getSiteTest();
        if (siteTest == null) {
            siteTest = readRuncardinfoFromFiles("siteTest");
        }
        return siteTest;
    }


    /**
     * 根据站点名获得runcardinfo数据
     *
     * @param id
     * @param siteName
     * @return
     */
    @Override
    public String getRuncardInfoBySiteName(Long id, String siteName) {
        String data = null;
        switch (siteName) {
            case "IQC":
                data = getIQC(id);
                break;
            case "OQC":
                data = getOQC(id);
                break;
            case "FVI":
                data = getFVI(id);
                break;
            case "FQC":
                data = getFQC(id);
                break;
            case "Baking":
                data = getBaking(id);
                break;
            case "Packing":
                data = getPacking(id);
                break;
            case "siteTest":
                data = getSiteTest(id);
                break;
            case "ALL":
                data = getRuncardInfo(id);
//                data = getRuncardInfoAfterReplaced(id);
                break;
            case "BeforeReplaced":
                //此时传入的id为CustomerftlotId
                data = getRuncardInfoByCustomerLotId(id);
                break;
            case "AfterReplaced":
                //此时传入的id为CustomerftlotId
                data = getRuncardInfoAfterReplaced(id);
                break;
            default:
                data = "";
        }
        return data;
    }


    /**
     * 验证runcard是否已经填写完成
     *
     * @param id
     * @param runcardinfo
     * @return
     */
    public boolean isRuncardFinished(Long id, String[] runcardinfo) {

        FTRuncardTemplate FTRuncardTemplate = ftRuncardTemplateApplication.findByInternalProductId(id);
        if (FTRuncardTemplate == null) {
            return false;
        }
        String data = null;
        for (String s : runcardinfo) {
            switch (s) {
                case "IQC":
                    data = FTRuncardTemplate.getIQC();
                    break;
                case "OQC":
                    data = FTRuncardTemplate.getOQC();
                    break;
                case "FVI":
                    data = FTRuncardTemplate.getFVI();
                    break;
                case "FQC":
                    data = FTRuncardTemplate.getFQC();
                    break;
                case "Baking":
                    data = FTRuncardTemplate.getBaking();
                    break;
                case "siteTest":
                    data = FTRuncardTemplate.getSiteTest();
                    break;
                case "Packing":
                    data = FTRuncardTemplate.getPacking();
                    break;
                default:
                    continue;
            }
            if (data == null)
                return false;
        }
        return true;
    }


    /**
     * 判断runcardinfo是否已经全部填写完成
     * @param customerLotId
     * @param runcardinfo
     * @return
     */
    public boolean isRuncardFinished2(Long customerLotId, String[] runcardinfo) {
        CustomerFTLot customerFTLot = customerFTLotApplication.get(customerLotId);
        return isRuncardFinished(customerFTLot.getFtInfo().getId(), runcardinfo);
    }


    /**
     * 发送邮件
     *
     * @param to 发给者的邮箱
     * @return 发出true  失败 false
     */
    @Override
    public boolean sendEmail(String to, String subject, String content) {
        /**
         * 验证邮箱
         */
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(to);
        if (!matcher.matches()) {
            return false;
        }

        senderMailUtils.sendMailHelper(subject, content, to);

        return true;
    }


    /**
     * 暂时不对sendemail的信息的返回值进行判断
     * 如果需要对发出邮件的成功和失败信息进行进一步的统计,此处需要修改
     *
     * @param emails
     */
    @Override
    public void sendEmailToPersons(List<String> emails, String subject, String content) {
        for (String email : emails) {
            sendEmail(email, subject, content);
        }
    }


    /**
     * 异步发送邮件
     * @param id
     */
    @Async
    @Override
    public void sendEmailToPersons(Long id) {
        //拿到所有的产品负责人,并向他们发送邮件   async防止前端页面阻塞
        //如果需要回馈邮箱的成功与否  需要使用消息队列
        String subject = ResourcesUtil.getValue("mail", "subject");
        FTInfo ftInfo = ftInfoApplication.get(id);
        String customerName = ftInfo.getCustomerDirect().getChineseName();
        String customerProductNumber = ftInfo.getCustomerProductNumber();
        String customerProductRevision = ftInfo.getCustomerProductRevision();
        String[] contentData = {customerName, customerProductNumber, customerProductRevision};
        String content = ResourcesUtil.getValue("mail", "content", contentData);

        List<String> emails = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        employees = getAllEmployees(id);
        emails = getAllEmployeesEmail(employees);
        sendEmailToPersons(emails, subject, content);
    }


    /**
     * 获取所有的产品负责人员  用于发送邮件
     *
     * @param id
     * @return
     */
    @Override
    public List<Employee> getAllEmployees(Long id) {
        List<Employee> list = new ArrayList<>();
        FTInfo ftInfo = ftInfoApplication.get(id);
        if (ftInfo == null) {
            return new ArrayList<Employee>();
        }
        Employee keyProductionManager = ftInfo.getKeyProductionManager();
        Employee assistProductionManager = ftInfo.getAssistProductionManager();
        Employee keyQuantityManager = ftInfo.getKeyQuantityManager();
        Employee assistQuantityManager = ftInfo.getAssistQuantityManager();
        Employee keyTDEManager = ftInfo.getKeyTDEManager();
        Employee assistTDEManager = ftInfo.getAssistTDEManager();
        if (keyProductionManager != null) {
            list.add(keyProductionManager);
        }
        if (assistProductionManager != null) {
            list.add(assistProductionManager);
        }
        if (keyQuantityManager != null) {
            list.add(keyQuantityManager);
        }
        if (assistQuantityManager != null) {
            list.add(assistQuantityManager);
        }
        if (keyTDEManager != null) {
            list.add(keyTDEManager);
        }
        if (assistTDEManager != null) {
            list.add(assistTDEManager);
        }
        return list;
    }


    /**
     * 获得所有产品负责人的邮箱
     *
     * @param employees
     * @return
     */
    @Override
    public List<String> getAllEmployeesEmail(List<Employee> employees) {
        List<String> emails = new ArrayList<>();
        for (Employee employee : employees) {
            String email = employee.getPerson().getEmail();
            if (email != null) {
                emails.add(email);
            }
        }
        return emails;
    }


    /**
     * 根据当前登入人的id 查出它的employeeid
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

    /**
     * @param ftinfoId
     * @param userId
     * @param opinion
     * @param note
     * @return
     */
    @Override
    public InvokeResult signRuncardInfo(Long ftinfoId, Long userId, String opinion, String note) {
        FTInfo ftInfo = ftInfoApplication.get(ftinfoId);
        if (ftInfo == null) {
            return InvokeResult.failure("数据错误");
        }
        FTRuncardTemplate FTRuncardTemplate = ftRuncardTemplateApplication.findByInternalProductId(ftinfoId);

        AcetecAuthorization acetecAuthorization = null;

        Long employeeid = getEmployeeIdByUserId(userId);

        if (employeeid == null) {
            InvokeResult.failure("没有此雇员信息");
        }

        //根据id查找对应的部门   在此之前保证id的合法性
        if (ftInfo.getKeyProductionManager().getId().equals(employeeid)) {
            acetecAuthorization = FTRuncardTemplate.getKeyProductionAuthorization();
            acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class, employeeid));
            acetecAuthorization.setOpinion(opinion);
            acetecAuthorization.setNote(note);
            FTRuncardTemplate.setKeyProductionAuthorization(acetecAuthorization);
        } else if (ftInfo.getAssistProductionManager().getId().equals(employeeid)) {
            acetecAuthorization = FTRuncardTemplate.getAssistProductionAuthorization();

            acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class, employeeid));
            acetecAuthorization.setOpinion(opinion);
            acetecAuthorization.setNote(note);

            FTRuncardTemplate.setAssistProductionAuthorization(acetecAuthorization);
        } else if (ftInfo.getKeyQuantityManager().getId().equals(employeeid)) {
            acetecAuthorization = FTRuncardTemplate.getKeyQuantityAuthorization();

            acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class, employeeid));
            acetecAuthorization.setOpinion(opinion);
            acetecAuthorization.setNote(note);

            FTRuncardTemplate.setKeyQuantityAuthorization(acetecAuthorization);
        } else if (ftInfo.getAssistQuantityManager().getId().equals(employeeid)) {
            acetecAuthorization = FTRuncardTemplate.getAssistQuantityAuthorization();

            acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class, employeeid));
            acetecAuthorization.setOpinion(opinion);
            acetecAuthorization.setNote(note);

            FTRuncardTemplate.setAssistQuantityAuthorization(acetecAuthorization);
        } else if (ftInfo.getKeyTDEManager().getId().equals(employeeid)) {
            acetecAuthorization = FTRuncardTemplate.getKeyTDEAuthorization();

            acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class, employeeid));
            acetecAuthorization.setOpinion(opinion);
            acetecAuthorization.setNote(note);

            FTRuncardTemplate.setKeyTDEAuthorization(acetecAuthorization);
        } else if (ftInfo.getAssistTDEManager().getId().equals(employeeid)) {
            acetecAuthorization = FTRuncardTemplate.getAssistTDEAuthorization();
            acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class, employeeid));
            acetecAuthorization.setOpinion(opinion);
            acetecAuthorization.setNote(note);
            FTRuncardTemplate.setAssistTDEAuthorization(acetecAuthorization);
        }


        FTRuncardTemplate.setSignedTime(new Date());
        ftRuncardTemplateApplication.update(FTRuncardTemplate);

        return InvokeResult.success("签核成功");
    }


    /**
     * 验证runcard是否已经签核
     *
     * @param id
     * @return
     */

    @Override
    public boolean isRuncardInfoSigned(Long id) {
        return ftRuncardTemplateApplication.isRuncardSigned(id);
    }


    /**
     * 根据人员的id查找他对应的部门信息
     *
     * @param ftinfoId
     * @param userId
     * @return
     */
    @Override
    public String getDepartmentByEmployeeId(Long ftinfoId, Long userId) {
        Long employeeId = getEmployeeIdByUserId(userId);
        //查不到雇员信息
        if (employeeId == null) {
            return "false";
        }
        FTInfo ftInfo = ftInfoApplication.get(ftinfoId);
        if (ftInfo.getKeyProductionManager().getId().equals(employeeId)) {
            return "产品部门主负责人";
        } else if (ftInfo.getAssistProductionManager().getId().equals(employeeId)) {
            return "产品部门协助负责人";
        } else if (ftInfo.getKeyQuantityManager().getId().equals(employeeId)) {
            return "质量部门主负责人";
        } else if (ftInfo.getAssistQuantityManager().getId().equals(employeeId)) {
            return "质量部门协助负责人";
        } else if (ftInfo.getKeyTDEManager().getId().equals(employeeId)) {
            return "TDE部门主负责人";
        } else if (ftInfo.getAssistTDEManager().getId().equals(employeeId)) {
            return "TDE部门协助负责人";
        } else {
            return "false";
        }
    }


    /**
     * 得到雇员的runcard签核信息
     *
     * @param ftinfoId
     * @param userId
     * @return
     */
    @Override
    public RuncardSignDTO getRuncardSignInfo(Long ftinfoId, Long userId) {

        Long employeeid = getEmployeeIdByUserId(userId);
        RuncardSignDTO runcardSignDTO = new RuncardSignDTO();

        //查不到雇员信息
        if (employeeid == null) {
            runcardSignDTO.setValidate(false);
            return runcardSignDTO;
        }


        AcetecAuthorization acetecAuthorization = new AcetecAuthorization();

        FTInfo ftInfo = ftInfoApplication.get(ftinfoId);

        FTRuncardTemplate FTRuncardTemplate = ftRuncardTemplateApplication.findByInternalProductId(ftinfoId);
        if (FTRuncardTemplate == null) {
            runcardSignDTO.setValidate(false);
            return runcardSignDTO;
        }
        if (ftInfo.getKeyProductionManager().getId().equals(employeeid)) {
            acetecAuthorization = ftRuncardTemplateApplication.findByInternalProductId(ftinfoId).getKeyProductionAuthorization();

            runcardSignDTO.setOpinion(acetecAuthorization.getOpinion());
            runcardSignDTO.setNote(acetecAuthorization.getNote());
            runcardSignDTO.setDepartmentName("产品部门主负责人");
            runcardSignDTO.setValidate(true);
        } else if (ftInfo.getAssistProductionManager().getId().equals(employeeid)) {
            acetecAuthorization = ftRuncardTemplateApplication.findByInternalProductId(ftinfoId).getAssistProductionAuthorization();

            runcardSignDTO.setOpinion(acetecAuthorization.getOpinion());
            runcardSignDTO.setNote(acetecAuthorization.getNote());
            runcardSignDTO.setDepartmentName("产品部门协助负责人");
            runcardSignDTO.setValidate(true);
        } else if (ftInfo.getKeyQuantityManager().getId().equals(employeeid)) {
            acetecAuthorization = ftRuncardTemplateApplication.findByInternalProductId(ftinfoId).getKeyQuantityAuthorization();

            runcardSignDTO.setOpinion(acetecAuthorization.getOpinion());
            runcardSignDTO.setNote(acetecAuthorization.getNote());
            runcardSignDTO.setDepartmentName("质量部门主负责人");
            runcardSignDTO.setValidate(true);
        } else if (ftInfo.getAssistQuantityManager().getId().equals(employeeid)) {
            acetecAuthorization = ftRuncardTemplateApplication.findByInternalProductId(ftinfoId).getAssistQuantityAuthorization();

            runcardSignDTO.setOpinion(acetecAuthorization.getOpinion());
            runcardSignDTO.setNote(acetecAuthorization.getNote());
            runcardSignDTO.setDepartmentName("质量部门协助负责人");
            runcardSignDTO.setValidate(true);
        } else if (ftInfo.getKeyTDEManager().getId().equals(employeeid)) {
            acetecAuthorization = ftRuncardTemplateApplication.findByInternalProductId(ftinfoId).getKeyTDEAuthorization();

            runcardSignDTO.setOpinion(acetecAuthorization.getOpinion());
            runcardSignDTO.setNote(acetecAuthorization.getNote());
            runcardSignDTO.setDepartmentName("TDE部门主负责人");
            runcardSignDTO.setValidate(true);
        } else if (ftInfo.getAssistTDEManager().getId().equals(employeeid)) {
            acetecAuthorization = ftRuncardTemplateApplication.findByInternalProductId(ftinfoId).getAssistTDEAuthorization();

            runcardSignDTO.setOpinion(acetecAuthorization.getOpinion());
            runcardSignDTO.setNote(acetecAuthorization.getNote());
            runcardSignDTO.setDepartmentName("TDE部门协助负责人");
            runcardSignDTO.setValidate(true);
        } else {
            runcardSignDTO.setValidate(false);
        }

        return runcardSignDTO;
    }


    /**
     * 获取特殊表单
     *
     * @param ftinfoId
     * @return
     */
    @Override
    public String getSpecialFormByFtinfoId(Long ftinfoId, String sheetType) {
        FTRuncardTemplate FTRuncardTemplate = ftRuncardTemplateApplication.findByInternalProductId(ftinfoId);
        String data = "";
        if (FTRuncardTemplate == null) {
            switch (sheetType) {
                case "reelcodeSheet":
                    data = readRuncardinfoFromFiles("specialform/reelcodeSheet");
                    break;
                case "recordSheet":
                    data = readRuncardinfoFromFiles("specialform/recordSheet");
                    break;
                case "summarySheet":
                    data = readRuncardinfoFromFiles("specialform/summarySheet");
                    break;
                case "firstSheet":
                    data = readRuncardinfoFromFiles("specialform/firstSheet");
                    break;
                case "checkSheet":
                    data = readRuncardinfoFromFiles("specialform/checkSheet");
                    break;
                case "machineMaterialRecordSheet":
                    data = readRuncardinfoFromFiles("specialform/machineMaterialSheet");
                    break;
                default:
                    data = "";
            }
            return data;
        }
        switch (sheetType) {
            case "reelcodeSheet":
                data = FTRuncardTemplate.getSpecialFormTemplate().getReelcodeSheet();
                break;
            case "recordSheet":
                data = FTRuncardTemplate.getSpecialFormTemplate().getRecordSheet();
                break;
            case "summarySheet":
                data = FTRuncardTemplate.getSpecialFormTemplate().getSummarySheet();
                break;
            case "firstSheet":
                data = FTRuncardTemplate.getSpecialFormTemplate().getFirstSheet();
                break;
            case "checkSheet":
                data = FTRuncardTemplate.getSpecialFormTemplate().getCheckSheet();
                break;
            case "machineMaterialRecordSheet":
                data = FTRuncardTemplate.getSpecialFormTemplate().getMachineMaterialRecordSheet();
                break;
            default:
                data = "";
        }

        return data;
    }


    /**
     * 保存特殊表单的打印状态
     * @param id
     * @param specialFormStatusVo
     * @return
     */
    @Override
    public InvokeResult saveSpecialFormStatus(Long id, SpecialFormStatusVo specialFormStatusVo) {
        FTRuncardTemplate ftRuncardTemplate = ftRuncardTemplateApplication.findByInternalProductId(id);

        if (ftRuncardTemplate == null) {
            ftRuncardTemplate = createFTRuncardTemplate();
            FTInfo ftInfo = ftInfoApplication.get(id);
            ftRuncardTemplate.setInternalProduct(ftInfo);
        }
        ftRuncardTemplate.getSpecialFormTemplate().setRecordSheetStatus(specialFormStatusVo.getRecordSheetStatus());
        ftRuncardTemplate.getSpecialFormTemplate().setReelcodeSheetStatus(specialFormStatusVo.getReelcodeSheetStatus());
//        FTRuncardTemplate.setFirstSheetStatus(specialFormStatusVo.getFirstSheetStatus());
        ftRuncardTemplate.getSpecialFormTemplate().setSummarySheetStatus(specialFormStatusVo.getSummarySheetStatus());
        ftRuncardTemplate.getSpecialFormTemplate().setMachineMaterialRecordSheetStatus(specialFormStatusVo.getMachineMaterialRecordSheetStatus());
        ftRuncardTemplate.getSpecialFormTemplate().setCheckSheetStatus(specialFormStatusVo.getCheckSheetStatus());

        ftRuncardTemplate.save();

        return InvokeResult.success("保存成功");
    }


    /**
     * 获得特殊表单的打印状态
     * @param id
     * @return
     */
    @Override
    public InvokeResult getSpecialFormStatus(Long id) {
        FTRuncardTemplate FTRuncardTemplate = ftRuncardTemplateApplication.findByInternalProductId(id);
        SpecialFormStatusVo specialFormStatusVo = new SpecialFormStatusVo();
        if (FTRuncardTemplate == null) {
            return InvokeResult.success(specialFormStatusVo);
        }
        specialFormStatusVo.setFirstSheetStatus(FTRuncardTemplate.getSpecialFormTemplate().getFirstSheetStatus());
        specialFormStatusVo.setRecordSheetStatus(FTRuncardTemplate.getSpecialFormTemplate().getRecordSheetStatus());
        specialFormStatusVo.setReelcodeSheetStatus(FTRuncardTemplate.getSpecialFormTemplate().getReelcodeSheetStatus());
        specialFormStatusVo.setSummarySheetStatus(FTRuncardTemplate.getSpecialFormTemplate().getSummarySheetStatus());
        specialFormStatusVo.setMachineMaterialRecordSheetStatus(FTRuncardTemplate.getSpecialFormTemplate().getMachineMaterialRecordSheetStatus());
        specialFormStatusVo.setCheckSheetStatus(FTRuncardTemplate.getSpecialFormTemplate().getCheckSheetStatus());
        return InvokeResult.success(specialFormStatusVo);
    }




    /**
     * 获得占位符被替换以后的特殊表单的数据
     * @param ftLotId
     * @return
     */
    @Override
    public String getRuncardInfoAfterReplaced(Long ftLotId) {

        FTLot ftLot = ftLotApplication.get(ftLotId);
        CustomerFTLot customerFTLot = ftLot.getCustomerFTLot();
        FTInfo ftInfo = customerFTLot.getFtInfo();

        FTRuncard ftRuncard = ftLot.getFtRuncard();
        PlaceHolder placeHolder = new PlaceHolder();
        placeHolder = placeHolderApplication.findByFTLot(ftLotId);


        replaceIQCPlaceholder(ftRuncard, placeHolder);
        replaceBakingPlaceholder(ftRuncard, placeHolder);
        replaceTestPlaceholder(ftRuncard, placeHolder);
        replaceFVIPlaceholder(ftRuncard, placeHolder);
        replacePackingPlaceholder(ftRuncard, placeHolder);
        replaceFQCPlaceholder(ftRuncard, placeHolder);
        replaceOQCPlaceholder(ftRuncard, placeHolder);


        StringBuffer sb = new StringBuffer();
        SpecialFormTemplate specialFormTemplate = ftRuncard.getSpecialFormTemplate();
        //首页 首页替换
        sb.append(replaceFirstSheetPlaceholder(ftLotId, specialFormTemplate.getFirstSheet(), placeHolder));
        sb.append("<br/><br/>");
        //flow
        sb.append(convertData(ftRuncard.getIQC()));

        sb.append(convertData(ftRuncard.getBaking()));

        sb.append(convertData(ftRuncard.getSiteTest()));

        sb.append(convertData(ftRuncard.getFVI()));

        sb.append(convertData(ftRuncard.getPacking()));

        sb.append(convertData(ftRuncard.getFQC()));

        sb.append(convertData(ftRuncard.getOQC()));

        if (specialFormTemplate.getSummarySheetStatus()) {
            //summary
            sb.append(specialFormTemplate.getSummarySheet());
            sb.append("<br/><br/>");
        }

        if (specialFormTemplate.getRecordSheetStatus()) {
            //记录表
            sb.append(specialFormTemplate.getRecordSheet());
            sb.append("<br/><br/>");
        }

        if (specialFormTemplate.getReelcodeSheetStatus()) {
            //reelcode
            sb.append(specialFormTemplate.getReelcodeSheet());
            sb.append("<br/><br/>");
        }

        if (specialFormTemplate.getMachineMaterialRecordSheetStatus()) {
            //reelcode
            sb.append(specialFormTemplate.getMachineMaterialRecordSheet());
            sb.append("<br/><br/>");
        }


        if (specialFormTemplate.getCheckSheetStatus()) {
            //reelcode
            sb.append(specialFormTemplate.getCheckSheet());
            sb.append("<br/><br/>");
        }

        return sb.toString();
    }




    private void replaceIQCPlaceholder(FTRuncard ftRuncard, PlaceHolder placeHolder) {
        String IQC = ftRuncard.getIQC();
        String Worker = ResourcesUtil.getValue("placeholder", "Worker");
        String IQCMaterialWeight = ResourcesUtil.getValue("placeholder", "IQCMaterialWeight");
        String ActualWeight = ResourcesUtil.getValue("placeholder", "ActualWeight");
        String DifferenceValue = ResourcesUtil.getValue("placeholder", "DifferenceValue");

        String WorkerReplace = placeHolder.getWorker();
        String IQCMaterialWeightReplace = placeHolder.getIQCMaterialWeight();
        String ActualWeightReplace = placeHolder.getActualWeight();
        String DifferenceValueReplace = placeHolder.getDifferenceValue();

        if (WorkerReplace != null)
            IQC = IQC.replace(Worker, WorkerReplace);
        if (IQCMaterialWeightReplace != null)
            IQC = IQC.replace(IQCMaterialWeight, IQCMaterialWeightReplace);
        if (ActualWeightReplace != null)
            IQC = IQC.replace(ActualWeight, ActualWeightReplace);
        if (DifferenceValueReplace != null)
            IQC = IQC.replace(DifferenceValue, DifferenceValueReplace);
        ftRuncard.setIQC(IQC);
    }


    private void replaceBakingPlaceholder(FTRuncard ftRuncard, PlaceHolder placeHolder) {
        String baking = ftRuncard.getBaking();

        String OvenNumber = ResourcesUtil.getValue("placeholder", "OvenNumber");
        String BakingTemp = ResourcesUtil.getValue("placeholder", "BakingTemp");
        String BakingTime = ResourcesUtil.getValue("placeholder", "BakingTime");
        String CheckInTime = ResourcesUtil.getValue("placeholder", "CheckInTime");
        String CheckOutTime = ResourcesUtil.getValue("placeholder", "CheckOutTime");
        String EnterBakingOperator = ResourcesUtil.getValue("placeholder", "EnterBakingOperator");
        String OutBakingOperator = ResourcesUtil.getValue("placeholder", "OutBakingOperator");

        String OvenNumberReplace = placeHolder.getOvenNumber();
        String BakingTempReplace = placeHolder.getBakingTemp();
        String BakingTimeReplace = placeHolder.getBakingTime();
        String CheckInTimeReplace = placeHolder.getCheckInTime();
        String CheckOutTimeReplace = placeHolder.getCheckOutTime();
        String EnterBakingOperatorReplace = placeHolder.getEnterBakingOperator();
        String OutBakingOperatorReplace = placeHolder.getOutBakingOperator();


        if (OvenNumberReplace != null)
            baking = baking.replace(OvenNumber, OvenNumberReplace);
        if (BakingTempReplace != null)
            baking = baking.replace(BakingTemp, BakingTempReplace);
        if (BakingTimeReplace != null)
            baking = baking.replace(BakingTime, BakingTimeReplace);
        if (CheckInTimeReplace != null)
            baking = baking.replace(CheckInTime, CheckInTimeReplace);
        if (CheckOutTimeReplace != null)
            baking = baking.replace(CheckOutTime, CheckOutTimeReplace);
        if (EnterBakingOperatorReplace != null)
            baking = baking.replace(EnterBakingOperator, EnterBakingOperatorReplace);
        if (OutBakingOperatorReplace != null)
            baking = baking.replace(OutBakingOperator, OutBakingOperatorReplace);

        ftRuncard.setBaking(baking);
    }


    private void replaceTestPlaceholder(FTRuncard ftRuncard, PlaceHolder placeHolder) {
        String siteTest = ftRuncard.getSiteTest();

        String EQCLowYieldReport = ResourcesUtil.getValue("placeholder", "EQCLowYieldReport");
        String TesterType = ResourcesUtil.getValue("placeholder", "TesterType");
        String NormalTestProgram = ResourcesUtil.getValue("placeholder", "NormalTestProgram");
        String ProgramVersion = ResourcesUtil.getValue("placeholder", "ProgramVersion");


        String RejectReport = ResourcesUtil.getValue("placeholder", "RejectReport");
        String LATOprator = ResourcesUtil.getValue("placeholder", "LATOprator");


        String EQCLowYieldReportReplace = placeHolder.getEQCLowYieldReport();
        String TesterTypeReplace = placeHolder.getTesterType();
        String NormalTestProgramReplace = placeHolder.getNormalTestProgram();
        String ProgramVersionReplace = placeHolder.getProgramVersion();


        String RejectReportReplace = placeHolder.getRejectReport();
        String LATOpratorReplace = placeHolder.getLATOprator();


        if (EQCLowYieldReportReplace != null)
            siteTest = siteTest.replace(EQCLowYieldReport, EQCLowYieldReportReplace);
        if (TesterTypeReplace != null)
            siteTest = siteTest.replace(TesterType, TesterTypeReplace);
        if (NormalTestProgramReplace != null)
            siteTest = siteTest.replace(NormalTestProgram, NormalTestProgramReplace);
        if (ProgramVersionReplace != null)
            siteTest = siteTest.replace(ProgramVersion, ProgramVersionReplace);

        if (RejectReportReplace != null)
            siteTest = siteTest.replace(RejectReport, RejectReportReplace);
        if (LATOpratorReplace != null)
            siteTest = siteTest.replace(LATOprator, LATOpratorReplace);

        ftRuncard.setSiteTest(siteTest);


    }

    private void replaceFVIPlaceholder(FTRuncard ftRuncard, PlaceHolder placeHolder) {
        String FVI = ftRuncard.getFVI();

        String FVILowYieldReport = ResourcesUtil.getValue("placeholder", "FVILowYieldReport");
        String FVIOperator = ResourcesUtil.getValue("placeholder", "FVIOperator");

        String FVILowYieldReportReplace = placeHolder.getFVILowYieldReport();
        String FVIOperatorReplace = placeHolder.getFVIOperator();

        if (FVILowYieldReportReplace != null)
            FVI = FVI.replace(FVILowYieldReport, FVILowYieldReportReplace);
        if (FVIOperatorReplace != null)
            FVI = FVI.replace(FVIOperator, FVIOperatorReplace);

        ftRuncard.setFVI(FVI);
    }


    private void replacePackingPlaceholder(FTRuncard ftRuncard, PlaceHolder placeHolder) {
        String packing = ftRuncard.getPacking();

        String PackingRejectReport = ResourcesUtil.getValue("placeholder", "PackingRejectReport");

        String PackingRejectReportReplace = placeHolder.getPackingRejectReport();
        if (PackingRejectReportReplace != null)
            packing = packing.replace(PackingRejectReport, PackingRejectReportReplace);

        ftRuncard.setPacking(packing);
    }


    private void replaceFQCPlaceholder(FTRuncard ftRuncard, PlaceHolder placeHolder) {
        String FQC = ftRuncard.getFQC();

        String FQCRejectReport = ResourcesUtil.getValue("placeholder", "FQCRejectReport");
        String FQCOperator = ResourcesUtil.getValue("placeholder", "FQCOperator");

        String FQCRejectReportReplace = placeHolder.getFQCRejectReport();
        String FQCOperatorReplace = placeHolder.getFQCOperator();
        if (FQCRejectReportReplace != null)
            FQC = FQC.replace(FQCRejectReport, FQCRejectReportReplace);
        if (FQCOperatorReplace != null)
            FQC = FQC.replace(FQCOperator, FQCOperatorReplace);

        ftRuncard.setFQC(FQC);
    }


    private void replaceOQCPlaceholder(FTRuncard ftRuncard, PlaceHolder placeHolder) {
        String OQC = ftRuncard.getOQC();

        String OQCRejectReport = ResourcesUtil.getValue("placeholder", "OQCRejectReport");
        String OQCOperator = ResourcesUtil.getValue("placeholder", "OQCOperator");

        String OQCRejectReportReplace = placeHolder.getOQCRejectReport();
        String OQCOperatorReplace = placeHolder.getOQCOperator();

        if (OQCRejectReportReplace != null)
            OQC = OQC.replace(OQCRejectReport, OQCRejectReportReplace);
        if (OQCOperatorReplace != null)
            OQC = OQC.replace(OQCOperator, OQCOperatorReplace);

        ftRuncard.setOQC(OQC);
    }


    private String replaceFirstSheetPlaceholder(Long ftLotId, String data, PlaceHolder placeHolder) {
        String customerCode = ResourcesUtil.getValue("placeholder", "customerCode");
        String productName = ResourcesUtil.getValue("placeholder", "productName");
        String productRevision = ResourcesUtil.getValue("placeholder", "productRevision");
        String assyCompanyName = ResourcesUtil.getValue("placeholder", "assyCompanyName");
        String assyLot = ResourcesUtil.getValue("placeholder", "assyLot");
        String customerLot = ResourcesUtil.getValue("placeholder", "customerLot");
        String acetecLot = ResourcesUtil.getValue("placeholder", "acetecLot");
        String dataCode = ResourcesUtil.getValue("placeholder", "dataCode");
        String packageSize = ResourcesUtil.getValue("placeholder", "packageSize");
        String internalPPO = ResourcesUtil.getValue("placeholder", "customerPPO");
        String receiveData = ResourcesUtil.getValue("placeholder", "receiveData");
        String receiveQty = ResourcesUtil.getValue("placeholder", "receiveQty");
        String bondOrNot = ResourcesUtil.getValue("placeholder", "taxType");
        String packingType = ResourcesUtil.getValue("placeholder", "packingType");
        String shippingType = ResourcesUtil.getValue("placeholder", "shippingType");
        String waferManufacturer = ResourcesUtil.getValue("placeholder", "waferManufacturer");
        String keyQuantityManagerName = ResourcesUtil.getValue("placeholder", "keyQuantityManagerName");
        String keyProductionManagerName = ResourcesUtil.getValue("placeholder", "keyProductionManagerName");
        String keyTDEManagerName = ResourcesUtil.getValue("placeholder", "keyTDEManagerName");


        String customerCodeReplace = placeHolder.getCustomerCode();
        String productNameReplace = placeHolder.getProductName();
        String productRevisionReplace = placeHolder.getProductVersion();
        String assyCompanyNameReplace = placeHolder.getAssyCompanyName();
        String assyLotReplace = placeHolder.getAssyLot();
        String customerLotReplace = placeHolder.getCustomerLot();
        String acetecLotReplace = placeHolder.getAcetecLot();
        String dataCodeReplace = placeHolder.getDataCode();
        String packageSizeReplace = placeHolder.getPackageSize();
        String internalPPOReplace = placeHolder.getCustomerPPO();
        String receiveDataReplace = placeHolder.getReceiveData();
        String receiveQtyReplace = placeHolder.getReceiveQty();
        String bondOrNotReplace = placeHolder.getTaxType();
        String packingTypeReplace = placeHolder.getPackingType();
        String shippingTypeReplace = placeHolder.getShippingType();
        String waferManufacturerReplace = placeHolder.getWaferManufacturer();
        String keyQuantityManagerNameReplace = placeHolder.getKeyQuantityManagerName();
        String keyProductionManagerNameReplace = placeHolder.getKeyProductionManagerName();
        String keyTDEManagerNameReplace = placeHolder.getKeyTDEManagerName();


        FTLot ftLot = ftLotApplication.get(ftLotId);
        if (ftLot != null){
            FTRuncard ftRuncard = ftLot.getFtRuncard();
            String signedTimeReplace = ftRuncard.getSignedTime().toString();
            String signedTime = ResourcesUtil.getValue("placeholder", "signedTime");
            data = data.replace(signedTime, signedTimeReplace);
        }

        if (customerCodeReplace != null)
            data = data.replace(customerCode, customerCodeReplace);
        if (productNameReplace != null)
            data = data.replace(productName, productNameReplace);
        if (productRevisionReplace != null)
            data = data.replace(productRevision, productRevisionReplace);
        if (assyCompanyNameReplace != null)
            data = data.replace(assyCompanyName, assyCompanyNameReplace);
        if (assyLotReplace != null)
            data = data.replace(assyLot, assyLotReplace);
        if (customerLotReplace != null)
            data = data.replace(customerLot, customerLotReplace);
        if (acetecLotReplace != null)
            data = data.replace(acetecLot, acetecLotReplace);
        if (dataCodeReplace != null)
            data = data.replace(dataCode, dataCodeReplace);
        if (packageSizeReplace != null)
            data = data.replace(packageSize, packageSizeReplace);
        if (internalPPOReplace != null)
            data = data.replace(internalPPO, internalPPOReplace);
        if (receiveDataReplace != null)
            data = data.replace(receiveData, receiveDataReplace);
        if (receiveQtyReplace != null)
            data = data.replace(receiveQty, receiveQtyReplace);
        if (bondOrNotReplace != null)
            data = data.replace(bondOrNot, bondOrNotReplace);
        if (packingTypeReplace != null)
            data = data.replace(packingType, packingTypeReplace);
        if (shippingTypeReplace != null)
            data = data.replace(shippingType, shippingTypeReplace);
        if (waferManufacturerReplace != null )
        	data = data.replace(shippingType, shippingTypeReplace);
        if (keyProductionManagerNameReplace != null )
        	data = data.replace(keyProductionManagerName, keyProductionManagerNameReplace);
        if (keyQuantityManagerNameReplace != null )
        	data = data.replace(keyQuantityManagerName, keyQuantityManagerNameReplace);
        if (keyTDEManagerNameReplace != null )
        	data = data.replace(keyTDEManagerName, keyTDEManagerNameReplace);

        return data;
    }


    /**
     * 根据state状态获得runcardinfo数据
     * @param ftLotId
     * @param specialFormArr
     * @param state
     * @return
     */
    @Override
    public InvokeResult getRuncardInfoByState(Long ftLotId, String[] specialFormArr, Long state) {
        FTLot ftLot = ftLotApplication.get(ftLotId);
        FTRuncard ftRuncard = ftLot.getFtRuncard();

        StringBuilder sb = new StringBuilder();
        PlaceHolder placeHolder = placeHolderApplication.findByFTLot(ftLotId);
        for (String specialForm : specialFormArr) {
            SpecialFormTemplate specialFormTemplate = ftRuncard.getSpecialFormTemplate();

            switch (specialForm) {
                case "reelcodeSheet":
                    sb.append(specialFormTemplate.getReelcodeSheet());
                    break;
                case "recordSheet":
                    sb.append(specialFormTemplate.getRecordSheet());
                    break;
                case "summarySheet":
                    sb.append(specialFormTemplate.getSummarySheet());
                    break;
                case "firstSheet":
                    sb.append(replaceFirstSheetPlaceholder(ftLotId,specialFormTemplate.getFirstSheet(), placeHolder));
                    break;
                case "checkSheet":
                    sb.append(specialFormTemplate.getCheckSheet());
                    break;
                case "machineMaterialRecordSheet":
                    sb.append(specialFormTemplate.getMachineMaterialRecordSheet());
                    break;
                default:
                    break;
            }
        }

        if (state.equals(0L)) {
            List<FTNode> endStartFTNodes = ftNodeApplication.findEndedFTNodeByFTLotId(ftLotId);
            Collections.sort(endStartFTNodes);
            for (FTNode ftNode : endStartFTNodes) {
                sb.append(getRuncardInfoBySiteName(placeHolder, ftRuncard, ftNode.getName()));
            }
        } else if (state.equals(1L)) {
            List<FTNode> unreachedTestNodes = ftNodeApplication.findUnreachedFTNodeByFTLotId(ftLotId);
            Collections.sort(unreachedTestNodes);
            for (FTNode ftNode : unreachedTestNodes) {
                sb.append(getRuncardInfoBySiteName(placeHolder, ftRuncard, ftNode.getName()));
            }
        } else if (state.equals(2L)) {
            List<FTNode> toStartFTNode = ftNodeApplication.findToStartFTNodeByFTLotId(ftLotId);
            if (!toStartFTNode.isEmpty()) {
                sb.append(getRuncardInfoBySiteName(placeHolder, ftRuncard, toStartFTNode.get(0).getName()));
            } else {
                List<FTNode> startFTNode = ftNodeApplication.findStartedFTNodeByFTLotId(ftLotId);
                sb.append(getRuncardInfoBySiteName(placeHolder, ftRuncard, startFTNode.get(0).getName()));
            }
        }
        return InvokeResult.success(sb.toString());
    }


    /**
     * 根据站点名获得runcardinfo数据
     * @param placeHolder
     * @param ftRuncard
     * @param siteName
     * @return
     */
    private String getRuncardInfoBySiteName(PlaceHolder placeHolder, FTRuncard ftRuncard, String siteName) {
        String data = null;
        switch (siteName) {
            case "IQC":
                replaceIQCPlaceholder(ftRuncard, placeHolder);
                data = ftRuncard.getIQC();
                break;
            case "OQC":
                replaceOQCPlaceholder(ftRuncard, placeHolder);
                data = ftRuncard.getOQC();
                break;
            case "FVI":
                replaceFVIPlaceholder(ftRuncard, placeHolder);
                data = ftRuncard.getFVI();
                break;
            case "FQC":
                replaceFQCPlaceholder(ftRuncard, placeHolder);
                data = ftRuncard.getFQC();
                break;
            case "Baking":
                replaceBakingPlaceholder(ftRuncard, placeHolder);
                data = ftRuncard.getBaking();
                break;
            case "Packing":
                replacePackingPlaceholder(ftRuncard, placeHolder);
                data = ftRuncard.getPacking();
                break;
            case "siteTest":
                data = ftRuncard.getSiteTest();
                break;
            default:
                if (siteName.startsWith("Test-"))
                    data = ftRuncard.getSiteTest();
                break;
        }
        return data;
    }


    /**
     * 根据ftinfoId获得其对应ftlotId
     * @param id
     * @return
     */
    public Long convertFTLotIdtoFTinfoId(Long id) {
        FTLot ftLot = ftLotApplication.get(id);
        return ftLot.getFtInfo().getId();
    }


    /**
     * 获得runcard中各自站点是否填写完成的状态
     * @param ftinfoId
     * @param totalSites
     * @return
     */
    public InvokeResult getRuncardFinishedStatus(Long ftinfoId,String[] totalSites){
        Map<String,Boolean> resultMap = new HashMap<>();
        FTRuncardTemplate FTRuncardTemplate = ftRuncardTemplateApplication.findByInternalProductId(ftinfoId);
        if (FTRuncardTemplate == null) {
            return InvokeResult.success(resultMap);
        }
        String data = null;
        for (String s : totalSites) {
            switch (s) {
                case "IQC":
                    data = FTRuncardTemplate.getIQC();
                    break;
                case "OQC":
                    data = FTRuncardTemplate.getOQC();
                    break;
                case "FVI":
                    data = FTRuncardTemplate.getFVI();
                    break;
                case "FQC":
                    data = FTRuncardTemplate.getFQC();
                    break;
                case "Baking":
                    data = FTRuncardTemplate.getBaking();
                    break;
                case "siteTest":
                    data = FTRuncardTemplate.getSiteTest();
                    break;
                case "Packing":
                    data = FTRuncardTemplate.getPacking();
                    break;
                default:
                    data = null;
            }
            if (data == null){
                resultMap.put(s,false);
            }else {
                resultMap.put(s,true);
            }
        }
        return InvokeResult.success(resultMap);
    }


    @Override
    public InvokeResult saveSpecialForm(Long ftinfoId, String formType, String data) {

        FTInfo ftInfo = ftInfoApplication.get(ftinfoId);
        FTRuncardTemplate ftRuncardTemplate = ftRuncardTemplateApplication.findByInternalProductId(ftinfoId);
        if (ftRuncardTemplate == null) {
            ftRuncardTemplate = createFTRuncardTemplate();
            ftRuncardTemplate.setInternalProduct(ftInfo);
        }
        SpecialFormTemplate specialFormTemplate = ftRuncardTemplate.getSpecialFormTemplate();
        switch (formType) {
            case "reelcodeSheet":
                specialFormTemplate.setReelcodeSheet(data);
                break;
            case "recordSheet":
                specialFormTemplate.setRecordSheet(data);
                break;
            case "summarySheet":
                specialFormTemplate.setSummarySheet(data);
                break;
            case "firstSheet":
                specialFormTemplate.setFirstSheet(data);
                break;
            case "checkSheet":
                specialFormTemplate.setCheckSheet(data);
                break;
            case "machineMaterialRecordSheet":
                specialFormTemplate.setMachineMaterialRecordSheet(data);
                break;
        }
        ftRuncardTemplate.save();
        return InvokeResult.success();
    }



}
