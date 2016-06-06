package org.seu.acetec.mes2Koala.facade.impl.assembler;

import org.seu.acetec.mes2Koala.core.domain.Customer;
import org.seu.acetec.mes2Koala.facade.dto.CustomerDTO;

import com.sun.star.java.RestartRequiredException;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerAssembler {

    public static CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerDTO result = new CustomerDTO();
        result.setId(customer.getId());
        result.setVersion(customer.getVersion());
        result.setCode(customer.getCode());
        result.setNumber(customer.getNumber());
        result.setEnglishName(customer.getEnglishName());
        result.setChineseName(customer.getChineseName());
        result.setStatus(customer.getStatus());
        result.setPhone(customer.getPhone());
        result.setAddress1(customer.getAddress1());
        result.setAddress2(customer.getAddress2());
        result.setCreateTime(customer.getCreateTimestamp() == null ? "null" :customer.getCreateTimestamp().toString());
        result.setCreateTimestamp(customer.getCreateTimestamp());
        result.setLogo(customer.getLogo());
        result.setReelFixCode(customer.getReelFixCode());
        result.setReelQty(customer.getReelQty());
        return result;
    }

    public static List<CustomerDTO> toDTOs(Collection<Customer> customers) {
        if (customers == null) {
            return null;
        }
        List<CustomerDTO> results = new ArrayList<CustomerDTO>();
        for (Customer each : customers) {
            results.add(toDTO(each));
        }
        return results;
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }
        Customer result = new Customer();
        result.setId(customerDTO.getId());
        result.setVersion(customerDTO.getVersion());
        result.setCode(customerDTO.getCode());
        result.setNumber(customerDTO.getNumber());
        result.setEnglishName(customerDTO.getEnglishName());
        result.setChineseName(customerDTO.getChineseName());
        result.setStatus(customerDTO.getStatus());
        result.setPhone(customerDTO.getPhone());
        result.setAddress1(customerDTO.getAddress1());
        result.setAddress2(customerDTO.getAddress2());
        result.setCreateTimestamp((null == customerDTO.getCreateTime() 
        		|| "null".equalsIgnoreCase(customerDTO.getCreateTime()) 
        		|| "".equals(customerDTO.getCreateTime())
        		) ? null : java.sql.Timestamp.valueOf(customerDTO.getCreateTime()));
        result.setLogo(customerDTO.getLogo());
        result.setReelFixCode(customerDTO.getReelFixCode());
        result.setReelQty(customerDTO.getReelQty());
        return result;
    }

    public static List<Customer> toEntities(Collection<CustomerDTO> customerDTOs) {
        if (customerDTOs == null) {
            return null;
        }

        List<Customer> results = new ArrayList<Customer>();
        for (CustomerDTO each : customerDTOs) {
            results.add(toEntity(each));
        }
        return results;
    }
}
