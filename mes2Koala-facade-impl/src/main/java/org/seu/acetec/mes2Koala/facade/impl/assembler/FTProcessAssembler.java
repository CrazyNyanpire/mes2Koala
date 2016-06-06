package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.FTNode;
import org.seu.acetec.mes2Koala.core.domain.FTProcess;
import org.seu.acetec.mes2Koala.core.domain.Process;
import org.seu.acetec.mes2Koala.core.enums.FTNodeState;
import org.seu.acetec.mes2Koala.facade.dto.FTProcessDTO;
import org.seu.acetec.mes2Koala.facade.dto.SBLDTO;

import java.util.*;

public class FTProcessAssembler {

    public static FTProcessDTO toDTO(FTProcess process) {
        if (process == null) {
            return null;
        }
        FTProcessDTO result = new FTProcessDTO();
        result.setId(process.getId());
        result.setVersion(process.getVersion());
        result.setName(process.getName());
        result.setContent(process.getContent());
        result.setTestType(process.getTestType());
        result.setProcessState(process.getProcessState());
        result.setRunCard(process.getRunCard());
        result.setTestContent(process.getTestContent());
        result.setSpecialForm(process.getSpecialForm());
        result.setNote(process.getNote());
        result.setFtNodeDTOs(FTNodeAssembler.toDTOs(process.getFtNodes()));
        return result;
    }

    public static List<FTProcessDTO> toDTOs(Collection<FTProcess> processs) {
        if (processs == null) {
            return null;
        }
        List<FTProcessDTO> results = new ArrayList<FTProcessDTO>();
        for (FTProcess each : processs) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static Process toEntity(FTProcessDTO FTProcessDTO) {
        if (FTProcessDTO == null) {
            return null;
        }
        FTProcess result = new FTProcess();
        result.setId(FTProcessDTO.getId());
        result.setVersion(FTProcessDTO.getVersion());
        result.setName(FTProcessDTO.getName());
        result.setContent(FTProcessDTO.getContent());
        result.setTestType(FTProcessDTO.getTestType());
        result.setProcessState(FTProcessDTO.getProcessState());
        result.setRunCard(FTProcessDTO.getRunCard());
        result.setTestContent(FTProcessDTO.getTestContent());
        result.setSpecialForm(FTProcessDTO.getSpecialForm());
        result.setNote(FTProcessDTO.getNote());
        result.setFtNodes(FTNodeAssembler.toEntities(FTProcessDTO.getFtNodeDTOs()));
        result.setCreateEmployNo(FTProcessDTO.getCreateEmployNo());
        result.setCreateTimestamp(FTProcessDTO.getCreateTimestamp());
        result.setLastModifyEmployNo(FTProcessDTO.getLastModifyEmployNo());
        result.setCreateTimestamp(FTProcessDTO.getLastModifyTimestamp());
        return result;
    }

    public static List<Process> toEntities(Collection<FTProcessDTO> FTProcessDTOs) {
        if (FTProcessDTOs == null) {
            return null;
        }

        List<Process> results = new ArrayList<Process>();
        for (FTProcessDTO each : FTProcessDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }

    public static List<SBLDTO> CurrentSbltoDTO(FTProcess process) {
        if (process == null) {
            return null;
        }

        // 站点信息取得
        List<FTNode> ftNodes = process.getFtNodes();
        List<SBLDTO> sblDTOs = new ArrayList<SBLDTO>();

        for (FTNode ftNode : ftNodes) {
            if (ftNode.getState() == FTNodeState.TO_START || ftNode.getState() == FTNodeState.STARTED) {
                sblDTOs = SBLAssembler.toDTOs(ftNode.getSbls());
            }
        }
        return sblDTOs;
    }
}
