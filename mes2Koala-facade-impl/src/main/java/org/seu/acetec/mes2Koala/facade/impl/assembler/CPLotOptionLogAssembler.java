package org.seu.acetec.mes2Koala.facade.impl.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.seu.acetec.mes2Koala.facade.dto.*;
import org.seu.acetec.mes2Koala.core.domain.*;

public class CPLotOptionLogAssembler {
	
	public static CPLotOptionLogDTO  toDTO(CPLotOptionLog  cPLotOptionLog){
		if (cPLotOptionLog == null) {
			return null;
		}
		CPLotOptionLogDTO result  = new CPLotOptionLogDTO();
	    result.setId (cPLotOptionLog.getId());
     	result.setVersion (cPLotOptionLog.getVersion());
     	result.setCreateTimestamp (cPLotOptionLog.getCreateTimestamp());
     	result.setLastModifyTimestamp (cPLotOptionLog.getLastModifyTimestamp());
     	result.setCreateEmployNo (cPLotOptionLog.getCreateEmployNo());
     	result.setLastModifyEmployNo (cPLotOptionLog.getLastModifyEmployNo());
     	result.setLogic (cPLotOptionLog.getLogic());
     	result.setOptType (cPLotOptionLog.getOptType());
     	result.setRemark (cPLotOptionLog.getRemark());
     	result.setFlownow (cPLotOptionLog.getFlownow());
     	result.setCpLotDTO (CPLotAssembler.toDTO(cPLotOptionLog.getCpLot()));
     	result.setLotNo(cPLotOptionLog.getCpLot().getInternalLotNumber());
     	return result;
	 }
	
	public static List<CPLotOptionLogDTO>  toDTOs(Collection<CPLotOptionLog>  cPLotOptionLogs){
		if (cPLotOptionLogs == null) {
			return null;
		}
		List<CPLotOptionLogDTO> results = new ArrayList<CPLotOptionLogDTO>();
		for (CPLotOptionLog each : cPLotOptionLogs) {
			results.add(toDTO(each));
		}
		return results;
	}
	
	 public static CPLotOptionLog  toEntity(CPLotOptionLogDTO  cpLotOptionLogDTO){
	 	if (cpLotOptionLogDTO == null) {
			return null;
		}
	 	 CPLotOptionLog result  = new CPLotOptionLog();
         result.setId (cpLotOptionLogDTO.getId());
         result.setVersion (cpLotOptionLogDTO.getVersion());
         result.setCreateTimestamp (cpLotOptionLogDTO.getCreateTimestamp());
         result.setLastModifyTimestamp (cpLotOptionLogDTO.getLastModifyTimestamp());
         result.setCreateEmployNo (cpLotOptionLogDTO.getCreateEmployNo());
         result.setLastModifyEmployNo (cpLotOptionLogDTO.getLastModifyEmployNo());
         result.setLogic (cpLotOptionLogDTO.getLogic());
         result.setOptType (cpLotOptionLogDTO.getOptType());
         result.setRemark (cpLotOptionLogDTO.getRemark());
         result.setFlownow (cpLotOptionLogDTO.getFlownow());
         result.setCpLot (CPLotAssembler.toEntity(cpLotOptionLogDTO.getCpLotDTO()));
 	  	return result;
	 }
	
	public static List<CPLotOptionLog> toEntities(Collection<CPLotOptionLogDTO> CPLotOptionLogDTOs) {
		if (CPLotOptionLogDTOs == null) {
			return null;
		}
		
		List<CPLotOptionLog> results = new ArrayList<CPLotOptionLog>();
		for (CPLotOptionLogDTO each : CPLotOptionLogDTOs) {
			results.add(toEntity(each));
		}
		return results;
	}
}
