package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.AcetecAuthorizationApplication;
import org.seu.acetec.mes2Koala.application.TestProgramTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.AcetecAuthorization;
import org.seu.acetec.mes2Koala.core.domain.TestProgramTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@Transactional
public class TestProgramTemplateApplicationImpl extends GenericMES2ApplicationImpl<TestProgramTemplate> implements TestProgramTemplateApplication {
	
	@Inject
	private AcetecAuthorizationApplication acetecAuthorizationApplication;

    @Override
    public List<TestProgramTemplate> findAuthorizedTestProgramTemplateByProductId(Long id) {
        List<TestProgramTemplate> list = find("select o from TestProgramTemplate o where o.internalProduct.id= " + id);
        List<TestProgramTemplate> result = new ArrayList<TestProgramTemplate>();
        for (TestProgramTemplate entity : list) {
            if (entity == null) {
                continue;
            }
            List<AcetecAuthorization> acetecAuthorizations = entity.getAcetecAuthorizations();
            if (acetecAuthorizations == null || acetecAuthorizations.size() == 0) {
                continue;
            }
            AcetecAuthorization acetecAuthorization = acetecAuthorizations.get(0);
            if (AcetecAuthorization.acceptedOption(acetecAuthorization)) {
                result.add(entity);
            }
        }
        return result;
    }

    /**
     * 此方法仅适用于有且仅有一个授权人的情况。有多个授权人时该方法会产生错误。
     * 未用到
     */
	@Override
	public void authorize(Long id, AcetecAuthorization acetecAuthorization) {
		TestProgramTemplate template = get(id);

		acetecAuthorization.setVersion(acetecAuthorizationApplication.get(acetecAuthorization.getId()).getVersion());
		acetecAuthorizationApplication.update(acetecAuthorization);

		template.setAllowState(acetecAuthorization.getOpinion());
		template.setLastModifyTimestamp(new Date());
		update(template);
	}
}
