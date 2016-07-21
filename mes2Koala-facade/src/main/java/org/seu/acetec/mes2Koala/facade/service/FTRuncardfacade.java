package org.seu.acetec.mes2Koala.facade.service;


import org.apache.pdfbox.util.operator.Invoke;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.core.domain.Employee;
import org.seu.acetec.mes2Koala.core.domain.AcetecAuthorization;
import org.seu.acetec.mes2Koala.facade.dto.RuncardSignDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.SpecialFormStatusVo;

import java.util.List;


public interface FTRuncardfacade {
    //保存runcard数据
    public void saveRuncardInfo(Long id, String currentSite, String data);
    //获得runcard的数据
    public String getRuncardInfo(Long id);
    //获得IQC数据
    public String getIQC(Long id);
    //获得baking数据
    public String getBaking(Long id);
    //获得test站点的数据
    public String getSiteTest(Long id);
    //获得fvi的数据
    public String getFVI(Long id);
    //获得fqc的数据
    public String getFQC(Long id);
    //获得packing的数据
    public String getPacking(Long id);
    //获得oqc数据
    public String getOQC(Long id);

    //根据站点名获得runcard的数据
    public String getRuncardInfoBySiteName(Long id, String siteName);

    //验证runcard信息是否已经填写完成
    public boolean isRuncardFinished(Long id, String[] runcardinfo);

    //验证runcard信息是否已经填写完成
    public boolean isRuncardFinished2(Long customerLotId, String[] runcardinfo);

    //发送邮件
    public boolean sendEmail(String to, String subject, String content);

    //获取所有的employee
    public List<Employee> getAllEmployees(Long id);

    //获得runcard对应签核人员的所有的邮箱数据
    public List<String> getAllEmployeesEmail(List<Employee> employees);

    //对runcardinfo进行授权
    public InvokeResult signRuncardInfo(Long ftinfoId, Long userId, String opinion, String note);

    //判断是否已经授权
    public boolean isRuncardInfoSigned(Long id);

//    //获取runcard的签核信息
//    public InvokeResult getRuncardSignInfo(Long id);

    //发给多个邮箱
    public void sendEmailToPersons(List<String> emails, String subject, String content);
    //发送邮件
    public void sendEmailToPersons(Long id);

    //根据人员id查找对应的部门信息
    public String getDepartmentByEmployeeId(Long ftinfoId, Long userId);

    //根据人员id查找runcard签核信息
    public RuncardSignDTO getRuncardSignInfo(Long ftinfoId, Long userId);

    //获得特殊表单的数据
    public String getSpecialFormByFtinfoId(Long ftinfoId, String sheetType);

    //保存特殊表单的打印状态
    public InvokeResult saveSpecialFormStatus(Long id, SpecialFormStatusVo specialFormStatusVo);

    //获得特殊表单的打印状态
    public InvokeResult getSpecialFormStatus(Long id);

    //替换FLOW表单中的占位符
    public String getRuncardInfoAfterReplaced(Long ftLotId);

    //根据state获得runcardinfo的数据
    public InvokeResult getRuncardInfoByState(Long ftLotId,String[] specialFormArr,Long state);

    //根据ftinfoId获得ftlotId
    public Long convertFTLotIdtoFTinfoId(Long id);

    //获得runcard中各个站点的保存状态
    public InvokeResult getRuncardFinishedStatus(Long ftinfoId,String[] totalSites);

    //保存特殊表单
    public InvokeResult saveSpecialForm(Long ftinfoId, String formType, String data);
}
