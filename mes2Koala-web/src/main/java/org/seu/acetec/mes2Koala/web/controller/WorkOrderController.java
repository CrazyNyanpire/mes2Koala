package org.seu.acetec.mes2Koala.web.controller;

import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.WorkOrderFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
@RequestMapping("/WorkOrder")
public class WorkOrderController {

    @Inject
    private WorkOrderFacade workOrderFacade;

    @ResponseBody
    @RequestMapping("/create")
    public InvokeResult createAll(@RequestParam String workOrderNumber, @RequestParam String lotIds, @RequestParam String bomIds) {
        String[] value = bomIds.split(",");
        Long[] bomIdArr = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            bomIdArr[i] = Long.parseLong(value[i]);
        }
        value = lotIds.split(",");
        Long[] lotIdArr = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            lotIdArr[i] = Long.parseLong(value[i]);
        }
        return workOrderFacade.create(workOrderNumber, lotIdArr, bomIdArr);
    }

    @ResponseBody
    @RequestMapping("/getWorkOrderVo")
    public InvokeResult getWorkOrderVo(@RequestParam String workOrderNumber, @RequestParam String lotIds, @RequestParam String bomIds) {
        String[] value = bomIds.split(",");
        Long[] bomIdArr = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            bomIdArr[i] = Long.parseLong(value[i]);
        }
        value = lotIds.split(",");
        Long[] lotIdArr = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            lotIdArr[i] = Long.parseLong(value[i]);
        }
        return workOrderFacade.getWorkOrderVo(workOrderNumber, lotIdArr, bomIdArr);
    }

//    @ResponseBody
//    @RequestMapping("/getExpectedWorkOrderNumber/{id}")
//    public InvokeResult getExpectedWorkOrderNumber(@PathVariable Long id) {
//        return workOrderFacade.getExpectedWorkOrderNumber(id);
//    }

    @ResponseBody
    @RequestMapping("/getExpectedWorkOrderNumber/{ids}")
    public InvokeResult getExpectedWorkOrderNumber(@PathVariable String ids) {
        String[] value = ids.split(",");
        Long[] lotIdArr = new Long[value.length];
        for (int i = 0; i < value.length; i++) {
            lotIdArr[i] = Long.parseLong(value[i]);
        }
        return workOrderFacade.getExpectedWorkOrderNumber(lotIdArr);
    }

}
