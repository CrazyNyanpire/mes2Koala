package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.LabelPlanApplication;
import org.seu.acetec.mes2Koala.core.domain.LabelPlan;
import org.seu.acetec.mes2Koala.facade.LabelPlanFacade;
import org.seu.acetec.mes2Koala.facade.dto.LabelPlanDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.LabelPlanAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class LabelPlanFacadeImpl implements LabelPlanFacade {

    @Inject
    private LabelPlanApplication application;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getLabelPlan(Long id) {
        return InvokeResult.success(LabelPlanAssembler.toDTO(application.get(id)));
    }

    public InvokeResult creatLabelPlan(LabelPlanDTO labelPlanDTO) {
        application.create(LabelPlanAssembler.toEntity(labelPlanDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateLabelPlan(LabelPlanDTO labelPlanDTO) {
        application.update(LabelPlanAssembler.toEntity(labelPlanDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeLabelPlan(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeLabelPlans(Long[] ids) {
        Set<LabelPlan> labelPlans = new HashSet<LabelPlan>();
        for (Long id : ids) {
            labelPlans.add(application.get(id));
        }
        application.removeAll(labelPlans);
        return InvokeResult.success();
    }

    public List<LabelPlanDTO> findAllLabelPlan() {
        return LabelPlanAssembler.toDTOs(application.findAll());
    }

    public Page<LabelPlanDTO> pageQueryLabelPlan(LabelPlanDTO queryVo, int currentPage, int pageSize) {

        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _labelPlan from LabelPlan _labelPlan   where 1=1 ");
        Page<LabelPlan> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        return new Page<LabelPlanDTO>(pages.getStart(), pages.getResultCount(), pageSize, LabelPlanAssembler.toDTOs(pages.getData()));
    }

    @Override
    public LabelPlanDTO findLabelPlanByProduct(Long id) {
        LabelPlan labelPlan = application.findByInternalProductId(id);
        return LabelPlanAssembler.toDTO(labelPlan);
    }
}
