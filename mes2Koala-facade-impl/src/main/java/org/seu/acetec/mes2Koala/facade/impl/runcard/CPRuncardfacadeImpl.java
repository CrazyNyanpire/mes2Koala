package org.seu.acetec.mes2Koala.facade.impl.runcard;

import help.FilenameHelper;

import org.apache.poi.hslf.model.Placeholder;
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
import org.seu.acetec.mes2Koala.facade.dto.vo.CPSpecialFormStatusVo;
import org.seu.acetec.mes2Koala.facade.service.CPRuncardfacade;
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
 * Created by LCN on 2016/4/27.
 */
@Named
@Transactional
public class CPRuncardfacadeImpl implements CPRuncardfacade {

    @Inject
    private CPInfoApplication cpInfoApplication;
    @Inject
    private BaseApplication baseApplication;
    @Inject
    private AcetecAuthorizationApplication acetecAuthorizationApplication;
    @Inject
    private CPRuncardTemplateApplication cpRuncardTemplateApplication;
    @Inject
    private SenderMailUtils senderMailUtils;
    @Inject
    private SecurityAccessApplication securityAccessApplication;
    @Inject
    private CPLotApplication cpLotApplication;
    @Inject
    private CustomerCPLotApplication customerCPLotApplication;
    @Inject
    private CPNodeApplication cpNodeApplication;
    @Inject
    private PlaceHolderApplication placeHolderApplication;


    /**
     * 读取文件中的runcardinfo信息
     *
     * @param name
     * @return
     */
    private String readRuncardinfoFromFiles(String name) {
        InputStream in = null;
        try {
            in = new FileInputStream(this.getClass().getResource("/").getPath() + "cpinfo/" + name + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return FilenameHelper.readFileToString(in);
    }


    /**
     * 辅助类   传入两个string  验证是否这个部门签核通过
     *
     * @param keyMangerOpinion
     * @param assistManagerOpinion
     * @return
     */
    private boolean checkRuncardFinished(String keyMangerOpinion, String assistManagerOpinion) {
        if (keyMangerOpinion == null && assistManagerOpinion == null) {
            return false;
        }
        if (keyMangerOpinion == null) {
            if (assistManagerOpinion.equals("不同意")) {
                return false;
            } else {
                return true;
            }
        }
        if (assistManagerOpinion == null) {
            if (keyMangerOpinion.equals("不同意")) {
                return false;
            } else {
                return true;
            }
        }
        if (keyMangerOpinion.equals("不同意") || assistManagerOpinion.equals("不同意")) {
            return false;
        }
        return true;
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
     * 验证是否已经创建runcard
     * 如果没有则创建runcard信息
     *
     * @param id ftinfo id
     * @return
     */
    private CPRuncardTemplate validateRuncardTemplate(Long id) {
        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(id);
        if (cpRuncardTemplate == null) {
            cpRuncardTemplate = new CPRuncardTemplate();
        }
        return cpRuncardTemplate;
    }


    private CPRuncardTemplate createCPRuncardTemplate() {
        CPRuncardTemplate cpRuncardTemplate = new CPRuncardTemplate();
        //特殊表单也在此时创建
        CPSpecialFormTemplate cpSpecialFormTemplate = new CPSpecialFormTemplate();

        cpSpecialFormTemplate.setCP1Sheet(readRuncardinfoFromFiles("specialform/CP1"));
        cpSpecialFormTemplate.setCP2Sheet(readRuncardinfoFromFiles("specialform/CP2"));
        cpSpecialFormTemplate.setMap_Shift1Sheet(readRuncardinfoFromFiles("specialform/MAP_SHIFT1"));
        cpSpecialFormTemplate.setMCP_Cover1Sheet(readRuncardinfoFromFiles("specialform/MCP_COVER"));
        cpSpecialFormTemplate.setSheet1(readRuncardinfoFromFiles("specialform/SHEET1"));
        cpSpecialFormTemplate.setSheet2(readRuncardinfoFromFiles("specialform/SHEET2"));

        cpRuncardTemplate.setCpSpecialFormTemplate(cpSpecialFormTemplate);
        //6个部门对应的签核信息
        cpRuncardTemplate.setKeyProductionAuthorization(new AcetecAuthorization());
        cpRuncardTemplate.setAssistProductionAuthorization(new AcetecAuthorization());

        cpRuncardTemplate.setKeyQuantityAuthorization(new AcetecAuthorization());
        cpRuncardTemplate.setAssistQuantityAuthorization(new AcetecAuthorization());

        cpRuncardTemplate.setKeyTDEAuthorization(new AcetecAuthorization());
        cpRuncardTemplate.setAssistTDEAuthorization(new AcetecAuthorization());
        return cpRuncardTemplate;
    }


    /**
     * 保存runcard
     *
     * @param id          cpinfoid
     * @param currentSite 对应的站点
     * @param data        对应的数据
     */
    public void saveRuncardInfo(Long id, String currentSite, String data) {
        CPInfo cpInfo = cpInfoApplication.get(id);

        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(id);

        if (cpRuncardTemplate == null) {
            cpRuncardTemplate = createCPRuncardTemplate();
            cpRuncardTemplate.setInternalProduct(cpInfo);
        }
        switch (currentSite) {
            case "CP":
            case "CP1":
                cpRuncardTemplate.setCP1(data);
                break;
            case "INK":
                cpRuncardTemplate.setINK(data);
                break;
            case "CP_After_Bake":
            case "CP_After_Bake1":
            case "CP1_After_Bake":
                cpRuncardTemplate.setCP1_After_Bake(data);
                break;
            case "CP_Bake":
            case "CP_Bake1":
            case "CP1_Bake":
                cpRuncardTemplate.setCP1_Before_Bake(data);
                break;
            case "CP_DT":
            case "CP_DT1":
            case "CP1_DT":
                cpRuncardTemplate.setCP1_DT(data);
                break;

            case "CP2":
                cpRuncardTemplate.setCP2(data);
                break;
            case "CP_After_Bake2":
            case "CP2_After_Bake":
                cpRuncardTemplate.setCP2_After_Bake(data);
                break;
            case "CP_Bake2":
            case "CP2_Bake":
                cpRuncardTemplate.setCP2_Before_Bake(data);
                break;
            case "CP_DT2":
            case "CP2_DT":
                cpRuncardTemplate.setCP2_DT(data);
                break;

            case "CP3":
                cpRuncardTemplate.setCP3(data);
                break;
            case "CP_After_Bake3":
            case "CP3_After_Bake":
                cpRuncardTemplate.setCP3_After_Bake(data);
                break;
            case "CP_Bake3":
            case "CP3_Bake":
                cpRuncardTemplate.setCP3_Before_Bake(data);
                break;
            case "CP_DT3":
            case "CP3_DT":
                cpRuncardTemplate.setCP3_DT(data);
                break;

            case "CP4":
                cpRuncardTemplate.setCP4(data);
                break;
            case "CP_After_Bake4":
            case "CP4_After_Bake":
                cpRuncardTemplate.setCP4_After_Bake(data);
                break;
            case "CP_Bake4":
            case "CP4_Bake":
                cpRuncardTemplate.setCP4_Before_Bake(data);
                break;
            case "CP_DT4":
            case "CP4_DT":
                cpRuncardTemplate.setCP4_DT(data);
                break;
            case "FQC":
                cpRuncardTemplate.setFQC(data);
                break;
            case "IQC":
                cpRuncardTemplate.setIQC(data);
                break;
            case "OQC":
                cpRuncardTemplate.setOQC(data);
                break;
            case "Packing":
                cpRuncardTemplate.setPacking(data);
                break;
            default:
                break;
        }

        cpRuncardTemplate.save();
    }


    /**
     * 保存Runcard的totalSite属性
     *
     * @param id
     * @param data
     */
    @Override
    public void saveTotalSitesOfRuncard(Long id, String data) {
        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(id);
        String totalSites = cpRuncardTemplate.getTotalSite();
        if (totalSites == null) {
            cpRuncardTemplate.setTotalSite(data);
            cpRuncardTemplate.save();
        }
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


    @Override
    public String getIQC(Long id) {
        String IQC = validateRuncardTemplate(id).getIQC();
        if (IQC == null) {
            IQC = readRuncardinfoFromFiles("IQC");
        }
        return IQC;
    }


    @Override
    public String getCP1_DT(Long id) {
        String CP1_DT = validateRuncardTemplate(id).getCP1_DT();
        if (CP1_DT == null) {
            CP1_DT = readRuncardinfoFromFiles("CP1_DT");
        }
        return CP1_DT;
    }


    @Override
    public String getCP2_DT(Long id) {
        String CP2_DT = validateRuncardTemplate(id).getCP2_DT();
        if (CP2_DT == null) {
            CP2_DT = readRuncardinfoFromFiles("CP1_DT");
        }
        return CP2_DT;
    }

    @Override
    public String getCP3_DT(Long id) {
        String CP3_DT = validateRuncardTemplate(id).getCP3_DT();
        if (CP3_DT == null) {
            CP3_DT = readRuncardinfoFromFiles("CP1_DT");
        }
        return CP3_DT;
    }


    @Override
    public String getCP4_DT(Long id) {
        String CP4_DT = validateRuncardTemplate(id).getCP4_DT();
        if (CP4_DT == null) {
            CP4_DT = readRuncardinfoFromFiles("CP1_DT");
        }
        return CP4_DT;
    }

    @Override
    public String getCP1_Before_Bake(Long id) {
        String CP1_Before_Bake = validateRuncardTemplate(id).getCP1_Before_Bake();
        if (CP1_Before_Bake == null) {
            CP1_Before_Bake = readRuncardinfoFromFiles("CP1_BEFORE_BAKE");
        }
        return CP1_Before_Bake;
    }

    @Override
    public String getCP1(Long id) {
        String CP1 = validateRuncardTemplate(id).getCP1();
        if (CP1 == null) {
            CP1 = readRuncardinfoFromFiles("CP1");
        }
        return CP1;
    }
    

    private String getINK(Long id) {
    	String INK = validateRuncardTemplate(id).getINK();
        if (INK == null) {
            INK = readRuncardinfoFromFiles("INK");
        }
        return INK;
	}


    @Override
    public String getCP1_After_Bake(Long id) {
        String CP1_After_Bake = validateRuncardTemplate(id).getCP1_After_Bake();
        if (CP1_After_Bake == null) {
            CP1_After_Bake = readRuncardinfoFromFiles("CP1_AFTER_BAKE");
        }
        return CP1_After_Bake;
    }


    @Override
    public String getCP2_Before_Bake(Long id) {
        String CP2_Before_Bake = validateRuncardTemplate(id).getCP2_Before_Bake();
        if (CP2_Before_Bake == null) {
            CP2_Before_Bake = readRuncardinfoFromFiles("CP1_BEFORE_BAKE");
        }
        return CP2_Before_Bake;
    }

    @Override
    public String getCP2(Long id) {
        String CP2 = validateRuncardTemplate(id).getCP2();
        if (CP2 == null) {
            CP2 = readRuncardinfoFromFiles("CP1");
        }
        return CP2;
    }


    @Override
    public String getCP2_After_Bake(Long id) {
        String CP2_After_Bake = validateRuncardTemplate(id).getCP2_After_Bake();
        if (CP2_After_Bake == null) {
            CP2_After_Bake = readRuncardinfoFromFiles("CP1_AFTER_BAKE");
        }
        return CP2_After_Bake;
    }


    @Override
    public String getCP3_Before_Bake(Long id) {
        String CP3_Before_Bake = validateRuncardTemplate(id).getCP3_Before_Bake();
        if (CP3_Before_Bake == null) {
            CP3_Before_Bake = readRuncardinfoFromFiles("CP1_BEFORE_BAKE");
        }
        return CP3_Before_Bake;
    }

    @Override
    public String getCP3(Long id) {
        String CP3 = validateRuncardTemplate(id).getCP3();
        if (CP3 == null) {
            CP3 = readRuncardinfoFromFiles("CP1");
        }
        return CP3;
    }


    @Override
    public String getCP3_After_Bake(Long id) {
        String CP3_After_Bake = validateRuncardTemplate(id).getCP3_After_Bake();
        if (CP3_After_Bake == null) {
            CP3_After_Bake = readRuncardinfoFromFiles("CP1_AFTER_BAKE");
        }
        return CP3_After_Bake;
    }


    @Override
    public String getCP4_Before_Bake(Long id) {
        String CP4_Before_Bake = validateRuncardTemplate(id).getCP4_Before_Bake();
        if (CP4_Before_Bake == null) {
            CP4_Before_Bake = readRuncardinfoFromFiles("CP1_BEFORE_BAKE");
        }
        return CP4_Before_Bake;
    }

    @Override
    public String getCP4(Long id) {
        String CP4 = validateRuncardTemplate(id).getCP3();
        if (CP4 == null) {
            CP4 = readRuncardinfoFromFiles("CP1");
        }
        return CP4;
    }


    @Override
    public String getCP4_After_Bake(Long id) {
        String CP4_After_Bake = validateRuncardTemplate(id).getCP4_After_Bake();
        if (CP4_After_Bake == null) {
            CP4_After_Bake = readRuncardinfoFromFiles("CP1_AFTER_BAKE");
        }
        return CP4_After_Bake;
    }

    @Override
    public String getFQC(Long id) {
        String FQC = validateRuncardTemplate(id).getFQC();
        if (FQC == null) {
            FQC = readRuncardinfoFromFiles("FQC");
        }
        return FQC;
    }


    @Override
    public String getPacking(Long id) {
        String Paking = validateRuncardTemplate(id).getPacking();
        if (Paking == null) {
            Paking = readRuncardinfoFromFiles("PACKING");
        }
        return Paking;
    }

    @Override
    public String getOQC(Long id) {
        String OQC = validateRuncardTemplate(id).getOQC();
        if (OQC == null) {
            OQC = readRuncardinfoFromFiles("OQC");
        }
        return OQC;
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
            case "CP1":
                data = getCP1(id);
                break;
            case "INK":
                data = getINK(id);
                break;
            case "CP_After_Bake1":
            case "CP1_After_Bake":
                data = getCP1_After_Bake(id);
                break;
            case "CP_Bake1":
            case "CP1_Bake":
                data = getCP1_Before_Bake(id);
                break;
            case "CP1_DT":
                data = getCP1_DT(id);
                break;
            case "CP2":
                data = getCP2(id);
                break;
            case "CP_After_Bake2":
            case "CP2_After_Bake":
                data = getCP2_After_Bake(id);
                break;
            case "CP_Bake2":
            case "CP2_Bake":
                data = getCP2_Before_Bake(id);
                break;
            case "CP2_DT":
                data = getCP2_DT(id);
                break;
            case "CP3":
                data = getCP3(id);
                break;
            case "CP_After_Bake3":
            case "CP3_After_Bake":
                data = getCP3_After_Bake(id);
                break;
            case "CP_Bake3":
            case "CP3_Bake":
                data = getCP3_Before_Bake(id);
                break;
            case "CP3_DT":
                data = getCP3_DT(id);
                break;
            case "CP4":
                data = getCP4(id);
                break;
            case "CP_After_Bake4":
            case "CP4_After_Bake":
                data = getCP4_After_Bake(id);
                break;
            case "CP_Bake4":
            case "CP4_Bake":
                data = getCP4_Before_Bake(id);
                break;
            case "CP4_DT":
                data = getCP4_DT(id);
                break;

            case "FQC":
                data = getFQC(id);
                break;
            case "IQC":
                data = getIQC(id);
                break;
            case "OQC":
                data = getOQC(id);
                break;
            case "Packing":
                data = getPacking(id);
                break;
            case "ALL":
                data = getRuncardInfo(id);
                break;
            case "BeforeOrdered":
                data = getRuncardInfoBeforeOrdered(id);
                break;
            case "AfterOrdered":
                data = getRuncardInfoByCPLotId(id);
                break;
            default:
                data = "";
        }
        return data;
    }




	/**
     * 判断runcard是否已经填写完成
     *
     * @param id
     * @param runcardinfo
     * @return
     */
    @Override
    public boolean isRuncardFinished(Long id, String[] runcardinfo) {
        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(id);
        if (cpRuncardTemplate == null) {
            return false;
        }

        String data = null;

        for (String s : runcardinfo) {
            switch (s) {
                case "CP1":
                    data = cpRuncardTemplate.getCP1();
                    break;
                case "INK":
                    data = cpRuncardTemplate.getINK();
                    break;
                case "CP_After_Bake1":
                case "CP1_After_Bake":
                    data = cpRuncardTemplate.getCP1_After_Bake();
                    break;
                case "CP_Bake1":
                case "CP1_Bake":
                    data = cpRuncardTemplate.getCP1_Before_Bake();
                    break;
                case "CP1_DT":
                    data = cpRuncardTemplate.getCP1_DT();
                    break;

                case "CP2":
                    data = cpRuncardTemplate.getCP2();
                    break;
                case "CP_After_Bake2":
                case "CP2_After_Bake":
                    data = cpRuncardTemplate.getCP2_After_Bake();
                    break;
                case "CP_Bake2":
                case "CP2_Bake":
                    data = cpRuncardTemplate.getCP2_Before_Bake();
                    break;
                case "CP2_DT":
                    data = cpRuncardTemplate.getCP2_DT();
                    break;

                case "CP3":
                    data = cpRuncardTemplate.getCP3();
                    break;
                case "CP_After_Bake3":
                case "CP3_After_Bake":
                    data = cpRuncardTemplate.getCP3_After_Bake();
                    break;
                case "CP_Bake3":
                case "CP3_Bake":
                    data = cpRuncardTemplate.getCP3_Before_Bake();
                    break;
                case "CP3_DT":
                    data = cpRuncardTemplate.getCP3_DT();
                    break;

                case "CP4":
                    data = cpRuncardTemplate.getCP4();
                    break;
                case "CP_After_Bake4":
                case "CP4_After_Bake":
                    data = cpRuncardTemplate.getCP4_After_Bake();
                    break;
                case "CP_Bake4":
                case "CP4_Bake":
                    data = cpRuncardTemplate.getCP4_Before_Bake();
                    break;
                case "CP4_DT":
                    data = cpRuncardTemplate.getCP4_DT();
                    break;
                case "FQC":
                    data = cpRuncardTemplate.getFQC();
                    break;
                case "IQC":
                    data = cpRuncardTemplate.getIQC();
                    break;
                case "OQC":
                    data = cpRuncardTemplate.getOQC();
                    break;
                case "Packing":
                    data = cpRuncardTemplate.getPacking();
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
     * 判断runcard是否已经填写完成
     * 此时传入的id是customerCPLotId
     *
     * @param id
     * @param runcardinfo
     * @return
     */
    @Override
    public boolean isRuncardFinished2(Long id, String[] runcardinfo) {
        CustomerCPLot customerCPLot = customerCPLotApplication.get(id);
        return isRuncardFinished(customerCPLot.getCpInfo().getId(), runcardinfo);
    }

    /**
     * 判断runcard是否已经签核
     *
     * @param id
     * @return
     */
    @Override
    public boolean isRuncardInfoSigned(Long id) {
        boolean isFinished = true;
        CPInfo cpInfo = cpInfoApplication.get(id);
        if (cpInfo == null) {
            return false;
        }
        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(id);
        if (cpRuncardTemplate == null) {
            return false;
        }
        //对应三个部门
        String keyMangerOpinion = "";
        String assistManagerOpinion = "";
        //质量部门
        keyMangerOpinion = cpRuncardTemplate.getKeyQuantityAuthorization().getOpinion();
        assistManagerOpinion = cpRuncardTemplate.getAssistQuantityAuthorization().getOpinion();
        isFinished = checkRuncardFinished(keyMangerOpinion, assistManagerOpinion);
        if (!isFinished)
            return false;

        //生产部门
        keyMangerOpinion = cpRuncardTemplate.getKeyProductionAuthorization().getOpinion();
        assistManagerOpinion = cpRuncardTemplate.getAssistProductionAuthorization().getOpinion();
        isFinished = checkRuncardFinished(keyMangerOpinion, assistManagerOpinion);
        if (!isFinished)
            return false;

        //TDE部门
        keyMangerOpinion = cpRuncardTemplate.getKeyTDEAuthorization().getOpinion();
        assistManagerOpinion = cpRuncardTemplate.getAssistTDEAuthorization().getOpinion();

        isFinished = checkRuncardFinished(keyMangerOpinion, assistManagerOpinion);
        if (!isFinished)
            return false;

        return true;
    }


    /**
     * 对runcard进行签核
     *
     * @param cpinfoId
     * @param userId
     * @param opinion
     * @param note
     * @return
     */
    @Override
    public InvokeResult signRuncardInfo(Long cpinfoId, Long userId, String opinion, String note) {
        CPInfo cpInfo = cpInfoApplication.get(cpinfoId);

        if (cpInfo == null) {
            return InvokeResult.failure("数据错误");
        }

        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(cpinfoId);
        AcetecAuthorization acetecAuthorization = null;

        Long employeeid = getEmployeeIdByUserId(userId);

        if (employeeid == null) {
            InvokeResult.failure("没有此雇员信息");
        }


        //根据id查找对应的部门   在此之前保证id的合法性
        if (cpInfo.getKeyProductionManager().getId().equals(employeeid)) {
            acetecAuthorization = cpRuncardTemplate.getKeyProductionAuthorization();
            acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class, employeeid));
            acetecAuthorization.setOpinion(opinion);
            acetecAuthorization.setNote(note);
            cpRuncardTemplate.setKeyProductionAuthorization(acetecAuthorization);
        } else if (cpInfo.getAssistProductionManager().getId().equals(employeeid)) {
            acetecAuthorization = cpRuncardTemplate.getAssistProductionAuthorization();

            acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class, employeeid));
            acetecAuthorization.setOpinion(opinion);
            acetecAuthorization.setNote(note);

            cpRuncardTemplate.setAssistProductionAuthorization(acetecAuthorization);
        } else if (cpInfo.getKeyQuantityManager().getId().equals(employeeid)) {
            acetecAuthorization = cpRuncardTemplate.getKeyQuantityAuthorization();

            acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class, employeeid));
            acetecAuthorization.setOpinion(opinion);
            acetecAuthorization.setNote(note);

            cpRuncardTemplate.setKeyQuantityAuthorization(acetecAuthorization);
        } else if (cpInfo.getAssistQuantityManager().getId().equals(employeeid)) {
            acetecAuthorization = cpRuncardTemplate.getAssistQuantityAuthorization();

            acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class, employeeid));
            acetecAuthorization.setOpinion(opinion);
            acetecAuthorization.setNote(note);

            cpRuncardTemplate.setAssistQuantityAuthorization(acetecAuthorization);
        } else if (cpInfo.getKeyTDEManager().getId().equals(employeeid)) {
            acetecAuthorization = cpRuncardTemplate.getKeyTDEAuthorization();

            acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class, employeeid));
            acetecAuthorization.setOpinion(opinion);
            acetecAuthorization.setNote(note);

            cpRuncardTemplate.setKeyTDEAuthorization(acetecAuthorization);
        } else if (cpInfo.getAssistTDEManager().getId().equals(employeeid)) {
            acetecAuthorization = cpRuncardTemplate.getAssistTDEAuthorization();
            acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class, employeeid));
            acetecAuthorization.setOpinion(opinion);
            acetecAuthorization.setNote(note);
            cpRuncardTemplate.setAssistTDEAuthorization(acetecAuthorization);
        }
        cpRuncardTemplate.setSignedTime(new Date());
        cpRuncardTemplateApplication.update(cpRuncardTemplate);
        return InvokeResult.success("签核成功");
    }


    /**
     * 获得人员的部门信息
     *
     * @param cpinfoId
     * @param userId
     * @return
     */
    @Override
    public String getDepartmentByEmployeeId(Long cpinfoId, Long userId) {
        Long employeeId = getEmployeeIdByUserId(userId);
        //查不到雇员信息
        if (employeeId == null) {
            return "false";
        }
        CPInfo cpInfo = cpInfoApplication.get(cpinfoId);
        if (cpInfo.getKeyProductionManager().getId().equals(employeeId)) {
            return "产品部门主负责人";
        } else if (cpInfo.getAssistProductionManager().getId().equals(employeeId)) {
            return "产品部门协助负责人";
        } else if (cpInfo.getKeyQuantityManager().getId().equals(employeeId)) {
            return "质量部门主负责人";
        } else if (cpInfo.getAssistQuantityManager().getId().equals(employeeId)) {
            return "质量部门协助负责人";
        } else if (cpInfo.getKeyTDEManager().getId().equals(employeeId)) {
            return "TDE部门主负责人";
        } else if (cpInfo.getAssistTDEManager().getId().equals(employeeId)) {
            return "TDE部门协助负责人";
        } else {
            return "false";
        }
    }


    /**
     * 获得runcard的签核记录
     *
     * @param cpinfoId
     * @param userId
     * @return
     */
    @Override
    public RuncardSignDTO getRuncardSignInfo(Long cpinfoId, Long userId) {

        Long employeeid = getEmployeeIdByUserId(userId);
        RuncardSignDTO runcardSignDTO = new RuncardSignDTO();

        //查不到雇员信息
        if (employeeid == null) {
            runcardSignDTO.setValidate(false);
            return runcardSignDTO;
        }

        AcetecAuthorization acetecAuthorization = new AcetecAuthorization();

        CPInfo cpInfo = cpInfoApplication.get(cpinfoId);

        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(cpinfoId);

        if (cpRuncardTemplate == null) {
            runcardSignDTO.setValidate(false);
            return runcardSignDTO;
        }
        if (cpInfo.getKeyProductionManager().getId().equals(employeeid)) {
            acetecAuthorization = cpRuncardTemplateApplication.findByInternalProductId(cpinfoId).getKeyProductionAuthorization();

            runcardSignDTO.setOpinion(acetecAuthorization.getOpinion());
            runcardSignDTO.setNote(acetecAuthorization.getNote());
            runcardSignDTO.setDepartmentName("产品部门主负责人");
            runcardSignDTO.setValidate(true);
        } else if (cpInfo.getAssistProductionManager().getId().equals(employeeid)) {
            acetecAuthorization = cpRuncardTemplateApplication.findByInternalProductId(cpinfoId).getAssistProductionAuthorization();

            runcardSignDTO.setOpinion(acetecAuthorization.getOpinion());
            runcardSignDTO.setNote(acetecAuthorization.getNote());
            runcardSignDTO.setDepartmentName("产品部门协助负责人");
            runcardSignDTO.setValidate(true);
        } else if (cpInfo.getKeyQuantityManager().getId().equals(employeeid)) {
            acetecAuthorization = cpRuncardTemplateApplication.findByInternalProductId(cpinfoId).getKeyQuantityAuthorization();

            runcardSignDTO.setOpinion(acetecAuthorization.getOpinion());
            runcardSignDTO.setNote(acetecAuthorization.getNote());
            runcardSignDTO.setDepartmentName("质量部门主负责人");
            runcardSignDTO.setValidate(true);
        } else if (cpInfo.getAssistQuantityManager().getId().equals(employeeid)) {
            acetecAuthorization = cpRuncardTemplateApplication.findByInternalProductId(cpinfoId).getAssistQuantityAuthorization();

            runcardSignDTO.setOpinion(acetecAuthorization.getOpinion());
            runcardSignDTO.setNote(acetecAuthorization.getNote());
            runcardSignDTO.setDepartmentName("质量部门协助负责人");
            runcardSignDTO.setValidate(true);
        } else if (cpInfo.getKeyTDEManager().getId().equals(employeeid)) {
            acetecAuthorization = cpRuncardTemplateApplication.findByInternalProductId(cpinfoId).getKeyTDEAuthorization();

            runcardSignDTO.setOpinion(acetecAuthorization.getOpinion());
            runcardSignDTO.setNote(acetecAuthorization.getNote());
            runcardSignDTO.setDepartmentName("TDE部门主负责人");
            runcardSignDTO.setValidate(true);
        } else if (cpInfo.getAssistTDEManager().getId().equals(employeeid)) {
            acetecAuthorization = cpRuncardTemplateApplication.findByInternalProductId(cpinfoId).getAssistTDEAuthorization();

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
     * 获得特殊表单数据
     *
     * @param cpinfoId
     * @param sheetType
     * @return
     */
    @Override
    public String getSpecialFormByCPinfoId(Long cpinfoId, String sheetType) {
        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(cpinfoId);
        String data = "";
        if (cpRuncardTemplate == null) {
            switch (sheetType) {
                case "CP1SHEET":
                    data = readRuncardinfoFromFiles("specialform/CP1");
                    break;
                case "CP2SHEET":
                    data = readRuncardinfoFromFiles("specialform/CP2");
                    break;
                case "MAP_SHIFT1SHEET":
                    data = readRuncardinfoFromFiles("specialform/MAP_SHIFT1");
                    break;
                case "SHEET1":
                    data = readRuncardinfoFromFiles("specialform/SHEET1");
                    break;
                case "SHEET2":
                    data = readRuncardinfoFromFiles("specialform/SHEET2");
                    break;
                case "MCP_COVER1SHEET":
                    data = readRuncardinfoFromFiles("specialform/MCP_COVER");
                    break;
                default:
                    break;
            }
            return data;
        }

        switch (sheetType) {
            case "CP1SHEET":
                data = cpRuncardTemplate.getCpSpecialFormTemplate().getCP1Sheet();
                break;
            case "CP2SHEET":
                data = cpRuncardTemplate.getCpSpecialFormTemplate().getCP2Sheet();
                break;
            case "MAP_SHIFT1SHEET":
                data = cpRuncardTemplate.getCpSpecialFormTemplate().getMap_Shift1Sheet();
                break;
            case "SHEET1":
                data = cpRuncardTemplate.getCpSpecialFormTemplate().getSheet1();
                break;
            case "SHEET2":
                data = cpRuncardTemplate.getCpSpecialFormTemplate().getSheet2();
                break;
            case "MCP_COVER1SHEET":
                data = cpRuncardTemplate.getCpSpecialFormTemplate().getMCP_Cover1Sheet();
                break;
            default:
                break;
        }
        return data;
    }


    /**
     * 保存特殊表单的打印状态
     *
     * @param id
     * @param cpSpecialFormStatusVo
     * @return
     */
    @Override
    public InvokeResult saveSpecialFormStatus(Long id, CPSpecialFormStatusVo cpSpecialFormStatusVo) {
        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(id);
        if (cpRuncardTemplate == null) {
            cpRuncardTemplate = createCPRuncardTemplate();
            CPInfo cpInfo = cpInfoApplication.get(id);
            cpRuncardTemplate.setInternalProduct(cpInfo);
        }
        cpRuncardTemplate.getCpSpecialFormTemplate().setMCP_Cover1SheetStatus(cpSpecialFormStatusVo.getMCP_COVER1SheetStatus());
        cpRuncardTemplate.getCpSpecialFormTemplate().setCP1SheetStatus(cpSpecialFormStatusVo.getCP1SheetStatus());
        cpRuncardTemplate.getCpSpecialFormTemplate().setCP2SheetStatus(cpSpecialFormStatusVo.getCP2SheetStatus());
        cpRuncardTemplate.getCpSpecialFormTemplate().setMap_Shift1SheetStatus(cpSpecialFormStatusVo.getMAP_SHIFT1Status());
        cpRuncardTemplate.getCpSpecialFormTemplate().setSheet1Status(cpSpecialFormStatusVo.getSheet1Status());
        cpRuncardTemplate.getCpSpecialFormTemplate().setSheet2Status(cpSpecialFormStatusVo.getSheet2Status());
        cpRuncardTemplate.save();
        return InvokeResult.success("保存成功");
    }


    /**
     * 保存特殊表单的打印状态
     *
     * @param id
     * @return
     */
    @Override
    public InvokeResult getSpecialFormStatus(Long id) {
        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(id);
        CPSpecialFormStatusVo cpSpecialFormStatusVo = new CPSpecialFormStatusVo();
        if (cpRuncardTemplate == null) {
            return InvokeResult.success(cpSpecialFormStatusVo);
        }

        CPSpecialFormTemplate cpSpecialFormTemplate = cpRuncardTemplate.getCpSpecialFormTemplate();

        cpSpecialFormStatusVo.setCP1SheetStatus(cpSpecialFormTemplate.getCP1SheetStatus());
        cpSpecialFormStatusVo.setCP2SheetStatus(cpSpecialFormTemplate.getCP2SheetStatus());
        cpSpecialFormStatusVo.setMAP_SHIFT1Status(cpSpecialFormTemplate.getMap_Shift1SheetStatus());
        cpSpecialFormStatusVo.setMCP_COVER1SheetStatus(cpSpecialFormTemplate.getMCP_Cover1SheetStatus());
        cpSpecialFormStatusVo.setSheet1Status(cpSpecialFormTemplate.getSheet1Status());
        cpSpecialFormStatusVo.setSheet2Status(cpSpecialFormTemplate.getSheet2Status());
        return InvokeResult.success(cpSpecialFormStatusVo);
    }


    /**
     * 保存特殊表单的打印状态
     *
     * @param id
     * @return
     */
    @Override
    public InvokeResult getSpecialFormStatusByCPLotId(Long id) {
        CPLot cpLot = cpLotApplication.get(id);
        CPSpecialFormStatusVo cpSpecialFormStatusVo = new CPSpecialFormStatusVo();
        if (cpLot == null) {
            return InvokeResult.success(cpSpecialFormStatusVo);
        }
        CPRuncard cpRuncard = cpLot.getCpRuncard();
        CPSpecialFormTemplate cpSpecialFormTemplate = cpRuncard.getCpSpecialFormTemplate();
        cpSpecialFormStatusVo.setCP1SheetStatus(cpSpecialFormTemplate.getCP1SheetStatus());
        cpSpecialFormStatusVo.setCP2SheetStatus(cpSpecialFormTemplate.getCP2SheetStatus());
        cpSpecialFormStatusVo.setMAP_SHIFT1Status(cpSpecialFormTemplate.getMap_Shift1SheetStatus());
        cpSpecialFormStatusVo.setMCP_COVER1SheetStatus(cpSpecialFormTemplate.getMCP_Cover1SheetStatus());
        cpSpecialFormStatusVo.setSheet1Status(cpSpecialFormTemplate.getSheet1Status());
        cpSpecialFormStatusVo.setSheet2Status(cpSpecialFormTemplate.getSheet2Status());
        return InvokeResult.success(cpSpecialFormStatusVo);
    }

    @Override
    public InvokeResult saveSpecialForm(Long id, String formType, String data) {
        CPInfo cpInfo = cpInfoApplication.get(id);

        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(id);
        if (cpRuncardTemplate == null) {
            cpRuncardTemplate = createCPRuncardTemplate();
            cpRuncardTemplate.setInternalProduct(cpInfo);
        }

        CPSpecialFormTemplate cpSpecialFormTemplate = cpRuncardTemplate.getCpSpecialFormTemplate();

        switch (formType) {
            case "CP1SHEET":
                cpSpecialFormTemplate.setCP1Sheet(data);
                break;
            case "CP2SHEET":
                cpSpecialFormTemplate.setCP2Sheet(data);
                break;
            case "MAP_SHIFT1SHEET":
                cpSpecialFormTemplate.setMap_Shift1Sheet(data);
                break;
            case "SHEET1":
                cpSpecialFormTemplate.setSheet1(data);
                break;
            case "SHEET2":
                cpSpecialFormTemplate.setSheet2(data);
                break;
            case "MCP_COVER1SHEET":
                cpSpecialFormTemplate.setMCP_Cover1Sheet(data);
                break;
            default:
                break;
        }

        cpRuncardTemplate.save();

        return InvokeResult.success();
    }


    /**
     * 获得runcardinfo数据
     *
     * @param id
     * @return
     */
    @Override
    public String getRuncardInfo(Long id) {

        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(id);

        if (cpRuncardTemplate == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        //MCP_COVER1
        if (cpRuncardTemplate.getCpSpecialFormTemplate().getMCP_Cover1SheetStatus()) {
            sb.append(cpRuncardTemplate.getCpSpecialFormTemplate().getMCP_Cover1Sheet());
            sb.append("<br/><br/>");
        }

        //FLOW

        String totalSites = cpRuncardTemplate.getTotalSite();

        String[] totalSitesArr = totalSites.split(",");

        for (String site : totalSitesArr) {
            switch (site) {
                case "CP1":
                    sb.append(convertData(cpRuncardTemplate.getCP1()));
                    break;
                case "INK":
                    sb.append(convertData(cpRuncardTemplate.getINK()));
                    break;
                case "CP_After_Bake1":
                case "CP1_After_Bake":
                    sb.append(convertData(cpRuncardTemplate.getCP1_After_Bake()));
                    break;
                case "CP_Bake1":
                case "CP1_Bake":
                    sb.append(convertData(cpRuncardTemplate.getCP1_Before_Bake()));
                    break;
                case "CP1_DT":
                    sb.append(convertData(cpRuncardTemplate.getCP1_DT()));
                    break;
                case "CP2":
                    sb.append(convertData(cpRuncardTemplate.getCP2()));
                    break;
                case "CP_After_Bake2":
                case "CP2_After_Bake":
                    sb.append(convertData(cpRuncardTemplate.getCP2_After_Bake()));
                    break;
                case "CP_Bake2":
                case "CP2_Bake":
                    sb.append(convertData(cpRuncardTemplate.getCP2_Before_Bake()));
                    break;
                case "CP2_DT":
                    sb.append(convertData(cpRuncardTemplate.getCP2_DT()));
                    break;
                case "CP3":
                    sb.append(convertData(cpRuncardTemplate.getCP3()));
                    break;
                case "CP_After_Bake3":
                case "CP3_After_Bake":
                    sb.append(convertData(cpRuncardTemplate.getCP3_After_Bake()));
                    break;
                case "CP_Bake3":
                case "CP3_Bake":
                    sb.append(convertData(cpRuncardTemplate.getCP3_Before_Bake()));
                    break;
                case "CP3_DT":
                    sb.append(convertData(cpRuncardTemplate.getCP3_DT()));
                    break;
                case "CP4":
                    sb.append(convertData(cpRuncardTemplate.getCP4()));
                    break;
                case "CP_After_Bake4":
                case "CP4_After_Bake":
                    sb.append(convertData(cpRuncardTemplate.getCP4_After_Bake()));
                    break;
                case "CP_Bake4":
                case "CP4_Bake":
                    sb.append(convertData(cpRuncardTemplate.getCP4_Before_Bake()));
                    break;
                case "CP4_DT":
                    sb.append(convertData(cpRuncardTemplate.getCP4_DT()));
                    break;
                case "FQC":
                    sb.append(convertData(cpRuncardTemplate.getFQC()));
                    break;
                case "IQC":
                    sb.append(convertData(cpRuncardTemplate.getIQC()));
                    break;
                case "OQC":
                    sb.append(convertData(cpRuncardTemplate.getOQC()));
                    break;
                case "Packing":
                    sb.append(convertData(cpRuncardTemplate.getPacking()));
                    break;
                default:
                    continue;
            }
        }

        //CP1
        if (cpRuncardTemplate.getCpSpecialFormTemplate().getCP1SheetStatus()) {
            sb.append(cpRuncardTemplate.getCpSpecialFormTemplate().getCP1Sheet());
            sb.append("<br/><br/>");
        }
        //CP2
        if (cpRuncardTemplate.getCpSpecialFormTemplate().getCP2SheetStatus()) {
            sb.append(cpRuncardTemplate.getCpSpecialFormTemplate().getCP2Sheet());
            sb.append("<br/><br/>");
        }
        //MAPSHIFT-1
        if (cpRuncardTemplate.getCpSpecialFormTemplate().getMap_Shift1SheetStatus()) {
            sb.append(cpRuncardTemplate.getCpSpecialFormTemplate().getMap_Shift1Sheet());
            sb.append("<br/><br/>");
        }
        //SHEET1
        if (cpRuncardTemplate.getCpSpecialFormTemplate().getSheet1Status()) {
            sb.append(cpRuncardTemplate.getCpSpecialFormTemplate().getSheet1());
            sb.append("<br/><br/>");
        }

        //SHEET2
        if (cpRuncardTemplate.getCpSpecialFormTemplate().getSheet2Status()) {
            sb.append(cpRuncardTemplate.getCpSpecialFormTemplate().getSheet2());
            sb.append("<br/><br/>");
        }

        return sb.toString();
    }

    public String getRuncardInfoBeforeOrdered(Long id) {
        CustomerCPLot customerCPLot = customerCPLotApplication.get(id);
        CPInfo cpInfo = customerCPLot.getCpInfo();
        return getRuncardInfo(cpInfo.getId());

    }


    @Override
    public String getRuncardInfoByCPLotId(Long id) {


        CPLot cpLot = cpLotApplication.get(id);

        CPRuncard cpRuncard = cpLot.getCpRuncard();

        if (cpRuncard == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        //MCP_COVER1
        if (cpRuncard.getCpSpecialFormTemplate().getMCP_Cover1SheetStatus()) {
            sb.append(cpRuncard.getCpSpecialFormTemplate().getMCP_Cover1Sheet());
            sb.append("<br/><br/>");
        }

        //FLOW

        String totalSites = cpRuncard.getTotalSite();

        String[] totalSitesArr = totalSites.split(",");

        for (String site : totalSitesArr) {
            switch (site) {
                case "CP1":
                    sb.append(convertData(cpRuncard.getCP1()));
                    break;
                case "INK":
                    sb.append(convertData(cpRuncard.getINK()));
                    break;
                case "CP_After_Bake1":
                case "CP1_After_Bake":
                    sb.append(convertData(cpRuncard.getCP1_After_Bake()));
                    break;
                case "CP_Bake1":
                case "CP1_Bake":
                    sb.append(convertData(cpRuncard.getCP1_Before_Bake()));
                    break;
                case "CP1_DT":
                    sb.append(convertData(cpRuncard.getCP1_DT()));
                    break;
                case "CP2":
                    sb.append(convertData(cpRuncard.getCP2()));
                    break;
                case "CP_After_Bake2":
                case "CP2_After_Bake":
                    sb.append(convertData(cpRuncard.getCP2_After_Bake()));
                    break;
                case "CP_Bake2":
                case "CP2_Bake":
                    sb.append(convertData(cpRuncard.getCP2_Before_Bake()));
                    break;
                case "CP2_DT":
                    sb.append(convertData(cpRuncard.getCP2_DT()));
                    break;
                case "CP3":
                    sb.append(convertData(cpRuncard.getCP3()));
                    break;
                case "CP_After_Bake3":
                case "CP3_After_Bake":
                    sb.append(convertData(cpRuncard.getCP3_After_Bake()));
                    break;
                case "CP_Bake3":
                case "CP3_Bake":
                    sb.append(convertData(cpRuncard.getCP3_Before_Bake()));
                    break;
                case "CP3_DT":
                    sb.append(convertData(cpRuncard.getCP3_DT()));
                    break;
                case "CP4":
                    sb.append(convertData(cpRuncard.getCP4()));
                    break;
                case "CP_After_Bake4":
                case "CP4_After_Bake":
                    sb.append(convertData(cpRuncard.getCP4_After_Bake()));
                    break;
                case "CP_Bake4":
                case "CP4_Bake":
                    sb.append(convertData(cpRuncard.getCP4_Before_Bake()));
                    break;
                case "CP4_DT":
                    sb.append(convertData(cpRuncard.getCP4_DT()));
                    break;
                case "FQC":
                    sb.append(convertData(cpRuncard.getFQC()));
                    break;
                case "IQC":
                    sb.append(convertData(cpRuncard.getIQC()));
                    break;
                case "OQC":
                    sb.append(convertData(cpRuncard.getOQC()));
                    break;
                case "Packing":
                    sb.append(convertData(cpRuncard.getPacking()));
                    break;
                default:
                    continue;
            }
        }

        //CP1
        if (cpRuncard.getCpSpecialFormTemplate().getCP1SheetStatus()) {
            sb.append(cpRuncard.getCpSpecialFormTemplate().getCP1Sheet());
            sb.append("<br/><br/>");
        }
        //CP2
        if (cpRuncard.getCpSpecialFormTemplate().getCP2SheetStatus()) {
            sb.append(cpRuncard.getCpSpecialFormTemplate().getCP2Sheet());
            sb.append("<br/><br/>");
        }
        //MAPSHIFT-1
        if (cpRuncard.getCpSpecialFormTemplate().getMap_Shift1SheetStatus()) {
            sb.append(cpRuncard.getCpSpecialFormTemplate().getMap_Shift1Sheet());
            sb.append("<br/><br/>");
        }
        //SHEET1
        if (cpRuncard.getCpSpecialFormTemplate().getSheet1Status()) {
            sb.append(cpRuncard.getCpSpecialFormTemplate().getSheet1());
            sb.append("<br/><br/>");
        }

        //SHEET2
        if (cpRuncard.getCpSpecialFormTemplate().getSheet2Status()) {
            sb.append(cpRuncard.getCpSpecialFormTemplate().getSheet2());
            sb.append("<br/><br/>");
        }

        return sb.toString();
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


    @Async
    @Override
    public void sendEmailToPersons(Long id) {
        //拿到所有的产品负责人,并向他们发送邮件   async防止前端页面阻塞
        //如果需要回馈邮箱的成功与否  需要使用消息队列
        String subject = ResourcesUtil.getValue("mail", "subject");
        CPInfo cpInfo = cpInfoApplication.get(id);
        String customerName = cpInfo.getCustomerDirect().getChineseName();
        String customerProductNumber = cpInfo.getCustomerProductNumber();
        String customerProductRevision = cpInfo.getCustomerProductRevision();
        String[] contentData = {customerName, customerProductNumber, customerProductRevision};
        String content = ResourcesUtil.getValue("mail", "content", contentData);

        List<String> emails = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        employees = getAllEmployees(id);
        emails = getAllEmployeesEmail(employees);
        sendEmailToPersons(emails, subject, content);
    }


    private List<Employee> getAllEmployees(Long id) {
        List<Employee> list = new ArrayList<>();
        CPInfo cpInfo = cpInfoApplication.get(id);
        if (cpInfo == null) {
            return new ArrayList<Employee>();
        }
        Employee keyProductionManager = cpInfo.getKeyProductionManager();
        Employee assistProductionManager = cpInfo.getAssistProductionManager();
        Employee keyQuantityManager = cpInfo.getKeyQuantityManager();
        Employee assistQuantityManager = cpInfo.getAssistQuantityManager();
        Employee keyTDEManager = cpInfo.getKeyTDEManager();
        Employee assistTDEManager = cpInfo.getAssistTDEManager();
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
    private List<String> getAllEmployeesEmail(List<Employee> employees) {
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
     * 获得runcard中每个站点的保存状态
     *
     * @param cpinfoId
     * @param totalSites
     * @return
     */
    @Override
    public InvokeResult getRuncardFinishedStatus(Long cpinfoId, String[] totalSites) {
        Map<String, Boolean> resultMap = new HashMap<>();
        CPRuncardTemplate cpRuncardTemplate = cpRuncardTemplateApplication.findByInternalProductId(cpinfoId);
        if (cpRuncardTemplate == null) {
            return InvokeResult.success(resultMap);
        }
        String data = null;
        for (String s : totalSites) {
            switch (s) {
                case "CP1":
                    data = cpRuncardTemplate.getCP1();
                    break;
                case "INK":
                    data = cpRuncardTemplate.getINK();
                    break;
                case "CP_After_Bake1":
                case "CP1_After_Bake":
                    data = cpRuncardTemplate.getCP1_After_Bake();
                    break;
                case "CP_Bake1":
                case "CP1_Bake":
                    data = cpRuncardTemplate.getCP1_Before_Bake();
                    break;
                case "CP1_DT":
                    data = cpRuncardTemplate.getCP1_DT();
                    break;

                case "CP2":
                    data = cpRuncardTemplate.getCP2();
                    break;
                case "CP_After_Bake2":
                case "CP2_After_Bake":
                    data = cpRuncardTemplate.getCP2_After_Bake();
                    break;
                case "CP_Bake2":
                case "CP2_Bake":
                    data = cpRuncardTemplate.getCP2_Before_Bake();
                    break;
                case "CP2_DT":
                    data = cpRuncardTemplate.getCP2_DT();
                    break;

                case "CP3":
                    data = cpRuncardTemplate.getCP3();
                    break;
                case "CP_After_Bake3":
                case "CP3_After_Bake":
                    data = cpRuncardTemplate.getCP3_After_Bake();
                    break;
                case "CP_Bake3":
                case "CP3_Bake":
                    data = cpRuncardTemplate.getCP3_Before_Bake();
                    break;
                case "CP3_DT":
                    data = cpRuncardTemplate.getCP3_DT();
                    break;

                case "CP4":
                    data = cpRuncardTemplate.getCP4();
                    break;
                case "CP_After_Bake4":
                case "CP4_After_Bake":
                    data = cpRuncardTemplate.getCP4_After_Bake();
                    break;
                case "CP_Bake4":
                case "CP4_Bake":
                    data = cpRuncardTemplate.getCP4_Before_Bake();
                    break;
                case "CP4_DT":
                    data = cpRuncardTemplate.getCP4_DT();
                    break;
                case "FQC":
                    data = cpRuncardTemplate.getFQC();
                    break;
                case "IQC":
                    data = cpRuncardTemplate.getIQC();
                    break;
                case "OQC":
                    data = cpRuncardTemplate.getOQC();
                    break;
                case "Packing":
                    data = cpRuncardTemplate.getPacking();
                    break;
                default:
                    data = null;
            }
            if (data == null) {
                resultMap.put(s, false);
            } else {
                resultMap.put(s, true);
            }
        }
        return InvokeResult.success(resultMap);
    }


    @Override
    public InvokeResult getRuncardInfoByState(Long cpLotId, String[] specialFormArr, Long state) {
        CPLot cpLot = cpLotApplication.get(cpLotId);
        CPRuncard cpRuncard = cpLot.getCpRuncard();

        StringBuilder sb = new StringBuilder();

        PlaceHolderCP placeholder = new PlaceHolderCP();
        placeholder = placeHolderApplication.findByCPLot(cpLotId);

        for (String specialForm : specialFormArr) {
            switch (specialForm) {
                case "mcp_COVER1Sheet":
                    sb.append(replaceMcpCoverPlaceholder(cpRuncard, placeholder));
                    break;

                default:
                    break;
            }
        }

        if (state.equals(0L)) {
            List<CPNode> endStartCPNodes = cpNodeApplication.findEndedCPNodeByCPLotId(cpLotId);
            Collections.sort(endStartCPNodes);
            for (CPNode cpNode : endStartCPNodes) {
                sb.append(getRuncardInfoBySiteName(cpRuncard, cpNode.getName(), placeholder));
            }
        } else if (state.equals(1L)) {
            List<CPNode> unreachedTestNodes = cpNodeApplication.findUnreachedCPNodeByCPLotId(cpLotId);
            Collections.sort(unreachedTestNodes);
            for (CPNode cpNode : unreachedTestNodes) {
                sb.append(getRuncardInfoBySiteName(cpRuncard, cpNode.getName(), placeholder));
            }
        } else if (state.equals(2L)) {
            List<CPNode> toStartCPNode = cpNodeApplication.findToStartCPNodeByCPLotId(cpLotId);
            if (!toStartCPNode.isEmpty()) {
                sb.append(getRuncardInfoBySiteName(cpRuncard, toStartCPNode.get(0).getName(), placeholder));
            } else {
                List<CPNode> startCPNode = cpNodeApplication.findStartedCPNodeByCPLotId(cpLotId);
                sb.append(getRuncardInfoBySiteName(cpRuncard, startCPNode.get(0).getName(), placeholder));
            }
        }
        for (String specialForm : specialFormArr) {
            CPSpecialFormTemplate cpSpecialFormTemplate = cpRuncard.getCpSpecialFormTemplate();
            switch (specialForm) {
                case "cp1Sheet":
                    sb.append(cpSpecialFormTemplate.getCP1Sheet());
                    break;
                case "cp2Sheet":
                    sb.append(cpSpecialFormTemplate.getCP2Sheet());
                    break;
                case "sheet1":
                    sb.append(cpSpecialFormTemplate.getSheet1());
                    break;
                case "sheet2":
                    sb.append(cpSpecialFormTemplate.getSheet2());
                    break;
                case "map_SHIFT1":
                    sb.append(cpSpecialFormTemplate.getMap_Shift1Sheet());
                    break;
                default:
                    break;
            }
        }
        return InvokeResult.success(sb.toString());
    }


    private String getRuncardInfoBySiteName(CPRuncard cpRuncard, String siteName, PlaceHolderCP placeholder) {
        String data = null;
        switch (siteName) {
            case "CP1":
//                data = cpRuncard.getCP1();
                data = replaceCPPlaceholder(cpRuncard.getCP1(), placeholder, "CP1");
                break;
            case "INK":
//              data = cpRuncard.getCP1();
              data = replaceCPPlaceholder(cpRuncard.getINK(), placeholder, "INK");
              break;
            case "CP_After_Bake1":
            case "CP1_After_Bake":
                data = cpRuncard.getCP1_After_Bake();
                break;
            case "CP_Bake1":
            case "CP1_Bake":
                data = cpRuncard.getCP1_Before_Bake();
                break;
            case "CP1_DT":
                data = cpRuncard.getCP1_DT();
                break;
            case "CP2":
//                data = cpRuncard.getCP2();
                data = replaceCPPlaceholder(cpRuncard.getCP2(), placeholder, "CP2");
                break;
            case "CP_After_Bake2":
            case "CP2_After_Bake":
                data = cpRuncard.getCP2_After_Bake();
                break;
            case "CP_Bake2":
            case "CP2_Bake":
                data = cpRuncard.getCP2_Before_Bake();
                break;
            case "CP2_DT":
                data = cpRuncard.getCP2_DT();
                break;
            case "CP3":
//                data = cpRuncard.getCP3();
                data = replaceCPPlaceholder(cpRuncard.getCP3(), placeholder, "CP3");
                break;
            case "CP_After_Bake3":
            case "CP3_After_Bake":
                data = cpRuncard.getCP3_After_Bake();
                break;
            case "CP_Bake3":
            case "CP3_Bake":
                data = cpRuncard.getCP3_Before_Bake();
                break;
            case "CP3_DT":
                data = cpRuncard.getCP3_DT();
                break;
            case "CP4":
//                data = cpRuncard.getCP4();
                data = replaceCPPlaceholder(cpRuncard.getCP4(), placeholder, "CP4");
                break;
            case "CP_After_Bake4":
            case "CP4_After_Bake":
                data = cpRuncard.getCP4_After_Bake();
                break;
            case "CP_Bake4":
            case "CP4_Bake":
                data = cpRuncard.getCP4_Before_Bake();
                break;
            case "CP4_DT":
                data = cpRuncard.getCP4_DT();
                break;

            case "FQC":
                data = cpRuncard.getFQC();
                break;
            case "IQC":
                data = replceIQCPlaceholder(cpRuncard.getIQC(), placeholder);
                break;
            case "OQC":
                data = cpRuncard.getOQC();
                break;
            case "Packing":
                data = cpRuncard.getPacking();
                break;
            default:
                data = "";
                break;
        }
        return data;
    }


    private String replaceMcpCoverPlaceholder(CPRuncard cpRuncard, PlaceHolderCP placeholder) {
        String data = cpRuncard.getCpSpecialFormTemplate().getMCP_Cover1Sheet();
        String customerCode = ResourcesUtil.getValue("cpplaceholder", "customerCode");
        String productName = ResourcesUtil.getValue("cpplaceholder", "productName");
        String customerLot = ResourcesUtil.getValue("cpplaceholder", "customerLot");
        String acetecLot = ResourcesUtil.getValue("cpplaceholder", "acetecLot");
        String customerPPO = ResourcesUtil.getValue("cpplaceholder", "customerPPO");
        String receiveQty = ResourcesUtil.getValue("cpplaceholder", "receiveQty");
        String receiveData = ResourcesUtil.getValue("cpplaceholder", "receiveData");
        String packageSize = ResourcesUtil.getValue("cpplaceholder", "packageSize");
        String packingType = ResourcesUtil.getValue("cpplaceholder", "packingType");
        String shippingType = ResourcesUtil.getValue("cpplaceholder", "shippingType");
        String keyQuantityAuthorization = ResourcesUtil.getValue("cpplaceholder", "keyQuantityAuthorization");
        String keyProductionAuthorization = ResourcesUtil.getValue("cpplaceholder", "keyProductionAuthorization");
        String keyTDEAuthorization = ResourcesUtil.getValue("cpplaceholder", "keyTDEAuthorization");
        String signedTime = ResourcesUtil.getValue("cpplaceholder", "signedTime");
        String printDate = ResourcesUtil.getValue("cpplaceholder", "printDate");
        String waferSize = ResourcesUtil.getValue("cpplaceholder", "waferSize");
        String maskName = ResourcesUtil.getValue("cpplaceholder", "maskName");
        String grossDie = ResourcesUtil.getValue("cpplaceholder", "grossDie");
        String internalProductNumber = ResourcesUtil.getValue("cpplaceholder", "internalProductNumber");

        String customerCodeReplace = placeholder.getCustomerCode();
        String productNameReplace = placeholder.getProductName();
        String customerLotReplace = placeholder.getCustomerLot();
        String acetecLotReplace = placeholder.getAcetecLot();
        String customerPPOReplace = placeholder.getCustomerPPO();
        String receiveQtyReplace = placeholder.getReceiveQty();
        String receiveDataReplace = placeholder.getReceiveData();
        String packageSizeReplace = placeholder.getPackageSize();
        String packingTypeReplace = placeholder.getPackingType();
        String shippingTypeReplace = placeholder.getShippingType();
        String keyQuantityAuthorizationReplace = placeholder.getKeyQuantityAuthorization();
        String keyProductionAuthorizationReplace = placeholder.getKeyProductionAuthorization();
        String keyTDEAuthorizationreplace = placeholder.getKeyTDEAuthorization();
        Date signedTimeReplace = cpRuncard.getSignedTime();
        String printDateReplace = placeholder.getPrintDate();
        String waferSizeReplace = placeholder.getWaferSize();
        String maskNameReplace = placeholder.getMaskName();
        String grossDieReplace = placeholder.getGrossDie();
        String internalProductNumberReplace = placeholder.getInternalProductNumber();


        if (customerCodeReplace != null) {
            data = data.replace(customerCode, customerCodeReplace);
        }
        if (productNameReplace != null) {
            data = data.replace(productName, productNameReplace);
        }
        if (customerLotReplace != null) {
            data = data.replace(customerLot, customerLotReplace);
        }
        if (acetecLotReplace != null) {
            data = data.replace(acetecLot, acetecLotReplace);
        }
        if (customerPPOReplace != null) {
            data = data.replace(customerPPO, customerPPOReplace);
        }
        if (receiveQtyReplace != null) {
            data = data.replace(receiveQty, receiveQtyReplace);
        }
        if (receiveDataReplace != null) {
            data = data.replace(receiveData, receiveDataReplace);
        }
        if (packingTypeReplace != null) {
            data = data.replace(packingType, packingTypeReplace);
        }
        if (shippingTypeReplace != null) {
            data = data.replace(shippingType, shippingTypeReplace);
        }
        if (keyQuantityAuthorizationReplace != null) {
            data = data.replace(keyQuantityAuthorization, keyQuantityAuthorizationReplace);
        }
        if (keyProductionAuthorizationReplace != null) {
            data = data.replace(keyProductionAuthorization, keyProductionAuthorizationReplace);
        }
        if (keyTDEAuthorizationreplace != null) {
            data = data.replace(keyTDEAuthorization, keyTDEAuthorizationreplace);
        }

        if (signedTimeReplace != null) {
            data = data.replace(signedTime, signedTimeReplace.toString());
        }

        if (printDateReplace != null) {
            data = data.replace(printDate, printDateReplace);
        }
        if (waferSizeReplace != null) {
            data = data.replace(waferSize, waferSizeReplace);
        }
        if (maskNameReplace != null) {
            data = data.replace(maskName, maskNameReplace);
        }
        if (grossDieReplace != null) {
            data = data.replace(grossDie, grossDieReplace);
        }
        if (internalProductNumberReplace != null) {
            data = data.replace(internalProductNumber, internalProductNumberReplace);
        }


        String processContent = placeholder.getProcessContent();
        String[] contents = processContent.split("\\|");
        String siteNamePlaceholder = ResourcesUtil.getValue("cpplaceholder", "siteName");

        for (int i = 1; i <= contents.length; i++) {
            String tempPlaceholder = siteNamePlaceholder;
            String value = contents[i - 1];
            tempPlaceholder = String.format("%s%02d", tempPlaceholder, i);
            data = data.replace(tempPlaceholder, value);
        }

        for (int i = contents.length + 1; i <= 19; i++) {
            String tempPlaceholder = siteNamePlaceholder;
            tempPlaceholder = String.format("%s%02d", tempPlaceholder, i);
            data = data.replace(tempPlaceholder, "");
        }


        return data;
    }


    private String replceIQCPlaceholder(String data, PlaceHolderCP placeHolder) {

        String waferPlaceholder = ResourcesUtil.getValue("cpplaceholder", "waferID");
        List<CPWafer> cpWafers = placeHolder.getWafers();
        for (CPWafer cpWafer : cpWafers) {
            int length = cpWafer.getInternalWaferCode().length();
            String index = cpWafer.getInternalWaferCode().substring(length - 2);
            String replaceData = cpWafer.getInternalWaferCode();
            data = data.replace(waferPlaceholder + index, replaceData);
        }
        for (int i = 1; i <= 9; i++) {
            data = data.replace(waferPlaceholder + '0' + i, "");
        }
        for (int i = 10; i <= 25; i++) {
            data = data.replace(waferPlaceholder + i, "");
        }
        return data;
    }


    private String replaceCPPlaceholder(String data, PlaceHolderCP placeholder, String siteName) {

        Map<String, String> testProgram = placeholder.getTestPrograms();

        String testProgramPlaceholder = ResourcesUtil.getValue("cpplaceholder", "testProgram");
        if (testProgram != null && testProgram.containsKey(siteName)) {
            String tempPlaceholder = testProgramPlaceholder;
            String value = testProgram.get(siteName);
            tempPlaceholder = tempPlaceholder + siteName;
            data = data.replace(tempPlaceholder, value);
        }
        return data;
    }

}
