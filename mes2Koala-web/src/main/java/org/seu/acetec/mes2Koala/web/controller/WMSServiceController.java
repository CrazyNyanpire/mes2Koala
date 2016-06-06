package org.seu.acetec.mes2Koala.web.controller;


import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.CustomerCPLotFacade;
import org.seu.acetec.mes2Koala.facade.CustomerFTLotFacade;
import org.seu.acetec.mes2Koala.facade.CustomerFacade;
import org.seu.acetec.mes2Koala.facade.InternalProductFacade;
import org.seu.acetec.mes2Koala.facade.dto.CustomerDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/WMSService")
public class WMSServiceController {

    @Inject
    CustomerFTLotFacade customerFTLotFacade;

    @Inject
    CustomerCPLotFacade customerCPLotFacade;

    @Inject
    CustomerFacade customerFacade;
    @Inject
    InternalProductFacade internalProductFacade;
    
    @ResponseBody
    @RequestMapping(value = {"/addCustomerFTLots", "/CustomerFTLots/add"}, method = RequestMethod.POST)
    public InvokeResult addWMSCustomerFTLots(@RequestParam String json) {
        try {
            json = URLDecoder.decode(json, "UTF-8");
            return customerFTLotFacade.addWmsCustomerFTLots(json);
        } catch (UnsupportedEncodingException e) {
            return InvokeResult.failure(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/CustomerFTLots/update", method = RequestMethod.POST)
    public InvokeResult updateWMSCustomerFTLots(@RequestParam String json) {
        return customerFTLotFacade.updateWmsCustomerFTLots(json);
    }

    @ResponseBody
    @RequestMapping(value = "/CustomerFTLots/delete", method = RequestMethod.POST)
    public InvokeResult deleteWMSCustomerFTLots(@RequestParam String json) {
        return customerFTLotFacade.deleteWmsCustomerFTLos(json);
    }

    @ResponseBody
    @RequestMapping(value = "/CustomerCPLot/addAll", method = RequestMethod.POST)
    public InvokeResult addWMSCustomerCPLots(@RequestParam String json) {
        try {
            json = URLDecoder.decode(json, "UTF-8");
            return customerCPLotFacade.addWmsCustomerCPLots(json);
        } catch (UnsupportedEncodingException e) {
            return InvokeResult.failure(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/CustomerCPLot/updateAll", method = RequestMethod.POST)
    public InvokeResult updateWMSCustomerCPLots(@RequestParam String json) {
        return customerCPLotFacade.updateWmsCustomerCPLots(json);
    }

    @ResponseBody
    @RequestMapping(value = "/CustomerCPLot/deleteAll", method = RequestMethod.POST)
    public InvokeResult deleteWMSCustomerCPLots(@RequestParam String json) {
        return customerCPLotFacade.deleteWmsCustomerCPLots(json);
    }
    /** 
     * 查询所有客户，返回给WMS
     * @author Eva 20160412
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/Customer/queryAllCustomer", method = RequestMethod.POST)
    public InvokeResult queryCustomerinfo() {
        return InvokeResult.success(customerFacade.findAllCustomer());
    }
    /** 
     * 根据客户编号获取客户名称
     * @author Eva 20160412
     * @param json 客户编号
     * @return 客户名称
     */
    @ResponseBody
    @RequestMapping(value = "/Customer/queryCustomerByNumber", method = RequestMethod.POST)
    public InvokeResult queryCustomerByNumber(@RequestParam String json) {
        return customerFacade.queryCustomerByNumber(json);
    }
    /**
     * 根据客户编号及测试类型获取产品型号
     * @param json 客户编号;测试类型
     * @return 产品型号
     */
    @ResponseBody
    @RequestMapping(value = "/Product/queryAllProduct", method = RequestMethod.POST)
    public InvokeResult queryAllProduct(@RequestParam String json) {
    	String[] params=json.split(";");
        return InvokeResult.success(internalProductFacade.findAllInternalProductByCus(params[0],params[1]));
    }
    /**
     * 根据客户编号、版本获取产品型号
     * @param json 客户编号;版本
     * @return 产品型号
     */
    @ResponseBody
    @RequestMapping(value = "/Product/queryProductByVersion", method = RequestMethod.POST)
    public InvokeResult queryProductByVersion(@RequestParam String json) {
    	String[] params=json.split(";");
        return InvokeResult.success(internalProductFacade.queryProductByVersion(params[0],params[1]));
    } 
    
}
