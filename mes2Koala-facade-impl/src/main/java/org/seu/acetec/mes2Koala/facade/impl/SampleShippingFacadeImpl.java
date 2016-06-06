package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.FTLotApplication;
import org.seu.acetec.mes2Koala.application.SampleShippingApplication;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.SampleShipping;
import org.seu.acetec.mes2Koala.facade.SampleShippingFacade;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskDTO;
import org.seu.acetec.mes2Koala.facade.dto.SampleShippingDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.SampleShippingAssembler;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
public class SampleShippingFacadeImpl implements SampleShippingFacade {

    @Inject
    private SampleShippingApplication application;

    @Inject
    private FTLotApplication fTLotApplication;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(
                    QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getSampleShipping(Long id) {
        return InvokeResult.success(SampleShippingAssembler.toDTO(application
                .get(id)));
    }

    public InvokeResult creatSampleShipping(SampleShippingDTO sampleShippingDTO) {
        application.create(SampleShippingAssembler
                .toEntity(sampleShippingDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateSampleShipping(SampleShippingDTO sampleShippingDTO) {
        application.update(SampleShippingAssembler
                .toEntity(sampleShippingDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeSampleShipping(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeSampleShippings(Long[] ids) {
        Set<SampleShipping> sampleShippings = new HashSet<SampleShipping>();
        for (Long id : ids) {
            sampleShippings.add(application.get(id));
        }
        application.removeAll(sampleShippings);
        return InvokeResult.success();
    }

    public List<SampleShippingDTO> findAllSampleShipping() {
        return SampleShippingAssembler.toDTOs(application
                .findAll());
    }

    public Page<SampleShippingDTO> pageQuerySampleShipping(
            SampleShippingDTO queryVo, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder(
                "select _sampleShipping from SampleShipping _sampleShipping   where 1=1 ");
        if (queryVo.getCreateTimestamp() != null) {
            jpql.append(" and _sampleShipping.createTimestamp between ? and ? ");
            conditionVals.add(queryVo.getCreateTimestamp());
            conditionVals.add(queryVo.getCreateTimestampEnd());
        }
        if (queryVo.getLastModifyTimestamp() != null) {
            jpql.append(" and _sampleShipping.lastModifyTimestamp between ? and ? ");
            conditionVals.add(queryVo.getLastModifyTimestamp());
            conditionVals.add(queryVo.getLastModifyTimestampEnd());
        }
        if (queryVo.getCreateEmployNo() != null
                && !"".equals(queryVo.getCreateEmployNo())) {
            jpql.append(" and _sampleShipping.createEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getCreateEmployNo()));
        }
        if (queryVo.getLastModifyEmployNo() != null
                && !"".equals(queryVo.getLastModifyEmployNo())) {
            jpql.append(" and _sampleShipping.lastModifyEmployNo like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getLastModifyEmployNo()));
        }
        if (queryVo.getQty() != null) {
            jpql.append(" and _sampleShipping.qty=?");
            conditionVals.add(queryVo.getQty());
        }
        if (queryVo.getQtyTotal() != null) {
            jpql.append(" and _sampleShipping.qtyTotal=?");
            conditionVals.add(queryVo.getQtyTotal());
        }
        if (queryVo.getQuality() != null && !"".equals(queryVo.getQuality())) {
            jpql.append(" and _sampleShipping.quality like ?");
            conditionVals.add(MessageFormat.format("%{0}%",
                    queryVo.getQuality()));
        }
        if (queryVo.getNote() != null && !"".equals(queryVo.getNote())) {
            jpql.append(" and _sampleShipping.note like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNote()));
        }
        Page<SampleShipping> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString()).setParameters(conditionVals)
                .setPage(currentPage, pageSize).pagedList();

        return new Page<SampleShippingDTO>(pages.getStart(),
                pages.getResultCount(), pageSize,
                SampleShippingAssembler.toDTOs(pages.getData()));
    }

    @Override
    public FTLotDTO findFtLotBySampleShipping(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReelDiskDTO findReelDiskBySampleShipping(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InvokeResult findQtyByQuality(SampleShippingDTO sampleShipping) {
        FTLot ftLot = fTLotApplication.get(sampleShipping.getFtLotId());
        if (sampleShipping.getQuality().equalsIgnoreCase("LAT")) {
            return InvokeResult.success(ftLot.getLat());
        } else if (sampleShipping.getQuality().equalsIgnoreCase("PASS")) {
            return InvokeResult.success(ftLot.getPass());
        } else if (sampleShipping.getQuality().equalsIgnoreCase("FAIL")) {
            return InvokeResult.success(ftLot.getFail());
        } else {
            return InvokeResult.success(ftLot.getQty());
        }
    }
}
