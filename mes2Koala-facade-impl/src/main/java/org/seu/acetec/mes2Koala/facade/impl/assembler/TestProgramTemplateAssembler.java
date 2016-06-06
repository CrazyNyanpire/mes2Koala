package org.seu.acetec.mes2Koala.facade.impl.assembler;

import com.google.common.base.Joiner;
import org.seu.acetec.mes2Koala.core.domain.AcetecAuthorization;
import org.seu.acetec.mes2Koala.core.domain.TestProgramTemplate;
import org.seu.acetec.mes2Koala.facade.dto.TestProgramTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.TestProgramTemplatePageVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestProgramTemplateAssembler {

    public static List<TestProgramTemplatePageVo> toPageVos(Collection<TestProgramTemplate> testProgramTemplates) {
        List<TestProgramTemplatePageVo> vos = new ArrayList<>();
        for (TestProgramTemplate testProgramTemplate : testProgramTemplates) {
            vos.add(toPageVo(testProgramTemplate));
        }
        return vos;
    }

    private static TestProgramTemplatePageVo toPageVo(TestProgramTemplate testProgramTemplate) {
        if (testProgramTemplate == null) {
            return null;
        }
        TestProgramTemplatePageVo result = new TestProgramTemplatePageVo();
        result.setId(testProgramTemplate.getId());
        result.setVersion(testProgramTemplate.getVersion());
        result.setName(testProgramTemplate.getName());
        result.setProductVersion(testProgramTemplate.getProductVersion());
        result.setTestSys(testProgramTemplate.getTestSys());
        result.setSite(testProgramTemplate.getSite());
        result.setYellow(testProgramTemplate.isYellow());
        result.setUPHReality(testProgramTemplate.getUPHReality());
        result.setUPHTheory(testProgramTemplate.getUPHTheory());
        result.setRevision(testProgramTemplate.getRevision());
        result.setNote(testProgramTemplate.getNote());
        result.setCustomerNumber(testProgramTemplate.getInternalProduct().getCustomerDirect().getNumber());
        result.setCustomerProductNumber(testProgramTemplate.getInternalProduct().getCustomerProductNumber());
        List<String> authorizationEmployeeNames = new ArrayList<>();
        String authorizationDateTime = "";
        for (AcetecAuthorization acetecAuthorization : testProgramTemplate.getAcetecAuthorizations()) {
            authorizationEmployeeNames.add(acetecAuthorization.getEmployee().getName());
            authorizationDateTime = acetecAuthorization.getLastModifyTimestamp().toString();
            if (acetecAuthorization.getOpinion() != null && acetecAuthorization.getOpinion().equals("同意"))
                result.setAllowState("已授权");
            else if (acetecAuthorization.getOpinion() != null && acetecAuthorization.getOpinion().equals("不同意"))
                result.setAllowState("未通过");
            else if (acetecAuthorization.getOpinion() == null || acetecAuthorization.getOpinion().equals(""))
                result.setAllowState("未授权");
        }
        result.setAuthorizationEmployeeNames(Joiner.on(",").join(authorizationEmployeeNames));
        result.setAuthorizationDatetime(authorizationDateTime);
        return result;
    }

    public static TestProgramTemplateDTO toDTO(TestProgramTemplate testProgramTemplate) {
        if (testProgramTemplate == null) {
            return null;
        }
        TestProgramTemplateDTO result = new TestProgramTemplateDTO();
        result.setId(testProgramTemplate.getId());
        result.setVersion(testProgramTemplate.getVersion());
        result.setName(testProgramTemplate.getName());
        result.setProductVersion(testProgramTemplate.getProductVersion());
        result.setTestSys(testProgramTemplate.getTestSys());
        result.setSite(testProgramTemplate.getSite());
        result.setIsYellow(testProgramTemplate.isYellow());
        result.setUPHReality(testProgramTemplate.getUPHReality());
        result.setUPHTheory(testProgramTemplate.getUPHTheory());
        result.setRevision(testProgramTemplate.getRevision());
        result.setNote(testProgramTemplate.getNote());
        result.setTestType(testProgramTemplate.getTestType());
        result.setAcetecAuthorizationDTOs(AcetecAuthorizationAssembler.toDTOs
                (testProgramTemplate.getAcetecAuthorizations()));
        result.setInternalProductDTO(InternalProductAssembler.toDTO(testProgramTemplate.getInternalProduct()));
        result.setCustomerDirectNumber(testProgramTemplate.getInternalProduct().getCustomerDirect().getNumber());
        return result;
    }

    public static List<TestProgramTemplateDTO> toDTOs(Collection<TestProgramTemplate> testProgramTemplates) {
        if (testProgramTemplates == null) {
            return null;
        }
        List<TestProgramTemplateDTO> results = new ArrayList<TestProgramTemplateDTO>();
        for (TestProgramTemplate each : testProgramTemplates) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static TestProgramTemplate toEntity(TestProgramTemplateDTO testProgramTemplateDTO) {
        if (testProgramTemplateDTO == null) {
            return null;
        }
        TestProgramTemplate result = new TestProgramTemplate();
        result.setId(testProgramTemplateDTO.getId());
        result.setVersion(testProgramTemplateDTO.getVersion());
        result.setName(testProgramTemplateDTO.getName());
        result.setProductVersion(testProgramTemplateDTO.getProductVersion());
        result.setTestSys(testProgramTemplateDTO.getTestSys());
        result.setSite(testProgramTemplateDTO.getSite());
        result.setYellow(testProgramTemplateDTO.getIsYellow());
        result.setUPHReality(testProgramTemplateDTO.getUPHReality());
        result.setUPHTheory(testProgramTemplateDTO.getUPHTheory());
        result.setRevision(testProgramTemplateDTO.getRevision());
        result.setNote(testProgramTemplateDTO.getNote());

        //LHB
        List<AcetecAuthorization> acetecAuthorizations = new ArrayList<AcetecAuthorization>();
        List<Long> authorizationIds = testProgramTemplateDTO.getAcetecAuthorizationIds();
        for (Long each : authorizationIds) {
            AcetecAuthorization authorization = new AcetecAuthorization();
            authorization.setVersion(0);
            authorization.setId(each);
            acetecAuthorizations.add(authorization);
        }
        result.setAcetecAuthorizations(acetecAuthorizations);
        //result.setAcetecAuthorizations (AcetecAuthorizationAssembler.toEntities
        //		 (testProgramTemplateDTO.getAcetecAuthorizationDTOs()));

        result.setInternalProduct(InternalProductAssembler.toEntity(testProgramTemplateDTO.getInternalProductDTO()));
        return result;
    }

    public static List<TestProgramTemplate> toEntities(Collection<TestProgramTemplateDTO> testProgramTemplateDTOs) {
        if (testProgramTemplateDTOs == null) {
            return null;
        }

        List<TestProgramTemplate> results = new ArrayList<TestProgramTemplate>();
        for (TestProgramTemplateDTO each : testProgramTemplateDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
