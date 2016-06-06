package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.FTPassNode;
import org.seu.acetec.mes2Koala.facade.dto.FT_PassNodeDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FTPassNodeAssembler {

    public static FT_PassNodeDTO toDTO(FTPassNode fT_PassNode) {
        if (fT_PassNode == null) {
            return null;
        }
        FT_PassNodeDTO result = new FT_PassNodeDTO();
        result.setId(fT_PassNode.getId());
        result.setVersion(fT_PassNode.getVersion());
        result.setSite(fT_PassNode.getSite());
        result.setResult(fT_PassNode.getPassResult());
        return result;
    }

    public static List<FT_PassNodeDTO> toDTOs(Collection<FTPassNode> fT_PassNodes) {
        if (fT_PassNodes == null) {
            return null;
        }
        List<FT_PassNodeDTO> results = new ArrayList<FT_PassNodeDTO>();
        for (FTPassNode each : fT_PassNodes) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static FTPassNode toEntity(FT_PassNodeDTO fT_PassNodeDTO) {
        if (fT_PassNodeDTO == null) {
            return null;
        }
        FTPassNode result = new FTPassNode();
        result.setId(fT_PassNodeDTO.getId());
        result.setVersion(fT_PassNodeDTO.getVersion());
        result.setSite(fT_PassNodeDTO.getSite());
        result.setPassResult(fT_PassNodeDTO.getResult());
        return result;
    }

    public static List<FTPassNode> toEntities(Collection<FT_PassNodeDTO> fT_PassNodeDTOs) {
        if (fT_PassNodeDTOs == null) {
            return null;
        }

        List<FTPassNode> results = new ArrayList<FTPassNode>();
        for (FT_PassNodeDTO each : fT_PassNodeDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
