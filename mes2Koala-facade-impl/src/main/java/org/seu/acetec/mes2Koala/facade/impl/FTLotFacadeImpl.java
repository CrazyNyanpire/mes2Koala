package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.PlaceHolderApplication;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.PlaceHolder;
import org.seu.acetec.mes2Koala.facade.CustomerFTLotFacade;
import org.seu.acetec.mes2Koala.facade.FTLotFacade;
import org.seu.acetec.mes2Koala.facade.FTProcessFacade;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.FTLotPageVo;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CustomerFTLotAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTLotAssembler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.*;

@Named
@Transactional
public class FTLotFacadeImpl implements FTLotFacade {

    @Inject
    FTProcessFacade FTProcessFacade;

    @Inject
    CustomerFTLotFacade customerFTLotFacade;

    @Inject
    private FTLotApplication application;
    
    @Inject
    PlaceHolderApplication placeHolderApplication;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    @Override
    public InvokeResult getFTLot(Long id) {
        return InvokeResult.success(FTLotAssembler.toDTO(application.get(id)));
    }

    @Override
    public InvokeResult getInternalLotFT(Long id) {
        return InvokeResult.success(FTLotAssembler.toPageVo(application.get(id)));
    }

    @Override
    public List<FTLotPageVo> getFTPageDTOs(Long[] idArray) {
        if (idArray == null)
            return null;
        List<FTLotPageVo> ftPageDTOs = new ArrayList<FTLotPageVo>();
        for (Long id : idArray) {
            ftPageDTOs.add(FTLotAssembler.toPageVo(application.get(id)));
        }
        return ftPageDTOs;
    }

    @Override
    public InvokeResult createCheckedFTLot(FTLotDTO childFTLotDTO) {
        try {
            FTLot ftLot = FTLotAssembler.toEntity(childFTLotDTO);
            application.create(ftLot);
            return InvokeResult.success(ftLot.getId()); // 保持接口不变
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚所有操作
            return InvokeResult.failure(ex.getMessage());
        }
    }

    @Override
    public InvokeResult updateFTLot(FTLotDTO fTLotDTO) {
        application.update(FTLotAssembler.toEntity(fTLotDTO));
        return InvokeResult.success();
    }

    @Override
    public InvokeResult removeFTLot(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    @Override
    public InvokeResult removeFTLots(Long[] ids) {
        Set<FTLot> fTLots = new HashSet<FTLot>();
        for (Long id : ids) {
            fTLots.add(application.get(id));
        }
        application.removeAll(fTLots);
        return InvokeResult.success();
    }

    @Override
    public List<FTLotDTO> findAllFTLot() {
        return FTLotAssembler.toDTOs(application.findAll());
    }

    @Override
    public Page<FTLotPageVo> pageQueryFTPage(FTLotPageVo queryVo, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<>();
        StringBuilder jpql = new StringBuilder("select _internalLot from FTLot _internalLot   where 1=1 ");
        
        if (queryVo.getPackageType() != null && !"".equals(queryVo.getPackageType())) {
            jpql.append(" and _internalLot.customerLot.internalProduct.packageType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPackageType()));
        }
        if (queryVo.getLotNumber() != null && !"".equals(queryVo.getLotNumber())) {
            jpql.append(" and _internalLot.internalLotNumber like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPackageType()));
        }
        if (queryVo.getState() != null && !"".equals(queryVo.getState())) {
            jpql.append(" and _internalLot.currentState like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getState()));
        }
        if (queryVo.getCreateTimestamp() != null) {
            jpql.append(" and _internalLot.createTimestamp between ? and ? ");
            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(queryVo.getCreateTimestampEnd());
            calendarEnd.add(Calendar.MILLISECOND, 23 * 59 * 59 * 1000 + 999);

            conditionVals.add(queryVo.getCreateTimestamp());
            conditionVals.add(calendarEnd.getTime());
        }
        jpql.append(" and (_internalLot.logic is null or _internalLot.logic <> 1) ");
        Page<FTLot> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        return new Page<FTLotPageVo>(pages.getStart(), pages.getResultCount(), pageSize, FTLotAssembler.toPageVos(pages.getData()));
    }

    public Page<FTLotDTO> pageQueryFTLot(FTLotDTO queryVo, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _fTLot from FTLot _fTLot   where 1=1 ");
        if (queryVo.getCreateTimestamp() != null) {
            jpql.append(" and _fTLot.createTimestamp between ? and ? ");
            conditionVals.add(queryVo.getCreateTimestamp());
            conditionVals.add(queryVo.getCreateTimestampEnd());
        }
        if (queryVo.getLastModifyTimestamp() != null) {
            jpql.append(" and _fTLot.lastModifyTimestamp between ? and ? ");
            conditionVals.add(queryVo.getLastModifyTimestamp());
            conditionVals.add(queryVo.getLastModifyTimestampEnd());
        }
        if (queryVo.getCreateEmployNo() != null && !"".equals(queryVo.getCreateEmployNo())) {
            jpql.append(" and _fTLot.createEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCreateEmployNo()));
        }
        if (queryVo.getLastModifyEmployNo() != null && !"".equals(queryVo.getLastModifyEmployNo())) {
            jpql.append(" and _fTLot.lastModifyEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLastModifyEmployNo()));
        }
        if (queryVo.getBorrow() != null) {
            jpql.append(" and _fTLot.borrow=?");
            conditionVals.add(queryVo.getBorrow());
        }
        if (queryVo.getLoss() != null) {
            jpql.append(" and _fTLot.loss=?");
            conditionVals.add(queryVo.getLoss());
        }
        if (queryVo.getQty() != null) {
            jpql.append(" and _fTLot.Qty=?");
            conditionVals.add(queryVo.getQty());
        }
        if (queryVo.getHoldState() != null) {
            jpql.append(" and _fTLot.holdState like ?");
            conditionVals.add(queryVo.getHoldState());
        }
        if (queryVo.getCurrentState() != null) {
            jpql.append(" and _fTLot.currentState like ?");
            conditionVals.add(queryVo.getCurrentState());
        }
        if (queryVo.getType() != null && !"".equals(queryVo.getType())) {
            jpql.append(" and _fTLot.type like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getType()));
        }
        if ( queryVo.getInternalLotNumber() != null && !"".equals(queryVo.getInternalLotNumber()) ) {
        	jpql.append(" and _fTLot.internalLotNumber like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getInternalLotNumber()));
        }
        Page<FTLot> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();
        return new Page<FTLotDTO>(pages.getStart(), pages.getResultCount(), pageSize, FTLotAssembler.toDTOs(pages.getData()));
    }

	@Override
	public InvokeResult getPlaceHold(Long id) {
		 placeHolderApplication.findByFTLot(id);
		 return InvokeResult.success();
	}

	@Override
	public InvokeResult getProcessTemplateByFtLotId(Long id) {
		try{
			FTLot ftLot = application.get(id);		
			String content = CustomerFTLotAssembler.toDTO(ftLot.getCustomerFTLot()).getProcessTemplateContent();
			return InvokeResult.success(content);
		}catch(Exception e){
			return InvokeResult.failure("ERR");
		}
	}

}
