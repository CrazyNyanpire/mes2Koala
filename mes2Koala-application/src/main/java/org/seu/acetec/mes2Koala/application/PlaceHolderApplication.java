package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.core.domain.PlaceHolder;
import org.seu.acetec.mes2Koala.core.domain.PlaceHolderCP;

public interface PlaceHolderApplication extends GenericMES2Application<PlaceHolder>{

	PlaceHolder findByFTLot(Long ftLotId);
	
	PlaceHolderCP findByCPLot(Long cpLotId);
	
}
