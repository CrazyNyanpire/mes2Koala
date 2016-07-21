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


    //根据站点名获得runcardinfo数据
    public String getRuncardInfoBySiteName(Long id, String siteName);

    //判断runcard是否已经填写完成，此时的id为cpinfoId
    public boolean isRuncardFinished(Long id, String[] runcardinfo);

    //判断runcard是否已经填写完成，此时的id是customerCPLotId
    public boolean isRuncardFinished2(Long id, String[] runcardinfo);

    //判断是否已经授权
    public boolean isRuncardInfoSigned(Long id);

    //对runcardinfo进行授权
    public InvokeResult signRuncardInfo(Long cpinfoId, Long userId, String opinion, String note);

    //根据人员id查找对应的部门信息
    public String getDepartmentByEmployeeId(Long cpinfoId, Long userId);

    //根据人员id查找runcard签核信息
    public RuncardSignDTO getRuncardSignInfo(Long cpinfoId, Long userId);

    //获得特殊表单的数据
    public String getSpecialFormByCPinfoId(Long cpinfoId, String sheetType);

    //保存特殊表单的打印状态
    public InvokeResult saveSpecialFormStatus(Long id, CPSpecialFormStatusVo cpSpecialFormStatusVo);

    //获得特殊表单的打印状态
    public InvokeResult getSpecialFormStatus(Long id);

    public InvokeResult getSpecialFormStatusByCPLotId(Long id);

    //保存特殊表单c
    public InvokeResult saveSpecialForm(Long id, String formType, String data);

    //根据cpinfo的id返回runcard信息
    public String getRuncardInfo(Long id);


    //保存runcardinfo的数据
    public void saveRuncardInfo(Long id, String currentSite, String data);

    //保存runcard中的totalSite字段
    public void saveTotalSitesOfRuncard(Long id, String data);


    //发送邮件
    public boolean sendEmail(String to, String subject, String content);

    //发给多个邮箱
    public void sendEmailToPersons(List<String> emails, String subject, String content);

    //发送邮件
    public void sendEmailToPersons(Long id);

    //获得runcard中各自站点是否保存的状态
    public InvokeResult getRuncardFinishedStatus(Long cpinfoId, String[] totalSites);

    //根据cpinfoId获得runcardinfo的数据
    public String getRuncardInfoByCPLotId(Long id);

    public String getRuncardInfoBeforeOrdered(Long id);


    //根据state获得runcardinfo的数据
    public InvokeResult getRuncardInfoByState(Long cpLotId,String[] specialFormArr,Long state);

}
