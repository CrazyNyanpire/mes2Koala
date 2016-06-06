package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.BomTemplateApplication;
import org.seu.acetec.mes2Koala.application.BomUseApplication;
import org.seu.acetec.mes2Koala.core.domain.BomUse;
import org.seu.acetec.mes2Koala.facade.BomUseFacade;
import org.seu.acetec.mes2Koala.facade.dto.BomTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.BomUseDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.BomTemplateAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.BomUseAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.*;

@Named
public class BomUseFacadeImpl implements BomUseFacade {

    @Inject
    BomTemplateApplication bomTemplateApplication;
    @Inject
    private BomUseApplication application;
    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getBomUse(Long id) {
        return InvokeResult.success(BomUseAssembler.toDTO(application.get(id)));
    }

    public InvokeResult createBomUse(BomUseDTO bomUseDTO) {
        application.create(BomUseAssembler.toEntity(bomUseDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateBomUse(BomUseDTO bomUseDTO) {
        application.update(BomUseAssembler.toEntity(bomUseDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeBomUse(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeBomUses(Long[] ids) {
        Set<BomUse> bomUses = new HashSet<BomUse>();
        for (Long id : ids) {
            BomUse bomUse = application.get(id);
            if (bomUse != null)
                bomUses.add(bomUse);
        }
        application.removeAll(bomUses);
        return InvokeResult.success();
    }

    public List<BomUseDTO> findAllBomUseList() {
        return BomUseAssembler.toDTOs(application.findAll());
    }

    public Page<BomUseDTO> pageQueryBomUse(BomUseDTO bomUseDTO, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        // TODO 修改语句
        StringBuilder jpql = new StringBuilder("select _bomUse from BomUse _bomUse where 1=1 ");
        if (bomUseDTO.getCreateTimestamp() != null) {
            jpql.append(" and _bomUse.createTimestamp between ? and ? ");
            conditionVals.add(bomUseDTO.getCreateTimestamp());
            conditionVals.add(new Date());
        }
        if (bomUseDTO.getLastModifyTimestamp() != null) {
            jpql.append(" and _bomUse.lastModifyTimestamp between ? and ? ");
            conditionVals.add(bomUseDTO.getLastModifyTimestamp());
            conditionVals.add(new Date());
        }
        if (bomUseDTO.getCreateEmployNo() != null && !"".equals(bomUseDTO.getCreateEmployNo())) {
            jpql.append(" and _bomUse.createEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%", bomUseDTO.getCreateEmployNo()));
        }
        if (bomUseDTO.getLastModifyEmployNo() != null && !"".equals(bomUseDTO.getLastModifyEmployNo())) {
            jpql.append(" and _bomUse.lastModifyEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%", bomUseDTO.getLastModifyEmployNo()));
        }
        if (bomUseDTO.getSelected() != null) {
            jpql.append(" and _bomUse.selected is ?");
            conditionVals.add(bomUseDTO.getSelected());
        }
        Page<BomUse> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        return new Page<BomUseDTO>(pages.getStart(), pages.getResultCount(), pageSize, BomUseAssembler.toDTOs(pages.getData()));
    }

    @Override
    public BomTemplateDTO findBomTemplateByBomUseId(Long id) {
        return BomTemplateAssembler.toDTO(bomTemplateApplication.findByBomUseId(id));
    }
}
