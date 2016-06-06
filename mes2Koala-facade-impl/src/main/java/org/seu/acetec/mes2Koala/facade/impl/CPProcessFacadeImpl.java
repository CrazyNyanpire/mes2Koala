package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.CPNodeApplication;
import org.seu.acetec.mes2Koala.application.CPProcessApplication;
import org.seu.acetec.mes2Koala.core.domain.CPNode;
import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import org.seu.acetec.mes2Koala.facade.CPProcessFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPProcessDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPNodeAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CPProcessAssembler;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
@Named
@Transactional
public class CPProcessFacadeImpl implements CPProcessFacade {

    @Inject
    CPProcessApplication cpProcessApplication;

    @Inject
    CPNodeApplication cpNodeApplication;

    QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    @Override
    public InvokeResult getCPProcess(Long id) {
        return InvokeResult.success(CPProcessAssembler.toDTO(cpProcessApplication.get(id)));
    }

    @Override
    public Page<CPProcessDTO> pageQueryCPProcess(CPProcessDTO cpProcess, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _process from CPProcess _process  where 1=1 ");
        if (cpProcess.getName() != null && !"".equals(cpProcess.getName())) {
            jpql.append(" and _process.nodeName like ?");
            conditionVals.add(MessageFormat.format("%{0}%", cpProcess.getName()));
        }
        if (cpProcess.getContent() != null && !"".equals(cpProcess.getContent())) {
            jpql.append(" and _process.content like ?");
            conditionVals.add(MessageFormat.format("%{0}%", cpProcess.getContent()));
        }
        if (cpProcess.getTestType() != null && !"".equals(cpProcess.getTestType())) {
            jpql.append(" and _process.testType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", cpProcess.getTestType()));
        }
        if (cpProcess.getProcessState() != null && !"".equals(cpProcess.getProcessState())) {
            jpql.append(" and _process.processState like ?");
            conditionVals.add(MessageFormat.format("%{0}%", cpProcess.getProcessState()));
        }
        if (cpProcess.getRunCard() != null && !"".equals(cpProcess.getRunCard())) {
            jpql.append(" and _process.runCard like ?");
            conditionVals.add(MessageFormat.format("%{0}%", cpProcess.getRunCard()));
        }
        Page<CPProcess> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        return new Page<CPProcessDTO>(pages.getStart(), pages.getResultCount(), pageSize, CPProcessAssembler.toDTOs(pages.getData()));
    }

    @Override
    public Page<CPNodeDTO> pageQueryCPNodesByCPProcessId(Long cpProcessId, int currentPage, int pageSize) {
        Page<CPNode> pages = cpNodeApplication.pageQueryCPNodesByCPProcessId(cpProcessId, currentPage, pageSize);
        return new Page<CPNodeDTO>(pages.getStart(), pages.getResultCount(), pageSize, CPNodeAssembler.toDTOs(pages.getData()));
    }

    @Override
    public InvokeResult findCPProcessByCPLotId(Long id) {
        try {
            return InvokeResult.success(CPProcessAssembler.toDTO(cpProcessApplication.findByCPLotId(id)));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return InvokeResult.failure(ex.getMessage());
        }
    }
}
