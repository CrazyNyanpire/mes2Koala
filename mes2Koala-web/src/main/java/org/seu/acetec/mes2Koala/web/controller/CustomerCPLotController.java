package org.seu.acetec.mes2Koala.web.controller;

import org.dayatang.utils.Page;
import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.facade.CustomerCPLotFacade;
import org.seu.acetec.mes2Koala.facade.dto.CPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.CustomerCPLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.FTLotDTO;
import org.seu.acetec.mes2Koala.facade.dto.vo.CPCustomerLotPageVo;
import org.seu.acetec.mes2Koala.facade.service.CPRuncardfacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * @author 阙宇翔
 * @version 2016/2/29
 * @verison 2016/3/27
 */
@Controller
@RequestMapping("/CustomerCPLot")
public class CustomerCPLotController extends BaseController {

	@Inject
	CustomerCPLotFacade customerCPLotFacade;
	@Inject
	private CPRuncardfacade cpRuncardfacade;

	@ResponseBody
	@RequestMapping("/get/{id}")
	public InvokeResult get(@PathVariable Long id) {
		return customerCPLotFacade.getCustomerCPLot(id);
	}

	@ResponseBody
	@RequestMapping("/getCPVo/{id}")
	public InvokeResult getCPVo(@PathVariable Long id) {
		return customerCPLotFacade.getCPVo(id);
	}

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page<CPCustomerLotPageVo> pageJson(
			CustomerCPLotDTO customerCPLotDTO, @RequestParam int page,
			@RequestParam int pagesize) {
		return customerCPLotFacade.pageQueryCustomerCPLot(customerCPLotDTO,
				page, pagesize);
	}

	/**
	 * 手动下单
	 *
	 * @param cpLotDTO
	 *            待下单携带参数
	 * @return
	 * @version 2016/3/22 YuxiangQue 由FTLotController调整至CustomerFTLotController
	 * @version 2016/4/14 YuxiangQue
	 */
	@ResponseBody
	@RequestMapping("/order")
	public InvokeResult order(CPLotDTO cpLotDTO) {
		Long id;
		if (cpLotDTO.getCpInfoId() != null) {
			id = cpLotDTO.getCpInfoId();
		} else {
			id = customerCPLotFacade.getCPinfoIdByCustomerCPLotId(cpLotDTO
					.getCustomerCPLotDTO().getId());
		}
		boolean isRuncardFinished = cpRuncardfacade.isRuncardInfoSigned(id);
		if (!isRuncardFinished) {
			return InvokeResult.failure("runcard签核未完成, 不能下单");
		}
		cpLotDTO = this.lastModifyBase(cpLotDTO);
		return customerCPLotFacade.order(
				cpLotDTO.getCustomerCPLotDTO().getId(), cpLotDTO);
	}

	/**
	 * 批量下单
	 *
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderInBatches")
	public InvokeResult placeOrders(@RequestParam String ids, CPLotDTO cpLotDTO) {
		String[] value = ids.split(",");
		Long[] idArr = new Long[value.length];
		for (int i = 0; i < value.length; i++) {
			idArr[i] = Long.parseLong(value[i]);
		}

		StringBuilder sb = new StringBuilder();
		boolean orderState = true;
		for (int i = 0; i < idArr.length; i++) {
			Long id = customerCPLotFacade
					.getCPinfoIdByCustomerCPLotId(idArr[i]);
			boolean isRuncardSigned = cpRuncardfacade.isRuncardInfoSigned(id);
			if (!isRuncardSigned) {
				orderState = false;
				sb.append(idArr[i]).append(',');
			}
		}

		if (!orderState) {
			return InvokeResult.failure("以下编号的产品runcard没有签核完成，不能下单："
					+ sb.toString());
		}
		cpLotDTO = this.lastModifyBase(cpLotDTO);
		return customerCPLotFacade.batchOrder(idArr, cpLotDTO);
	}

	/**
	 * 获取内部批号
	 *
	 * @param customerCPLotId
	 *            CP批次Id
	 * @return 内部批号
	 */
	@ResponseBody
	@RequestMapping("/getExpectedLotNumber")
	public InvokeResult getExpectedLotNumber(@RequestParam Long customerCPLotId) {
		return customerCPLotFacade.getExpectedLotNumber(customerCPLotId);
	}

	/**
	 * 获取RC号
	 *
	 * @param customerCPLotId
	 *            CP批次Id
	 * @return RC号
	 */
	@ResponseBody
	@RequestMapping("/getExpectedRCNumber")
	public InvokeResult getExpectedRCNumber(@RequestParam Long customerCPLotId) {
		return customerCPLotFacade.getExpectedRCNumber(customerCPLotId);
	}

	/**
	 * 获取CPCustomerWafer
	 *
	 * @param customerCPLotId
	 *            CP批次Id
	 * @return RC号
	 */
	@ResponseBody
	@RequestMapping("/getCPCustomerWafer")
	public InvokeResult getCPCustomerWafer(@RequestParam Long customerCPLotId) {
		return customerCPLotFacade.getCPCustomerWafer(customerCPLotId);
	}

	/**
	 * 删除已下单lot
	 *
	 * @param ids
	 * @return
	 * @version 2016/5/30 harlow
	 */
	@ResponseBody
	@RequestMapping("/deleteOrder")
	public InvokeResult deleteOrder(@RequestParam String ids) {
		String[] idArr = ids.split(",");
		CPLotDTO cpLotDTO = new CPLotDTO();
		cpLotDTO = this.createBase(cpLotDTO);
		InvokeResult res = InvokeResult.success("删除成功！");
		for (String id : idArr) {
			cpLotDTO.setId(Long.valueOf(id));
			res = customerCPLotFacade.deleteOrder(cpLotDTO);
			if (res.isHasErrors()) {
				return res;
			}
		}
		return res;
	}

	@ResponseBody
	@RequestMapping("/findPIDByCustomerCPLotId/{id}")
	public InvokeResult findPIDByCustomerFTLotId(@PathVariable Long id) {
		return this.customerCPLotFacade.findPIDByCustomerCPLotId(id);
	}
}
