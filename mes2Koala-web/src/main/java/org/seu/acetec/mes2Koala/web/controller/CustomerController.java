package org.seu.acetec.mes2Koala.web.controller;

import help.ExcelTemplateHelper;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.CustomerFacade;
import org.seu.acetec.mes2Koala.facade.ExcelFacade;
import org.seu.acetec.mes2Koala.facade.SystemDictionaryFacade;
import org.seu.acetec.mes2Koala.facade.dto.CustomerDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Customer")
public class CustomerController {

    @Inject
    private CustomerFacade customerFacade;

    @Inject
    private SystemDictionaryFacade systemDictionaryFacade;

    @Inject
    private ExcelFacade excelFacade;

    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(CustomerDTO customerDTO) {
        if (!systemDictionaryFacade.updateCustomerSerialNumber(customerDTO.getCode()).isSuccess())
            return InvokeResult.failure("Update customer serial number failer! Try to refresh the Webpage!");
        return customerFacade.creatCustomer(customerDTO);
    }

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(CustomerDTO customerDTO) {
        return customerFacade.updateCustomer(customerDTO);
    }

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(CustomerDTO customerDTO, @RequestParam int page, @RequestParam int pagesize, @RequestParam(required = false) String sortname, @RequestParam(required = false) String sortorder) {
        Page<CustomerDTO> all = customerFacade.pageQueryCustomer(customerDTO, page, pagesize, sortname, sortorder);
        return all;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public InvokeResult remove(@RequestParam String ids) {
        String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            idArrs[i] = Long.parseLong(value[i]);
        }
        return customerFacade.removeCustomers(idArrs);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return customerFacade.getCustomer(id);
    }

    @ResponseBody
    @RequestMapping("/findCustomer")
    public List<CustomerDTO> queryAllCustomer() {
        return customerFacade.findAllCustomer();
    }

    /**
     * 导出成Excel
     *
     * @param ids
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/exportExcel")
    public InvokeResult exportExcel(@RequestParam String ids, HttpServletRequest request) {
        Long[] idArray = ExcelTemplateHelper.extractIdArray(ids, ",");
        return excelFacade.exportCustomer(this.getClass(), idArray);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        //CustomDateEditor 可以换成自己定义的编辑器。  
        //注册一个Date 类型的绑定器 。
        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
    }

}
