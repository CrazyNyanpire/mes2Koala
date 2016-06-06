package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.FT_TestDTO;

import java.util.List;

public interface FTTestFacade {

    public InvokeResult getFT_Test(Long id);

    public InvokeResult creatFT_Test(FT_TestDTO fT_Test);

    public InvokeResult updateFT_Test(FT_TestDTO fT_Test);

    public InvokeResult removeFT_Test(Long id);

    public InvokeResult removeFT_Tests(Long[] ids);

    public List<FT_TestDTO> findAllFT_Test();

    public Page<FT_TestDTO> pageQueryFT_Test(FT_TestDTO fT_Test, int currentPage, int pageSize);

}

