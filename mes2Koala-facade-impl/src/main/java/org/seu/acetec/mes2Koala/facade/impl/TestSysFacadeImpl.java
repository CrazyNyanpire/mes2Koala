package org.seu.acetec.mes2Koala.facade.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.ProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.TestSysApplication;
import org.seu.acetec.mes2Koala.core.domain.TestSys;
import org.seu.acetec.mes2Koala.facade.TestSysFacade;
import org.seu.acetec.mes2Koala.facade.dto.TestSysDTO;
import org.seu.acetec.mes2Koala.facade.ganttvo.RowVo;
import org.seu.acetec.mes2Koala.facade.ganttvo.RowVoAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.TestSysAssembler;
import org.seu.acetec.mes2Koala.infra.EmsFetcher;

@Named
public class TestSysFacadeImpl implements TestSysFacade {

    @Inject
    private TestSysApplication application;

    @Inject
    private ProductionScheduleApplication productionScheduleApplication;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getTestSys(Long id) {
        return InvokeResult.success(TestSysAssembler.toDTO(application.get(id)));
    }

    public InvokeResult creatTestSys(TestSysDTO testSysDTO) {
        application.create(TestSysAssembler.toEntity(testSysDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateTestSys(TestSysDTO testSysDTO) {
        application.update(TestSysAssembler.toEntity(testSysDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeTestSys(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeTestSyss(Long[] ids) {
        Set<TestSys> testSyss = new HashSet<TestSys>();
        for (Long id : ids) {
            testSyss.add(application.get(id));
        }
        application.removeAll(testSyss);
        return InvokeResult.success();
    }

    public List<TestSysDTO> findAllTestSys( String testType ) {
    	/**方案一：每次访问从EMS获取信息，转化为实体后保存，再取出返回前端 **/
    	
    	try {
			//尝试从EMS中获取所有的机台信息
    		String testerEmsJson = null;
    		if ( testType.toLowerCase().equals("ft") ){
    			testerEmsJson = EmsFetcher.fetchAllFTPlatform();
    		}
    		else if ( testType.toLowerCase().equals("cp") ){
    			testerEmsJson = EmsFetcher.fetchAllCPPlatform();
    		}
    		else {
    			throw new IllegalArgumentException("机台类型未指定");
			}
			//将EMSJson转化为实体保存(update)
			application.createOrUpdate( TestSysAssembler.emsTestSysJsonToEntities(testerEmsJson) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("同步EMS信息失败，返回本地信息");
		}
		
    	//将所有信息返回至前端
        return TestSysAssembler.toDTOs(application.findAllTestSysByTestType(testType));
    	/**方案二：每次访问从EMS获取信息，转化为DTO之后直接返回至前端**/
    	//尝试从EMS中获取所有的机台信息
//    	String testerEmsJson = EmsFetcher.fetchAllTester();
//    	return TestSysAssembler.emsTesterJsonToDTOs( testerEmsJson );
    	//将EMSJson转化为DTO返回
    }

    public Page<TestSysDTO> pageQueryTestSys(TestSysDTO queryVo, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _testSys from TestSys _testSys   where 1=1 ");
        if (queryVo.getCreateTimestamp() != null) {
            jpql.append(" and _testSys.createTimestamp between ? and ? ");
            conditionVals.add(queryVo.getCreateTimestamp());
            conditionVals.add(queryVo.getCreateTimestampEnd());
        }
        if (queryVo.getLastModifyTimestamp() != null) {
            jpql.append(" and _testSys.lastModifyTimestamp between ? and ? ");
            conditionVals.add(queryVo.getLastModifyTimestamp());
            conditionVals.add(queryVo.getLastModifyTimestampEnd());
        }
        if (queryVo.getCreateEmployNo() != null && !"".equals(queryVo.getCreateEmployNo())) {
            jpql.append(" and _testSys.createEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateEmployNo()));
        }
        if (queryVo.getLastModifyEmployNo() != null && !"".equals(queryVo.getLastModifyEmployNo())) {
            jpql.append(" and _testSys.lastModifyEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLastModifyEmployNo()));
        }
        if (queryVo.getPlatformNumber() != null && !"".equals(queryVo.getPlatformNumber())) {
            jpql.append(" and _testSys.platformNumber like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPlatformNumber()));
        }
        if (queryVo.getTestType() != null && !"".equals(queryVo.getTestType())) {
            jpql.append(" and _testSys.testType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTestType()));
        }
        Page<TestSys> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        return new Page<TestSysDTO>(pages.getStart(), pages.getResultCount(), pageSize, TestSysAssembler.toDTOs(pages.getData()));
    }

    public List<RowVo> findAllGanttRows() {
        return RowVoAssembler.toVos(application.findAll());
    }

    public InvokeResult updateTestSyss(List<RowVo> rowVos) {
        if (rowVos == null)
            return InvokeResult.failure("参数为空。");
        List<TestSys> testSyss = RowVoAssembler.toEntities(rowVos);
        for (TestSys testSys : testSyss) {
            productionScheduleApplication.updateAll(testSys.getProductions());
        }
        return InvokeResult.success();
    }

}
