package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.core.enums.FTNodeState;
import org.seu.acetec.mes2Koala.facade.dto.FTNodeDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FTNodeAssembler {

    public static FTNodeDTO toDTO(FTNode fTNode) {
        if (fTNode == null) {
            return null;
        }
        FTNodeDTO result = new FTNodeDTO();
        result.setId(fTNode.getId());
        result.setVersion(fTNode.getVersion());
        result.setName(fTNode.getName());
        result.setFtState(FTNodeState.getIntValue(fTNode.getState()));
        result.setSblDTOs(SBLAssembler.toDTOs(fTNode.getSbls()));
        if (fTNode instanceof FTIQCNode) {
            result.setFtIQCDTO(FTIQCNodeAssembler.toDTO((FTIQCNode) fTNode));
        } else if (fTNode instanceof FTBakingNode) {
            result.setFtBakingDTO(FTBakingNodeAssembler.toDTO((FTBakingNode) fTNode));
        } else if (fTNode instanceof FTComposedTestNode) {
            result.setFtTestDTO(FT_TestAssembler.toDTO((FTComposedTestNode) fTNode));
        } else if (fTNode instanceof FTPassNode) {
            result.setFtPassNodeDTO(FTPassNodeAssembler.toDTO((FTPassNode) fTNode));
        } else if (fTNode instanceof FTGuTestNode) {
            result.setFtGuTestDTO(FTGuTestNodeAssembler.toDTO((FTGuTestNode) fTNode));
        } else if (fTNode instanceof FTFinishNode) {
            result.setFtFinishDTO(FTFinishNodeAssembler.toDTO((FTFinishNode) fTNode));
        }
        return result;
    }

    public static List<FTNodeDTO> toDTOs(Collection<FTNode> fTNodes) {
        if (fTNodes == null) {
            return null;
        }
        List<FTNodeDTO> results = new ArrayList<FTNodeDTO>();
        for (FTNode each : fTNodes) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static FTNode toEntity(FTNodeDTO fTNodeDTO) {
        if (fTNodeDTO == null) {
            return null;
        }
        FTNode result0 = FTIQCNodeAssembler.toEntity(fTNodeDTO.getFtIQCDTO());
        FTNode result1 = FTBakingNodeAssembler.toEntity(fTNodeDTO.getFtBakingDTO());
        FTNode result2 = FT_TestAssembler.toEntity(fTNodeDTO.getFtTestDTO());
        FTNode result3 = FTPassNodeAssembler.toEntity(fTNodeDTO.getFtPassNodeDTO());
        FTNode result4 = FTGuTestNodeAssembler.toEntity(fTNodeDTO.getFtGuTestDTO());
        FTNode result5 = FTFinishNodeAssembler.toEntity(fTNodeDTO.getFtFinishDTO());
        FTNode result =
                result0 != null ? result0 :
                        (result1 != null ? result1 :
                                (result2 != null ? result2 :
                                        (result3 != null ? result3 :
                                                (result4 != null ? result4 :
                                                        (result5 != null ? result5 : null
                                                        )))));
        if (result == null) {
            return null;
        }
        // TODO 测试
        result.setId(fTNodeDTO.getId());
        result.setVersion(fTNodeDTO.getVersion());
        result.setName(fTNodeDTO.getName());
        result.setState(FTNodeState.fromIntValue(fTNodeDTO.getFtState()));
        result.setCreateEmployNo(fTNodeDTO.getCreateEmployNo());
        result.setCreateTimestamp(fTNodeDTO.getCreateTimestamp());
        result.setLastModifyEmployNo(fTNodeDTO.getLastModifyEmployNo());
        result.setLastModifyTimestamp(fTNodeDTO.getLastModifyTimestamp());
        return result;
    }

    public static List<FTNode> toEntities(Collection<FTNodeDTO> ftNodeDTOs) {
        if (ftNodeDTOs == null) {
            return null;
        }

        List<FTNode> results = new ArrayList<FTNode>();
        for (FTNodeDTO each : ftNodeDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
