package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.organisation.application.BaseApplication;
import org.openkoala.organisation.core.domain.Employee;
import org.openkoala.security.application.SecurityAccessApplication;
import org.openkoala.security.org.core.domain.EmployeeUser;
import org.seu.acetec.mes2Koala.application.AcetecAuthorizationApplication;
import org.seu.acetec.mes2Koala.application.TestProgramTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.AcetecAuthorization;
import org.seu.acetec.mes2Koala.core.domain.TestProgramTemplate;
import org.seu.acetec.mes2Koala.facade.TestProgramTemplateFacade;
import org.seu.acetec.mes2Koala.facade.dto.AcetecAuthorizationDTO;
import org.seu.acetec.mes2Koala.facade.dto.TestProgramTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.TestProgramTemplatePageVo;
import org.seu.acetec.mes2Koala.facade.impl.assembler.AcetecAuthorizationAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.TestProgramTemplateAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class TestProgramTemplateFacadeImpl implements TestProgramTemplateFacade {
	
	@Inject
	private BaseApplication baseApplication;

    @Inject
    private TestProgramTemplateApplication application;

    @Inject
    private AcetecAuthorizationApplication acetecAuthorizationApplication;

    @Inject
    private SecurityAccessApplication securityAccessApplication;

    @Inject
    private TestProgramTemplateApplication testProgramTemplateApplication;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getTestProgramTemplate(Long id) {
        return InvokeResult.success(TestProgramTemplateAssembler.toDTO(application.get(id)));
    }

    public InvokeResult createTestProgramTemplate(TestProgramTemplateDTO testProgramTemplateDTO) {
        application.create(TestProgramTemplateAssembler.toEntity(testProgramTemplateDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateTestProgramTemplate(TestProgramTemplateDTO testProgramTemplateDTO) {
        int version = application.get(testProgramTemplateDTO.getId()).getVersion();
        testProgramTemplateDTO.setVersion(version);
        application.update(TestProgramTemplateAssembler.toEntity(testProgramTemplateDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeTestProgramTemplate(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeTestProgramTemplates(Long[] ids) {
        Set<TestProgramTemplate> testProgramTemplates = new HashSet<TestProgramTemplate>();
        for (Long id : ids) {
            testProgramTemplates.add(application.get(id));
        }
        application.removeAll(testProgramTemplates);
        return InvokeResult.success();
    }

    public List<TestProgramTemplateDTO> findAllTestProgramTemplate() {
        return TestProgramTemplateAssembler.toDTOs(application.findAll());
    }

    public Page<TestProgramTemplatePageVo> pageQueryTestProgramTemplate(TestProgramTemplateDTO queryVo, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _testProgram from TestProgramTemplate _testProgram   where 1=1 ");
        if (queryVo.getName() != null && !"".equals(queryVo.getName())) {
            jpql.append(" and _testProgram.name like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getName()));
        }
        if (queryVo.getTestSys() != null && !"".equals(queryVo.getTestSys())) {
            jpql.append(" and _testProgram.testSys like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTestSys()));
        }
        if (queryVo.getSite() != null && !"".equals(queryVo.getSite())) {
            jpql.append(" and _testProgram.site like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSite()));
        }
        if (queryVo.getIsYellow() != null) {
            jpql.append(" and _testProgram.isYellow is ?");
            conditionVals.add(queryVo.getIsYellow());
        }
        if (queryVo.getUPHReality() != null) {
            jpql.append(" and _testProgram.UPHReality=?");
            conditionVals.add(queryVo.getUPHReality());
        }
        if (queryVo.getUPHTheory() != null) {
            jpql.append(" and _testProgram.UPHTheory=?");
            conditionVals.add(queryVo.getUPHTheory());
        }
        if (queryVo.getRevision() != null && !"".equals(queryVo.getRevision())) {
            jpql.append(" and _testProgram.revision like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRevision()));
        }
        if (queryVo.getNote() != null && !"".equals(queryVo.getNote())) {
            jpql.append(" and _testProgram.note like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNote()));
        }
/*	   	if (queryVo.getInternalProductDTO() != null && queryVo.getInternalProductDTO().getCustomerDirectDTO() != null && queryVo.getInternalProductDTO().getCustomerDirectDTO().getId() != null){
               jpql.append(" and _testProgram.internalProduct.customerDirect.id=?");
	   		conditionVals.add(queryVo.getInternalProductDTO().getCustomerDirectDTO().getId());
	   	}*/
        if (queryVo.getInternalProductDTO() != null) {
            if (queryVo.getInternalProductDTO().getCustomerDirectDTO() != null
                    && queryVo.getInternalProductDTO().getCustomerDirectDTO().getNumber() != null
                    && !"".equals(queryVo.getInternalProductDTO().getCustomerDirectDTO().getNumber())) {
                jpql.append(" and _testProgram.internalProduct.customerDirect.number like ?");
                conditionVals.add(MessageFormat.format("%{0}%", queryVo.getInternalProductDTO().getCustomerDirectDTO().getNumber()));
            }
            if (queryVo.getInternalProductDTO().getCustomerDirectDTO() != null && queryVo.getInternalProductDTO().getCustomerDirectDTO().getId() != null) {
                jpql.append(" and _testProgram.internalProduct.customerDirect.id=?");
                conditionVals.add(queryVo.getInternalProductDTO().getCustomerDirectDTO().getId());
            }
            if (queryVo.getInternalProductDTO().getTestType() != null && !"".equals(queryVo.getInternalProductDTO().getTestType())) {
                jpql.append(" and _testProgram.internalProduct.testType is ?");
                conditionVals.add(queryVo.getInternalProductDTO().getTestType());
            }
            if (queryVo.getInternalProductDTO().getPackageType() != null && !"".equals(queryVo.getInternalProductDTO().getPackageType())) {
                jpql.append(" and _testProgram.internalProduct.packageType is ?");
                conditionVals.add(queryVo.getInternalProductDTO().getPackageType());
            }
            if (queryVo.getInternalProductDTO().getCustomerProductNumber() != null && !"".equals(queryVo.getInternalProductDTO().getCustomerProductNumber())) {
                jpql.append(" and _testProgram.internalProduct.customerProductNumber like ?");
                conditionVals.add(MessageFormat.format("%{0}%",queryVo.getInternalProductDTO().getCustomerProductNumber()));
            }
        }
        Page<TestProgramTemplate> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();
        return new Page<TestProgramTemplatePageVo>(pages.getStart(), pages.getResultCount(), pageSize, TestProgramTemplateAssembler.toPageVos(pages.getData()));
    }


    public Page<AcetecAuthorizationDTO> findAcetecAuthorizationsByTestProgram(Long id, int currentPage, int pageSize) {
        Page<AcetecAuthorization> pages = acetecAuthorizationApplication.pageQueryByTestProgramTemplateId(id, currentPage, pageSize);
        return new Page<>(pages.getStart(), pages.getResultCount(), pageSize, AcetecAuthorizationAssembler.toDTOs(pages.getData()));
    }

    /**
     * 得到某一产品的所有测试程序
     *
     * @param id
     * @return
     * @verison LHB
     * @version 2015/3/6 YuxiangQue
     */
    public List<TestProgramTemplateDTO> findTestProgramTemplatesByProduct(Long id) {
        return TestProgramTemplateAssembler.toDTOs(application.findAuthorizedTestProgramTemplateByProductId(id));
    }


    /**
     * 验证有资格签核
     * @param id
     * @return
     */
    @Override
    public InvokeResult checkTheAuthorizer(Long testProgreamTemplateId,Long userId){
        TestProgramTemplate testProgramTemplate = testProgramTemplateApplication.get(testProgreamTemplateId);
        List<AcetecAuthorization> acetecAuthorizations = testProgramTemplate.getAcetecAuthorizations();
        for (AcetecAuthorization acetecAuthorization : acetecAuthorizations){
            Employee employee = acetecAuthorization.getEmployee();
            if (employee.getId().equals(getEmployeeIdByUserId(userId))){
                return InvokeResult.success("符合签核要求");
            }
        }
        return InvokeResult.failure("不能签核该产品");
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
     * 未用到
     */
	@Override
	public InvokeResult authorize(Long testProgramId, AcetecAuthorizationDTO acetecAuthorizationDTO) {
		AcetecAuthorization acetecAuthorization = AcetecAuthorizationAssembler.toEntity(acetecAuthorizationDTO);
		acetecAuthorization.setEmployee(baseApplication.getEntity(Employee.class,acetecAuthorizationDTO.getEmployeeId()));
		application.authorize( testProgramId, acetecAuthorization );
		return InvokeResult.success();
	}


}
