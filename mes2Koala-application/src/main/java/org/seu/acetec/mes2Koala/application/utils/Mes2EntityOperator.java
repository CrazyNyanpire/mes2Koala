package org.seu.acetec.mes2Koala.application.utils;

import org.apache.commons.lang.SerializationUtils;
import org.seu.acetec.mes2Koala.core.domain.MES2AbstractEntity;

public class Mes2EntityOperator {
	
	public static MES2AbstractEntity instancePrototype( MES2AbstractEntity original ) {
		if ( null == original )
			throw new IllegalArgumentException("深克隆源对象为空，克隆失败");
        MES2AbstractEntity copied = (MES2AbstractEntity) SerializationUtils.clone(original);
        resetMES2AbstractEntity(copied);

        return copied;
	}
	
	public static void resetMES2AbstractEntity( MES2AbstractEntity entity )
	{
		if ( null == entity )
			throw new IllegalArgumentException("entity为空");
        entity.setId(null);
        entity.setVersion(0);
        entity.setLogic(null);
        entity.setCreateEmployNo(null);
        entity.setCreateTimestamp(null);
        entity.setLastModifyEmployNo(null);
        entity.setLastModifyTimestamp(null);
	}

}
