package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.AcetecAuthorizationDTO;
import org.seu.acetec.mes2Koala.facade.dto.ProcessTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.ProcessTemplateSignDTO;
import org.seu.acetec.mes2Koala.facade.dto.TestingTemplateDTO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface ProcessTemplateFacade {

    public InvokeResult getProcessTemplate(Long id);

    public InvokeResult creatProcessTemplate(ProcessTemplateDTO processTemplate);

    public InvokeResult updateProcessTemplate(ProcessTemplateDTO processTemplate);

    public InvokeResult removeProcessTemplate(Long id);

    public InvokeResult removeProcessTemplates(Long[] ids);

    public List<ProcessTemplateDTO> findAllProcessTemplate();

    public Page<ProcessTemplateDTO> pageQueryProcessTemplate(ProcessTemplateDTO processTemplate, int currentPage, int pageSize, String sortname, String sortorder);

    public TestingTemplateDTO findTestingTemplateByProcessTemplate(Long id);


    public Page<AcetecAuthorizationDTO> findAcetecAuthorizationsByProcessTemplate(Long id, int currentPage, int pageSize);

    public void updateAllowState(Long id);

    public Long findSpecialFormByProcessTemplate(Long id);

    public List<ProcessTemplateDTO> findProcessByHandler(String handlerType);

    /**
     * @param Long         id 需要生成Runcard的processTemplate对象id
     * @param InputStream  templateStream 模板文件创建的流
     * @param OutputStream targetStream 输出文件创建的流
     * @return InvokeResult 失败时返回错误信息；成功时返回空InvokeResult
     * @throws 抛出IOException异常
     * @author Howard
     * @version v1.0
     * @lastModifyDate 2015.12.29
     */
    public InvokeResult checkRuncard(Long id, InputStream templateStream, OutputStream targetStream) throws IOException;

    public InvokeResult findRuncardNoteByProcessTemplate(Long id);

    /**
     * 根据产品Id找出对应的ProcessTemplate
     *
     * @param id
     * @return ProcessTemplateDTO
     * @version 2016/3/3 YuxiangQue
     */
    InvokeResult findProcessTemplateByInternalProductId(Long id);

    InvokeResult findProcessTemplateContentByProduct(Long id);

    InvokeResult signProcessTemplate(Long processTemplateId, Long userId, String opinion, String note);


    InvokeResult getProcessTemplateSignInfo(Long processTemplateId, Long userId);

}

