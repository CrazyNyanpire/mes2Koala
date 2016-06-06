package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.CPReworkItemApplication;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.CPReworkItem;
import org.seu.acetec.mes2Koala.facade.CPReworkItemFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPReworkDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPReworkItemDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPReworkItemAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class CPReworkItemFacadeImpl implements CPReworkItemFacade {

    @Inject
    private CPReworkItemApplication application;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(
                    QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getCPReworkItem(Long id) {
        return InvokeResult.success(CPReworkItemAssembler.toDTO(application
                .get(id)));
    }

    public InvokeResult creatCPReworkItem(CPReworkItemDTO cpReworkItemDTO) {
        application.create(CPReworkItemAssembler
                .toEntity(cpReworkItemDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateCPReworkItem(CPReworkItemDTO cpReworkItemDTO) {
        application.update(CPReworkItemAssembler
                .toEntity(cpReworkItemDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeCPReworkItem(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeCPReworkItems(Long[] ids) {
        Set<CPReworkItem> cpReworkItems = new HashSet<CPReworkItem>();
        for (Long id : ids) {
            cpReworkItems.add(application.get(id));
        }
        application.removeAll(cpReworkItems);
        return InvokeResult.success();
    }

    public List<CPReworkItemDTO> findAllCPReworkItem() {
        return CPReworkItemAssembler.toDTOs(application.findAll());
    }

    public Page<CPReworkItemDTO> pageQueryCPReworkItem(CPReworkItemDTO queryVo,
                                                       int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder(
                "select _cpReworkItem from CPReworkItem _cpReworkItem   where 1=1 ");
        if (queryVo.getCreateTimestamp() != null) {
            jpql.append(" and _cpReworkItem.createTimestamp between ? and ? ");
            conditionVals.add(queryVo.getCreateTimestamp());
            conditionVals.add(queryVo.getCreateTimestampEnd());
        }
        if (queryVo.getLastModifyTimestamp() != null) {
            jpql.append(" and _cpReworkItem.lastModifyTimestamp between ? and ? ");
            conditionVals.add(queryVo.getLastModifyTimestamp());
            conditionVals.add(queryVo.getLastModifyTimestampEnd());
        }
        if (queryVo.getCreateEmployNo() != null
                && !"".equals(queryVo.getCreateEmployNo())) {
            jpql.append(" and _cpReworkItem.createEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getCreateEmployNo()));
        }
        if (queryVo.getLastModifyEmployNo() != null
                && !"".equals(queryVo.getLastModifyEmployNo())) {
            jpql.append(" and _cpReworkItem.lastModifyEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getLastModifyEmployNo()));
        }
        if (queryVo.getAccomplishDate() != null) {
            jpql.append(" and _cpReworkItem.accomplishDate between ? and ? ");
            conditionVals.add(queryVo.getAccomplishDate());
            conditionVals.add(queryVo.getAccomplishDateEnd());
        }
        if (queryVo.getAttentionItem() != null
                && !"".equals(queryVo.getAttentionItem())) {
            jpql.append(" and _cpReworkItem.attentionItem like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getAttentionItem()));
        }
        if (queryVo.getOperator() != null && !"".equals(queryVo.getOperator())) {
            jpql.append(" and _cpReworkItem.operator like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getOperator()));
        }
        if (queryVo.getReworkFlow() != null
                && !"".equals(queryVo.getReworkFlow())) {
            jpql.append(" and _cpReworkItem.reworkFlow like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getReworkFlow()));
        }
        Page<CPReworkItem> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString()).setParameters(conditionVals)
                .setPage(currentPage, pageSize).pagedList();

        return new Page<CPReworkItemDTO>(pages.getStart(),
                pages.getResultCount(), pageSize,
                CPReworkItemAssembler.toDTOs(pages.getData()));
    }

    public Page<CPReworkItemDTO> findReworkItemsByCPReworkItem(Long id,
                                                               int currentPage, int pageSize) {
        List<CPReworkItemDTO> result = new ArrayList<CPReworkItemDTO>();
        String jpql = "select e from CPReworkItem o right join o.reworkItems e where o.id=?";
        Page<CPReworkItem> pages = getQueryChannelService()
                .createJpqlQuery(jpql).setParameters(new Object[]{id})
                .setPage(currentPage, pageSize).pagedList();
        for (CPReworkItem entity : pages.getData()) {
            CPReworkItemDTO dto = new CPReworkItemDTO();
            try {
                BeanUtils.copyProperties(dto, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.add(dto);
        }
        return new Page<CPReworkItemDTO>(Page.getStartOfPage(currentPage,
                pageSize), pages.getResultCount(), pageSize, result);
    }

    @Override
    public CPReworkDTO findCpReWorkByCPReworkItem(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

}
