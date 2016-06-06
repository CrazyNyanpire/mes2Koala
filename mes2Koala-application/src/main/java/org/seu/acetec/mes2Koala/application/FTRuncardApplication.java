package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.FTRuncard;

/**
 * Created by harlow on 2016/5/30.
 */
public interface FTRuncardApplication extends GenericMES2Application<FTRuncard> {
 
	public boolean deleteRuncardByLotId(Long lotId);
}
