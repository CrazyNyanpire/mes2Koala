package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.FTResultApplication;
import org.seu.acetec.mes2Koala.core.domain.FTResult;
import org.seu.acetec.mes2Koala.facade.FTResultFacade;
import org.seu.acetec.mes2Koala.facade.dto.FT_ResultDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTResultAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class FTResultFacadeImpl implements FTResultFacade {

    @Inject
    private FTResultApplication application;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getFTResult(Long id) {
        return InvokeResult.success(FTResultAssembler.toDTO(application.get(id)));
    }

    public InvokeResult createFTResult(FT_ResultDTO fT_ResultDTO) {
        application.create(FTResultAssembler.toEntity(fT_ResultDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateFTResult(FT_ResultDTO fT_ResultDTO) {
        application.update(FTResultAssembler.toEntity(fT_ResultDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeFTResult(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeFTResults(Long[] ids) {
        Set<FTResult> fT_Results = new HashSet<FTResult>();
        for (Long id : ids) {
            fT_Results.add(application.get(id));
        }
        application.removeAll(fT_Results);
        return InvokeResult.success();
    }

    public List<FT_ResultDTO> findAllFTResult() {
        return FTResultAssembler.toDTOs(application.findAll());
    }

    public Page<FT_ResultDTO> pageQueryFT_Result(FT_ResultDTO queryVo, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _fT_Result from FTResult _fT_Result   where 1=1 ");
//	   	if (queryVo.getInTape() != null) {
//	   		jpql.append(" and _fT_Result.inTape=?");
//	   		conditionVals.add(queryVo.getInTape());
//	   	}
        if (queryVo.getYield() != null) {
            jpql.append(" and _fT_Result.yield=?");
            conditionVals.add(queryVo.getYield());
        }
        if (queryVo.getLoss() != null) {
            jpql.append(" and _fT_Result.loss=?");
            conditionVals.add(queryVo.getLoss());
        }
        if (queryVo.getBackUp() != null) {
            jpql.append(" and _fT_Result.backUp=?");
            conditionVals.add(queryVo.getBackUp());
        }
        if (queryVo.getOther() != null) {
            jpql.append(" and _fT_Result.other=?");
            conditionVals.add(queryVo.getOther());
        }
        Page<FTResult> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        return new Page<FT_ResultDTO>(pages.getStart(), pages.getResultCount(), pageSize, FTResultAssembler.toDTOs(pages.getData()));
    }


}
