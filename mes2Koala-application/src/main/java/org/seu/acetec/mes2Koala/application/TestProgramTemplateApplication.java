package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.AcetecAuthorization;
import org.seu.acetec.mes2Koala.core.domain.TestProgramTemplate;

import java.util.List;

public interface TestProgramTemplateApplication extends GenericMES2Application<TestProgramTemplate> {


    /**
     * 找与产品绑定的测试程序
     *
     * @param ftLotId
     * @verison 2016/3/23 YuxiangQue
     */
    List<TestProgramTemplate> findAuthorizedTestProgramTemplateByProductId(Long ftLotId);

	void authorize(Long testProgramId, AcetecAuthorization acetecAuthorization);
}

