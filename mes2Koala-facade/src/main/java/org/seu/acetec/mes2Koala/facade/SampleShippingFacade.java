package org.seu.acetec.mes2Koala.facade;

import java.util.List;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.*;

public interface SampleShippingFacade {

	public InvokeResult getSampleShipping(Long id);

	public InvokeResult creatSampleShipping(SampleShippingDTO sampleShipping);

	public InvokeResult updateSampleShipping(SampleShippingDTO sampleShipping);

	public InvokeResult removeSampleShipping(Long id);

	public InvokeResult removeSampleShippings(Long[] ids);

	public List<SampleShippingDTO> findAllSampleShipping();

	public Page<SampleShippingDTO> pageQuerySampleShipping(
			SampleShippingDTO sampleShipping, int currentPage, int pageSize);

	public FTLotDTO findFtLotBySampleShipping(Long id);

	public ReelDiskDTO findReelDiskBySampleShipping(Long id);
	
	public InvokeResult findQtyByQuality(SampleShippingDTO sampleShipping);

}
