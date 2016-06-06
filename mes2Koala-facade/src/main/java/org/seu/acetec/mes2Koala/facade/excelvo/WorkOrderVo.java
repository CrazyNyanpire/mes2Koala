package org.seu.acetec.mes2Koala.facade.excelvo;

import java.util.List;

/**
 * 工单excelVo
 * @author Howard
 *
 */
public class WorkOrderVo {
	
	private String customerNumber;	//客户编号
	private String amount;	//生产数量
	private String startTime;	//开始日期
	private String workOrderNumber;	//工单编号
	private String customerProductNumber;	//产品型号
	private String internalLotNumber;	//生产批号

	private List<BomTemplateVo> bomTemplateVos;    //需求物料清单，其中theoryQty当作最小用量重新计算

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getWorkOrderNumber() {
		return workOrderNumber;
	}

	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}

	public String getCustomerProductNumber() {
		return customerProductNumber;
	}

	public void setCustomerProductNumber(String customerProductNumber) {
		this.customerProductNumber = customerProductNumber;
	}

	public String getInternalLotNumber() {
		return internalLotNumber;
	}

	public void setInternalLotNumber(String internalLotNumber) {
		this.internalLotNumber = internalLotNumber;
	}

	public List<BomTemplateVo> getBomTemplateVos() {
		return bomTemplateVos;
	}

	public void setBomTemplateVos(List<BomTemplateVo> bomTemplateVos) {
		this.bomTemplateVos = bomTemplateVos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((bomTemplateVos == null) ? 0 : bomTemplateVos.hashCode());
		result = prime * result + ((customerNumber == null) ? 0 : customerNumber.hashCode());
		result = prime * result + ((customerProductNumber == null) ? 0 : customerProductNumber.hashCode());
		result = prime * result + ((internalLotNumber == null) ? 0 : internalLotNumber.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((workOrderNumber == null) ? 0 : workOrderNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkOrderVo other = (WorkOrderVo) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (bomTemplateVos == null) {
			if (other.bomTemplateVos != null)
				return false;
		} else if (!bomTemplateVos.equals(other.bomTemplateVos))
			return false;
		if (customerNumber == null) {
			if (other.customerNumber != null)
				return false;
		} else if (!customerNumber.equals(other.customerNumber))
			return false;
		if (customerProductNumber == null) {
			if (other.customerProductNumber != null)
				return false;
		} else if (!customerProductNumber.equals(other.customerProductNumber))
			return false;
		if (internalLotNumber == null) {
			if (other.internalLotNumber != null)
				return false;
		} else if (!internalLotNumber.equals(other.internalLotNumber))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (workOrderNumber == null) {
			if (other.workOrderNumber != null)
				return false;
		} else if (!workOrderNumber.equals(other.workOrderNumber))
			return false;
		return true;
	}
	

}
