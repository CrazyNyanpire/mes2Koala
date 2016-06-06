package org.seu.acetec.mes2Koala.facade.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.LabelApplication;
import org.seu.acetec.mes2Koala.core.domain.Label;
import org.seu.acetec.mes2Koala.core.domain.LabelPlan;
import org.seu.acetec.mes2Koala.facade.LabelFacade;
import org.seu.acetec.mes2Koala.facade.dto.LabelDTO;
import org.seu.acetec.mes2Koala.facade.dto.LabelPlanDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.LabelAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class LabelFacadeImpl implements LabelFacade {

    @Inject
    private LabelApplication application;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getLabel(Long id) {
        return InvokeResult.success(LabelAssembler.toDTO(application.get(id)));
    }

    public InvokeResult creatLabel(LabelDTO labelDTO) {
    	try{
    		application.create(LabelAssembler.toEntity(labelDTO));
    	}catch(Exception e){
    		return InvokeResult.failure("ERR：标签名重复");
    	}
        return InvokeResult.success();
    }

    public InvokeResult updateLabel(LabelDTO labelDTO) {
        application.update(LabelAssembler.toEntity(labelDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeLabel(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeLabels(Long[] ids) {
        Set<Label> labels = new HashSet<Label>();
        for (Long id : ids) {
            labels.add(application.get(id));
        }
        application.removeAll(labels);
        return InvokeResult.success();
    }

    public List<LabelDTO> findAllLabel() {
        return LabelAssembler.toDTOs(application.findAll());
    }

    public Page<LabelDTO> pageQueryLabel(LabelDTO queryVo, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _label from Label _label   where 1=1 ");
        if (queryVo.getLabelName() != null && !"".equals(queryVo.getLabelName())) {
            jpql.append(" and _label.labelName like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLabelName()));
        }
        if (queryVo.getLabelType() != null && !"".equals(queryVo.getLabelType())) {
            jpql.append(" and _label.labelType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLabelType()));
        }
        if (queryVo.getTestType() != null && !"".equals(queryVo.getTestType())) {
            jpql.append(" and _label.testType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTestType()));
        }
        if (queryVo.getPackageType() != null && !"".equals(queryVo.getPackageType())) {
            jpql.append(" and _label.packageType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPackageType()));
        }
        Page<Label> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        return new Page<LabelDTO>(pages.getStart(), pages.getResultCount(), pageSize, LabelAssembler.toDTOs(pages.getData()));
    }


    public Page<LabelPlanDTO> findInsideLabelsByLabel(Long id, int currentPage, int pageSize) {
        List<LabelPlanDTO> result = new ArrayList<LabelPlanDTO>();
        String jpql = "select e from Label o right join o.insideLabels e where o.id=?";
        Page<LabelPlan> pages = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{id}).setPage(currentPage, pageSize).pagedList();
        for (LabelPlan entity : pages.getData()) {
            LabelPlanDTO dto = new LabelPlanDTO();
            try {
                BeanUtils.copyProperties(dto, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.add(dto);
        }
        return new Page<LabelPlanDTO>(Page.getStartOfPage(currentPage, pageSize), pages.getResultCount(), pageSize, result);
    }

    public Page<LabelPlanDTO> findOutSideLabelsByLabel(Long id, int currentPage, int pageSize) {
        List<LabelPlanDTO> result = new ArrayList<LabelPlanDTO>();
        String jpql = "select e from Label o right join o.outSideLabels e where o.id=?";
        Page<LabelPlan> pages = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{id}).setPage(currentPage, pageSize).pagedList();
        for (LabelPlan entity : pages.getData()) {
            LabelPlanDTO dto = new LabelPlanDTO();
            try {
                BeanUtils.copyProperties(dto, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.add(dto);
        }
        return new Page<LabelPlanDTO>(Page.getStartOfPage(currentPage, pageSize), pages.getResultCount(), pageSize, result);
    }

    public Page<LabelPlanDTO> findReelLabelsByLabel(Long id, int currentPage, int pageSize) {
        List<LabelPlanDTO> result = new ArrayList<LabelPlanDTO>();
        String jpql = "select e from Label o right join o.reelLabels e where o.id=?";
        Page<LabelPlan> pages = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{id}).setPage(currentPage, pageSize).pagedList();
        for (LabelPlan entity : pages.getData()) {
            LabelPlanDTO dto = new LabelPlanDTO();
            try {
                BeanUtils.copyProperties(dto, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.add(dto);
        }
        return new Page<LabelPlanDTO>(Page.getStartOfPage(currentPage, pageSize), pages.getResultCount(), pageSize, result);
    }


    @Override
    public List<LabelDTO> findFTLabelsByPackageType(String packageType) {
        List<Label> list = application.findFTLabelsByPackageType(packageType);
        return LabelAssembler.toDTOs(list);
    }

    @Override
    public List<LabelDTO> findLabelsByInternalProductId(Long id) {
        try {
            List<Label> labels = application.findLabelsByInternalProductId(id);
            return LabelAssembler.toDTOs(labels);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }


    @Override
    public List<LabelDTO> getLabelByType(LabelDTO labelDTO) {
        List<LabelDTO> result = new ArrayList<LabelDTO>();
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _label from Label _label  where 1=1");
        if (labelDTO.getTestType() != null && (!labelDTO.getTestType().equals(""))) {
            jpql.append(" and _label.testType like ? ");
            conditionVals.add(MessageFormat.format("%{0}%", labelDTO.getTestType()));
        }
        if (labelDTO.getPackageType() != null && (!labelDTO.getPackageType().equals(""))) {
            jpql.append(" and _label.packageType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", labelDTO.getPackageType()));
        }
        if (labelDTO.getLabelType() != null && (!labelDTO.getLabelType().equals(""))) {
            jpql.append(" and _label.labelType like ? ");
            conditionVals.add(MessageFormat.format("%{0}%", labelDTO.getLabelType()));
        }
        Page<Label> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(0, 10000)
                .pagedList();

        for (Label entity : pages.getData()) {
            LabelDTO dto = new LabelDTO();
            result.add(LabelAssembler.toDTO(entity));
        }
        return result;
    }

}
