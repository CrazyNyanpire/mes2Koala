package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import org.seu.acetec.mes2Koala.core.domain.Process;
import org.seu.acetec.mes2Koala.facade.dto.CPProcessDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
public class CPProcessAssembler {
    public static CPProcessDTO toDTO(CPProcess process) {
        if (process == null) {
            return null;
        }
        CPProcessDTO result = new CPProcessDTO();        
        result.setId(process.getId());
        /*
        result.setVersion(process.getVersion());
        result.setName(process.getName());
        result.setContent(process.getContent());
        result.setTestType(process.getTestType());
        result.setProcessState(process.getProcessState());
        result.setRunCard(process.getRunCard());
        result.setTestContent(process.getTestContent());
        result.setSpecialForm(process.getSpecialForm());
        */
        result.setCpNodeDTOs(CPNodeAssembler.toDTOs(process.getCpNodes()));
        result.setName(process.getName());
        result.setRunCard(process.getRunCard());
        result.setContent(process.getContent());
        // TODO
        // result.setCpNodeDTOs(CPNodeAssembler.toPageVos(process.getCPNodes()));
        return result;
    }

    public static List<CPProcessDTO> toDTOs(Collection<CPProcess> processs) {
        if (processs == null) {
            return null;
        }
        List<CPProcessDTO> results = new ArrayList<CPProcessDTO>();
        for (CPProcess each : processs) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static Process toEntity(CPProcessDTO CPProcessDTO) {
        if (CPProcessDTO == null) {
            return null;
        }
        CPProcess result = new CPProcess();
        result.setId(CPProcessDTO.getId());
        result.setVersion(CPProcessDTO.getVersion());
        result.setName(CPProcessDTO.getName());
        result.setContent(CPProcessDTO.getContent());
        result.setTestType(CPProcessDTO.getTestType());
        result.setProcessState(CPProcessDTO.getProcessState());
        result.setRunCard(CPProcessDTO.getRunCard());
        result.setTestContent(CPProcessDTO.getTestContent());
        result.setSpecialForm(CPProcessDTO.getSpecialForm());

        // TODO
        // result.setCPNodes(CPNodeAssembler.toEntities(CPProcessDTO.getCPNodeDTOs()));
        return result;
    }

    public static List<Process> toEntities(Collection<CPProcessDTO> CPProcessDTOs) {
        if (CPProcessDTOs == null) {
            return null;
        }
        List<Process> results = new ArrayList<Process>();
        for (CPProcessDTO each : CPProcessDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
