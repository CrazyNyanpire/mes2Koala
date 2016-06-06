package org.seu.acetec.mes2Koala.web.controller;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.CustomerFacade;
import org.seu.acetec.mes2Koala.facade.InternalProductFacade;
import org.seu.acetec.mes2Koala.facade.dto.InternalProductDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/InternalProduct")
public class InternalProductController {

    @Inject
    private InternalProductFacade internalProductFacade;

    @Inject
    private CustomerFacade customerFacade;

    @ResponseBody
    @RequestMapping("/add")
    public InvokeResult add(InternalProductDTO internalProductDTO) {
        return internalProductFacade.createInternalProduct(internalProductDTO);
    }

    @ResponseBody
    @RequestMapping("/update")
    public InvokeResult update(InternalProductDTO internalProductDTO) {
        return internalProductFacade.updateInternalProduct(internalProductDTO);
    }

    @ResponseBody
    @RequestMapping("/pageJson")
    public Page pageJson(InternalProductDTO internalProductDTO, @RequestParam int page, @RequestParam int pagesize) {
        Page<InternalProductDTO> all = internalProductFacade.pageQueryInternalProduct(internalProductDTO, page, pagesize);
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
        return internalProductFacade.removeInternalProducts(idArrs);
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public InvokeResult get(@PathVariable Long id) {
        return internalProductFacade.getInternalProduct(id);
    }

    @ResponseBody
    @RequestMapping("/findCustomerDirectByInternalProduct/{id}")
    public Map<String, Object> findCustomerDirectByInternalProduct(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", customerFacade.findCustomerDirectByInternalProductId(id));
        return result;
    }

    @ResponseBody
    @RequestMapping("/findCustomerIndirectByInternalProduct/{id}")
    public Map<String, Object> findCustomerIndirectByInternalProduct(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", customerFacade.findCustomerIndirectByInternalProductId(id));
        return result;
    }

    @ResponseBody
    @RequestMapping("/findAllInternalProduct")
    public List<InternalProductDTO> findAllInternalProduct() {
        return internalProductFacade.findAllInternalProduct();
    }
    
    @ResponseBody
    @RequestMapping("/findAllInternalProduct/{type}")
    public List<InternalProductDTO> findAllInternalProduct(@PathVariable String type) {
        return internalProductFacade.findAllInternalProduct(type);
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

    @ResponseBody
    @RequestMapping("/bindProcess")
    public InvokeResult bindProcess(InternalProductDTO internalProductDTO) {
        return internalProductFacade.bindProcess(internalProductDTO);
    }
    
    @ResponseBody
    @RequestMapping("/createPID")
    public InvokeResult createPID(InternalProductDTO internalProductDTO) {
        return internalProductFacade.createPID(internalProductDTO);
    }
    
    
}
