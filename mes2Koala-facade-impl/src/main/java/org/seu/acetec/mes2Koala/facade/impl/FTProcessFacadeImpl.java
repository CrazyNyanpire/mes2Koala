package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.FTInfoApplication;
import org.seu.acetec.mes2Koala.application.FTNodeApplication;
import org.seu.acetec.mes2Koala.application.FTProcessApplication;
import org.seu.acetec.mes2Koala.core.domain.FTInfo;
import org.seu.acetec.mes2Koala.core.domain.FTNode;
import org.seu.acetec.mes2Koala.core.domain.FTProcess;
import org.seu.acetec.mes2Koala.core.domain.ProcessTemplate;
import org.seu.acetec.mes2Koala.facade.FTProcessFacade;
import org.seu.acetec.mes2Koala.facade.dto.FTNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTProcessDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTNodeAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.FTProcessAssembler;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Named
@Transactional
public class FTProcessFacadeImpl implements FTProcessFacade {


    @Inject
    FTProcessApplication ftProcessApplication;

    @Inject
    FTInfoApplication ftInfoApplication;

    @Inject
    FTNodeApplication ftNodeApplication;

    QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    @Override
    public InvokeResult getFTProcess(Long id) {
        return InvokeResult.success(FTProcessAssembler.toDTO(ftProcessApplication.get(id)));
    }

    @Override
    public Page<FTProcessDTO> pageQueryFTProcess(FTProcessDTO ftProcess, int currentPage, int pageSize) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _process from FTProcess _process   where 1=1 ");
        if (ftProcess.getName() != null && !"".equals(ftProcess.getName())) {
            jpql.append(" and _process.nodeName like ?");
            conditionVals.add(MessageFormat.format("%{0}%", ftProcess.getName()));
        }
        if (ftProcess.getContent() != null && !"".equals(ftProcess.getContent())) {
            jpql.append(" and _process.content like ?");
            conditionVals.add(MessageFormat.format("%{0}%", ftProcess.getContent()));
        }
        if (ftProcess.getTestType() != null && !"".equals(ftProcess.getTestType())) {
            jpql.append(" and _process.testType like ?");
            conditionVals.add(MessageFormat.format("%{0}%", ftProcess.getTestType()));
        }
        if (ftProcess.getProcessState() != null && !"".equals(ftProcess.getProcessState())) {
            jpql.append(" and _process.processState like ?");
            conditionVals.add(MessageFormat.format("%{0}%", ftProcess.getProcessState()));
        }
        if (ftProcess.getRunCard() != null && !"".equals(ftProcess.getRunCard())) {
            jpql.append(" and _process.runCard like ?");
            conditionVals.add(MessageFormat.format("%{0}%", ftProcess.getRunCard()));
        }
        Page<FTProcess> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        return new Page<FTProcessDTO>(pages.getStart(), pages.getResultCount(), pageSize, FTProcessAssembler.toDTOs(pages.getData()));
    }

    @Override
    public Page<FTNodeDTO> pageQueryFTNodesByFTProcessId(Long ftProcessId, int currentPage, int pageSize) {
        Page<FTNode> pages = ftNodeApplication.pageQueryFTNodesByFTProcessId(ftProcessId, currentPage, pageSize);
        return new Page<FTNodeDTO>(pages.getStart(), pages.getResultCount(), pageSize, FTNodeAssembler.toDTOs(pages.getData()));
    }

    /**
     * @param ftLotId
     * @return
     */
    @Override
    public InvokeResult findFTProcessByFTLotId(Long ftLotId) {
        try {
            FTProcess result = ftProcessApplication.findFTProcessByFTLotId(ftLotId);
            Collections.sort(result.getFtNodes());
            return InvokeResult.success(FTProcessAssembler.toDTO(result));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return InvokeResult.failure(ex.getMessage());
        }
    }


    @Override
    public List<String> findComposedTestNodeNamesByFTLotId(Long ftLotId) {
        FTInfo ftInfo = ftInfoApplication.findByFTLotId(ftLotId);
        ProcessTemplate processTemplate = ftInfo.getProcessTemplate();
        List<String> nodeNames = new ArrayList<>();
        for (String nodeName : processTemplate.getContent().split("\\|")) {
            if (nodeName.startsWith("Test-")) {
                nodeNames.add(nodeName);
            }
        }
        return nodeNames;
    }

    @Override
    public List<String> findComposedTestNodeNamesByProductId(Long id) {
        FTInfo ftInfo = ftInfoApplication.get(id);
        ProcessTemplate processTemplate = ftInfo.getProcessTemplate();
        List<String> nodeNames = new ArrayList<>();
        for (String nodeName : processTemplate.getContent().split("\\|")) {
            if (nodeName.startsWith("Test-")) {
                nodeNames.add(nodeName);
            }
        }
        return nodeNames;
    }

	@Override
	public InvokeResult updateFTNote(Long processId,String note) {
		FTProcess f = ftProcessApplication.get(processId);
		f.setNote(note);
		ftProcessApplication.update(f);
		return InvokeResult.success();
	}
}

