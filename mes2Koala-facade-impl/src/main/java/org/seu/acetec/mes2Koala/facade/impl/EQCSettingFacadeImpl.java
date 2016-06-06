package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.EQCSettingApplication;
import org.seu.acetec.mes2Koala.core.domain.EQCSetting;
import org.seu.acetec.mes2Koala.facade.EQCSettingFacade;
import org.seu.acetec.mes2Koala.facade.dto.EQCSettingDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.EQCSettingAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class EQCSettingFacadeImpl implements EQCSettingFacade {

    @Inject
    private EQCSettingApplication application;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    @Override
    public InvokeResult createEQCSettings(List<EQCSettingDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            createEQCSetting(list.get(i));
        }
        return InvokeResult.success();
    }

    public InvokeResult getEQCSetting(Long id) {
        return InvokeResult.success(EQCSettingAssembler.toDTO(application.get(id)));
    }

    public InvokeResult createEQCSetting(EQCSettingDTO eQCsettingDTO) {
        application.create(EQCSettingAssembler.toEntity(eQCsettingDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateEQCSetting(EQCSettingDTO eQCsettingDTO) {
        application.update(EQCSettingAssembler.toEntity(eQCsettingDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeEQCSetting(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeEQCSettings(Long[] ids) {
        Set<EQCSetting> eqcSettings = new HashSet<EQCSetting>();
        for (Long id : ids) {
            eqcSettings.add(application.get(id));
        }
        application.removeAll(eqcSettings);
        return InvokeResult.success();
    }

    @Override
    public InvokeResult removeEQCSettings(List<EQCSettingDTO> original) {
        if (original != null && original.get(0) != null) {
            Long[] idArrs = new Long[original.size()];
            for (int j = 0; j < original.size(); j++) {
                idArrs[j] = original.get(j).getId();
            }
            removeEQCSettings(idArrs);
        }
        return InvokeResult.success();
    }

    public List<EQCSettingDTO> findAllEQCSetting() {
        return EQCSettingAssembler.toDTOs(application.findAll());
    }

    public Page<EQCSettingDTO> pageQueryEQCSetting(EQCSettingDTO queryVo, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _eQCsetting from EQCSetting _eQCsetting   where 1=1 ");
        if (queryVo.getQty() != null) {
            jpql.append(" and _eQCsetting.Qty=?");
            conditionVals.add(queryVo.getQty());
        }
        if (queryVo.getLowerLimit() != null) {
            jpql.append(" and _eQCsetting.lowerLimit=?");
            conditionVals.add(queryVo.getLowerLimit());
        }
        if (queryVo.getUpperLimit() != null) {
            jpql.append(" and _eQCsetting.upperLimit=?");
            conditionVals.add(queryVo.getUpperLimit());
        }
        Page<EQCSetting> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        return new Page<EQCSettingDTO>(pages.getStart(), pages.getResultCount(), pageSize, EQCSettingAssembler.toDTOs(pages.getData()));
    }

    @Override
    public List<EQCSettingDTO> findEQCSettingsByProduct(Long id) {
        List<EQCSetting> eqcSettings = application.findByInternalProductId(id);
        return EQCSettingAssembler.toDTOs(eqcSettings);
    }

    @Override
    public InvokeResult replaceAll(List<EQCSettingDTO> list) {
        List<EQCSettingDTO> original = findEQCSettingsByProduct(list.get(0).getInternalProductDTO().getId());
        removeEQCSettings(original);
        createEQCSettings(list);
        return InvokeResult.success();
    }
}
