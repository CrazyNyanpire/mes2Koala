package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.EQCSettingDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTInfoDTO;
import org.seu.acetec.mes2Koala.facade.dto.SBLTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.FTInfoPageVo;

import java.util.List;

public interface FTInfoFacade {

    InvokeResult getFTInfo(Long id);
    
    InvokeResult getFTInfoPageVo( Long id );

    InvokeResult creatFTInfo(FTInfoDTO fTInfo);

    InvokeResult updateFTInfo(FTInfoDTO fTInfo);

    InvokeResult removeFTInfo(Long id);

    InvokeResult removeFTInfos(Long[] ids);

    List<FTInfoDTO> findAllFTInfo();

    Page<FTInfoPageVo> pageQueryFTInfo(FTInfoDTO fTInfo, int currentPage, int pageSize, String sortname, String sortorder);

    InvokeResult bindProcess(Long ftInfoId, Long processId);

    InvokeResult bindLabel(Long ftInfoId, Long labelId);

    InvokeResult bindLabels(Long ftInfoId, Long[] labelIds);

    List<SBLTemplateDTO> getSBLTemplatesByProduct(Long id);

    List<EQCSettingDTO> getEQCSettingsByProduct(Long id);


    InvokeResult clearProcess(Long id);

    InvokeResult clearLabel(Long id);

}

