package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.openkoala.organisation.core.domain.Employee;
import org.openkoala.organisation.facade.impl.assembler.EmployeeAssembler;
import org.seu.acetec.mes2Koala.core.domain.CPInfo;
import org.seu.acetec.mes2Koala.core.domain.Customer;
import org.seu.acetec.mes2Koala.facade.dto.CPInfoDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.CPInfoPageVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 阙宇翔
 * @version 2016/2/13
 */
public class CPInfoAssembler {

	public static CPInfoDTO toDTO(CPInfo cpInfo) {
		if (cpInfo == null) {
			return null;
		}
		CPInfoDTO result = new CPInfoDTO();
		result.setId(cpInfo.getId());
		result.setVersion(cpInfo.getVersion());
		result.setCustomerDirectDTO(CustomerAssembler.toDTO(cpInfo
				.getCustomerDirect()));
		if (cpInfo != null) {
			result.setCustomerIndirectDTO(CustomerAssembler.toDTO(cpInfo
					.getCustomerIndirect()));
		}
		result.setCustomerProductNumber(cpInfo.getCustomerProductNumber());
		result.setCustomerProductRevision(cpInfo.getCustomerProductRevision());
		result.setInternalProductNumber(cpInfo.getInternalProductNumber());
		result.setInternalProductRevision(cpInfo.getInternalProductRevision());
		result.setShipmentProductNumber(cpInfo.getShipmentProductNumber());

		// 6个产品负责人
		result.setKeyProductionManagerDTO(EmployeeAssembler.toDTO(cpInfo
				.getKeyProductionManager()));
		result.setAssistProductionManagerDTO(EmployeeAssembler.toDTO(cpInfo
				.getAssistProductionManager()));
		result.setKeyQuantityManagerDTO(EmployeeAssembler.toDTO(cpInfo
				.getKeyQuantityManager()));
		result.setAssistQuantityManagerDTO(EmployeeAssembler.toDTO(cpInfo
				.getAssistQuantityManager()));
		result.setKeyTDEManagerDTO(EmployeeAssembler.toDTO(cpInfo
				.getKeyTDEManager()));
		result.setAssistTDEManagerDTO(EmployeeAssembler.toDTO(cpInfo
				.getAssistTDEManager()));

		result.setGrossDie(cpInfo.getGrossDie());
		result.setWaferSize(cpInfo.getWaferSize());
		result.setTestTime(cpInfo.getTestTime());
		result.setTouchQty(cpInfo.getTouchQty());
		result.setWarningQty(cpInfo.getWarningQty());
		result.setWarningType(cpInfo.getWarningType());
		result.setProductRequire(cpInfo.getProductRequire());
		return result;
	}

	public static List<CPInfoDTO> toDTOs(Collection<CPInfo> cpInfos) {
		if (cpInfos == null) {
			return null;
		}
		List<CPInfoDTO> result = new ArrayList<>();
		for (CPInfo cpInfo : cpInfos)
			result.add(toDTO(cpInfo));
		return result;
	}

	public static CPInfo toEntity(CPInfoDTO cpInfoDTO) {
		if (cpInfoDTO == null) {
			return null;
		}
		CPInfo result = new CPInfo();
		result.setId(cpInfoDTO.getId());
		result.setVersion(cpInfoDTO.getVersion());
		result.setCustomerDirect(CustomerAssembler.toEntity(cpInfoDTO
				.getCustomerDirectDTO()));
		if (cpInfoDTO.getCustomerIndirectDTO().getId() != null) {
			result.setCustomerIndirect(CustomerAssembler.toEntity(cpInfoDTO
					.getCustomerIndirectDTO()));
		}

		result.setCustomerProductNumber(cpInfoDTO.getCustomerProductNumber());
		result.setCustomerProductRevision(cpInfoDTO
				.getCustomerProductRevision());
		result.setInternalProductNumber(cpInfoDTO.getInternalProductNumber());
		result.setInternalProductRevision(cpInfoDTO
				.getInternalProductRevision());
		result.setShipmentProductNumber(cpInfoDTO.getShipmentProductNumber());
		result.setTestType("CP");

		// 6个产品负责人
		result.setKeyProductionManager(EmployeeAssembler.toEntity(cpInfoDTO
				.getKeyProductionManagerDTO()));
		result.setAssistProductionManager(EmployeeAssembler.toEntity(cpInfoDTO
				.getAssistProductionManagerDTO()));
		result.setKeyQuantityManager(EmployeeAssembler.toEntity(cpInfoDTO
				.getKeyQuantityManagerDTO()));
		result.setAssistQuantityManager(EmployeeAssembler.toEntity(cpInfoDTO
				.getAssistQuantityManagerDTO()));
		result.setKeyTDEManager(EmployeeAssembler.toEntity(cpInfoDTO
				.getKeyTDEManagerDTO()));
		result.setAssistTDEManager(EmployeeAssembler.toEntity(cpInfoDTO
				.getAssistTDEManagerDTO()));

		// Integer需要检查null
		result.setGrossDie(cpInfoDTO.getGrossDie() == null ? 0 : cpInfoDTO
				.getGrossDie());
		result.setWaferSize(cpInfoDTO.getWaferSize());
		result.setTestTime(cpInfoDTO.getTestTime());
		result.setTouchQty(cpInfoDTO.getTouchQty() == null ? 0 : cpInfoDTO
				.getTouchQty());
		result.setWarningQty(cpInfoDTO.getWarningQty() == null ? 0 : cpInfoDTO
				.getWarningQty());
		result.setWarningType(cpInfoDTO.getWarningType());
		result.setProductRequire(cpInfoDTO.getProductRequire());
		return result;
	}

	public static List<CPInfo> toEntities(Collection<CPInfoDTO> cpInfos) {
		if (cpInfos == null) {
			return null;
		}
		List<CPInfo> result = new ArrayList<>();
		for (CPInfoDTO cpInfo : cpInfos)
			result.add(toEntity(cpInfo));
		return result;
	}

	public static List<CPInfoPageVo> toPageVos(Collection<CPInfo> cpInfos) {
		List<CPInfoPageVo> vos = new ArrayList<CPInfoPageVo>();
		for (CPInfo cpInfo : cpInfos) {
			vos.add(toPageVo(cpInfo));
		}
		return vos;
	}

	public static CPInfoPageVo toPageVo(CPInfo cpInfo) {
		CPInfoPageVo cpInfoPageVo = new CPInfoPageVo();
		cpInfoPageVo.setId(cpInfo.getId());
		Employee employee = cpInfo.getKeyProductionManager();
		if (employee != null) {
			cpInfoPageVo.setKeyProductionManagerName(employee.getName());
		}
		employee = cpInfo.getAssistProductionManager();
		if (employee != null) {
			cpInfoPageVo.setAssistProductionManagerName(employee.getName());
		}
		employee = cpInfo.getKeyQuantityManager();
		if (employee != null) {
			cpInfoPageVo.setKeyQuantityManagerName(employee.getName());
		}
		employee = cpInfo.getAssistQuantityManager();
		if (employee != null) {
			cpInfoPageVo.setAssistQuantityManagerName(employee.getName());
		}
		employee = cpInfo.getKeyTDEManager();
		if (employee != null) {
			cpInfoPageVo.setKeyTDEManagerName(employee.getName());
		}
		employee = cpInfo.getAssistTDEManager();
		if (employee != null) {
			cpInfoPageVo.setAssistTDEManagerName(employee.getName());
		}
		Customer customerDirect = cpInfo.getCustomerDirect();
		Customer customerIndirect = cpInfo.getCustomerIndirect();
		cpInfoPageVo.setCustomerDirectName(customerDirect.getChineseName());
		cpInfoPageVo.setCustomerIndirectName(customerIndirect.getChineseName());
		cpInfoPageVo
				.setCustomerProductNumber(cpInfo.getCustomerProductNumber());
		cpInfoPageVo.setCustomerProductRevision(cpInfo
				.getCustomerProductRevision());
		cpInfoPageVo
				.setShipmentProductNumber(cpInfo.getShipmentProductNumber());
		cpInfoPageVo.setWaferSize(cpInfo.getWaferSize());
		cpInfoPageVo.setGrossDie(cpInfo.getGrossDie());
		cpInfoPageVo.setWarningQty(cpInfo.getWarningQty());
		cpInfoPageVo.setWarningType(cpInfo.getWarningType());
		cpInfoPageVo.setTestTime(cpInfo.getTestTime());
		cpInfoPageVo.setProductRequire(cpInfo.getProductRequire());
		cpInfoPageVo.setTouchQty(cpInfo.getTouchQty());
		cpInfoPageVo.setInternalProductNumber(cpInfo.getInternalProductNumber());
		return cpInfoPageVo;
	}
}
