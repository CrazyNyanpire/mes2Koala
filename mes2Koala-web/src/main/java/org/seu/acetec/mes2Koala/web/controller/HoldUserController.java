package org.seu.acetec.mes2Koala.web.controller;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.CPLotFacade;
import org.seu.acetec.mes2Koala.facade.CPProcessFacade;
import org.seu.acetec.mes2Koala.facade.CPTransferStorageFacade;
import org.seu.acetec.mes2Koala.facade.FTLotFacade;
import org.seu.acetec.mes2Koala.facade.FTProcessFacade;
import org.seu.acetec.mes2Koala.facade.HoldMailFacade;
import org.seu.acetec.mes2Koala.facade.ReelDiskFacade;
import org.seu.acetec.mes2Koala.facade.ReelDiskTransferStorageFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.ReelDiskTransferStorageDTO;
import org.seu.acetec.mes2Koala.core.common.Mes2DateUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/HoldMail")
public class HoldUserController extends BaseController {

	@Inject
	private HoldMailFacade holdMailFacade;

	@ResponseBody
	@RequestMapping("/holdUsers")
	public InvokeResult createStorage(@RequestParam String dept) {

		switch (dept.toUpperCase()) {
		case "TDE":
			return holdMailFacade.findHoldTDE();
		case "QA":
			return holdMailFacade.findHoldQA();
		case "PP":
			return holdMailFacade.findHoldPP();
		case "CS":
			return holdMailFacade.findHoldCS();
		case "EE":
			return holdMailFacade.findHoldEE();
		default:
			return holdMailFacade.findHoldUsers();
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
		// CustomDateEditor 可以换成自己定义的编辑器。
		// 注册一个Date 类型的绑定器 。
		binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
	}
}
