package org.seu.acetec.mes2Koala.facade.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.CustomerApplication;
import org.seu.acetec.mes2Koala.core.domain.Customer;
import org.seu.acetec.mes2Koala.core.domain.RawData;
import org.seu.acetec.mes2Koala.facade.CustomerFacade;
import org.seu.acetec.mes2Koala.facade.dto.CustomerDTO;
import org.seu.acetec.mes2Koala.facade.impl.assembler.CustomerAssembler;
import org.seu.acetec.mes2Koala.facade.impl.assembler.RawDataAssembler;

import javax.inject.Inject;
import javax.inject.Named;

import java.text.MessageFormat;
import java.util.*;

@Named
public class CustomerFacadeImpl implements CustomerFacade {

    @Inject
    private CustomerApplication application;

    private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService() {
        if (queryChannel == null) {
            queryChannel = InstanceFactory.getInstance(QueryChannelService.class, "queryChannel");
        }
        return queryChannel;
    }

    public InvokeResult getCustomer(Long id) {
        return InvokeResult.success(CustomerAssembler.toDTO(application.get(id)));
    }

    public InvokeResult creatCustomer(CustomerDTO customerDTO) {
        //按照规则生成客户编号
        customerDTO.setNumber(customerDTO.getCode() + '-' + customerDTO.getNumber());
        application.create(CustomerAssembler.toEntity(customerDTO));
        return InvokeResult.success();
    }

    public InvokeResult updateCustomer(CustomerDTO customerDTO) {
        //按照规则生成客户编号
        customerDTO.setNumber(customerDTO.getCode() + '-' + customerDTO.getNumber());
        application.update(CustomerAssembler.toEntity(customerDTO));
        return InvokeResult.success();
    }

    public InvokeResult removeCustomer(Long id) {
        application.remove(application.get(id));
        return InvokeResult.success();
    }

    public InvokeResult removeCustomers(Long[] ids) {
        Set<Customer> customers = new HashSet<Customer>();
        for (Long id : ids) {
            customers.add(application.get(id));
        }
        application.removeAll(customers);
        return InvokeResult.success();
    }

    public List<CustomerDTO> findAllCustomer() {
        return CustomerAssembler.toDTOs(application.findAll());
    }

    public Page<CustomerDTO> pageQueryCustomer(CustomerDTO queryVo, int currentPage, int pageSize, String sortname, String sortorder) {
        List<Object> conditionVals = new ArrayList<Object>();
        StringBuilder jpql = new StringBuilder("select _customer from Customer _customer   where 1=1 ");
        if (queryVo.getCode() != null && !"".equals(queryVo.getCode())) {
            jpql.append(" and _customer.code like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCode()));
        }
        if (queryVo.getNumber() != null && !"".equals(queryVo.getNumber())) {
            jpql.append(" and _customer.number like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNumber()));
        }
        if (queryVo.getEnglishName() != null && !"".equals(queryVo.getEnglishName())) {
            jpql.append(" and _customer.englishName like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getEnglishName()));
        }
        if (queryVo.getChineseName() != null && !"".equals(queryVo.getChineseName())) {
            jpql.append(" and _customer.chineseName like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getChineseName()));
        }
        if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
            jpql.append(" and _customer.status like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getStatus()));
        }
        if (queryVo.getPhone() != null && !"".equals(queryVo.getPhone())) {
            jpql.append(" and _customer.phone like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPhone()));
        }
        if (queryVo.getAddress1() != null && !"".equals(queryVo.getAddress1())) {
            jpql.append(" and _customer.address1 like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getAddress1()));
        }
        if (queryVo.getAddress2() != null && !"".equals(queryVo.getAddress2())) {
            jpql.append(" and _customer.address2 like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getAddress2()));
        }
        if (queryVo.getCreateTimestamp() != null) {
            jpql.append(" and _customer.createTimestamp between ? and ? ");
            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(queryVo.getCreateTimestampEnd());
            calendarEnd.add(Calendar.MILLISECOND, 23 * 59 * 59 * 1000 + 999);

            conditionVals.add(queryVo.getCreateTimestamp());
            conditionVals.add(calendarEnd.getTime());
        }
        if (queryVo.getLogo() != null && !"".equals(queryVo.getLogo())) {
            jpql.append(" and _customer.logo like ?");
            conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLogo()));
        }
        if (sortname != null && !"".equals(sortname) && sortorder != null && !"".equals(sortname)) {
            jpql.append(" order by _customer." + sortname + " " + sortorder);
        }
        Page<Customer> pages = getQueryChannelService()
                .createJpqlQuery(jpql.toString())
                .setParameters(conditionVals)
                .setPage(currentPage, pageSize)
                .pagedList();

        return new Page<CustomerDTO>(pages.getStart(), pages.getResultCount(), pageSize, CustomerAssembler.toDTOs(pages.getData()));
    }

    @Override
    public CustomerDTO findCustomerDirectByInternalProductId(Long internalProductId) {
        return CustomerAssembler.toDTO(application.findCustomerDirectByInternalProductId(internalProductId));
    }

    @Override
    public CustomerDTO findCustomerIndirectByInternalProductId(Long internalProductId) {
        return CustomerAssembler.toDTO(application.findCustomerIndirectByInternalProductId(internalProductId));
    }
     
    public InvokeResult queryCustomerByNumber(String number) { 
    	String jpql = "select _customer from Customer _customer where _customer.number=?";
	    List<Customer> customers = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[]{number}).list();
		return InvokeResult.success(CustomerAssembler.toDTOs(customers));
    }
}
