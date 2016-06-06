package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.FTQDNApplication;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.FTQDN;
import org.seu.acetec.mes2Koala.facade.FTQDNFacade;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTQDNDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTLotAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTQDNAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class FTQDNFacadeImpl implements FTQDNFacade {

    @Inject
    FTLotApplication ftLotApplication;
    @Inject
    private FTQDNApplication application;
    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(
                    QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getFTQDN(Long id) {
        return InvokeResult.success(FTQDNAssembler.toDTO(application.get(id)));
    }

    public InvokeResult creatFTQDN(FTQDNDTO fTQDNDTO) {
        application.create(FTQDNAssembler.toEntity(fTQDNDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateFTQDN(FTQDNDTO fTQDNDTO) {
        application.update(FTQDNAssembler.toEntity(fTQDNDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeFTQDN(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeFTQDNs(Long[] ids) {
        Set<FTQDN> fTQDNs = new HashSet<FTQDN>();
        for (Long id : ids) {
            fTQDNs.add(application.get(id));
        }
        application.removeAll(fTQDNs);
        return InvokeResult.success();
    }

    public List<FTQDNDTO> findAllFTQDN() {
        return FTQDNAssembler.toDTOs(application.findAll());
    }

    public Page<FTQDNDTO> pageQueryFTQDN(FTQDNDTO queryVo, int currentPage,
                                         int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder(
                "select _fTQDN from FTQDN _fTQDN   where 1=1 ");
        if (queryVo.getCreateTimestamp() != null) {
            jpql.append(" and _fTQDN.createTimestamp between ? and ? ");
            conditionVals.add(queryVo.getCreateTimestamp());
            conditionVals.add(queryVo.getCreateTimestampEnd());
        }
        if (queryVo.getLastModifyTimestamp() != null) {
            jpql.append(" and _fTQDN.lastModifyTimestamp between ? and ? ");
            conditionVals.add(queryVo.getLastModifyTimestamp());
            conditionVals.add(queryVo.getLastModifyTimestampEnd());
        }
        if (queryVo.getCreateEmployNo() != null
                && !"".equals(queryVo.getCreateEmployNo())) {
            jpql.append(" and _fTQDN.createEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getCreateEmployNo()));
        }
        if (queryVo.getLastModifyEmployNo() != null
                && !"".equals(queryVo.getLastModifyEmployNo())) {
            jpql.append(" and _fTQDN.lastModifyEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getLastModifyEmployNo()));
        }
        if (queryVo.getCustomerNote() != null
                && !"".equals(queryVo.getCustomerNote())) {
            jpql.append(" and _fTQDN.customerNote like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getCustomerNote()));
        }
        if (queryVo.getCustomerSuggestion() != null
                && !"".equals(queryVo.getCustomerSuggestion())) {
            jpql.append(" and _fTQDN.customerSuggestion like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getCustomerSuggestion()));
        }
        if (queryVo.getMethod() != null && !"".equals(queryVo.getMethod())) {
            jpql.append(" and _fTQDN.method like ?");
            conditionVals
                    .add(MessageFormat.format("%{0}%", queryVo.getMethod()));
        }
        if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
            jpql.append(" and _fTQDN.ftLot.internalLotNumber like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLotNo()));
        }
        if (queryVo.getNote() != null && !"".equals(queryVo.getNote())) {
            jpql.append(" and _fTQDN.note like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNote()));
        }
        if (queryVo.getQaSuggestion() != null
                && !"".equals(queryVo.getQaSuggestion())) {
            jpql.append(" and _fTQDN.QASuggestion like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getQaSuggestion()));
        }
        if (queryVo.getReason() != null && !"".equals(queryVo.getReason())) {
            jpql.append(" and _fTQDN.reason like ?");
            conditionVals
                    .add(MessageFormat.format("%{0}%", queryVo.getReason()));
        }
        if (queryVo.getSuggestion() != null
                && !"".equals(queryVo.getSuggestion())) {
            jpql.append(" and _fTQDN.suggestion like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getSuggestion()));
        }
        if (queryVo.getAttachment() != null
                && !"".equals(queryVo.getAttachment())) {
            jpql.append(" and _fTQDN.attachment like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getAttachment()));
        }
        if (queryVo.getQdnNo() != null && !"".equals(queryVo.getQdnNo())) {
            jpql.append(" and _fTQDN.qdnNo like ?");
            conditionVals
                    .add(MessageFormat.format("%{0}%", queryVo.getQdnNo()));
        }
        if (queryVo.getFlowNow() != null && !"".equals(queryVo.getFlowNow())) {
            jpql.append(" and _fTQDN.flowNow like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getFlowNow()));
        }
        if (queryVo.getCustomerDeal() != null
                && !"".equals(queryVo.getCustomerDeal())) {
            jpql.append(" and _fTQDN.customerDeal like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getCustomerDeal()));
        }
        if (queryVo.getCustomerDealNote() != null
                && !"".equals(queryVo.getCustomerDealNote())) {
            jpql.append(" and _fTQDN.customerDealNote like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getCustomerDealNote()));
        }
        if (queryVo.getDate() != null) {
            jpql.append(" and _fTQDN.date between ? and ? ");
            conditionVals.add(queryVo.getDate());
            conditionVals.add(queryVo.getDateEnd());
        }
        if (queryVo.getDealDate() != null) {
            jpql.append(" and _fTQDN.dealDate between ? and ? ");
            conditionVals.add(queryVo.getDealDate());
            conditionVals.add(queryVo.getDealDateEnd());
        }
        if (queryVo.getDealPerson() != null
                && !"".equals(queryVo.getDealPerson())) {
            jpql.append(" and _fTQDN.dealPerson like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getDealPerson()));
        }
        if (queryVo.getNote() != null && !"".equals(queryVo.getNote())) {
            jpql.append(" and _fTQDN.note like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNote()));
        }
        if (queryVo.getQADeal() != null && !"".equals(queryVo.getQADeal())) {
            jpql.append(" and _fTQDN.QADeal like ?");
            conditionVals
                    .add(MessageFormat.format("%{0}%", queryVo.getQADeal()));
        }
        if (queryVo.getTesterSys() != null
                && !"".equals(queryVo.getTesterSys())) {
            jpql.append(" and _fTQDN.testerSys like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getTesterSys()));
        }
        if (queryVo.getToPerson() != null && !"".equals(queryVo.getToPerson())) {
            jpql.append(" and _fTQDN.toPerson like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getToPerson()));
        }
        if (queryVo.getType() != null && !"".equals(queryVo.getType())) {
            jpql.append(" and _fTQDN.type like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getType()));
        }
        if (queryVo.getWorkPerson() != null
                && !"".equals(queryVo.getWorkPerson())) {
            jpql.append(" and _fTQDN.workPerson like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getWorkPerson()));
        }
        if (queryVo.getFtLotDTO() != null) {
            if (queryVo.getFtLotDTO().getShipmentProductNumber() != null && !"".equals(queryVo.getFtLotDTO().getShipmentProductNumber())) {
                jpql.append(" and _fTQDN.ftLot.shipmentProductNumber like ?");
                conditionVals.add(MessageFormat.format("%{0}%",queryVo.getFtLotDTO().getShipmentProductNumber()));
            }
        }
        Page<FTQDN> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString()).setParameters(conditionVals)
                .setPage(currentPage, pageSize).pagedList();

        return new Page<FTQDNDTO>(pages.getStart(), pages.getResultCount(),
                pageSize, FTQDNAssembler.toDTOs(pages.getData()));
    }


    @Override
    public FTLotDTO findFtLotByFTQDN(Long id) {
        return FTLotAssembler.toDTO(ftLotApplication.findByFTQDNId(id));
    }

    @Override
    public InvokeResult disposeFTQDN(FTQDNDTO fTQDNDTO) {
        FTQDN fTQDN = application.get(fTQDNDTO.getId());
        BeanUtils.copyProperties(fTQDNDTO, fTQDN);
        fTQDN.setQASuggestion(fTQDNDTO.getQaSuggestion());
        fTQDN.setStatus(fTQDN.getStatus() + 1);
        application.update(fTQDN);
        return InvokeResult.success();
    }

    @Override
    public List<FTQDNDTO> findAllDoingFTQDNByLotId(Long lotId) {
        List<FTQDN> list = application.findAllDoingByFTLotId(lotId);
        return FTQDNAssembler.toDTOs(list);
    }

}
