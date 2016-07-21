package org.seu.acetec.mes2Koala.web.controller;

import net.sf.json.JSONObject;

import org.seu.acetec.mes2Koala.facade.MESInfoFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller
@RequestMapping("/MESInfo")
public class MESInfoController {

	@Inject
	private MESInfoFacade mesInfoFacade;

	@ResponseBody
	@RequestMapping(value = "/GetMesInfo", produces = "text/html;charset=UTF-8")
	public String getMesInfo(@RequestParam String flow,
			@RequestParam String testerNo) {
		return mesInfoFacade.getMesInfo(flow, testerNo);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/GetLotInfo", produces = "text/html;charset=UTF-8")
	public String getLotInfo(@RequestParam String lot,
			@RequestParam String category) {
		return mesInfoFacade.getLotInfo(lot, category);
	}
	
	@ResponseBody
	@RequestMapping(value = "/GetTouchdown", produces = "text/html;charset=UTF-8")
	public String getTouchDown(@RequestParam String productModel) {
//		String start = "<string xmlns=\"http://tempuri.org/\">";
//		String end = "</string>";
		return mesInfoFacade.getTouchdown(productModel);
	}
}
