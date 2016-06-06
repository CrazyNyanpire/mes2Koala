package org.seu.acetec.mes2Koala.facade;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.TestSysDTO;
import org.seu.acetec.mes2Koala.facade.ganttvo.RowVo;

import java.util.List;

public interface TestSysFacade {

    public InvokeResult getTestSys(Long id);

    public InvokeResult creatTestSys(TestSysDTO testSys);

    public InvokeResult updateTestSys(TestSysDTO testSys);

    public InvokeResult removeTestSys(Long id);

    public InvokeResult removeTestSyss(Long[] ids);

    public List<TestSysDTO> findAllTestSys( String testType );

    public Page<TestSysDTO> pageQueryTestSys(TestSysDTO testSys, int currentPage, int pageSize);

    public List<RowVo> findAllGanttRows();

    public InvokeResult updateTestSyss(List<RowVo> rowVos);
}

