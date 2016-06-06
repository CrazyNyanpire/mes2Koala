package org.seu.acetec.mes2Koala.web.controller;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.CPLotFacade;
import org.seu.acetec.mes2Koala.facade.CPProcessFacade;
import org.seu.acetec.mes2Koala.facade.CPTransferStorageFacade;
import org.seu.acetec.mes2Koala.facade.FTLotFacade;
import org.seu.acetec.mes2Koala.facade.FTProcessFacade;
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
@RequestMapping("/CPTransferStorage")
public class CPTransferStorageController extends BaseController {

	@Inject
	private ReelDiskFacade reelDiskFacade;

	@Inject
	private CPTransferStorageFacade cpTransferStorageFacade;

	@Inject
	private CPLotFacade cpLotFacade;

	@Inject
	private CPProcessFacade cpProcessFacade;

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(CPLotDTO cpLotDTO, @RequestParam int page,
			@RequestParam int pagesize) {
		Page<CPLotDTO> all = cpTransferStorageFacade.pageQuery(cpLotDTO, page,
				pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return cpLotFacade.getCPLot(id);
	}
	
	@ResponseBody
	@RequestMapping("/createStorage")
	public InvokeResult createStorage(String lotNo) {
		InvokeResult invokeResult = cpTransferStorageFacade.createTransferStorage(lotNo);
		if (invokeResult.isHasErrors()) {
			return invokeResult;
		}
		return InvokeResult.success();
	}


	@ResponseBody
	@RequestMapping("/reworkLot")
	public InvokeResult reworkLot(CPLotDTO cpLotDTO) {
		InvokeResult invokeResult = cpTransferStorageFacade.reworkLot(cpLotDTO);
		if (invokeResult.isHasErrors()) {
			return invokeResult;
		}
		return InvokeResult.success();
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
