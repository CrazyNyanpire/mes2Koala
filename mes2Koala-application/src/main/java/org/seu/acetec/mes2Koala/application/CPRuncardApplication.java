package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.CPRuncard;

/**
 * Created by harlow on 2016/6/23.
 */
public interface CPRuncardApplication extends
		GenericMES2Application<CPRuncard> {
	public CPRuncard findByCPLotId(Long id);

}
