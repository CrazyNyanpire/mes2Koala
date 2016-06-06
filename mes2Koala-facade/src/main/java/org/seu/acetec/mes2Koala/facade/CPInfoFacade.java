package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.CPInfoDTO;
import org.seu.acetec.mes2Koala.facade.dto.LabelDTO;
import org.seu.acetec.mes2Koala.facade.dto.RawDataDTO;
import org.seu.acetec.mes2Koala.facade.dto.SBLTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.CPInfoPageVo;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public interface CPInfoFacade {

    InvokeResult getCPInfo(Long id);

    InvokeResult createCPInfo(CPInfoDTO cpInfoDTO);

    InvokeResult updateCPInfo(CPInfoDTO cpInfoDTO);

    InvokeResult removeCPInfo(Long id);

    InvokeResult removeCPInfos(Long[] ids);

    List<CPInfoDTO> findAllCPInfo();

    Page<CPInfoPageVo> pageQueryCPInfo(CPInfoDTO cpInfoDTO, int currentPage, int pageSize);

    List<SBLTemplateDTO> getSBLTemplatesByProduct(Long id);

    List<LabelDTO> getLabelsByPackageType(String packageType);

    InvokeResult clearProcessTemplate(Long id);

    InvokeResult clearLabels(Long id);

    InvokeResult updateProcessTemplate(Long id, Long processTemplateId);

    InvokeResult updateLabels(Long id, List<Long> labelIds);

	//public void exportExcel(List<Object> cpInfoDTOs, OutputStream OutputStream);

	public void exportExcel(List<Object> cpInfoDTOs, String fileName);
}

