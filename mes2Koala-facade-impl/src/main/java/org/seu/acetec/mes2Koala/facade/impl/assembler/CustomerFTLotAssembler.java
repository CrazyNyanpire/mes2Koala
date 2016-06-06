package org.seu.acetec.mes2Koala.facade.impl.assembler;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.seu.acetec.mes2Koala.core.domain.CustomerFTLot;
import org.seu.acetec.mes2Koala.core.domain.FTInfo;
import org.seu.acetec.mes2Koala.core.domain.ProcessTemplate;
import org.seu.acetec.mes2Koala.core.enums.CustomerLotState;
import org.seu.acetec.mes2Koala.facade.dto.CustomerFTLotDTO;

import java.util.*;

public class CustomerFTLotAssembler {

    private static Map<String, String> bondedType = new HashMap<String, String>() {
        {
            put("01", "客户保税");
            put("02", "艾科保税");
            put("03", "非保税");
        }
    };

    public static CustomerFTLotDTO toDTO(CustomerFTLot customerFTLot) {
        if (customerFTLot == null) {
            return null;
        }
        CustomerFTLotDTO result = new CustomerFTLotDTO();

        // MES2AbstractEntity
        result.setId(customerFTLot.getId());
        result.setWms_id(customerFTLot.getWmsId());
        result.setVersion(customerFTLot.getVersion());

        // CustomerLot
        result.setParentSeparationId(customerFTLot.getParentSeparationId());
        result.setState(CustomerLotState.getIntValue(customerFTLot.getState()));
        result.setCustomerPPO(customerFTLot.getCustomerPPO());
        result.setCustomerLotNumber(customerFTLot.getCustomerLotNumber());
        result.setCustomerNumber(customerFTLot.getCustomerNumber());
        result.setIncomingDate(customerFTLot.getIncomingDate());
        result.setShipmentProductionNumber(customerFTLot.getShipmentNumber());

        // CustomerCPLot
        result.setPackageNumber(customerFTLot.getPackageNumber());
        result.setProductVersion(customerFTLot.getProductVersion());
        result.setCustomerProductNumber(customerFTLot.getCustomerProductNumber());
        result.setIncomingQuantity(customerFTLot.getIncomingQuantity());
        result.setDeliveryType(customerFTLot.getIncomingStyle());
        result.setDateCode(customerFTLot.getDateCode());
        result.setMaterialType(customerFTLot.getMaterialType());
        result.setTaxType(customerFTLot.getTaxType());
        result.setWireBond(customerFTLot.getWireBond());
        result.setWaferLot(customerFTLot.getWaferLot());
        result.setMFGPN(customerFTLot.getMFGPN());
        result.setWaferManufacturer(customerFTLot.getWaferManufacturer());

        // 提取processTemplate内容
        FTInfo internalProduct = customerFTLot.getFtInfo();
        if (internalProduct != null) {
            ProcessTemplate processTemplate = internalProduct.getProcessTemplate();
            if (processTemplate != null) {
                result.setProcessTemplateName(processTemplate.getName());
                result.setProcessTemplateContent(processTemplate.getContent());
            }
        } else {
            result.setProcessTemplateName("");
            result.setProcessTemplateContent("");
        }
        
        //尝试获取已下单批次的艾科批号
        try{
        	if (customerFTLot.getFtLots().size() > 0) {
        		result.setLotNumber(customerFTLot.getFtLots().get(0).getInternalLotNumber());
        	}	
        } catch (NullPointerException e){};

        return result;
    }

    public static List<CustomerFTLotDTO> toDTOs(Collection<CustomerFTLot> customerFTLots) {
        if (customerFTLots == null) {
            return null;
        }
        List<CustomerFTLotDTO> results = new ArrayList<CustomerFTLotDTO>();
        for (CustomerFTLot each : customerFTLots) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static CustomerFTLot toEntity(CustomerFTLotDTO customerFTLotDTO) {
        if (customerFTLotDTO == null) {
            return null;
        }
        CustomerFTLot result = new CustomerFTLot();

        // MES2AbstractEntity
        result.setId(customerFTLotDTO.getId());
        result.setWmsId(customerFTLotDTO.getWms_id());
        result.setVersion(customerFTLotDTO.getVersion());

        // CustomerLot
        result.setParentSeparationId(customerFTLotDTO.getParentSeparationId());
        result.setState(CustomerLotState.fromIntValue(customerFTLotDTO.getState()));
        result.setCustomerPPO(customerFTLotDTO.getCustomerPPO());
        result.setCustomerLotNumber(customerFTLotDTO.getCustomerLotNumber());
        result.setCustomerNumber(customerFTLotDTO.getCustomerNumber());

        // CustomerCPLot
        result.setPackageNumber(customerFTLotDTO.getPackageNumber());
        result.setProductVersion(customerFTLotDTO.getProductVersion());
        result.setCustomerProductNumber(customerFTLotDTO.getCustomerProductNumber());
        result.setIncomingQuantity(customerFTLotDTO.getIncomingQuantity());
        result.setIncomingStyle(customerFTLotDTO.getDeliveryType());
        result.setDateCode(customerFTLotDTO.getDateCode());
        result.setMaterialType(customerFTLotDTO.getMaterialType());
        result.setTaxType(customerFTLotDTO.getTaxType());
        result.setWireBond(customerFTLotDTO.getWireBond());
        result.setWaferLot(customerFTLotDTO.getWaferLot());
        result.setMFGPN(customerFTLotDTO.getMFGPN());
        result.setWaferManufacturer(customerFTLotDTO.getWaferManufacturer());

        return result;
    }

    public static List<CustomerFTLot> toEntities(Collection<CustomerFTLotDTO> customerFTLotDTOs) {
        if (customerFTLotDTOs == null) {
            return null;
        }

        List<CustomerFTLot> results = new ArrayList<CustomerFTLot>();
        for (CustomerFTLotDTO each : customerFTLotDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }

    private static CustomerFTLot wmsJsonToEntity(JSONObject jsonObject) {
        //新建用于返回的DTO
        CustomerFTLot customerFTLot = new CustomerFTLot();
        //根据对应的值组装DTO

        // CustomerLot
        customerFTLot.setWmsId(jsonObject.optString("ID"));
        customerFTLot.setState(CustomerLotState.Unordered);
        customerFTLot.setCustomerNumber(jsonObject.optString("CUS_CODE"));
        customerFTLot.setCustomerLotNumber(jsonObject.optString("CUS_LOT"));
        customerFTLot.setCustomerPPO(jsonObject.optString("CUS_PPO"));
        customerFTLot.setProductVersion(jsonObject.optString("VERSION"));
        customerFTLot.setCustomerProductNumber(jsonObject.optString("IN_PARTNUM"));
        customerFTLot.setMaterialType(jsonObject.optString("MATERIAL_TYPE"));
        customerFTLot.setIncomingQuantity(jsonObject.optLong("QUANTITY"));
        customerFTLot.setDateCode(jsonObject.optString("DATECODE"));
        customerFTLot.setIncomingStyle(jsonObject.optString("INCOMING_STYLE"));//到料形式以“01” （静电装散装）“02”（Tray盘箱装）的形式存储；
        customerFTLot.setIncomingDate(new Date());
        try {
        	//尝试获取出货型号，如果没有则等同于来料型号
        	customerFTLot.setShipmentNumber(jsonObject.getString("OUT_PARTNUM") );
        } catch ( JSONException e ) {
        	customerFTLot.setShipmentNumber(customerFTLot.getCustomerLotNumber());
        }

        // CustomerFTLot
        customerFTLot.setTaxType(bondedType.get(jsonObject.optString("BONDED_TYPE")));
        customerFTLot.setWaferLot(jsonObject.optString("LOT_ID"));
        customerFTLot.setWireBond(jsonObject.optString("WIREBOND_TYPE"));
        customerFTLot.setWaferManufacturer(jsonObject.optString("WAFER_CLIENT"));
        customerFTLot.setPackageNumber(jsonObject.optString("PACKING_LOT"));
        customerFTLot.setMFGPN(jsonObject.optString("MFG_PN"));


        //余下String成员设置为空字符串
        return customerFTLot;
    }

    public static Set<CustomerFTLot> wmsJsonToEntities(String wmsJson) {
        if (wmsJson == null) {
            return null;
        }
        Set<CustomerFTLot> customerFTLots = new HashSet<>();
        JSONArray jsonArray = JSONArray.fromObject(wmsJson);
        @SuppressWarnings("unchecked")
        List<JSONObject> jsonObjects = (List<JSONObject>) JSONArray.toCollection(jsonArray, JSONObject.class);
        for (JSONObject object : jsonObjects) {
            CustomerFTLot customerFTLot = wmsJsonToEntity(object);
            customerFTLots.add(customerFTLot);
        }
        return customerFTLots;
    }
}
