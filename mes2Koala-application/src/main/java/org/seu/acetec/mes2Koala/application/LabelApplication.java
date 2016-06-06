package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.Label;

import java.util.List;

public interface LabelApplication extends GenericMES2Application<Label> {

    List<Label> findFTLabelsByPackageType(String packageType);

    List<Label> findCPLabelsByPackageType(String packageType);

    List<Label> findLabelsByInternalProductId(Long id);
}

