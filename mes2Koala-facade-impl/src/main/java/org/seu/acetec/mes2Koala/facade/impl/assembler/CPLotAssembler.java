package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 阙宇翔
 * @version 2016/2/14
 */
public class CPLotAssembler {
    public static CPLotDTO toDTO(CPLot cpLot) {
        if (cpLot == null) {
            return null;
        }
        CPLotDTO result = new CPLotDTO();
        result.setId(cpLot.getId());
        result.setVersion(cpLot.getVersion());
        result.setCreateTimestamp(cpLot.getCreateTimestamp());
        result.setLastModifyTimestamp(cpLot.getLastModifyTimestamp());
        result.setCreateEmployNo(cpLot.getCreateEmployNo());
        result.setLastModifyEmployNo(cpLot.getLastModifyEmployNo());
        result.setLogic(cpLot.getLogic());
        result.setDiskContent(cpLot.getDiskContent());
        result.setInternalLotNumber(cpLot.getInternalLotNumber());
        result.setShipmentProductNumber(cpLot.getShipmentProductNumber());
        result.setRcNumber(cpLot.getRcNumber());
        result.setHoldState(cpLot.getHoldState());
        result.setCurrentState(cpLot.getCurrentState());
        result.setParentIntegrationIds(cpLot.getParentIntegrationIds());
        result.setParentSeparationId(cpLot.getParentSeparationId());
        result.setCustomerCPLotDTO(CustomerCPLotAssembler.toDTO(cpLot.getCustomerCPLot()));
        //result.setcPWaferDTOs(CPWaferAssembler.toDTOs(cpLot.getCpWafers()));
        result.setWmsTestId(cpLot.getWmsTestId());
        return result;
    }

    public static List<CPLotDTO> toDTOs(Collection<CPLot> cpLots) {
        if (cpLots == null) {
            return null;
        }
        List<CPLotDTO> results = new ArrayList<>();
        for (CPLot each : cpLots) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static CPLot toEntity(CPLotDTO cpLotDTO) {
        if (cpLotDTO == null) {
            return null;
        }
        CPLot result = new CPLot();
        result.setId(cpLotDTO.getId());
        result.setVersion(cpLotDTO.getVersion());
        result.setCreateTimestamp(cpLotDTO.getCreateTimestamp());
        result.setLastModifyTimestamp(cpLotDTO.getLastModifyTimestamp());
        result.setCreateEmployNo(cpLotDTO.getCreateEmployNo());
        result.setLastModifyEmployNo(cpLotDTO.getLastModifyEmployNo());
        result.setLogic(cpLotDTO.getLogic());
        result.setDiskContent(cpLotDTO.getDiskContent());
        result.setInternalLotNumber(cpLotDTO.getInternalLotNumber());
        result.setShipmentProductNumber(cpLotDTO.getShipmentProductNumber());
        result.setRcNumber(cpLotDTO.getRcNumber());
        result.setHoldState(cpLotDTO.getHoldState());
        result.setCurrentState(cpLotDTO.getCurrentState());
        result.setParentIntegrationIds(cpLotDTO.getParentIntegrationIds());
        result.setParentSeparationId(cpLotDTO.getParentSeparationId());
        result.setCustomerCPLot(CustomerCPLotAssembler.toEntity(cpLotDTO.getCustomerCPLotDTO()));
        result.setCpWafers(CPWaferAssembler.toEntities(cpLotDTO.getcPWaferDTOs()));
        result.setWmsTestId(cpLotDTO.getWmsTestId());
        return result;
    }

    public static List<CPLot> toEntities(Collection<CPLotDTO> cpLotDTOs) {
        if (cpLotDTOs == null) {
            return null;
        }
        List<CPLot> results = new ArrayList<>();
        for (CPLotDTO each : cpLotDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
