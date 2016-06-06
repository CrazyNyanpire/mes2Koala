package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.openkoala.organisation.core.domain.Employee;
import org.openkoala.organisation.facade.impl.assembler.EmployeeAssembler;
import org.seu.acetec.mes2Koala.application.FTInfoApplication;
import org.seu.acetec.mes2Koala.application.impl.FTInfoApplicationImpl;
import org.seu.acetec.mes2Koala.core.domain.FTInfo;
import org.seu.acetec.mes2Koala.facade.dto.FTInfoDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.FTInfoPageVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FTInfoAssembler {

    //直接注入无法注入  by lcn
    private static FTInfoApplication ftInfoApplication = new FTInfoApplicationImpl();

    public static FTInfoPageVo toPageVo(FTInfo fTInfo) {
        if (fTInfo == null) {
            return null;
        }
        FTInfoPageVo result = new FTInfoPageVo();

        result.setId(fTInfo.getId());
        result.setCustomerDirectName(fTInfo.getCustomerDirect().getChineseName());
        if(fTInfo.getCustomerIndirect() != null){
        	result.setCustomerIndirectName(fTInfo.getCustomerIndirect().getChineseName());
        }else{
        	result.setCustomerIndirectName("");
        }
        

        //6个产品负责人 支持数据库中旧数据 新版本应该不用判断null
        Employee employee = fTInfo.getKeyProductionManager();
        if (employee != null) {
            result.setKeyProductionManagerName(employee.getName());
        }

        employee = fTInfo.getAssistProductionManager();
        if (employee != null) {
            result.setAssistProductionManagerName(employee.getName());
        }

        employee = fTInfo.getKeyQuantityManager();
        if (employee != null) {
            result.setKeyQuantityManagerName(employee.getName());
        }
        employee = fTInfo.getAssistQuantityManager();
        if (employee != null) {
            result.setAssistQuantityManagerName(employee.getName());
        }
        employee = fTInfo.getKeyTDEManager();
        if (employee != null) {
            result.setKeyTDEManagerName(employee.getName());
        }
        employee = fTInfo.getAssistTDEManager();
        if (employee != null) {
            result.setAssistTDEManagerName(employee.getName());
        }

        result.setCustomerProductNumber(fTInfo.getCustomerProductNumber());
        result.setCustomerProductRevision(fTInfo.getCustomerProductRevision());
        result.setPackageType(fTInfo.getPackageType());
        result.setShipmentProductNumber(fTInfo.getShipmentProductNumber());

        result.setNote(fTInfo.getNote());
        result.setPackingFactory(fTInfo.getPackingFactory());
        result.setPackingType(fTInfo.getPackingType());
        result.setSize(fTInfo.getSize());
        result.setWaferFactory(fTInfo.getWaferFactory());
        result.setReelFixCode(fTInfo.getReelFixCode());
        result.setReelQty(fTInfo.getReelQty());
        result.setInternalProductNumber(fTInfo.getInternalProductNumber());

        return result;
    }

    public static List<FTInfoPageVo> toPageVos(Collection<FTInfo> fTInfos) {
        if (fTInfos == null) {
            return null;
        }
        List<FTInfoPageVo> result = new ArrayList<>();

        for (FTInfo ftInfo : fTInfos) {
            result.add(toPageVo(ftInfo));
        }
        return result;
    }


    public static FTInfoDTO toDTO(FTInfo fTInfo) {
        if (fTInfo == null) {
            return null;
        }
        FTInfoDTO result = new FTInfoDTO();
        result.setId(fTInfo.getId());
        result.setVersion(fTInfo.getVersion());
        result.setCustomerDirectDTO(CustomerAssembler.toDTO(fTInfo.getCustomerDirect()));
        result.setCustomerIndirectDTO(CustomerAssembler.toDTO(fTInfo.getCustomerIndirect()));

        //6个产品负责人
        result.setKeyProductionManagerDTO(EmployeeAssembler.toDTO(fTInfo.getKeyProductionManager()));
        result.setAssistProductionManagerDTO(EmployeeAssembler.toDTO(fTInfo.getAssistProductionManager()));
        result.setKeyQuantityManagerDTO(EmployeeAssembler.toDTO(fTInfo.getKeyQuantityManager()));
        result.setAssistQuantityManagerDTO(EmployeeAssembler.toDTO(fTInfo.getAssistQuantityManager()));
        result.setKeyTDEManagerDTO(EmployeeAssembler.toDTO(fTInfo.getKeyTDEManager()));
        result.setAssistTDEManagerDTO(EmployeeAssembler.toDTO(fTInfo.getAssistTDEManager()));


        result.setCustomerProductNumber(fTInfo.getCustomerProductNumber());
        result.setCustomerProductRevision(fTInfo.getCustomerProductRevision());
        result.setInternalProductNumber(fTInfo.getInternalProductNumber());
        result.setInternalProductRevision(fTInfo.getInternalProductRevision());
        result.setPackageType(fTInfo.getPackageType());
        result.setShipmentProductNumber(fTInfo.getShipmentProductNumber());
        result.setH_P_1(fTInfo.getH_P_1());
        result.setH_P_2(fTInfo.getH_P_2());
        result.setH_P_3(fTInfo.getH_P_3());
        result.setNote(fTInfo.getNote());
        result.setPackingFactory(fTInfo.getPackingFactory());
        result.setPackingType(fTInfo.getPackingType());
        result.setSize(fTInfo.getSize());
        result.setTestSys1(fTInfo.getTestSys1());
        result.setTestSys2(fTInfo.getTestSys2());
        result.setTestSys3(fTInfo.getTestSys3());
        result.setWaferFactory(fTInfo.getWaferFactory());
        result.setReelFixCode(fTInfo.getReelFixCode());
        result.setReelQty(fTInfo.getReelQty());
        return result;
    }

    public static List<FTInfoDTO> toDTOs(Collection<FTInfo> fTInfos) {
        if (fTInfos == null) {
            return null;
        }
        List<FTInfoDTO> results = new ArrayList<FTInfoDTO>();
        for (FTInfo each : fTInfos) {
            results.add(toDTO(each));
        }
        return results;
    }


    public static FTInfo toEntity(FTInfoDTO fTInfoDTO) {
        if (fTInfoDTO == null) {
            return null;
        }
        FTInfo result = null;
        if (fTInfoDTO.getId() != null) {
            result = ftInfoApplication.get(fTInfoDTO.getId());
        } else {
            result = new FTInfo();
        }
        result.setVersion(fTInfoDTO.getVersion());
        result.setCustomerDirect(CustomerAssembler.toEntity(fTInfoDTO.getCustomerDirectDTO()));
        if(fTInfoDTO.getCustomerIndirectDTO().getId() != null){
        	 result.setCustomerIndirect(CustomerAssembler.toEntity(fTInfoDTO.getCustomerIndirectDTO()));
        }
        //6个产品负责人
        result.setKeyProductionManager(EmployeeAssembler.toEntity(fTInfoDTO.getKeyProductionManagerDTO()));
        result.setAssistProductionManager(EmployeeAssembler.toEntity(fTInfoDTO.getAssistProductionManagerDTO()));
        result.setKeyQuantityManager(EmployeeAssembler.toEntity(fTInfoDTO.getKeyQuantityManagerDTO()));
        result.setAssistQuantityManager(EmployeeAssembler.toEntity(fTInfoDTO.getAssistQuantityManagerDTO()));
        result.setKeyTDEManager(EmployeeAssembler.toEntity(fTInfoDTO.getKeyTDEManagerDTO()));
        result.setAssistTDEManager(EmployeeAssembler.toEntity(fTInfoDTO.getAssistTDEManagerDTO()));


        result.setCustomerProductNumber(fTInfoDTO.getCustomerProductNumber());
        result.setCustomerProductRevision(fTInfoDTO.getCustomerProductRevision());
        result.setInternalProductNumber(fTInfoDTO.getInternalProductNumber());
        result.setInternalProductRevision(fTInfoDTO.getInternalProductRevision());
        result.setPackageType(fTInfoDTO.getPackageType());
        result.setShipmentProductNumber(fTInfoDTO.getShipmentProductNumber());
        result.setH_P_1(fTInfoDTO.getH_P_1());
        result.setH_P_2(fTInfoDTO.getH_P_2());
        result.setH_P_3(fTInfoDTO.getH_P_3());
        result.setNote(fTInfoDTO.getNote());
        result.setPackingFactory(fTInfoDTO.getPackingFactory());
        result.setPackingType(fTInfoDTO.getPackingType());
        result.setSize(fTInfoDTO.getSize());
        result.setTestSys1(fTInfoDTO.getTestSys1());
        result.setTestSys2(fTInfoDTO.getTestSys2());
        result.setTestSys3(fTInfoDTO.getTestSys3());
        result.setWaferFactory(fTInfoDTO.getWaferFactory());
        result.setTestType("FT");//该字段继承自InternalProduct，在此固定设置为FT

        result.setReelFixCode(fTInfoDTO.getReelFixCode());
        result.setReelQty(fTInfoDTO.getReelQty());

        return result;
    }

    public static List<FTInfo> toEntities(Collection<FTInfoDTO> fTInfoDTOs) {
        if (fTInfoDTOs == null) {
            return null;
        }

        List<FTInfo> results = new ArrayList<FTInfo>();
        for (FTInfoDTO each : fTInfoDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
