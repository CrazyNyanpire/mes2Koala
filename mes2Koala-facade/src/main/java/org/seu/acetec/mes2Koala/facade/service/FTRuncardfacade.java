package org.seu.acetec.mes2Koala.facade.service;


import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.core.domain.Employee;
import org.seu.acetec.mes2Koala.core.domain.AcetecAuthorization;
import org.seu.acetec.mes2Koala.facade.dto.RuncardSignDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.SpecialFormStatusVo;

import java.util.List;

/**
 * Created by LCN on 2016/3/10.
 */
public interface FTRuncardfacade {
    public void saveRuncardInfo(Long id, String currentSite, String data);

    public String getRuncardInfo(Long id);

    public String getIQC(Long id);

    public String getBaking(Long id);

    public String getSiteTest(Long id);

    public String getFVI(Long id);

    public String getFQC(Long id);

    public String getPacking(Long id);

    public String getOQC(Long id);


    public String getRuncardInfoBySiteName(Long id, String siteName);


    //验证runcard信息是否已经填写完成
    public boolean isRuncardFinished(Long id, String[] runcardinfo);

    //验证runcard信息是否已经填写完成
    public boolean isRuncardFinished2(Long customerLotId, String[] runcardinfo);

    //发送邮件
    public boolean sendEmail(String to, String subject, String content);

    //获取所有的employee
    public List<Employee> getAllEmployees(Long id);

    public List<String> getAllEmployeesEmail(List<Employee> employees);

    //对runcardinfo进行授权
    public InvokeResult signRuncardInfo(Long ftinfoId, Long userId, String opinion, String note);

    //判断是否已经授权
    public boolean isRuncardInfoSigned(Long id);

//    //获取runcard的签核信息
//    public InvokeResult getRuncardSignInfo(Long id);

    //发给多个邮箱
    public void sendEmailToPersons(List<String> emails, String subject, String content);

    public void sendEmailToPersons(Long id);

    //根据人员id查找对应的部门信息
    public String getDepartmentByEmployeeId(Long ftinfoId, Long userId);

    //根据人员id查找runcard签核信息
    public RuncardSignDTO getRuncardSignInfo(Long ftinfoId, Long userId);


    public String getSpecialFormByFtinfoId(Long ftinfoId, String sheetType);

    //保存特殊表单的打印状态
    public InvokeResult saveSpecialFormStatus(Long id, SpecialFormStatusVo specialFormStatusVo);

    //获得特殊表单的打印状态
    public InvokeResult getSpecialFormStatus(Long id);


    //保存特殊表单
    public InvokeResult saveSpecialForm(Long id, String formType, String data);

    //替换FLOW表单中的占位符
    public String getRuncardInfoAfterReplaced(Long ftLotId);

    public InvokeResult getRuncardInfoByState(Long ftLotId,String[] specialFormArr,Long state);

    public Long convertFTLotIdtoFTinfoId(Long id);


    public InvokeResult getRuncardFinishedStatus(Long ftinfoId,String[] totalSites);
}
