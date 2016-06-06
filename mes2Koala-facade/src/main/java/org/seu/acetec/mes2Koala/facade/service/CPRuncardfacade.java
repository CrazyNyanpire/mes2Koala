package org.seu.acetec.mes2Koala.facade.service;

import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.RuncardSignDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.CPSpecialFormStatusVo;

import java.util.List;

/**
 * Created by LCN on 2016/4/27.
 */
public interface CPRuncardfacade {
    //flow
    public String getIQC(Long id);

    public String getFQC(Long id);

    public String getPacking(Long id);

    public String getOQC(Long id);

    public String getCP1_DT(Long id);

    public String getCP2_DT(Long id);

    public String getCP3_DT(Long id);

    public String getCP4_DT(Long id);


    public String getCP1_Before_Bake(Long id);

    public String getCP1(Long id);

    public String getCP1_After_Bake(Long id);

    public String getCP2_Before_Bake(Long id);

    public String getCP2(Long id);

    public String getCP2_After_Bake(Long id);

    public String getCP3_Before_Bake(Long id);

    public String getCP3(Long id);

    public String getCP3_After_Bake(Long id);


    public String getCP4_Before_Bake(Long id);

    public String getCP4(Long id);

    public String getCP4_After_Bake(Long id);


    public String getRuncardInfoBySiteName(Long id, String siteName);

    public boolean isRuncardFinished(Long id, String[] runcardinfo);

    public boolean isRuncardFinished2(Long id, String[] runcardinfo);

    //判断是否已经授权
    public boolean isRuncardInfoSigned(Long id);

    //对runcardinfo进行授权
    public InvokeResult signRuncardInfo(Long cpinfoId, Long userId, String opinion, String note);

    //根据人员id查找对应的部门信息
    public String getDepartmentByEmployeeId(Long cpinfoId, Long userId);

    //根据人员id查找runcard签核信息
    public RuncardSignDTO getRuncardSignInfo(Long cpinfoId, Long userId);

    public String getSpecialFormByCPinfoId(Long cpinfoId, String sheetType);

    //保存特殊表单的打印状态
    public InvokeResult saveSpecialFormStatus(Long id, CPSpecialFormStatusVo cpSpecialFormStatusVo);

    //获得特殊表单的打印状态
    public InvokeResult getSpecialFormStatus(Long id);

    //保存特殊表单
    public InvokeResult saveSpecialForm(Long id, String formType, String data);

    //根据cpinfo的id返回runcard信息
    public String getRuncardInfo(Long id);

    public void saveRuncardInfo(Long id, String currentSite, String data);


    public void saveTotalSitesOfRuncard(Long id, String data);


    //发送邮件
    public boolean sendEmail(String to, String subject, String content);

    //发给多个邮箱
    public void sendEmailToPersons(List<String> emails, String subject, String content);


    public void sendEmailToPersons(Long id);

    public InvokeResult getRuncardFinishedStatus(Long cpinfoId, String[] totalSites);

    public String getRuncardInfoByCPLotId(Long id);

    public String getRuncardInfoBeforeOrdered(Long id);


    }
