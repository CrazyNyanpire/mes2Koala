package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.FTInfoApplication;
import org.seu.acetec.mes2Koala.application.LabelApplication;
import org.seu.acetec.mes2Koala.application.ProcessTemplateApplication;
import org.seu.acetec.mes2Koala.application.FTRuncardTemplateApplication;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.facade.FTInfoFacade;
import org.seu.acetec.mes2Koala.facade.dto.EQCSettingDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTInfoDTO;
import org.seu.acetec.mes2Koala.facade.dto.SBLTemplateDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.FTInfoPageVo;
import org.seu.acetec.mes2Koala.facade.impl.assembler.EQCSettingAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTInfoAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.SBLTemplateAssembler;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class FTInfoFacadeImpl implements FTInfoFacade {

    @Inject
    ProcessTemplateApplication processTemplateApplication;
    @Inject
    private FTInfoApplication application;
    @Inject
    private LabelApplication labelApplication;
    @Inject
    private FTRuncardTemplateApplication ftRuncardTemplateApplication;
    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    @Transactional
    public InvokeResult getFTInfo(Long id) {
        return InvokeResult.success(FTInfoAssembler.toDTO(application.get(id)));
    }
    
    @Transactional
    public InvokeResult getFTInfoPageVo( Long id ) {
    	return InvokeResult.success(FTInfoAssembler.toPageVo(application.get(id)));
    }

    public InvokeResult creatFTInfo(FTInfoDTO fTInfoDTO) {
        application.create(FTInfoAssembler.toEntity(fTInfoDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateFTInfo(FTInfoDTO fTInfoDTO) {
        FTInfo ftInfo1 = FTInfoAssembler.toEntity(fTInfoDTO);
        FTInfo ftInfo = application.get(fTInfoDTO.getId());
        ftInfo1.setProcessTemplate(ftInfo.getProcessTemplate()); // 保持process不变
        ftInfo1.setLabels(ftInfo.getLabels()); // 保持labels不变
        application.update(ftInfo1);
        return InvokeResult.success();
    }

    public InvokeResult removeFTInfo(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeFTInfos(Long[] ids) {
        Set<FTInfo> fTInfos = new HashSet<FTInfo>();
        for (Long id : ids) {
            fTInfos.add(application.get(id));
        }
        application.removeAll(fTInfos);
        return InvokeResult.success();
    }

    public List<FTInfoDTO> findAllFTInfo() {
        return FTInfoAssembler.toDTOs(application.findAll());
    }

    @Transactional
    public Page<FTInfoPageVo> pageQueryFTInfo(FTInfoDTO queryVo, int currentPage, int pageSize, String sortname, String sortorder) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _fTInfo from FTInfo _fTInfo  where 1=1 ");
        if (queryVo.getCustomerProductNumber() != null && !"".equals(queryVo.getCustomerProductNumber())) {
            jpql.append(" and _fTInfo.customerProductNumber like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerProductNumber()));
        }
        if (queryVo.getCustomerProductRevision() != null && !"".equals(queryVo.getCustomerProductRevision())) {
            jpql.append(" and _fTInfo.customerProductRevision like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerProductRevision()));
        }
        if (queryVo.getInternalProductNumber() != null && !"".equals(queryVo.getInternalProductNumber())) {
            jpql.append(" and _fTInfo.internalProductNumber like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getInternalProductNumber()));
        }
        if (queryVo.getInternalProductRevision() != null && !"".equals(queryVo.getInternalProductRevision())) {
            jpql.append(" and _fTInfo.internalProductRevision like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getInternalProductRevision()));
        }
        if (queryVo.getPackageType() != null && !"".equals(queryVo.getPackageType())) {
            jpql.append(" and _fTInfo.packageType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPackageType()));
        }
        if (queryVo.getShipmentProductNumber() != null && !"".equals(queryVo.getShipmentProductNumber())) {
            jpql.append(" and _fTInfo.shipmentProductNumber like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getShipmentProductNumber()));
        }
        if (queryVo.getH_P_1() != null && !"".equals(queryVo.getH_P_1())) {
            jpql.append(" and _fTInfo.H_P_1 like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getH_P_1()));
        }
        if (queryVo.getH_P_2() != null && !"".equals(queryVo.getH_P_2())) {
            jpql.append(" and _fTInfo.H_P_2 like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getH_P_2()));
        }
        if (queryVo.getH_P_3() != null && !"".equals(queryVo.getH_P_3())) {
            jpql.append(" and _fTInfo.H_P_3 like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getH_P_3()));
        }
        if (queryVo.getNote() != null && !"".equals(queryVo.getNote())) {
            jpql.append(" and _fTInfo.note like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNote()));
        }
        if (queryVo.getPackingFactory() != null && !"".equals(queryVo.getPackingFactory())) {
            jpql.append(" and _fTInfo.packingFactory like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPackingFactory()));
        }
        if (queryVo.getPackingType() != null && !"".equals(queryVo.getPackingType())) {
            jpql.append(" and _fTInfo.packingType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPackingType()));
        }
        if (queryVo.getSize() != null && !"".equals(queryVo.getSize())) {
            jpql.append(" and _fTInfo.size like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSize()));
        }
        if (queryVo.getTestSys1() != null && !"".equals(queryVo.getTestSys1())) {
            jpql.append(" and _fTInfo.TestSys1 like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTestSys1()));
        }
        if (queryVo.getTestSys2() != null && !"".equals(queryVo.getTestSys2())) {
            jpql.append(" and _fTInfo.TestSys2 like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTestSys2()));
        }
        if (queryVo.getTestSys3() != null && !"".equals(queryVo.getTestSys3())) {
            jpql.append(" and _fTInfo.TestSys3 like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getTestSys3()));
        }
        if (queryVo.getWaferFactory() != null && !"".equals(queryVo.getWaferFactory())) {
            jpql.append(" and _fTInfo.waferFactory like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getWaferFactory()));
        }
        if (queryVo.getCustomerDirectDTO() != null && queryVo.getCustomerDirectDTO().getNumber() != null && !"".equals(queryVo.getCustomerDirectDTO().getNumber())) {
            jpql.append(" and _fTInfo.customerDirect.number like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerDirectDTO().getNumber()));
        }

        if (sortname != null && sortorder != null) {
            if (sortname.startsWith("customer") && sortname.endsWith("Name")) {
                jpql.append(" order by _fTInfo." + sortname.substring(0, sortname.length() - 4) + ".chineseName" + " " + sortorder);
            } else if (sortname.startsWith("key") || sortname.startsWith("assist")) {
                jpql.append(" order by _fTInfo." + sortname.substring(0, sortname.length() - 4) + ".name" + " " + sortorder);
            } else if (!"".equals(sortname) && !"".equals(sortname)) {
                jpql.append(" order by _fTInfo." + sortname + " " + sortorder);
            }
        }

        Page<FTInfo> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        List<FTInfoPageVo> ftList = new ArrayList<FTInfoPageVo>();
        for(FTInfoPageVo ftInfoPageVo : FTInfoAssembler.toPageVos(pages.getData())){
        	ftInfoPageVo.setRuncardApproval(this.ftRuncardTemplateApplication.isRuncardSignedMsg(ftInfoPageVo.getId()));
        	ftList.add(ftInfoPageVo);
        }
        return new Page<FTInfoPageVo>(pages.getStart(), pages.getResultCount(), pageSize, ftList);
    }

    @Override
    public List<SBLTemplateDTO> getSBLTemplatesByProduct(Long id) {
        List<SBLTemplateDTO> result = new ArrayList<SBLTemplateDTO>();
        String jpql = "select o from SBLTemplate o right join o.internalProduct e where e.id=?";
        Page<SBLTemplate> pages = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{id}).setPage(0, 200).pagedList();
        for (SBLTemplate entity : pages.getData()) {
            SBLTemplateDTO dto = new SBLTemplateDTO();
            result.add(SBLTemplateAssembler.toDTO(entity));
        }
        return result;
    }

    @Override
    public List<EQCSettingDTO> getEQCSettingsByProduct(Long id) {
        List<EQCSettingDTO> result = new ArrayList<EQCSettingDTO>();
        String jpql = "select o from EQCSetting o right join o.internalProduct e where e.id=?";
        Page<EQCSetting> pages = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{id}).setPage(0, 200).pagedList();
        for (EQCSetting entity : pages.getData()) {
            EQCSettingDTO dto = new EQCSettingDTO();
            result.add(EQCSettingAssembler.toDTO(entity));
        }
        return result;
    }

    @Override
    public InvokeResult bindLabel(Long ftInfoId, Long labelId) {
        FTInfo ftInfo = application.get(ftInfoId);
        List<Label> labels = ftInfo.getLabels();
        Label label = labelApplication.get(labelId);
        labels.add(label);
        application.update(ftInfo);  // 级联更新Label
        return InvokeResult.success();
    }

    @Override
    public InvokeResult bindLabels(Long id, Long[] labelIds) {
        FTInfo ftInfo = application.get(id);
        List<Label> labels = ftInfo.getLabels();
        for (Long labelId : labelIds) {
            labels.add(labelApplication.get(labelId));
        }
        ftInfo.setLabels(labels);
        application.update(ftInfo);
        return InvokeResult.success();
    }

    @Override
    public InvokeResult clearLabel(Long ftInfoId) {
        FTInfo ftInfo = application.get(ftInfoId);
        ftInfo.setLabels(null);
        application.update(ftInfo);  // 级联更新Label
        return InvokeResult.success();
    }

    /**
     * modified by lcn
     * 重新绑定process时，清除runcard
     *
     * @param id
     * @param processId
     * @return
     */
    @Override
    public InvokeResult bindProcess(Long id, Long processId) {
        FTInfo ftInfo = application.get(id);
        ProcessTemplate processTemplate = processTemplateApplication.get(processId);
        ftInfo.setProcessTemplate(processTemplate);

        //将runcardtemplace删除
        FTRuncardTemplate FTRuncardTemplate = ftRuncardTemplateApplication.findByInternalProductId(id);
        if (FTRuncardTemplate != null) {
        	ftRuncardTemplateApplication.remove(FTRuncardTemplate);
        }


        application.update(ftInfo);
        return InvokeResult.success();
    }

    @Override
    public InvokeResult clearProcess(Long id) {
        FTInfoDTO fTInfoDTO = new FTInfoDTO();
        FTInfo fi = application.get(id);
        fi.setProcessTemplate(null);
        fTInfoDTO = FTInfoAssembler.toDTO(fi);

        application.update(FTInfoAssembler.toEntity(fTInfoDTO));
        return InvokeResult.success();
    }
}
