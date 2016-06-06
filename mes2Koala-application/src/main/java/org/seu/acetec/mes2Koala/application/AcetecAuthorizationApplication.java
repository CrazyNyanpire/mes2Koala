package org.seu.acetec.mes2Koala.application;


import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.core.domain.AcetecAuthorization;

import java.util.List;

public interface AcetecAuthorizationApplication extends GenericMES2Application<AcetecAuthorization> {

    /**
     * 找到最近添加的一条记录
     *
     * @return
     */
    AcetecAuthorization findLatest();

    List<AcetecAuthorization> findByTestProgramTemplateId(Long id);

    Page<AcetecAuthorization> pageQueryByTestProgramTemplateId(Long id, int currentPage, int pageSize);
}

