package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.InternalProduct;
import org.seu.acetec.mes2Koala.core.domain.ProcessTemplate;
import org.seu.acetec.mes2Koala.facade.dto.InternalProductDTO;

import com.sun.star.java.RestartRequiredException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InternalProductAssembler {

    public static InternalProductDTO toDTO(InternalProduct internalProduct) {
        if (internalProduct == null) {
            return null;
        }
        InternalProductDTO result = new InternalProductDTO();
        result.setId(internalProduct.getId());
        result.setVersion(internalProduct.getVersion());
        result.setCreateTimestamp(internalProduct.getCreateTimestamp());
        result.setLastModifyTimestamp(internalProduct.getLastModifyTimestamp());
        result.setCreateEmployNo(internalProduct.getCreateEmployNo());
        result.setLastModifyEmployNo(internalProduct.getLastModifyEmployNo());
        result.setLogic(internalProduct.getLogic());

        result.setInternalProductNumber(internalProduct.getInternalProductNumber());
        result.setInternalProductRevision(internalProduct.getInternalProductRevision());
        result.setCustomerProductNumber(internalProduct.getCustomerProductNumber());
        result.setCustomerProductRevision(internalProduct.getCustomerProductRevision());
        result.setShipmentProductNumber(internalProduct.getShipmentProductNumber());
        result.setPackageType(internalProduct.getPackageType());
        result.setTestType(internalProduct.getTestType());
        result.setCustomerDirectDTO(CustomerAssembler.toDTO(internalProduct.getCustomerDirect()));
        result.setCustomerIndirectDTO(CustomerAssembler.toDTO(internalProduct.getCustomerIndirect()));
        
        return result;
    }

    public static List<InternalProductDTO> toDTOs(Collection<InternalProduct> internalProducts) {
        if (internalProducts == null) {
            return null;
        }
        List<InternalProductDTO> results = new ArrayList<InternalProductDTO>();
        for (InternalProduct each : internalProducts) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static InternalProduct toEntity(InternalProductDTO internalProductDTO) {
        if (internalProductDTO == null) {
            return null;
        }
        InternalProduct result = new InternalProduct();
        result.setId(internalProductDTO.getId());
        result.setVersion(internalProductDTO.getVersion());
        result.setCreateTimestamp(internalProductDTO.getCreateTimestamp());
        result.setLastModifyTimestamp(internalProductDTO.getLastModifyTimestamp());
        result.setCreateEmployNo(internalProductDTO.getCreateEmployNo());
        result.setLastModifyEmployNo(internalProductDTO.getLastModifyEmployNo());
        result.setLogic(internalProductDTO.getLogic());

        result.setInternalProductNumber(internalProductDTO.getInternalProductNumber());
        result.setInternalProductRevision(internalProductDTO.getInternalProductRevision());
        result.setCustomerProductNumber(internalProductDTO.getCustomerProductNumber());
        result.setCustomerProductRevision(internalProductDTO.getCustomerProductRevision());
        result.setShipmentProductNumber(internalProductDTO.getShipmentProductNumber());
        result.setPackageType(internalProductDTO.getPackageType());
        result.setTestType(internalProductDTO.getTestType());
        result.setCustomerDirect(CustomerAssembler.toEntity(internalProductDTO.getCustomerDirectDTO()));
        result.setCustomerIndirect(CustomerAssembler.toEntity(internalProductDTO.getCustomerIndirectDTO()));
        
        return result;
    }

    public static List<InternalProduct> toEntities(Collection<InternalProductDTO> internalProductDTOs) {
        if (internalProductDTOs == null) {
            return null;
        }

        List<InternalProduct> results = new ArrayList<InternalProduct>();
        for (InternalProductDTO each : internalProductDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
