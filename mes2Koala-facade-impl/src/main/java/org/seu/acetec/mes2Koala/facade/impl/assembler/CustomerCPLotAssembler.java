package org.seu.acetec.mes2Koala.facade.impl.assembler;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.openkoala.koalacommons.resourceloader.util.StringUtils;
import org.seu.acetec.mes2Koala.core.domain.CPCustomerWafer;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.core.domain.CustomerCPLot;
import org.seu.acetec.mes2Koala.core.enums.CustomerLotState;
import org.seu.acetec.mes2Koala.facade.dto.CustomerCPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.CPCustomerLotPageVo;

import java.util.*;

/**
 * @author 阙宇翔
 * @version 2016/2/27
 */
public class CustomerCPLotAssembler {

	private static Map<String, String> bondedType = new HashMap<String, String>() {
		{
			put("01", "客户保税");
			put("02", "艾科保税");
			put("03", "非保税");
		}
	};

	public static CustomerCPLotDTO toDTO(CustomerCPLot customerCPLot) {
		if (customerCPLot == null) {
			return null;
		}
		CustomerCPLotDTO customerCPLotDTO = new CustomerCPLotDTO();

		// MES2AbstractEntity
		customerCPLotDTO.setId(customerCPLot.getId());
		customerCPLotDTO.setVersion(customerCPLot.getVersion());
		customerCPLotDTO.setCreateTimestamp(customerCPLot.getCreateTimestamp());
		customerCPLotDTO.setLastModifyTimestamp(customerCPLot
				.getLastModifyTimestamp());
		customerCPLotDTO.setCreateEmployNo(customerCPLot.getCreateEmployNo());
		customerCPLotDTO.setLastModifyEmployNo(customerCPLot
				.getLastModifyEmployNo());
		customerCPLotDTO.setLogic(customerCPLot.getLogic());

		// CustomerLot
		customerCPLotDTO.setParentSeparationId(customerCPLot
				.getParentSeparationId());
		customerCPLotDTO.setParentIntegrationIds(customerCPLot
				.getParentIntegrationIds());
		customerCPLotDTO.setState(CustomerLotState.getIntValue(customerCPLot
				.getState()));
		customerCPLotDTO.setCustomerPPO(customerCPLot.getCustomerPPO());
		customerCPLotDTO.setCustomerNumber(customerCPLot.getCustomerNumber());
		customerCPLotDTO.setCustomerLotNumber(customerCPLot
				.getCustomerLotNumber());
		customerCPLotDTO.setProductVersion(customerCPLot.getProductVersion());

		// CustomerCPLot
		customerCPLotDTO.setMaskName(customerCPLot.getMaskName());
		customerCPLotDTO.setSize(customerCPLot.getSize());
		customerCPLotDTO.setWmsId(customerCPLot.getWmsId());
		customerCPLotDTO.setPackingLot(customerCPLot.getPackingLot());
		customerCPLotDTO.setInternalProductDTO(CPInfoAssembler.toDTO(
				customerCPLot.getCpInfo(), false));
		customerCPLotDTO.setWaferLot(customerCPLot.getWaferLot());
		customerCPLotDTO.setMaterialType(customerCPLot.getMaterialType());
		return customerCPLotDTO;
	}

	public static CustomerCPLot toEntity(CustomerCPLotDTO customerCPLotDTO) {
		if (customerCPLotDTO == null) {
			return null;
		}
		CustomerCPLot customerCPLot = new CustomerCPLot();

		// MES2AbstractEntity
		customerCPLot.setId(customerCPLotDTO.getId());
		customerCPLot.setVersion(customerCPLotDTO.getVersion());
		customerCPLot.setCreateTimestamp(customerCPLotDTO.getCreateTimestamp());
		customerCPLot.setLastModifyTimestamp(customerCPLotDTO
				.getLastModifyTimestamp());
		customerCPLot.setCreateEmployNo(customerCPLotDTO.getCreateEmployNo());
		customerCPLot.setLastModifyEmployNo(customerCPLotDTO
				.getLastModifyEmployNo());
		customerCPLot.setLogic(customerCPLotDTO.getLogic());

		// CustomerLot
		customerCPLot.setParentSeparationId(customerCPLotDTO
				.getParentSeparationId());
		customerCPLot.setParentIntegrationIds(customerCPLotDTO
				.getParentIntegrationIds());
		customerCPLot.setState(CustomerLotState.fromIntValue(customerCPLotDTO
				.getState()));
		customerCPLot.setCustomerPPO(customerCPLotDTO.getCustomerPPO());
		customerCPLot.setCustomerNumber(customerCPLotDTO.getCustomerNumber());
		customerCPLot.setCustomerLotNumber(customerCPLotDTO
				.getCustomerLotNumber());
		customerCPLot.setWmsId(customerCPLotDTO.getWmsId());

		// CustomerCPLot
		customerCPLot.setMaskName(customerCPLotDTO.getMaskName());
		customerCPLot.setSize(customerCPLotDTO.getSize());
		customerCPLot.setPackingLot(customerCPLotDTO.getPackingLot());
		customerCPLot.setCpInfo(CPInfoAssembler.toEntity(customerCPLotDTO
				.getInternalProductDTO()));
		customerCPLot.setWaferLot(customerCPLotDTO.getWaferLot());
		customerCPLot.setMaterialType(customerCPLotDTO.getMaterialType());
		return customerCPLot;
	}

	public static List<CustomerCPLot> toEntities(
			Collection<CustomerCPLotDTO> customerCPLotDTOs) {
		List<CustomerCPLot> customerCPLots = new ArrayList<CustomerCPLot>(
				customerCPLotDTOs.size());
		for (CustomerCPLotDTO customerCPLotDTO : customerCPLotDTOs) {
			customerCPLots.add(toEntity(customerCPLotDTO));
		}
		return customerCPLots;
	}

	public static List<CustomerCPLotDTO> toDTOs(
			List<CustomerCPLot> customerCPLotList) {
		List<CustomerCPLotDTO> customerCPLotDTOs = new ArrayList<>(
				customerCPLotList.size());
		for (CustomerCPLot customerCPLot : customerCPLotList) {
			customerCPLotDTOs.add(toDTO(customerCPLot));
		}
		return customerCPLotDTOs;
	}

	public static CPCustomerLotPageVo toPageVo(CustomerCPLot customerCPLot) {
		CPCustomerLotPageVo customerLotPageVo = new CPCustomerLotPageVo();
		customerLotPageVo.setId(customerCPLot.getId());
		customerLotPageVo.setVersion(customerCPLot.getVersion());
		customerLotPageVo.setState(CustomerLotState.getIntValue(customerCPLot
				.getState()));
		customerLotPageVo.setCustomerLotNumber(customerCPLot
				.getCustomerLotNumber());
		customerLotPageVo.setCustomerPPO(customerCPLot.getCustomerPPO());
		customerLotPageVo.setCustomerNumber(customerCPLot.getCustomerNumber());
		customerLotPageVo.setProductVersion(customerCPLot.getProductVersion()); // TODO
		customerLotPageVo.setMaskName(customerCPLot.getMaskName());
		customerLotPageVo.setSize(customerCPLot.getSize());
		// customerLotPageVo.setWaferLotNumber("");
		customerLotPageVo.setIncomingQuantity(customerCPLot
				.getIncomingQuantity());
		customerLotPageVo.setIncomingStyle(customerCPLot.getIncomingStyle());
		customerLotPageVo.setCustomerProductNumber(customerCPLot
				.getCustomerProductNumber());
		customerLotPageVo.setIncomingDate(customerCPLot.getIncomingDate()
				.toString());
		customerLotPageVo.setPackingLot(customerCPLot.getPackingLot());
		customerLotPageVo.setWaferLot(customerCPLot.getWaferLot());
		customerLotPageVo.setMaterialType(customerCPLot.getMaterialType());
		if (customerCPLot.getCpInfo() != null
				&& customerCPLot.getCpInfo().getProcessTemplate() != null) {
			customerLotPageVo.setProcessName(customerCPLot.getCpInfo()
					.getProcessTemplate().getName());
			customerLotPageVo.setProcessContent(customerCPLot.getCpInfo()
					.getProcessTemplate().getContent());
			customerLotPageVo.setInternalProductNumber(customerCPLot
					.getCpInfo().getInternalProductNumber());
		}
		customerLotPageVo.setOrderUser(customerCPLot.getOrderUser());
		customerLotPageVo.setOrderDate(customerCPLot.getOrderDate());
		if (customerCPLot.getCpLots() != null
				&& customerCPLot.getCpLots().size() > 0) {
			customerLotPageVo.setInternalLotNumber("");
			List<String> lotList = new ArrayList<String>();
			for (CPLot cpLot : customerCPLot.getCpLots()) {// TODO
				if (cpLot.getShowFlag() == null || cpLot.getShowFlag() == false) {
					lotList.add(cpLot.getInternalLotNumber());
					customerLotPageVo.setShipmentProductNumber(cpLot
							.getShipmentProductNumber());
				}
			}
			customerLotPageVo.setInternalLotNumber(StringUtils
					.collectionToDelimitedString(lotList, ","));
		} else {
			customerLotPageVo.setShipmentProductNumber(customerCPLot
					.getShipmentNumber());
		}
		return customerLotPageVo;
	}

	public static List<CPCustomerLotPageVo> toPageVos(
			Collection<CustomerCPLot> customerCPLots) {
		List<CPCustomerLotPageVo> vos = new ArrayList<>();
		for (CustomerCPLot customerCPLot : customerCPLots) {
			vos.add(toPageVo(customerCPLot));
		}
		return vos;
	}

	private static CustomerCPLot wmsJsonToEntity(JSONObject jsonObject) {
		CustomerCPLot customerCPLot = new CustomerCPLot();

		// CustomerLot
		customerCPLot.setState(CustomerLotState.Unordered);
		customerCPLot.setCustomerPPO(jsonObject.optString("CUS_PPO"));
		customerCPLot.setCustomerLotNumber(jsonObject.optString("CUS_LOT"));
		customerCPLot.setCustomerNumber(jsonObject.optString("CUS_CODE"));
		customerCPLot.setWmsId(jsonObject.optString("ID"));
		customerCPLot.setIncomingStyle(jsonObject.optString("INCOMING_STYLE"));// 到料形式以“01”
																				// （静电装散装）“02”（Tray盘箱装）的形式存储；
		customerCPLot.setIncomingDate(new Date());
		customerCPLot.setIncomingQuantity(jsonObject.optLong("QUANTITY"));
		customerCPLot.setCustomerProductNumber(jsonObject
				.optString("IN_PARTNUM"));
		customerCPLot.setProductVersion(jsonObject.optString("VERSION"));

		// CustomerCPLot
		customerCPLot.setMaskName(jsonObject.optString("MASK_NAME"));
		customerCPLot.setSize(jsonObject.optString("SIZE"));
		customerCPLot.setPackingLot(jsonObject.optString("PACKING_LOT"));
		customerCPLot.setWaferLot(jsonObject.optString("WAFER_LOT"));
		customerCPLot.setMaterialType(jsonObject.optString("MATERIAL_TYPE"));
		customerCPLot.setShipmentNumber(jsonObject.optString("out_PARTNUM"));

		// Wafers
		List<CPCustomerWafer> customerWafers = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<JSONObject> infWafersJsonArray = (List<JSONObject>) JSONArray
				.toCollection(jsonObject.optJSONArray("ingWafers"),
						JSONObject.class);
		for (JSONObject jsonObject1 : infWafersJsonArray) {
			if (jsonObject1.optInt("IS_TRUE") == 1) {
				CPCustomerWafer cpCustomerWafer = new CPCustomerWafer();
				cpCustomerWafer.setWaferCode(jsonObject1
						.optString("WAFER_CODE"));
				cpCustomerWafer.setWaferIndex(jsonObject1
						.optString("WAFER_INDEX"));
				cpCustomerWafer.setCustomerCPLot(customerCPLot);
				customerWafers.add(cpCustomerWafer);
			}
		}
		customerCPLot.setCpCustomerWafers(customerWafers);
		return customerCPLot;
	}

	public static Set<CustomerCPLot> wmsJsonToEntities(String wmsJson) {
		if (wmsJson == null) {
			return null;
		}
		Set<CustomerCPLot> customerCPLots = new HashSet<CustomerCPLot>();
		JSONArray jsonArray = JSONArray.fromObject(wmsJson);
		List<JSONObject> jsonObjects = (List<JSONObject>) JSONArray
				.toCollection(jsonArray, JSONObject.class);
		for (JSONObject object : jsonObjects) {
			customerCPLots.add(wmsJsonToEntity(object));
		}
		return customerCPLots;
	}
}
