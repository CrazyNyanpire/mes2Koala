package org.seu.acetec.mes2Koala.facade.impl;

import help.RuncardHelper;
import org.apache.commons.beanutils.BeanUtils;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.jxls.area.Area;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.application.SecurityAccessApplication;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.seu.acetec.mes2Koala.application.AcetecAuthorizationApplication;
import org.seu.acetec.mes2Koala.application.ProcessTemplateApplication;
import org.seu.acetec.mes2Koala.application.TestingTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.AcetecAuthorization;
import org.seu.acetec.mes2Koala.core.domain.ProcessTemplate;
import org.seu.acetec.mes2Koala.core.domain.RuncardNote;
import org.seu.acetec.mes2Koala.core.domain.TestingTemplate;
import org.seu.acetec.mes2Koala.facade.ProcessTemplateFacade;
import org.seu.acetec.mes2Koala.facade.common.SenderMailUtils;
import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.facade.excelvo.RuncardVo;
import org.seu.acetec.mes2Koala.facade.excelvo.RuncardVoAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.ProcessTemplateAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.RuncardNoteAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.SpecialFormAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.*;

@Named
public class ProcessTemplateFacadeImpl implements ProcessTemplateFacade {

    @Inject
    TestingTemplateApplication testingTemplateApplication;

    @Inject
    private AcetecAuthorizationApplication acetecAuthorizationApplication;

    @Inject
    private ProcessTemplateApplication application;

    @Inject
    private SecurityAccessApplication securityAccessApplication;

    @Inject
    private SenderMailUtils senderMailUtils;


    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getProcessTemplate(Long id) {
        return InvokeResult.success(ProcessTemplateAssembler.toDTO(application.get(id)));
    }

    /**
     * 用于新建process。新建process时新建其中包含的RuncardNote
     *
     * @param processTemplate 需要填充RuncardNote的ProcessTemplate实体
     * @return 填充成功返回true
     * @author Howard
     * @version v1.0
     * @lastModifyDate 2016.01.06
     */
    private Boolean createRuncardNoteInProcessTemplate(ProcessTemplate processTemplate) {

        if (processTemplate == null)
            return false;

        //拼装完整的process流程，用以检测是否要建立对应的RuncardNote
        String process = processTemplate.getContent();
/*		if ( processTemplate.getTestingTemplate() != null ){
            String testContent = testingTemplateApplication.getTestingTemplate(processTemplate.getTestingTemplate().getId()).getContent();
			process = process.replace("test", testContent == null ? "" : testContent);
		}*/

        //新建可持久化的对象用以盛放新的note
        Map<String, RuncardNote> note = new HashMap<String, RuncardNote>();

        List<String> flowOfFT = new ArrayList<String>(
                Arrays.asList("IQC", "Baking", "PeelForceTest", "FT", "LAT", "FVI", "Packing", "FQC", "OQC"));
        //寻找每个模板中Flow节点
        for (final String flow : flowOfFT) {
            //如果该节点包括在process中，则需要存储
            if (process.contains(flow)) {
                RuncardNote runcardNote = new RuncardNote();
                runcardNote.setNodeName(flow);
                runcardNote.setNodeNote(new ArrayList<String>() {
                    {
                        add(flow + "注意事项");
                    }
                });
                note.put(flow, runcardNote);
            }
        }
        processTemplate.setNote(note);

        return true;

    }

    public InvokeResult creatProcessTemplate(ProcessTemplateDTO processTemplateDTO) {
        try {
            ProcessTemplate processTemplate = ProcessTemplateAssembler.toEntity(processTemplateDTO);
            if (!createRuncardNoteInProcessTemplate(processTemplate))
                return InvokeResult.failure("创建RuncardNote失败！");
            application.create(processTemplate);

            //add by lcn 发送邮件
			String content = "名称为" + processTemplate.getName() + "的ProcessTemplate已被创建，请签核!";
			for (AcetecAuthorization authorization : processTemplate.getAcetecAuthorizations()) {
				AcetecAuthorization acetecAuthorization = acetecAuthorizationApplication.get(authorization.getId());
				String to = acetecAuthorization.getEmployee().getPerson().getEmail();
				if (to != null && !"".equals(to)) {
					senderMailUtils.sendMail("process签核", content, to);
				}
			}

        } catch (Exception e) {
            //return InvokeResult.failure("创建失败");
            //创建失败默认名称重复
            return InvokeResult.failure("ERR：名称重复");
        }
        return InvokeResult.success();
    }

    public InvokeResult updateProcessTemplate(ProcessTemplateDTO processTemplateDTO) {
        //保持认证意见不变
        Page page = findAcetecAuthorizationsByProcessTemplate(processTemplateDTO.getId(), 0, 20);
        List<AcetecAuthorizationDTO> aDTOlist = page.getData();
        processTemplateDTO.setAcetecAuthorizationDTOs(aDTOlist);
        //保持SpecialForm不变
        SpecialFormDTO sDTO = SpecialFormAssembler.toDTO(application.get(processTemplateDTO.getId()).getSpecialForm());
        processTemplateDTO.setSpecialFormDTO(sDTO);

        //
        /*
        TestingTemplate oldTest = ftProcessApplication.getProcessTemplate(processTemplateDTO.getId()).getTestingTemplate();
		if(oldTest!=null && oldTest.getId()==processTemplateDTO.getTestId()){
			TestingTemplateDTO tDTO = TestingTemplateAssembler.toPageVo(oldTest);
			processTemplateDTO.setTestingTemplateDTO(tDTO);
		}
		*/
        //
        processTemplateDTO.setAllowState(application.get(processTemplateDTO.getId()).getAllowState());
        try {
            application.update(ProcessTemplateAssembler.toEntity(processTemplateDTO));
        } catch (Exception e) {
            return InvokeResult.failure("更新失败");
        }
        return InvokeResult.success();
    }

    @Override
    public void updateAllowState(Long id) {
        ProcessTemplate pt = application.get(id);
        List<AcetecAuthorization> aa = pt.getAcetecAuthorizations();
        String state = "未签核";
        String op1 = aa.get(0).getOpinion();
        String op2 = aa.get(1).getOpinion();
        String op3 = aa.get(2).getOpinion();


        if ((op1 == null || op1.equals("")) && (op2 == null || op2.equals("")) && (op3 == null || op3.equals(""))) {
            state = "未签核";
        } else {
            if ((op1 != null && !op1.equals("")) || (op2 != null && !op2.equals("")) || (op3 != null && !op3.equals(""))) {
                state = "签核中";
            }
            if (op1 != null && op1.equals("同意") && op2 != null && op2.equals("同意") && op3 != null && op3.equals("同意")) {
                state = "已签核";
            }
            if ((op1 != null && op1.equals("不同意")) || (op2 != null && op2.equals("不同意")) || (op3 != null && op3.equals("不同意"))) {
                state = "未通过";

            }
        }
        pt.setAllowState(state);
        application.update(pt);
    }

    public InvokeResult removeProcessTemplate(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeProcessTemplates(Long[] ids) {
        Set<ProcessTemplate> processTemplates = new HashSet<ProcessTemplate>();
        for (Long id : ids) {
            processTemplates.add(application.get(id));
        }
        application.removeAll(processTemplates);
        return InvokeResult.success();
    }

    public List<ProcessTemplateDTO> findAllProcessTemplate() {
        return ProcessTemplateAssembler.toDTOs(application.findAll());
    }

    public Page<ProcessTemplateDTO> pageQueryProcessTemplate(ProcessTemplateDTO queryVo, int currentPage, int pageSize, String sortname, String sortorder) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _processTemplate from ProcessTemplate _processTemplate where 1=1 ");
        if (queryVo.getCreateEmployNo() != null && !"".equals(queryVo.getCreateEmployNo())) {
            jpql.append(" and _processTemplate.createEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateEmployNo()));
        }
        if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
            jpql.append(" and _processTemplate.name like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getName()));
        }
        if (queryVo.getContent() != null && !"".equals(queryVo.getContent())) {
            jpql.append(" and _processTemplate.content like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getContent()));
        }
        if (queryVo.getHandlerType() != null && !"".equals(queryVo.getHandlerType())) {
            jpql.append(" and _processTemplate.handlerType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getHandlerType()));
        }
        if (queryVo.getTestType() != null && !"".equals(queryVo.getTestType())) {
            jpql.append(" and _processTemplate.testType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTestType()));
        }
        if (queryVo.getAllowState() != null && !"".equals(queryVo.getAllowState())) {
            jpql.append(" and _processTemplate.allowState like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getAllowState()));
        }
        if (queryVo.getRuncard() != null && !"".equals(queryVo.getRuncard())) {
            jpql.append(" and _processTemplate.Runcard like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRuncard()));
        }
        if (sortname != null && !"".equals(sortname) && sortorder != null && !"".equals(sortname)) {
            jpql.append(" order by _processTemplate." + sortname + " " + sortorder);
        }
        Page<ProcessTemplate> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        return new Page<ProcessTemplateDTO>(pages.getStart(), pages.getResultCount(), pageSize, ProcessTemplateAssembler.toDTOs(pages.getData()));
    }

    public TestingTemplateDTO findTestingTemplateByProcessTemplate(Long id) {
        String jpql = "select e from ProcessTemplate o right join o.testingTemplate e where o.id=?";
        TestingTemplate result = (TestingTemplate) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{id}).singleResult();
        TestingTemplateDTO dto = new TestingTemplateDTO();
        if (result != null) {
            try {
                BeanUtils.copyProperties(dto, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dto;
    }


    public Page<AcetecAuthorizationDTO> findAcetecAuthorizationsByProcessTemplate(Long id, int currentPage, int pageSize) {
        List<AcetecAuthorizationDTO> result = new ArrayList<AcetecAuthorizationDTO>();
        String jpql = "select e from ProcessTemplate o right join o.acetecAuthorizations e where o.id=?";
        Page<AcetecAuthorization> pages = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{id}).setPage(currentPage, pageSize).pagedList();
        for (AcetecAuthorization entity : pages.getData()) {
            AcetecAuthorizationDTO dto = new AcetecAuthorizationDTO();
            try {
                BeanUtils.copyProperties(dto, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.add(dto);
        }
        return new Page<AcetecAuthorizationDTO>(Page.getStartOfPage(currentPage, pageSize), pages.getResultCount(), pageSize, result);
    }

    public Long findSpecialFormByProcessTemplate(Long id) {
        return application.get(id).getSpecialForm().getId();
    }

    @Override
    public List<ProcessTemplateDTO> findProcessByHandler(String handlerType) {
        List<ProcessTemplateDTO> result = new ArrayList<ProcessTemplateDTO>();
        String jpql = "select o from ProcessTemplate o where o.handlerType=?";
        Page<ProcessTemplate> pages = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{handlerType}).setPage(0, 1000).pagedList();
        for (ProcessTemplate entity : pages.getData()) {
            if (entity.getAllowState().equals("已签核"))
                result.add(ProcessTemplateAssembler.toDTO(entity));
        }
        return result;
    }

    /**
     * @param id             需要生成Runcard的processTemplate对象id
     * @param templateStream 模板文件创建的流
     * @param targetStream   输出文件创建的流
     * @return InvokeResult 失败时返回错误信息；成功时返回空InvokeResult
     * @throws
     * @author Howard
     * @version v1.0
     * @lastModifyDate 2015.12.29
     */
    @Override
    public InvokeResult checkRuncard(Long id, InputStream templateStream, OutputStream targetStream) throws IOException {
        //获取拼装excel所需信息
        ProcessTemplateDTO processTemplateDTO = ProcessTemplateAssembler.toDTO(application.get(id));
        if (processTemplateDTO == null)
            return InvokeResult.failure("查询不到对应的ProcessTemplate");
        RuncardVo runcardVo = RuncardVoAssembler.toVo(processTemplateDTO);
        //获取拼装工具实例
        RuncardHelper runcardHelper = RuncardHelper.getInstance();

        //创建上下文，runcardHelper需要context来填充excel内容
        Context context = new Context();
        //此处"runcard"即为在excel模板中使用的变量名称
        context.putVar("runcard", runcardVo);

        //根据输入流、输出流创建transformer对象
        Transformer transformer = runcardHelper.createTransformer(templateStream, targetStream);
        runcardHelper.getAreaBuilder().setTransformer(transformer);
        //获取模板中建立的area
        List xlsAreaList = runcardHelper.getAreaBuilder().build();
        //创建iterator用于遍历area list
        Iterator iterator = xlsAreaList.iterator();

        while (iterator.hasNext()) {
            Area xlsArea = (Area) iterator.next();
            //获取模板文件中的sheet名称
            String templateSheetName = xlsArea.getStartCellRef().getSheetName();
            //将要生成的sheet名为模板文件中sheet名去掉Template后缀
            String sheetName = templateSheetName.substring(0, templateSheetName.indexOf("Template"));

            //根据传入的runcardVo判断是否应当生成该sheet
            if (runcardVo.getSpecialForms().contains(sheetName)) {
                xlsArea.applyAt(new CellRef(sheetName + "!A1"), context);
                //是否要处理公式？
                if (runcardHelper.isProcessFormulas()) {
                    runcardHelper.setFormulaProcessor(xlsArea);
                    xlsArea.processFormulas();
                }
            }
            //删除模板文件中的sheet
            transformer.deleteSheet(templateSheetName);
        }

        //写入目标文件当中
        transformer.write();

        return InvokeResult.success();
    }

    @Override
    public InvokeResult findRuncardNoteByProcessTemplate(Long id) {
        try {
            Map<String, RuncardNote> note = application.get(id).getNote();
            Map<String, RuncardNoteDTO> noteDTO = new HashMap<String, RuncardNoteDTO>();
            for (Map.Entry<String, RuncardNote> entry : note.entrySet()) {
                noteDTO.put(entry.getKey(), RuncardNoteAssembler.toDTO(entry.getValue()));
            }
            return InvokeResult.success(noteDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return InvokeResult.failure("无法获取对应的Runcard注意事项!");
        }
    }

    @Override
    public InvokeResult findProcessTemplateByInternalProductId(Long id) {
        ProcessTemplate processTemplate = application.findByInternalProductId(id);
        return InvokeResult.success(ProcessTemplateAssembler.toDTO(processTemplate));
    }

    @Override
    public InvokeResult findProcessTemplateContentByProduct(Long id) {
        String jpql = "select e.content from InternalProduct o right join o.processTemplate e where o.id=?";
        String content = (String) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{id}).singleResult();
        return InvokeResult.success(content);
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

    @Override
    public InvokeResult signProcessTemplate(Long processTemplateId, Long userId, String opinion, String note) {
        ProcessTemplate processTemplate = application.get(processTemplateId);
        if (processTemplate == null) {
            return InvokeResult.failure("数据错误");
        }

        List<AcetecAuthorization> acetecAuthorizations = processTemplate.getAcetecAuthorizations();
        Long employeeid = getEmployeeIdByUserId(userId);
        for (AcetecAuthorization acetecAuthorization : acetecAuthorizations) {
            if (acetecAuthorization.getEmployee().getId().equals(employeeid)) {
                AcetecAuthorization acetec = acetecAuthorizationApplication.get(acetecAuthorization.getId());
                acetec.setNote(note);
                acetec.setOpinion(opinion);
                acetecAuthorizationApplication.update(acetec);

                return InvokeResult.success("保存成功");
            }
        }
        return InvokeResult.failure("保存失败");
    }


    @Override
    public InvokeResult getProcessTemplateSignInfo(Long processTemplateId, Long userId) {
        Long employeeid = getEmployeeIdByUserId(userId);
        ProcessTemplateSignDTO processTemplateSignDTO = new ProcessTemplateSignDTO();

        //查不到雇员信息
        if (employeeid == null) {
            processTemplateSignDTO.setValidate(false);
            return InvokeResult.success(processTemplateSignDTO);
        }

        ProcessTemplate processTemplate = application.get(processTemplateId);
        List<AcetecAuthorization> acetecAuthorizations = processTemplate.getAcetecAuthorizations();
        for (AcetecAuthorization acetecAuthorization : acetecAuthorizations) {
            if (acetecAuthorization.getEmployee().getId().equals(employeeid)) {
                processTemplateSignDTO.setOpinion(acetecAuthorization.getOpinion());
                processTemplateSignDTO.setNote(acetecAuthorization.getNote());
                processTemplateSignDTO.setValidate(true);
                return InvokeResult.success(processTemplateSignDTO);
            }
        }

        processTemplateSignDTO.setValidate(false);
        return InvokeResult.success(processTemplateSignDTO);

    }


}
