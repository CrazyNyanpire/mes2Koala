package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.PlaceHolder;

public interface PlaceHolderApplication extends GenericMES2Application<PlaceHolder>{

	PlaceHolder findByFTLot(Long ftLotId);
	
}
