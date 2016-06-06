package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.FTReworkApplication;
import org.seu.acetec.mes2Koala.application.IncrementNumberApplication;
import org.seu.acetec.mes2Koala.core.common.BeanUtils;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.FTRework;
import org.seu.acetec.mes2Koala.core.domain.FTReworkItem;
import org.seu.acetec.mes2Koala.facade.FTReworkFacade;
import org.seu.acetec.mes2Koala.facade.FTReworkItemFacade;
import org.seu.acetec.mes2Koala.facade.dto.FTReworkDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTReworkItemDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTReworkAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class FTReworkFacadeImpl implements FTReworkFacade {

    @Inject
    private FTReworkApplication application;

    @Inject
    private FTReworkItemFacade fTReworkItemFacade;

    @Inject
    private FTLotApplication ftLotapplication;

    @Inject
    private IncrementNumberApplication incrementNumberApplication;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(
                    QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getFTRework(Long id) {
        return InvokeResult.success(FTReworkAssembler.toDTO(application
                .get(id)));
    }

    public InvokeResult creatFTRework(FTReworkDTO fTReworkDTO) {
        FTLot internalLot = ftLotapplication.get(fTReworkDTO.getLotId());

        // String reworkNo =
        // this.createReworkNo(ftLot.getFtLot().getCustomerFTLot().getCustomerNumber());
        // fTReworkDTO.setReworkNo(reworkNo);
        Set<FTReworkItem> set = new HashSet<FTReworkItem>();
        // fTReworkDTO.setFtReworkItems(list);
        FTRework ftRework = new FTRework();
        ftRework = FTReworkAssembler.toEntity(fTReworkDTO);
        ftRework.setReworkLot(internalLot);
        ftRework.setLotNo(internalLot.getInternalLotNumber());
        for (FTReworkItemDTO fTReworkItemDTO : fTReworkDTO.getReworkItems()) {
            FTReworkItem fTReworkItem = new FTReworkItem();
            BeanUtils.copyProperties(fTReworkItemDTO, fTReworkItem);
            fTReworkItem.setCreateEmployNo(fTReworkDTO.getCreateEmployNo());
            fTReworkItem.setCreateTimestamp(fTReworkDTO.getCreateTimestamp());
            fTReworkItem.setLastModifyEmployNo(fTReworkDTO
                    .getLastModifyEmployNo());
            fTReworkItem.setLastModifyTimestamp(fTReworkDTO
                    .getLastModifyTimestamp());
            fTReworkItem.setFtRework(ftRework);
            set.add(fTReworkItem);
        }
        ftRework.setReworkItems(set);
        application.create(ftRework);
        return InvokeResult.success();
    }

    public InvokeResult updateFTRework(FTReworkDTO fTReworkDTO) {
        application.update(FTReworkAssembler.toEntity(fTReworkDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeFTRework(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeFTReworks(Long[] ids) {
        Set<FTRework> fTReworks = new HashSet<FTRework>();
        for (Long id : ids) {
            fTReworks.add(application.get(id));
        }
        application.removeAll(fTReworks);
        return InvokeResult.success();
    }

    public List<FTReworkDTO> findAllFTRework() {
        return FTReworkAssembler.toDTOs(application.findAll());
    }

    public Page<FTReworkDTO> pageQueryFTRework(FTReworkDTO queryVo,
                                               int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder(
                "select _fTRework from FTRework _fTRework   where 1=1 ");
        if (queryVo.getCreateTimestamp() != null) {
            jpql.append(" and _fTRework.createTimestamp between ? and ? ");
            conditionVals.add(queryVo.getCreateTimestamp());
            conditionVals.add(queryVo.getCreateTimestampEnd());
        }
        if (queryVo.getLastModifyTimestamp() != null) {
            jpql.append(" and _fTRework.lastModifyTimestamp between ? and ? ");
            conditionVals.add(queryVo.getLastModifyTimestamp());
            conditionVals.add(queryVo.getLastModifyTimestampEnd());
        }
        if (queryVo.getCreateEmployNo() != null
                && !"".equals(queryVo.getCreateEmployNo())) {
            jpql.append(" and _fTRework.createEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getCreateEmployNo()));
        }
        if (queryVo.getLastModifyEmployNo() != null
                && !"".equals(queryVo.getLastModifyEmployNo())) {
            jpql.append(" and _fTRework.lastModifyEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getLastModifyEmployNo()));
        }
        if (queryVo.getReworkRCNo() != null
                && !"".equals(queryVo.getReworkRCNo())) {
            jpql.append(" and _fTRework.reworkRCNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getReworkRCNo()));
        }
        if (queryVo.getReworkCustomer() != null
                && !"".equals(queryVo.getReworkCustomer())) {
            jpql.append(" and _fTRework.reworkCustomer like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getReworkCustomer()));
        }
        if (queryVo.getReworkDate() != null) {
            jpql.append(" and _fTRework.reworkDate between ? and ? ");
            conditionVals.add(queryVo.getReworkDate());
            conditionVals.add(queryVo.getReworkDateEnd());
        }
        if (queryVo.getReworkDepartment() != null
                && !"".equals(queryVo.getReworkDepartment())) {
            jpql.append(" and _fTRework.reworkDepartment like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getReworkDepartment()));
        }
        if (queryVo.getReworkEquipment() != null
                && !"".equals(queryVo.getReworkEquipment())) {
            jpql.append(" and _fTRework.reworkEquipment like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getReworkEquipment()));
        }
        if (queryVo.getReworkNo() != null && !"".equals(queryVo.getReworkNo())) {
            jpql.append(" and _fTRework.reworkNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getReworkNo()));
        }
        if (queryVo.getReasonExplanation() != null
                && !"".equals(queryVo.getReasonExplanation())) {
            jpql.append(" and _fTRework.reasonExplanation like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getReasonExplanation()));
        }
        if (queryVo.getReasonOther() != null
                && !"".equals(queryVo.getReasonOther())) {
            jpql.append(" and _fTRework.reasonOther like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getReasonOther()));
        }
        if (queryVo.getReasonReasons() != null
                && !"".equals(queryVo.getReasonReasons())) {
            jpql.append(" and _fTRework.reasonReasons like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getReasonReasons()));
        }
        if (queryVo.getSummary() != null && !"".equals(queryVo.getSummary())) {
            jpql.append(" and _fTRework.summary like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getSummary()));
        }
        Page<FTRework> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString()).setParameters(conditionVals)
                .setPage(currentPage, pageSize).pagedList();

        return new Page<FTReworkDTO>(pages.getStart(), pages.getResultCount(),
                pageSize, FTReworkAssembler.toDTOs(pages.getData()));
    }

    @Override
    public Page<FTReworkItemDTO> findReworkItemsByFTRework(Long id,
                                                           int currentPage, int pageSize) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public InvokeResult approveFTRework(FTReworkDTO fTReworkDTO) {
        FTRework fTRework = application.get(fTReworkDTO.getId());
        fTRework.setApprove(fTReworkDTO.getApprove());
        fTRework.setApprovePerson(fTReworkDTO.getLastModifyEmployNo());
        fTRework.setApproveDate(fTRework.getLastModifyTimestamp());
        fTRework.setLastModifyEmployNo(fTReworkDTO.getLastModifyEmployNo());
        fTRework.setLastModifyTimestamp(fTRework.getLastModifyTimestamp());
        application.update(fTRework);
        return InvokeResult.success();
    }

    @Override
    public InvokeResult createReworkNo(Long id) {
        return InvokeResult.success(incrementNumberApplication.nextReworkNumber(ftLotapplication.get(id)));
    }

}
