package org.seu.acetec.mes2Koala.facade;

import java.util.List;

import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPNodeDTO;
import org.seu.acetec.mes2Koala.facade.dto.CPQDNDTO;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
public interface CPLotNodeOperationFacade {

    InvokeResult startCPNode(Long processId, CPNodeDTO cpNodeDTO);

    InvokeResult saveCPNode(Long processId, CPNodeDTO cpNodeDTO);

    InvokeResult endCPNode(Long processId, CPNodeDTO cpNodeDTO);

    InvokeResult jumpCPNode(Long processId, CPNodeDTO cpNodeDTO);

    InvokeResult hold(CPQDNDTO cpqdndto);

    InvokeResult unhold(CPLotDTO cpLotDTO);

    InvokeResult futureHold(CPQDNDTO cpqdndto);

    InvokeResult separateCPLot(Long cpLotId, List<CPLotDTO> cpLotDTOs, CPLotDTO cpLotDTO);

    InvokeResult integrateCPLots(Long[] cpLotIds, CPNodeDTO cpNodeDTO);

	void exportExcel(List<Object> cpLotDTOs, String fileName);

	InvokeResult mergeLot(String[] id, CPLotDTO cpLotDTO);

	InvokeResult getLabelInfo(Long id);

	InvokeResult dataCompensationChk(Long id);

	InvokeResult dataCompensation(String string);

}
