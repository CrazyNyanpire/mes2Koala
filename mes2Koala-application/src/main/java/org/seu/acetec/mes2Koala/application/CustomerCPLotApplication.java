package org.seu.acetec.mes2Koala.application;

import org.seu.acetec.mes2Koala.application.bean.SaveBaseBean;
import org.seu.acetec.mes2Koala.core.domain.CPCustomerWafer;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CPRuncard;
import org.seu.acetec.mes2Koala.core.domain.CPRuncardTemplate;
import org.seu.acetec.mes2Koala.core.domain.CustomerCPLot;
import org.seu.acetec.mes2Koala.core.domain.CustomerFTLot;
import org.seu.acetec.mes2Koala.core.enums.TestTypeForWms;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 阙宇翔
 * @version 2016/2/27
 */
public interface CustomerCPLotApplication extends
		GenericMES2Application<CustomerCPLot> {

	CustomerCPLot findByWmsId(String wmsId);

	List<CustomerCPLot> findByParentSeparationId(Long parentSeparationId);

	CustomerCPLot findByParentIntegrationId(String parentIntegrationId);

	void order(Long customerCPLotId, CPLot cpLot, Long cpInfoId,SaveBaseBean sbb);

	List<Long> batchOrder(Long[] customerCPLotIds, Map<String, Integer> messages,Long cpInfoId,SaveBaseBean sbb);

	String peekLotNumber(Long customerCPLotId);

	String peekRCNumber(Long customerCPLotId);

	List<CPCustomerWafer> getCPCustomerWafer(Long customerCPLotId);

	void rollbackWMSTestInfo(String ft, String wmsTestId, Long quantity);

	public void orderWithOutWMS(Long customerCPLotId, CPLot cpLot);

	void deleteOrder(CPLot cpLot);
	
	public CPRuncard createCPRuncard(CPRuncardTemplate cpRuncardTemplate);
}
