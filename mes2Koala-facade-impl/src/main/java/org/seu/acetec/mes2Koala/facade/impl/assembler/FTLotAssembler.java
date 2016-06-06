package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.CustomerFTLot;
import org.seu.acetec.mes2Koala.core.domain.FTLot;
import org.seu.acetec.mes2Koala.core.domain.InternalLot;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.FTLotPageVo;

import java.util.*;

public class FTLotAssembler {

    public static Map<String, String> holdStateNameMap = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put(InternalLot.HOLD_STATE_UNHOLD, "UNHOLD");
            put(InternalLot.HOLD_STATE_HOLD, "HOLD");
            put(InternalLot.HOLD_STATE_FUTURE_HOLD, "Future Hold");
        }
    };

    public static FTLotPageVo toPageVo(FTLot ftLot) {
        if (ftLot == null)
            return null;

        FTLotPageVo ftPageDTO = new FTLotPageVo();
        CustomerFTLot customerLot = ftLot.getCustomerFTLot();
        ftPageDTO.setId(ftLot.getId());
        if (holdStateNameMap.containsKey(ftLot.getHoldState())
                && Objects.equals(ftLot.getHoldState(), InternalLot.HOLD_STATE_HOLD)) {
            ftPageDTO.setState(holdStateNameMap.get(ftLot.getHoldState()));
        } else {
            ftPageDTO.setState(ftLot.getCurrentState());
        }
        ftPageDTO.setInternalPPO(""); // 暂定
        ftPageDTO.setCustomerPPO(customerLot.getCustomerPPO());
        ftPageDTO.setPackageNumber(customerLot.getPackageNumber());
        ftPageDTO.setProductVersion(customerLot.getProductVersion());
        ftPageDTO.setTaxType(customerLot.getTaxType());
        ftPageDTO.setLotNumber(ftLot.getInternalLotNumber());
        ftPageDTO.setQuantity(ftLot.getQty().toString());
        ftPageDTO.setCustomerLotNumber(customerLot.getCustomerLotNumber());
        ftPageDTO.setCustomerNumber(customerLot.getCustomerNumber());
        ftPageDTO.setShipmentProductNumber(ftLot.getShipmentProductNumber());
        ftPageDTO.setCustomerProductNumber(customerLot.getCustomerProductNumber());
        ftPageDTO.setWaferLotNumber(customerLot.getWaferLot());
        ftPageDTO.setIncomingDate(customerLot.getIncomingDate().toString());
        ftPageDTO.setInternalProductNumber(customerLot.getFtInfo().getInternalProductNumber());
        return ftPageDTO;
    }

    public static List<FTLotPageVo> toPageVos(Collection<FTLot> internalLots) {
        if (internalLots == null)
            return null;
        List<FTLotPageVo> ftPageDTOs = new ArrayList<FTLotPageVo>();
        for (FTLot internalLot : internalLots) {
            ftPageDTOs.add(toPageVo(internalLot));
        }
        return ftPageDTOs;
    }


    public static FTLotDTO toDTO(FTLot fTLot) {
        if (fTLot == null) {
            return null;
        }
        // MES2AbstractEntity
        FTLotDTO result = new FTLotDTO();
        result.setId(fTLot.getId());
        result.setVersion(fTLot.getVersion());
        result.setCreateTimestamp(fTLot.getCreateTimestamp());
        result.setLastModifyTimestamp(fTLot.getLastModifyTimestamp());
        result.setCreateEmployNo(fTLot.getCreateEmployNo());
        result.setLastModifyEmployNo(fTLot.getLastModifyEmployNo());
        result.setLogic(fTLot.getLogic());
        result.setCustomerLotDTO(CustomerFTLotAssembler.toDTO((CustomerFTLot) fTLot.getCustomerFTLot()));

        // InternalLot
        result.setInternalLotNumber(fTLot.getInternalLotNumber());
        result.setShipmentProductNumber(fTLot.getShipmentProductNumber());
        result.setRcNumber(fTLot.getRcNumber());
        result.setHoldState(fTLot.getHoldState());
        result.setCurrentState(fTLot.getCurrentState());
        result.setParentIntegrationIds(fTLot.getParentIntegrationIds());
        result.setParentSeparationId(fTLot.getParentSeparationId());

        // FTLot
        result.setBorrow(fTLot.getBorrow());
        result.setLoss(fTLot.getLoss());
        result.setQty(fTLot.getQty());
        result.setType(fTLot.getType());
        result.setWmsTestId(fTLot.getWmsTestId());
        // Extra
        return result;
    }

    public static List<FTLotDTO> toDTOs(Collection<FTLot> fTLots) {
        if (fTLots == null) {
            return null;
        }
        List<FTLotDTO> results = new ArrayList<FTLotDTO>();
        for (FTLot each : fTLots) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static FTLot toEntity(FTLotDTO fTLotDTO) {
        if (fTLotDTO == null) {
            return null;
        }
        // MES2AbstractEntity
        FTLot result = new FTLot();
        result.setId(fTLotDTO.getId());
        result.setVersion(fTLotDTO.getVersion());
        result.setCreateTimestamp(fTLotDTO.getCreateTimestamp());
        result.setLastModifyTimestamp(fTLotDTO.getLastModifyTimestamp());
        result.setCreateEmployNo(fTLotDTO.getCreateEmployNo());
        result.setLastModifyEmployNo(fTLotDTO.getLastModifyEmployNo());
        result.setLogic(fTLotDTO.getLogic());
        result.setCustomerFTLot(CustomerFTLotAssembler.toEntity(fTLotDTO.getCustomerLotDTO()));

        // InternalLot
        result.setInternalLotNumber(fTLotDTO.getInternalLotNumber());
        result.setShipmentProductNumber(fTLotDTO.getShipmentProductNumber());
        result.setRcNumber(fTLotDTO.getRcNumber());
        result.setHoldState(fTLotDTO.getHoldState());
        result.setCurrentState(fTLotDTO.getCurrentState());
        result.setParentIntegrationIds(fTLotDTO.getParentIntegrationIds());
        result.setParentSeparationId(fTLotDTO.getParentSeparationId());

        // FTLot
        result.setBorrow(fTLotDTO.getBorrow() == null ? 0 : fTLotDTO.getBorrow());
        result.setLoss(fTLotDTO.getLoss() == null ? 0 : fTLotDTO.getLoss());
        result.setQty(fTLotDTO.getQty() == null ? 0 : fTLotDTO.getQty());
        result.setType(fTLotDTO.getType());

        // Extra
        return result;
    }

    public static List<FTLot> toEntities(Collection<FTLotDTO> fTLotDTOs) {
        if (fTLotDTOs == null) {
            return null;
        }

        List<FTLot> results = new ArrayList<FTLot>();
        for (FTLotDTO each : fTLotDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
