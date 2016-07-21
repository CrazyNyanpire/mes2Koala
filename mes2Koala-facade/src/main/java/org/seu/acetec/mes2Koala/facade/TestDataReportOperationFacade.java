package org.seu.acetec.mes2Koala.facade;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.TSKInfoDTO;
import org.seu.acetec.mes2Koala.facade.dto.TestData3360InfoDTO;

/**
 * @author HongYu
 * @version 2016/6/6
 */
public interface TestDataReportOperationFacade {

	InvokeResult getTskFileNames(String upDown, String directory);

	InvokeResult resolveFile(String upDown, String directoryName);

	InvokeResult mapCreate(String upDown, String directoryName, String fileNameNum, String mapPath);

	InvokeResult resolve3360File(String customer, String testType, String device, String lotID);

	InvokeResult getHYNFileNames(String upDown, String directory);
	
	String exportHYNCP1Excel(String upDown, String directoryName,
			List<TSKInfoDTO> tskInfoDTOs);

	String exportInkList(String upDown, String directoryName,
			List<TSKInfoDTO> tskInfoDTOs);

	InvokeResult get3360LotInfo(String customer, String testType, String device);

	void tskExportExcel(List<TSKInfoDTO> list, String fileName);

	void exportExcel3360(List<TestData3360InfoDTO> list, String fileName);

	String exportHYNCP2Excel(String upDown, String directoryName,
			List<TSKInfoDTO> list);

	String exportHYNCP4Excel(String upDown, String directoryName,
			List<TSKInfoDTO> list);
}
