package org.seu.acetec.mes2Koala.facade.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.ProductionScheduleApplication;
import org.seu.acetec.mes2Koala.application.TestSysApplication;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.InternalLot;
import org.seu.acetec.mes2Koala.core.domain.ProductionSchedule;
import org.seu.acetec.mes2Koala.core.domain.TestSys;
import org.seu.acetec.mes2Koala.facade.ProductionScheduleFacade;
import org.seu.acetec.mes2Koala.facade.dto.ProductionScheduleDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.ProductionScheduleAssembler;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.core.joran.conditional.ThenOrElseActionBase;

@Named
public class ProductionScheduleFacadeImpl implements ProductionScheduleFacade {

    private final Long waitToBeScheduled = (long) 1;
    
    @Inject
    private CPLotApplication cpLotApplication;
    
    @Inject
    private FTLotApplication ftLotApplication;

    @Inject
    private TestSysApplication testSysApplication;

    @Inject
    private ProductionScheduleApplication application;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getProductionSchedule(Long id) {
        return InvokeResult.success(ProductionScheduleAssembler.toDTO(application.get(id)));
    }

    public InvokeResult creatProductionSchedule(ProductionScheduleDTO productionScheduleDTO) {
        application.create(ProductionScheduleAssembler.toEntity(productionScheduleDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateProductionSchedule(ProductionScheduleDTO productionScheduleDTO) {
        application.update(ProductionScheduleAssembler.toEntity(productionScheduleDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeProductionSchedule(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeProductionSchedules(Long[] ids) {
        Set<ProductionSchedule> productionSchedules = new HashSet<ProductionSchedule>();
        for (Long id : ids) {
            productionSchedules.add(application.get(id));
        }
        application.removeAll(productionSchedules);
        return InvokeResult.success();
    }

    public List<ProductionScheduleDTO> findAllProductionSchedule() {
        return ProductionScheduleAssembler.toDTOs(application.findAll());
    }
    
    @Transactional
    public Page<ProductionScheduleDTO> pageQueryProductionsWaitToBeScheduled( ProductionScheduleDTO queryVo, int currentPage, int pageSize ){
    	List<Object> conditionVals = new ArrayList<>();
    	StringBuilder jpql;
    	String testType = queryVo.getTestType();
    	if ( testType != null && testType.toLowerCase().equals("ft"))
    		jpql = new StringBuilder("select _p from FTProductionSchedule _p where _p.testSys is null and _p.logic is null");
    	else if ( testType != null && testType.toLowerCase().equals("cp") )
    		jpql = new StringBuilder("select _p from CPProductionSchedule _p where _p.testSys is null and _p.logic is null");
    	else
    		jpql = new StringBuilder("select _p from ProductionSchedule _p where _p.testSys is null and _p.logic is null");

    	if ( queryVo.getCustomerProductNumber() != null && !"".equals(queryVo.getCustomerProductNumber() ) ){
    		jpql.append(" and _p.customerProductNumber like ?" );
    		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerProductNumber() ) );
    	}

        Page<ProductionSchedule> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        return new Page<ProductionScheduleDTO>(pages.getStart(), pages.getResultCount(), pageSize, ProductionScheduleAssembler.toDTOs(pages.getData()));
    }

    @Transactional
    public Page<ProductionScheduleDTO> pageQueryProductionSchedule(ProductionScheduleDTO queryVo, int currentPage, int pageSize, String sortname, String sortorder) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = null;
        if ( queryVo.getTestType() == null || "".equals(queryVo.getTestType())){
        	jpql = new StringBuilder("select _productionSchedule from ProductionSchedule _productionSchedule   where 1=1 and _productionSchedule.logic is null and _productionSchedule.testSys is not null ");
        } else if ( queryVo.getTestType().toLowerCase().equals("ft") ) {
        	jpql = new StringBuilder("select _productionSchedule from FTProductionSchedule _productionSchedule   where 1=1 and _productionSchedule.logic is null and _productionSchedule.testSys is not null ");
        } else if ( queryVo.getTestType().toLowerCase().equals("cp") ) {
        	jpql = new StringBuilder("select _productionSchedule from CPProductionSchedule _productionSchedule   where 1=1 and _productionSchedule.logic is null and _productionSchedule.testSys is not null ");
        } else {
        	throw new IllegalArgumentException();
        }
        if (queryVo.getCreateTimestamp() != null) {
            jpql.append(" and _productionSchedule.createTimestamp between ? and ? ");
            conditionVals.add(queryVo.getCreateTimestamp());
            conditionVals.add(queryVo.getCreateTimestampEnd());
        }
        if (queryVo.getLastModifyTimestamp() != null) {
            jpql.append(" and _productionSchedule.lastModifyTimestamp between ? and ? ");
            conditionVals.add(queryVo.getLastModifyTimestamp());
            conditionVals.add(queryVo.getLastModifyTimestampEnd());
        }
        if (queryVo.getCreateEmployNo() != null && !"".equals(queryVo.getCreateEmployNo())) {
            jpql.append(" and _productionSchedule.createEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateEmployNo()));
        }
        if (queryVo.getLastModifyEmployNo() != null && !"".equals(queryVo.getLastModifyEmployNo())) {
            jpql.append(" and _productionSchedule.lastModifyEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLastModifyEmployNo()));
        }
        if (queryVo.getPlanedStartTimestamp() != null && !"".equals(queryVo.getPlanedStartTimestamp())) {
            jpql.append(" and _productionSchedule.planedStartTimestamp like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPlanedStartTimestamp()));
        }
        if (queryVo.getPlanedEndTimestamp() != null && !"".equals(queryVo.getPlanedEndTimestamp())) {
            jpql.append(" and _productionSchedule.planedEndTimestamp like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPlanedEndTimestamp()));
        }
        if (queryVo.getActualStartTimestamp() != null && !"".equals(queryVo.getActualStartTimestamp())) {
            jpql.append(" and _productionSchedule.actualStartTimestamp like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getActualStartTimestamp()));
        }
        if (queryVo.getActualEndTimestamp() != null && !"".equals(queryVo.getActualEndTimestamp())) {
            jpql.append(" and _productionSchedule.actualEndTimestamp like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getActualEndTimestamp()));
        }
        if (queryVo.getAmount() != null && !"".equals(queryVo.getAmount())) {
            jpql.append(" and _productionSchedule.amount like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getAmount()));
        }
        if (queryVo.getDoneQty() != null && !"".equals(queryVo.getDoneQty())) {
            jpql.append(" and _productionSchedule.doneQty like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDoneQty()));
        }
        if ( queryVo.getTestSysId() != null && !"".equals(queryVo.getTestSysId() ) ) {
        	jpql.append(" and _productionSchedule.testSys.id = ?");
        	conditionVals.add( queryVo.getTestSysId() );
        }
        if ( queryVo.getTestSysName() != null && !"".equals(queryVo.getTestSysName()) ) {
        	jpql.append(" and _productionSchedule.testSys.platformNumber like ?" );
        	conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTestSysName()));
        }
        if (queryVo.getNote() != null && !"".equals(queryVo.getNote())) {
            jpql.append(" and _productionSchedule.note like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNote()));
        }
        if (queryVo.getLotNumber() != null && !"".equals(queryVo.getLotNumber())) {
            jpql.append(" and _productionSchedule.lotNumber like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLotNumber()));
        }
        if ( queryVo.getState() != null && !"".equals( queryVo.getState() ) ) {
        	jpql.append(" and _productionSchedule.state like ?" );
        	conditionVals.add(MessageFormat.format("%{0}%", queryVo.getState()));
        } else {
        	//排除测试完成的批次
        	jpql.append(" and _productionSchedule.state <> ?" );
        	conditionVals.add(MessageFormat.format("{0}", "1"));
        }
        if (sortname != null && !"".equals(sortname) && sortorder != null && !"".equals(sortname)) {
        	//当sortname不为productionSchedule的直属属性时会出错
            jpql.append(" order by _productionSchedule." + sortname + " " + sortorder );
        }
        else{
        	jpql.append(" order by _productionSchedule.testSys.platformNumber desc, _productionSchedule.planedStartTimestamp asc");
        }
        Page<ProductionSchedule> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();
        
//        List<ProductionScheduleDTO> productionScheduleDTOs = ProductionScheduleAssembler.toDTOs(pages.getData());
//        fillLotState( productionScheduleDTOs );
        
        return new Page<ProductionScheduleDTO>(pages.getStart(), pages.getResultCount(), pageSize, ProductionScheduleAssembler.toDTOs(pages.getData()));
    }
    
    /**
     * 填充DTO中的lotCurrentState字段以及lotHoldState字段
     * @param productionScheduleDTOs
     */
    private void fillLotState( ProductionScheduleDTO productionScheduleDTO ) {

		try{
			InternalLot internalLot = null;
			if ( productionScheduleDTO.getTestType().toLowerCase().equals("ft") )
				internalLot = ftLotApplication.findByProductionId(productionScheduleDTO.getId());
			else if ( productionScheduleDTO.getTestType().toLowerCase().equals("cp") )
				internalLot = cpLotApplication.findByProductionId(productionScheduleDTO.getId());
			else {}
			productionScheduleDTO.setLotCurrentState(internalLot.getCurrentState());
			productionScheduleDTO.setLotHoldState(internalLot.getHoldState());
		} catch (NullPointerException e ){
			e.printStackTrace();
			productionScheduleDTO.setLotCurrentState("无法获取");
			productionScheduleDTO.setLotHoldState("无法获取");
		}
    	
    }
    /**
     * 填充DTOs中的lotCurrentState字段以及lotHoldState字段
     * @param productionScheduleDTOs
     */
    private void fillLotState( List<ProductionScheduleDTO> productionScheduleDTOs ) {
    	
        for ( ProductionScheduleDTO productionScheduleDTO : productionScheduleDTOs ) {
        	fillLotState(productionScheduleDTO);
        }

    }

    /**
     * 建立新的待排产的生产任务
     *
     * @param ftLot 与之关联的ftLot
     * @return 新建结果
     * @deprecated ProductionSchedule已与FTLot脱离关联
     */
    public InvokeResult createNewScheduleByInternalLot(FTLot ftLot) {
        if (ftLot == null)
            return InvokeResult.failure("创建生产任务失败");
        //新建schedule实体
        ProductionSchedule productionSchedule = new ProductionSchedule();
        //设置对应的值
        productionSchedule.setState("0");
        productionSchedule.setAmount(ftLot.getQty());
        productionSchedule.setDoneQty((long) 0);
        productionSchedule.setNote("");
        productionSchedule.setLotNumber(ftLot.getInternalLotNumber());
        //设置testSys(wait to be scheduled)
        TestSys testSys = testSysApplication.get(waitToBeScheduled);
        if (testSys == null)
            return InvokeResult.failure("无法获取待排产行。");
        productionSchedule.setTestSys(testSys);
        //设置ftLot
//		productionSchedule.setFtLot(ftLot);
        //从绑定的测试程序取出理论、实际uph(涉及到版本型号的问题，一个产品可能对应多个测试程序，暂时搁置)
//		productionSchedule.setUPHTheory( ftLot.getCustomerFTLot().getFtInfo().getTestProgramTemplate());

        //持久化
        application.create(productionSchedule);
        return InvokeResult.success();
    }

    @Override
    public InvokeResult updateState(Long id, String state, String note) {
        try {
            ProductionSchedule productionSchedule = application.get(id);
            productionSchedule.setState(state);
            productionSchedule.setNote(note);
            application.update(productionSchedule);
        } catch (Exception e) {
            e.printStackTrace();
            return InvokeResult.failure(e.getMessage());
        }
        return InvokeResult.success();
    }

    @Override
    public InvokeResult separate(Long id, String newLotNumber, Double percent, Long targetTestSysId) {
    	application.separate(id, newLotNumber, percent, targetTestSysId);
        return InvokeResult.success();
    }

	@Override
	public InvokeResult basicScheduling(Long id, Integer version, Long testSysId ) {
		application.basicScheduling(id, version, testSysId );
		return InvokeResult.success();
	}

	@Override
	public InvokeResult arrangeProductionsInATestSys(Long[] productionIds, Long testSysId) {
		application.arrangeProductionsInATestSys( productionIds, testSysId );
		return InvokeResult.success();
	}

	@Override
	public InvokeResult revokeProductionSchedules(Long[] ids) {
		application.revokeProductionSchedules( ids );
		return InvokeResult.success();
	}

	@Override
	public InvokeResult reorderUp(Long id) {
		application.reorderUp(id);
		return InvokeResult.success();
	}
	
	@Override
	public InvokeResult reorderDown(Long id) {
		application.reorderDown(id);
		return InvokeResult.success();
	}

	@Override
	public InvokeResult moveToTop(Long[] ids) {
		application.moveToTop(ids);
		return InvokeResult.success();
	}

	@Override
	public InvokeResult updateAllTestingProduction() {
		application.updateAllTestingProduction();
		return InvokeResult.success();
	}

	@Override
	public InvokeResult startTesting(Long id) {
		application.startTesting(id);
		return InvokeResult.success();
	}

	@Override
	public InvokeResult endTesting(Long id) {
		application.endTesting(id);
		return InvokeResult.success();
	}


}
