package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.InternalProductApplication;
import org.seu.acetec.mes2Koala.application.SBLTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.InternalProduct;
import org.seu.acetec.mes2Koala.core.domain.SBLTemplate;
import org.seu.acetec.mes2Koala.facade.SBLTemplateFacade;
import org.seu.acetec.mes2Koala.facade.dto.SBLTemplateDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.SBLTemplateAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Named
public class SBLTemplateFacadeImpl implements SBLTemplateFacade {

    @Inject
    InternalProductApplication internalProductApplication;
    @Inject
    private SBLTemplateApplication application;
    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    @Override
    public InvokeResult getSBLTemplate(Long id) {
        return InvokeResult.success(SBLTemplateAssembler.toDTO(application.get(id)));
    }

    @Override
    public InvokeResult createSBLTemplate(SBLTemplateDTO sblTemplateDTO) {
        application.create(SBLTemplateAssembler.toEntity(sblTemplateDTO));
        return InvokeResult.success();
    }

    @Override
    public void createSBLTemplates(List<SBLTemplateDTO> sblTemplateDTOs) {
        for (SBLTemplateDTO sblTemplateDTO : sblTemplateDTOs) {
            createSBLTemplate(sblTemplateDTO);
        }
    }

    @Override
    public InvokeResult updateSBLTemplate(SBLTemplateDTO sblTemplateDTO) {
        application.update(SBLTemplateAssembler.toEntity(sblTemplateDTO));
        return InvokeResult.success();
    }

    @Override
    public InvokeResult removeSBLTemplate(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    @Override
    public InvokeResult removeSBLTemplates(Long[] ids) {
        List<SBLTemplate> sblTemplates = new ArrayList<>();
        for (Long id : ids) {
            sblTemplates.add(application.get(id));
        }
        application.removeAll(sblTemplates);
        return InvokeResult.success();
    }

    @Override
    public InvokeResult removeSBLTemplates(List<SBLTemplateDTO> list) {
        List<SBLTemplate> SBLTemplates = new ArrayList<>();
        for (SBLTemplateDTO SBLTemplateDTO : list) {
            SBLTemplate template = application.get(SBLTemplateDTO.getId());
            SBLTemplates.add(template);
        }
        application.removeAll(SBLTemplates);
        return InvokeResult.success();
    }

    @Override
    public List<SBLTemplateDTO> findAllSBLTemplate() {
        return SBLTemplateAssembler.toDTOs(application.findAll());
    }

    @Override
    public Page<SBLTemplateDTO> pageQuerySBLTemplate(SBLTemplateDTO sblTemplateDTO, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<>();
        StringBuilder jpql = new StringBuilder("select _sblTemplate from SBLTemplate _sblTemplate   where 1=1 ");
        if (sblTemplateDTO.getBinType() != null && !"".equals(sblTemplateDTO.getBinType())) {
            jpql.append(" and _sblTemplate.BinType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", sblTemplateDTO.getBinType()));
        }
        if (sblTemplateDTO.getLowerLimit() != null) {
            jpql.append(" and _sblTemplate.lowerLimit=?");
            conditionVals.add(sblTemplateDTO.getLowerLimit());
        }
        if (sblTemplateDTO.getUpperLimit() != null) {
            jpql.append(" and _sblTemplate.upperLimit=?");
            conditionVals.add(sblTemplateDTO.getUpperLimit());
        }
        if (sblTemplateDTO.getBinQuality() != null && !"".equals(sblTemplateDTO.getBinQuality())) {
            jpql.append(" and _sblTemplate.BinQuality like ?");
            conditionVals.add(MessageFormat.format("%{0}%", sblTemplateDTO.getBinQuality()));
        }
        if (sblTemplateDTO.getNodeName() != null && !"".equals(sblTemplateDTO.getNodeName())) {
            jpql.append(" and _sblTemplate.nodeName like ?");
            conditionVals.add(MessageFormat.format("%{0}%", sblTemplateDTO.getNodeName()));
        }
        if (sblTemplateDTO.getSite() != null && !"".equals(sblTemplateDTO.getSite())) {
            jpql.append(" and _sblTemplate.site like ?");
            conditionVals.add(MessageFormat.format("%{0}%", sblTemplateDTO.getSite()));
        }
        Page<SBLTemplate> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();
        return new Page<>(pages.getStart(), pages.getResultCount(), pageSize, SBLTemplateAssembler.toDTOs(pages.getData()));
    }

    /**
     * 通过内部产品Id找出对应的所有SBLTemplate
     * 也可以通过InternalProduct的sblTemplates属性获取
     *
     * @param id
     * @return
     * @version 2016/3/2 YuxiangQue
     * @version 2016/3/29 YuxiangQue
     */
    @Override
    public List<SBLTemplateDTO> findSBLTemplatesDTOsByProductId(Long id) {
        return SBLTemplateAssembler.toDTOs(application.findByInternalProductId(id));
    }

    /**
     * 简单起见，找出sblTemplates对应的Product，
     * 删除其名下所有sblTemplates，然后添加更新后的
     *
     * @param internalProductId
     * @param sblTemplateDTOs
     * @return
     * @version 2016/3/2 YuxiangQue
     * @version 2016/3/14 YuxiangQue 修复SBL绑定产品
     */
    @Override
    public InvokeResult bindSBLTemplates(Long internalProductId, List<SBLTemplateDTO> sblTemplateDTOs) {
        if (sblTemplateDTOs.size() <= 0) {
            return InvokeResult.success();
        }
        InternalProduct internalProduct = internalProductApplication.get(internalProductId);

        // 先移除所有已经绑定的模板
        List<SBLTemplate> oldSBLTemplates = application.findByInternalProductId(internalProductId);
        application.removeAll(oldSBLTemplates);

        // 添加新增加的模板
        List<SBLTemplate> newSBLTemplates = SBLTemplateAssembler.toEntities(sblTemplateDTOs);
        for (SBLTemplate newSBLTemplate : newSBLTemplates) {
            // 绑定产品
            newSBLTemplate.setInternalProduct(internalProduct);
        }
        application.createAll(newSBLTemplates);
        return InvokeResult.success();
    }
}
