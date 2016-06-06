package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.FTReworkItemApplication;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.FTReworkItem;
import org.seu.acetec.mes2Koala.facade.FTReworkItemFacade;
import org.seu.acetec.mes2Koala.facade.dto.FTReworkDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTReworkItemDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTReworkItemAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class FTReworkItemFacadeImpl implements FTReworkItemFacade {

    @Inject
    private FTReworkItemApplication application;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(
                    QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getFTReworkItem(Long id) {
        return InvokeResult.success(FTReworkItemAssembler.toDTO(application
                .get(id)));
    }

    public InvokeResult creatFTReworkItem(FTReworkItemDTO fTReworkItemDTO) {
        application.create(FTReworkItemAssembler
                .toEntity(fTReworkItemDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateFTReworkItem(FTReworkItemDTO fTReworkItemDTO) {
        application.update(FTReworkItemAssembler
                .toEntity(fTReworkItemDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeFTReworkItem(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeFTReworkItems(Long[] ids) {
        Set<FTReworkItem> fTReworkItems = new HashSet<FTReworkItem>();
        for (Long id : ids) {
            fTReworkItems.add(application.get(id));
        }
        application.removeAll(fTReworkItems);
        return InvokeResult.success();
    }

    public List<FTReworkItemDTO> findAllFTReworkItem() {
        return FTReworkItemAssembler.toDTOs(application.findAll());
    }

    public Page<FTReworkItemDTO> pageQueryFTReworkItem(FTReworkItemDTO queryVo,
                                                       int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder(
                "select _fTReworkItem from FTReworkItem _fTReworkItem   where 1=1 ");
        if (queryVo.getCreateTimestamp() != null) {
            jpql.append(" and _fTReworkItem.createTimestamp between ? and ? ");
            conditionVals.add(queryVo.getCreateTimestamp());
            conditionVals.add(queryVo.getCreateTimestampEnd());
        }
        if (queryVo.getLastModifyTimestamp() != null) {
            jpql.append(" and _fTReworkItem.lastModifyTimestamp between ? and ? ");
            conditionVals.add(queryVo.getLastModifyTimestamp());
            conditionVals.add(queryVo.getLastModifyTimestampEnd());
        }
        if (queryVo.getCreateEmployNo() != null
                && !"".equals(queryVo.getCreateEmployNo())) {
            jpql.append(" and _fTReworkItem.createEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getCreateEmployNo()));
        }
        if (queryVo.getLastModifyEmployNo() != null
                && !"".equals(queryVo.getLastModifyEmployNo())) {
            jpql.append(" and _fTReworkItem.lastModifyEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getLastModifyEmployNo()));
        }
        if (queryVo.getAccomplishDate() != null) {
            jpql.append(" and _fTReworkItem.accomplishDate between ? and ? ");
            conditionVals.add(queryVo.getAccomplishDate());
            conditionVals.add(queryVo.getAccomplishDateEnd());
        }
        if (queryVo.getAttentionItem() != null
                && !"".equals(queryVo.getAttentionItem())) {
            jpql.append(" and _fTReworkItem.attentionItem like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getAttentionItem()));
        }
        if (queryVo.getOperator() != null && !"".equals(queryVo.getOperator())) {
            jpql.append(" and _fTReworkItem.operator like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getOperator()));
        }
        if (queryVo.getReworkFlow() != null
                && !"".equals(queryVo.getReworkFlow())) {
            jpql.append(" and _fTReworkItem.reworkFlow like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getReworkFlow()));
        }
        Page<FTReworkItem> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString()).setParameters(conditionVals)
                .setPage(currentPage, pageSize).pagedList();

        return new Page<FTReworkItemDTO>(pages.getStart(),
                pages.getResultCount(), pageSize,
                FTReworkItemAssembler.toDTOs(pages.getData()));
    }

    public Page<FTReworkItemDTO> findReworkItemsByFTReworkItem(Long id,
                                                               int currentPage, int pageSize) {
        List<FTReworkItemDTO> result = new ArrayList<FTReworkItemDTO>();
        String jpql = "select e from FTReworkItem o right join o.reworkItems e where o.id=?";
        Page<FTReworkItem> pages = getQueryChannelService()
                .createJpqlQuery(jpql).setParameters(new Object[]{id})
                .setPage(currentPage, pageSize).pagedList();
        for (FTReworkItem entity : pages.getData()) {
            FTReworkItemDTO dto = new FTReworkItemDTO();
            try {
                BeanUtils.copyProperties(dto, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.add(dto);
        }
        return new Page<FTReworkItemDTO>(Page.getStartOfPage(currentPage,
                pageSize), pages.getResultCount(), pageSize, result);
    }

    @Override
    public FTReworkDTO findFtReWorkByFTReworkItem(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

}
