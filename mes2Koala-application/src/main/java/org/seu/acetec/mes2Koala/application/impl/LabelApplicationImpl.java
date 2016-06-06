package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.LabelApplication;
import org.seu.acetec.mes2Koala.core.domain.Label;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

@Named
@Transactional
public class LabelApplicationImpl extends GenericMES2ApplicationImpl<Label> implements LabelApplication {

    @Override
    public List<Label> findFTLabelsByPackageType(String packageType) {
        return find("select _label from Label _label  where _label.packageType like ? and _label.testType like ?", packageType, "FT");
    }

    @Override
    public List<Label> findCPLabelsByPackageType(String packageType) {
        return find("select _label from Label _label  where _label.packageType like ? and _label.testType like ?", packageType, "CP");
    }

    @Override
    public List<Label> findLabelsByInternalProductId(Long id) {
        return find("select b from InternalProduct a right join a.labels b where a.id = ?", id);
    }
}
