package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.IncrementNumberApplication;
import org.seu.acetec.mes2Koala.application.SystemDictionaryApplication;
import org.seu.acetec.mes2Koala.core.domain.SystemDictionary;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.List;

@Named
@Transactional
public class SystemDictionaryApplicationImpl extends GenericMES2ApplicationImpl<SystemDictionary> implements SystemDictionaryApplication {
	
	@Inject
	IncrementNumberApplication incrementNumberApplication;

    public List<SystemDictionary> getByType(String type) {
        return SystemDictionary.getByType(type);
    }
    
    public SystemDictionary getByTypeAndPPOAndVersion( String type, String customerPPO, String productVersion ) {
    	return findOne("select o from SystemDictionary o where o.type = ? and o.label = ?", type, customerPPO + productVersion );
    }
    
	@Override
	public String getMassProductionSerialChar(String type, String customerPPO, String productVersion) {
		if ( type == null || customerPPO == null || productVersion == null )
			throw new IllegalArgumentException("获取量产序列字母参数错误");
		
		SystemDictionary result = getByTypeAndPPOAndVersion(type, customerPPO, productVersion);
		if ( result == null ) {
			SystemDictionary systemDictionary = new SystemDictionary();
			systemDictionary.setType(type);
			systemDictionary.setLabel( customerPPO + productVersion );
			systemDictionary.setValue( incrementNumberApplication.nextMassProductionSerialChar(type) );
			create(systemDictionary);
			result = systemDictionary;
		}
		return result.getValue();
	} 

}
