package org.seu.acetec.mes2Koala.application;


import org.seu.acetec.mes2Koala.core.domain.*;

public interface IncrementNumberApplication extends GenericMES2Application<IncrementNumber> {

    // next
    String peekWorkOrderNumber( Customer customer );

    // next
    String peekReelCode(FTInfo ftInfo);

    // next++;
    String nextLatNumber();

    // next
    String peekRCNumber(CustomerLot customerFTLot);

    // next++;
    String nextRCNumber(CustomerLot customerFTLot);

    // next
    String peekFTLotNumber(CustomerFTLot customerFTLot);


    // next++;
    String nextFTLotNumber(CustomerFTLot customerFTLot);

    // next++;
    String nextWorkOrderNumber( Customer customer );

    // next++;
    String nextReelCode(FTInfo ftInfo);

    // next++;
    String nextQdnNumber();

    // next++;
    String nextReworkNumber(FTLot ftLot);

    String peekCPLotNumber(CustomerCPLot customerCPLot);

    String nextCPLotNumber(CustomerCPLot customerCPLot);

	String nextReworkNumber(CPLot cpLot);
	
	// ++next
	public String nextMassProductionSerialChar(String type); 
}

