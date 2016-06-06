package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.CPNode;
import org.seu.acetec.mes2Koala.core.domain.CPProcess;
import org.seu.acetec.mes2Koala.core.enums.CPNodeState;
import org.seu.acetec.mes2Koala.facade.dto.CPNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPProcessDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yuxiangque
 * @version 2016/3/29
 */
public class CPNodeAssembler {
	public static CPNodeDTO toDTO(CPNode cpNode) {
		if (cpNode == null) {
			return null;
		}
		CPNodeDTO result = new CPNodeDTO();
        result.setCreateTimestamp(cpNode.getCreateTimestamp());
        result.setLastModifyTimestamp(cpNode.getLastModifyTimestamp());
        result.setCreateEmployNo(cpNode.getCreateEmployNo());
        result.setLastModifyEmployNo(cpNode.getLastModifyEmployNo());
        result.setLogic(cpNode.getLogic());
		result.setId(cpNode.getId());
		result.setVersion(cpNode.getVersion());
		result.setName(cpNode.getName());
		result.setState(cpNode.getState());
		result.setCpState(CPNodeState.getIntValue(cpNode.getState()));
		result.setTurn(cpNode.getTurn());
		return result;
	}

	public static List<CPNodeDTO> toDTOs(Collection<CPNode> cpNodes) {
		if (cpNodes == null) {
			return null;
		}
		List<CPNodeDTO> results = new ArrayList<CPNodeDTO>();
		for (CPNode each : cpNodes) {
			results.add(toDTO(each));
		}
		return results;
	}

	public static CPNode toEntity(CPNodeDTO cpNodeDTO) {
		if (cpNodeDTO == null) {
			return null;
		}
		CPNode result = new CPNode();
        result.setCreateTimestamp(cpNodeDTO.getCreateTimestamp());
        result.setLastModifyTimestamp(cpNodeDTO.getLastModifyTimestamp());
        result.setCreateEmployNo(cpNodeDTO.getCreateEmployNo());
        result.setLastModifyEmployNo(cpNodeDTO.getLastModifyEmployNo());
        result.setLogic(cpNodeDTO.getLogic());
		result.setId(cpNodeDTO.getId());
		result.setVersion(cpNodeDTO.getVersion());
		result.setName(cpNodeDTO.getName());
		result.setState(cpNodeDTO.getState());
		result.setTurn(cpNodeDTO.getTurn());
		return result;
	}

	public static List<CPNode> toEntities(Collection<CPNodeDTO> cpNodeDTOs) {
		throw new UnsupportedOperationException("Todo");
	}
}
