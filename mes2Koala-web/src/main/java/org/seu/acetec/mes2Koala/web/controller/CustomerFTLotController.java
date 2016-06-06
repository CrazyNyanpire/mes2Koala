package org.seu.acetec.mes2Koala.web.controller;

import org.dayatang.annotations.Transactional;
import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.security.shiro.CurrentUser;
import org.seu.acetec.mes2Koala.core.domain.Label;
import org.seu.acetec.mes2Koala.facade.CustomerFTLotFacade;
import org.seu.acetec.mes2Koala.facade.dto.CustomerFTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.CustomerFTLotSeparateDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.service.FTRuncardfacade;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/CustomerFTLot")
public class CustomerFTLotController extends BaseController {

	@Inject
	private CustomerFTLotFacade customerFTLotFacade;
	@Inject
	private FTRuncardfacade ftRuncardfacade;

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(CustomerFTLotDTO customerFTLotDTO,
			@RequestParam int page, @RequestParam int pagesize,
			@RequestParam(required = false) String sortname,
			@RequestParam(required = false) String sortorder) {
		Page<CustomerFTLotDTO> all = customerFTLotFacade
				.pageQueryCustomerFTLot(customerFTLotDTO, page, pagesize,
						sortname, sortorder);
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
		return customerFTLotFacade.removeCustomerFTLots(idArrs);
	}

	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return customerFTLotFacade.getCustomerFTLot(id);
	}

	/**
	 * 分批还原：将从同一个母批分出来的一个或多个批次恢复回去
	 *
	 * @param ids
	 *            需要还原的批次id
	 * @return 返回成功或错误信息
	 * @author Howard
	 */
	@ResponseBody
	@RequestMapping("/separationRestore")
	public InvokeResult separationRestore(@RequestParam String ids) {
		String[] value = ids.split(",");
		Long[] idArrs = new Long[value.length];
		for (int i = 0; i < value.length; i++) {
			idArrs[i] = Long.parseLong(value[i]);
		}
		return customerFTLotFacade.undoSeparation(idArrs);
	}

	/**
	 * 分批
	 *
	 * @param customerFTLotSeparateDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lotSeparate")
	public InvokeResult lotSeparation(
			CustomerFTLotSeparateDTO customerFTLotSeparateDTO) {
		return customerFTLotFacade.separateMany(
				customerFTLotSeparateDTO.getParentId(),
				customerFTLotSeparateDTO.getSeparationQties(),
				CurrentUser.getRoleName());
	}

	/**
	 * 手动合批
	 *
	 * @param ids
	 *            希望合成一批的批次id
	 * @return 成功标记或失败信息
	 * @author Howard
	 */
	@ResponseBody
	@RequestMapping("/lotCombineManually")
	public InvokeResult lotCombineManually(@RequestParam String ids) {
		// 由于传入的参数最终可能作为字段存储在实体中，故在此不做处理直接传入Facade
		String[] value = ids.split(",");
		Long[] idArrs = new Long[value.length];
		for (int i = 0; i < value.length; i++) {
			idArrs[i] = Long.parseLong(value[i]);
		}
		return customerFTLotFacade.integrateManual(idArrs,
				CurrentUser.getRoleName());
	}

	/**
	 * 手动下单
	 *
	 * @param ftLotDTO
	 * @return
	 * @version 2016/3/22 YuxiangQue 由FTLotController调整至CustomerFTLotController
	 * @version 2016/4/28 LCN runcard没有签核完成不能下单
	 */
	@ResponseBody
	@RequestMapping("/order")
	public InvokeResult order(FTLotDTO ftLotDTO) {

		Long id = customerFTLotFacade.getFTinfoIdByCustomerFTLotId(ftLotDTO
				.getCustomerLotDTO().getId());
		boolean isRuncardFinished = ftRuncardfacade.isRuncardInfoSigned(id);
		if (!isRuncardFinished) {
			return InvokeResult.failure("runcard签核未完成, 不能下单");
		}

		return customerFTLotFacade.order(ftLotDTO.getCustomerLotDTO().getId(),
				ftLotDTO, CurrentUser.getRoleName());
	}

	/**
	 * 批量下单
	 *
	 * @param ids
	 *            需要批量下单的批次id
	 * @return 批量下单结果
	 * @version 2016/4/28 LCN runcard没有签核完成不能下单
	 */
	@ResponseBody
	@RequestMapping("/orderInBatches")
	@Transactional
	public InvokeResult orderInBatches(@RequestParam String ids) {
		// 由于传入的参数最终可能作为字段存储在实体中，故在此不做处理直接传入Facade
		Map<String, Integer> messages = new HashMap<String, Integer>();
		// 解析ids，取出所有实体
		String[] value = ids.split(",");
		Long[] idArrs = new Long[value.length];
		for (int i = 0; i < value.length; i++) {
			idArrs[i] = Long.parseLong(value[i]);
		}

		StringBuilder sb = new StringBuilder();
		boolean orderState = true;
		for (int i = 0; i < idArrs.length; i++) {
			Long id = customerFTLotFacade
					.getFTinfoIdByCustomerFTLotId(idArrs[i]);
			boolean isRuncardSigned = ftRuncardfacade.isRuncardInfoSigned(id);
			if (!isRuncardSigned) {
				orderState = false;
				sb.append(idArrs[i]).append(',');
			}
		}

		if (!orderState) {
			return InvokeResult.failure("以下编号的产品runcard没有签核完成，不能下单："
					+ sb.toString());
		}

		return customerFTLotFacade.orderInBatches(idArrs,
				CurrentUser.getRoleName());
	}

	@ResponseBody
	@RequestMapping("/combinationRestore")
	public InvokeResult combinationRestore(@RequestParam Long id) {
		return customerFTLotFacade.undoIntegration(id);
	}

	@ResponseBody
	@RequestMapping("/getExpectedLotNumber/{id}")
	public InvokeResult getExpectedLotNumber(@PathVariable Long id) {
		return customerFTLotFacade.getExpectedLotNumber(id);
	}

	@ResponseBody
	@RequestMapping("/getExpectedRCNumber/{id}")
	public InvokeResult getExpectedRCNumber(@PathVariable Long id) {
		return customerFTLotFacade.getExpectedRCNumber(id);
	}

	@ResponseBody
	@RequestMapping("/findParentIntegrationByCustomerLot/{id}")
	public Map<String, Object> findParentIntegrationByCustomerLot(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				customerFTLotFacade.findParentIntegrationByCustomerLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findParentSeparationByCustomerLot/{id}")
	public Map<String, Object> findParentSeparationByCustomerLot(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				customerFTLotFacade.findParentSeparationByCustomerLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findInternalProductByCustomerLot/{id}")
	public Map<String, Object> findInternalProductByCustomerLot(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				customerFTLotFacade.findInternalProductByCustomerLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findCustomerFTLotByCustomerLot/{id}")
	public Map<String, Object> findCustomerFTLotByCustomerLot(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				customerFTLotFacade.findCustomerFTLotByCustomerLot(id));
		return result;
	}

	/**
	 * 删除已下单lot
	 *
	 * @param ftLotDTO
	 * @return
	 * @version 2016/5/30 harlow
	 */
	@ResponseBody
	@RequestMapping("/deleteOrder")
	public InvokeResult deleteOrder(@RequestParam String ids) {
		String[] idArr = ids.split(",");
		FTLotDTO ftLotDTO = new FTLotDTO();
		ftLotDTO = this.createBase(ftLotDTO);
		InvokeResult res = InvokeResult.success("删除成功！");
		for (String id : idArr) {
			ftLotDTO.setId(Long.valueOf(id));
			res = customerFTLotFacade.deleteOrder(ftLotDTO);
			if (res.isHasErrors()) {
				return res;
			}
		}
		return res;
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

	@ResponseBody
	@RequestMapping("/findPIDByCustomerFTLotId/{id}")
	public InvokeResult findPIDByCustomerFTLotId(@PathVariable Long id) {
		return this.customerFTLotFacade.findPIDByCustomerFTLotId(id);
	}

}
