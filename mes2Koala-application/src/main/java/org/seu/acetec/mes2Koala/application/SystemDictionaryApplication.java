package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.SystemDictionary;

import java.util.List;

public interface SystemDictionaryApplication extends GenericMES2Application<SystemDictionary> {

    List<SystemDictionary> getByType(String type);

	String getMassProductionSerialChar(String type, String customerPPO, String productVersion); 
}

