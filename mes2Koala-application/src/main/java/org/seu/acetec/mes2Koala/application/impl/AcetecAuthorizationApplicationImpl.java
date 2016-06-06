package org.seu.acetec.mes2Koala.application.impl;

import org.dayatang.utils.Page;
import org.seu.acetec.mes2Koala.application.AcetecAuthorizationApplication;
import org.seu.acetec.mes2Koala.core.domain.AcetecAuthorization;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

@Named
@Transactional
public class AcetecAuthorizationApplicationImpl extends GenericMES2ApplicationImpl<AcetecAuthorization> implements AcetecAuthorizationApplication {

    @Override
    public AcetecAuthorization findLatest() {
        return findOne("select a from AcetecAuthorization a order by a.id desc limit 1");
    }

    @Override
    public List<AcetecAuthorization> findByTestProgramTemplateId(Long id) {
        return find("select e from TestProgramTemplate o right join o.acetecAuthorizations e where o.id = ?", id);
    }

    @Override
    public Page<AcetecAuthorization> pageQueryByTestProgramTemplateId(Long id, int currentPage, int pageSize) {
        return page("select e from TestProgramTemplate o right join o.acetecAuthorizations e where o.id=?", currentPage, pageSize, id);
    }
}
