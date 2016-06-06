package org.seu.acetec.mes2Koala.facade.excelvo;

import org.seu.acetec.mes2Koala.facade.dto.CustomerDTO;

/**
 * 
 * @author Howard
 *
 */
public class CustomerVoAssembler {

	public static CustomerVo toVo(CustomerDTO customerDTO) {
		CustomerVo customerVo = new CustomerVo();
		customerVo.setAddress1(customerDTO.getAddress1());
		customerVo.setAddress2(customerDTO.getAddress2());
		customerVo.setChineseName(customerDTO.getChineseName());
		customerVo.setEnglishName(customerDTO.getEnglishName());
		customerVo.setCode(customerDTO.getCode());
		customerVo.setNumber(customerDTO.getNumber());
		customerVo.setPhone(customerDTO.getPhone());
		customerVo.setStatus(customerDTO.getStatus());
		customerVo.setCreateTime(customerDTO.getCreateTime());

		return customerVo;

	}
	
	public static CustomerDTO toDTO(CustomerVo customerVo) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setAddress1(customerVo.getAddress1());
		customerDTO.setAddress2(customerVo.getAddress2());
		customerDTO.setChineseName(customerVo.getChineseName());
		customerDTO.setEnglishName(customerVo.getEnglishName());
		customerDTO.setCode(customerVo.getCode());
		customerDTO.setNumber(customerVo.getNumber());
		customerDTO.setPhone(customerVo.getPhone());
		customerDTO.setStatus(customerVo.getStatus());
		
		return customerDTO;
	}

}
