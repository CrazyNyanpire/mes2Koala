package org.seu.acetec.mes2Koala.application.impl;

import org.seu.acetec.mes2Koala.application.IncrementNumberApplication;
import org.seu.acetec.mes2Koala.application.SystemDictionaryApplication;
import org.seu.acetec.mes2Koala.core.common.Mes2DateUtils;
import org.seu.acetec.mes2Koala.core.common.SerialNumberUtils;
import org.seu.acetec.mes2Koala.core.domain.*;
import org.seu.acetec.mes2Koala.infra.MyDateUtils;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Named
@Transactional
public class IncrementNumberApplicationImpl extends GenericMES2ApplicationImpl<IncrementNumber>
		implements IncrementNumberApplication {

	@Inject
	private SystemDictionaryApplication systemDictionaryApplication;

	private IncrementNumber findByType(String type) {
		return findOne("select a from IncrementNumber a where a.type = ?", type);
	}

	private IncrementNumber getWorkOrder(String type) {
		IncrementNumber incrementNumber = findByType(type);
		if (incrementNumber == null) {
			incrementNumber = new IncrementNumber();
			incrementNumber.setIntervalNum(1);
			incrementNumber.setMaxNum(1000);
			incrementNumber.setSerialNum(1);
			incrementNumber.setStartStr(Mes2DateUtils.getMMDD());
			incrementNumber.setEndStr("");
			incrementNumber.setType(type);
			create(incrementNumber);
		}
		return incrementNumber;
	}

	private IncrementNumber getRework(String type) {
		IncrementNumber incrementNumber = findByType(type);
		if (incrementNumber == null) {
			incrementNumber = new IncrementNumber();
			incrementNumber.setMaxNum(999);
			incrementNumber.setIntervalNum(1);
			incrementNumber.setSerialNum(0);
			incrementNumber.setType(type);
			create(incrementNumber);
		}
		return incrementNumber;
	}

	private IncrementNumber getLat(String type) {
		IncrementNumber incrementNumber = findByType(type);
		if (incrementNumber == null) {
			incrementNumber = new IncrementNumber();
			incrementNumber.setMaxNum(999);
			incrementNumber.setIntervalNum(1);
			incrementNumber.setSerialNum(0);
			incrementNumber.setType(type);
			create(incrementNumber);
		}
		return incrementNumber;
	}

	private IncrementNumber getQdn(String type) {
		IncrementNumber incrementNumber = findByType(type);
		if (incrementNumber == null) {
			incrementNumber = new IncrementNumber();
			incrementNumber.setMaxNum(999);
			incrementNumber.setIntervalNum(1);
			incrementNumber.setSerialNum(0);
			incrementNumber.setType(type);
			create(incrementNumber);
		}
		return incrementNumber;
	}

	private IncrementNumber getReelCode(String type) {
		IncrementNumber incrementNumber = findByType(type);
		if (incrementNumber == null) {
			incrementNumber = new IncrementNumber();
			incrementNumber.setMaxNum(999);
			incrementNumber.setIntervalNum(1);
			incrementNumber.setSerialNum(0);
			incrementNumber.setType(type);
			create(incrementNumber);
		}
		return incrementNumber;
	}

	private IncrementNumber getRC(String type) {
		IncrementNumber incrementNumber = findByType(type);
		if (incrementNumber == null) {
			incrementNumber = new IncrementNumber();
			incrementNumber.setIntervalNum(1);
			incrementNumber.setMaxNum(100000);
			incrementNumber.setSerialNum(1);
			incrementNumber.setStartStr(Mes2DateUtils.getY());
			incrementNumber.setEndStr("");
			incrementNumber.setType(type);
			create(incrementNumber);
		}
		return incrementNumber;
	}

	private IncrementNumber getFTLotIncremenNumber(String type, int digitOfSerialNumber) {
		IncrementNumber incrementNumber = findByType(type);
		if (incrementNumber == null) {
			incrementNumber = new IncrementNumber();
			incrementNumber.setIntervalNum(1);
			incrementNumber.setMaxNum((int) Math.pow(10, digitOfSerialNumber));
			incrementNumber.setSerialNum(1);
			incrementNumber.setStartStr(Mes2DateUtils.getYWW());
			incrementNumber.setEndStr("");
			incrementNumber.setType(type);
			create(incrementNumber);
		} else {
			if (!Mes2DateUtils.getYWW().equals(incrementNumber.getStartStr())) {
				// 如果日期有变化，则流水号清零
				incrementNumber.setStartStr(Mes2DateUtils.getYWW());
				incrementNumber.setSerialNum(1);
			} else if (incrementNumber.getSerialNum() >= incrementNumber.getMaxNum()) {
				// 如果超过了最大值，reset到1
				incrementNumber.setSerialNum(1);
			}
		}
		return incrementNumber;
	}

	private void incWorkOrder(String type) {
		IncrementNumber incrementNumber = findByType(type);
		String startStr = Mes2DateUtils.getMMDD();
		int serialNum = incrementNumber.getSerialNum();
		if (!startStr.equals(incrementNumber.getStartStr())) {
			incrementNumber.setStartStr(startStr);
			incrementNumber.setSerialNum(1);
			update(incrementNumber);
		} else if (serialNum >= incrementNumber.getMaxNum()) {
			incrementNumber.setSerialNum(1);
			update(incrementNumber);
		} else {
			incrementNumber.setSerialNum(serialNum + incrementNumber.getIntervalNum());
			update(incrementNumber);
		}
	}

	private void incRework(String type) {
		IncrementNumber incrementNumber = findByType(type);
		String startStr = Mes2DateUtils.getTodayTime("y");
		int serialNum = incrementNumber.getSerialNum();
		if (!Objects.equals(incrementNumber.getStartStr(), startStr)) {
			incrementNumber.setStartStr(startStr);
			incrementNumber.setSerialNum(0);
			update(incrementNumber);
		} else if (serialNum >= incrementNumber.getMaxNum()) { // 达到预定的最大值
			incrementNumber.setSerialNum(1);
			update(incrementNumber);
		} else {
			incrementNumber.setSerialNum(serialNum + incrementNumber.getIntervalNum());
			update(incrementNumber);
		}
	}

	private void incLat(String type) {
		IncrementNumber in = findByType(type);
		String startStr = Mes2DateUtils.getTodayTime("yyyyMMdd");
		int serialNum = in.getSerialNum();
		if (!Objects.equals(in.getStartStr(), startStr)) {
			// incrementNumberFacade.resetSerialNum(numberType);
			in.setStartStr(startStr);
			in.setSerialNum(0);
			update(in);
		} else if (serialNum >= in.getMaxNum()) { // 达到预定的最大值
			in.setSerialNum(1);
			update(in);
		} else {
			in.setSerialNum(serialNum + in.getIntervalNum());
			update(in);
		}
	}

	private void incQdn(String type) {
		IncrementNumber in = findByType(type);
		String startStr = Mes2DateUtils.getTodayTime("yyMMdd");
		int serialNum = in.getSerialNum();
		if (!Objects.equals(in.getStartStr(), startStr)) {
			in.setStartStr(startStr);
			in.setSerialNum(0);
			update(in);
		} else if (serialNum >= in.getMaxNum()) { // 达到预定的最大值
			in.setSerialNum(1);
			update(in);
		} else {
			in.setSerialNum(serialNum + in.getIntervalNum());
			update(in);
		}
	}

	private boolean incReelCode(String type) {
		IncrementNumber in = findByType(type);
		String startStr = Mes2DateUtils.getTodayTime("yyMMdd");
		int serialNum = in.getSerialNum();
		if (!Objects.equals(in.getStartStr(), startStr)) {
			in.setStartStr(startStr);
			in.setSerialNum(0);
			update(in);
			return false;
		} else if (serialNum >= in.getMaxNum()) { // 达到预定的最大值
			in.setSerialNum(1);
			update(in);
		} else {
			in.setSerialNum(serialNum + in.getIntervalNum());
			update(in);
		}
		return true;
	}

	private void incRC(String type) {
		IncrementNumber incrementNumber = findByType(type);
		String startStr = Mes2DateUtils.getY();
		int serialNum = incrementNumber.getSerialNum();
		if (!Objects.equals(incrementNumber.getStartStr(), startStr)) {
			incrementNumber.setStartStr(startStr);
			incrementNumber.setSerialNum(1);
			update(incrementNumber);
		} else if (serialNum >= incrementNumber.getMaxNum()) {
			incrementNumber.setSerialNum(1);
			update(incrementNumber);
		} else {
			incrementNumber.setSerialNum(serialNum + incrementNumber.getIntervalNum());
			update(incrementNumber);
		}
	}

	private void incLot(String type) {
		IncrementNumber incrementNumber = findByType(type);
		if (!Objects.equals(Mes2DateUtils.getYWW(), incrementNumber.getStartStr())) {
			incrementNumber.setStartStr(Mes2DateUtils.getYWW());
			incrementNumber.setSerialNum(1);
		} else if (incrementNumber.getSerialNum() >= incrementNumber.getMaxNum()) {
			incrementNumber.setSerialNum(1);
		} else {
			incrementNumber.setSerialNum(incrementNumber.getSerialNum() + incrementNumber.getIntervalNum());
		}
		update(incrementNumber);
	}

	/**
	 * "RC" + customerCode
	 *
	 * @param customerFTLot
	 * @return
	 */
	@Override
	public String peekRCNumber(CustomerLot customerFTLot) {
		String type = "RC" + customerFTLot.getCustomerNumber().substring(0, 4);
		IncrementNumber in = getRC(type);
		StringBuilder sb = new StringBuilder();
		sb.append(type);
		sb.append(Mes2DateUtils.getY());
		sb.append(SerialNumberUtils.getNumberStr(5, in.getSerialNum()));
		return sb.toString();
	}

	/**
	 * "RC" + customerCode
	 *
	 * @param customerFTLot
	 * @return
	 */
	@Override
	public String nextRCNumber(CustomerLot customerFTLot) {
		String type = "RC" + customerFTLot.getCustomerNumber().substring(0, 4);
		String result = peekRCNumber(customerFTLot);
		incRC(type);
		return result;
	}

	@Override
	public String peekFTLotNumber(CustomerFTLot customerFTLot) {
		if (customerFTLot == null)
			throw new IllegalArgumentException("获取批号参数错误");

		// prefix：Z+PN
		String prefix = "Z" + customerFTLot.getCustomerProductNumber();

		String materialType = "";
		int digitOfSerialNumber = 0; // 用于记录流水号的位数
		switch (customerFTLot.getMaterialType()) {
		case "01":
			// 物料类型为量产,materialType标识为A~Z
			// TODO: 寻找A~Z，具体对应关系存储在字典表中
			digitOfSerialNumber = 2;
			materialType = systemDictionaryApplication.getMassProductionSerialChar(
					prefix + MyDateUtils.getYYWW() + "MPSerialChar", customerFTLot.getCustomerPPO(),
					customerFTLot.getProductVersion()); // 获取量产序列字母A~Z
			break;
		case "02":
			// 物料类型为工程
			materialType = "EN";
			digitOfSerialNumber = 3;
			break;
		case "03":
			// 物料类型为RA
			materialType = "QS";
			digitOfSerialNumber = 3;
			break;
		case "04":
			// 物料类型为RMA
			materialType = "";
			digitOfSerialNumber = 3;
			break;
		case "05":
			// 物料类型为QS
			materialType = "RA";
			digitOfSerialNumber = 3;
			break;
		default:
			throw new UnsupportedOperationException("不支持该物料类型的艾科批号生成");
		}
		// type: prefix + materialType
		String type = prefix + materialType;

		// 艾科批号: prefix(Z+PN) + starstr(Y+WW) + materialType(A~Z/EN/QS/RA) +
		// serialNumber
		IncrementNumber in = getFTLotIncremenNumber(type, digitOfSerialNumber);
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append(in.getStartStr());
		sb.append(materialType);
		sb.append(SerialNumberUtils.getNumberStr(digitOfSerialNumber, in.getSerialNum()));
		return sb.toString();
	}

	@Override
	public String nextMassProductionSerialChar(String type) {
		if (type == null)
			throw new IllegalArgumentException("量产序列字符类型参数错误");

		IncrementNumber incrementNumber = findByType(type);
		if (incrementNumber == null) {
			// 没有该条记录，则创建
			incrementNumber = new IncrementNumber();
			incrementNumber.setIntervalNum(1);
			incrementNumber.setMaxNum((int) 'Z');
			incrementNumber.setSerialNum((int) 'A');
			incrementNumber.setStartStr(Mes2DateUtils.getYWW());
			incrementNumber.setEndStr("");
			incrementNumber.setType(type);
			create(incrementNumber);
		} else {
			if (!Mes2DateUtils.getYWW().equals(incrementNumber.getStartStr())) {
				// 如果日期有变化，则流水号清零
				incrementNumber.setStartStr(Mes2DateUtils.getYWW());
				incrementNumber.setSerialNum((int) 'A');
			} else if (incrementNumber.getSerialNum() > incrementNumber.getMaxNum()) {
				// 如果超过了最大值，reset到'A'
				incrementNumber.setSerialNum((int) 'A');
			} else {
				incrementNumber.setSerialNum(incrementNumber.getSerialNum() + incrementNumber.getIntervalNum());
			}
			update(incrementNumber);
		}
		return String.valueOf((char) incrementNumber.getSerialNum().intValue());
	}

	/**
	 * "interLotNum" + customerCode + customerProductNumber + meterialType
	 *
	 * @param customerFTLot
	 * @return
	 * @deprecated
	 */
	public String peekFTLotNumberDepracated(CustomerFTLot customerFTLot) {
		String prefix = customerFTLot.getCustomerNumber().substring(0, 4) + customerFTLot.getCustomerProductNumber();
		String type = "interLotNum" + prefix + customerFTLot.getMaterialType();
		IncrementNumber in = getFTLotIncremenNumber(type, 3);
		StringBuilder sb = new StringBuilder();
		sb.append("Z");
		sb.append(prefix);
		sb.append(in.getStartStr());
		if (!type.endsWith("01"))
			sb.append(customerFTLot.getMaterialType());
		sb.append(SerialNumberUtils.getNumberStr(3, in.getSerialNum()));
		return sb.toString();
	}

	/**
	 * "interLotNum" + customerCode + customerProductNumber + meterialType
	 *
	 * @param customerFTLot
	 * @return
	 */
	@Override
	public String nextFTLotNumber(CustomerFTLot customerFTLot) {
		// prefix：Z+PN
		String prefix = "Z" + customerFTLot.getCustomerProductNumber();

		String materialType = "";
		int digitOfSerialNumber = 0; // 用于记录流水号的位数
		switch (customerFTLot.getMaterialType()) {
		case "01":
			// 物料类型为量产,materialType标识为A~Z
			// TODO: 寻找A~Z，具体对应关系存储在字典表中
			digitOfSerialNumber = 2;
			materialType = systemDictionaryApplication.getMassProductionSerialChar(
					prefix + MyDateUtils.getYYWW() + "MPSerialChar", customerFTLot.getCustomerPPO(),
					customerFTLot.getProductVersion()); // 获取量产序列字母A~Z
			break;
		case "02":
			// 物料类型为工程
			materialType = "EN";
			digitOfSerialNumber = 3;
			break;
		case "03":
			// 物料类型为RA
			materialType = "QS";
			digitOfSerialNumber = 3;
			break;
		case "04":
			// 物料类型为RMA
			materialType = "";
			digitOfSerialNumber = 3;
			break;
		case "05":
			// 物料类型为QS
			materialType = "RA";
			digitOfSerialNumber = 3;
			break;
		default:
			throw new UnsupportedOperationException("不支持该物料类型的艾科批号生成");
		}
		// type: prefix + materialType
		String type = prefix + materialType;

		String result = peekFTLotNumber(customerFTLot);
		incLot(type);
		return result;
	}

	/**
	 * LotNumber=客户批号
	 *
	 * @param customerCPLot
	 * @return
	 */
	@Override
	public String peekCPLotNumber(CustomerCPLot customerCPLot) {
		return customerCPLot.getCustomerLotNumber();
	}

	@Override
	public String nextCPLotNumber(CustomerCPLot customerCPLot) {
		return customerCPLot.getCustomerLotNumber();
	}

	@Override
	public String peekReelCode(FTInfo ftInfo) {
		String dateCode = new SimpleDateFormat("yyMMdd").format(new Date());
		// 固定码
		String indexCode = ftInfo.getReelFixCode();
		// reel盘数量
		// int reelNumber = Integer.parseInt(ftInfoDTO.getReelQty());
		// 流水号
		String type = "reelCodeNum" + ftInfo.getCustomerProductNumber();
		IncrementNumber in = getReelCode(type);

		String temp = SerialNumberUtils.getNumberStr(String.valueOf(in.getMaxNum()).length(), in.getSerialNum());
		if (temp.length() > 3)
			temp = temp.substring(temp.length() - 3);

		StringBuilder sb = new StringBuilder();
		sb.append(indexCode);
		sb.append("-");
		sb.append(dateCode);
		sb.append(temp);
		return sb.toString();
	}

	@Override
	public String nextReelCode(FTInfo ftInfo) {
		String type = "reelCodeNum" + ftInfo.getCustomerProductNumber();
		String result = peekReelCode(ftInfo);
		if(incReelCode(type)==false){
			result = result.substring(0, result.length()-3);
			result += "000";
			incReelCode(type);		
		}
		return result;
	}

	/**
	 * @return
	 */
	@Override
	public String nextQdnNumber() {
		String type = "QDN";
		IncrementNumber in = getQdn(type);
		StringBuilder sb = new StringBuilder();
		sb.append(Mes2DateUtils.getTodayTime("yyMMdd"));
		sb.append(SerialNumberUtils.getNumberStr(String.valueOf(in.getMaxNum()).length(), in.getSerialNum()));
		incQdn(type);
		return sb.toString();
	}

	/**
	 * FT重工单号生成 R（Rework）+1201（客户编码）+CP（CP/FT）+5（年份）+0001（流水号）
	 *
	 * @param ftLot
	 * @return
	 */
	@Override
	public String nextReworkNumber(FTLot ftLot) {
		String type = "REWORK";
		String customerNumber = ftLot.getCustomerFTLot().getCustomerNumber().split("-")[0];
		IncrementNumber in = getRework(type);
		StringBuilder sb = new StringBuilder();
		sb.append("R");
		sb.append(customerNumber);
		sb.append("FT");
		sb.append(Mes2DateUtils.getY());
		sb.append(SerialNumberUtils.getNumberStr(String.valueOf(in.getMaxNum()).length(), in.getSerialNum()));
		incRework(type); // 递增
		return sb.toString();
	}

	/**
	 * CP重工单号生成 R（Rework）+1201（客户编码）+CP（CP/FT）+5（年份）+0001（流水号）
	 *
	 * @param ftLot
	 * @return
	 */
	@Override
	public String nextReworkNumber(CPLot cpLot) {
		String type = "REWORK";
		String customerNumber = cpLot.getCustomerCPLot().getCustomerNumber().split("-")[0];
		IncrementNumber in = getRework(type);
		StringBuilder sb = new StringBuilder();
		sb.append("R");
		sb.append(customerNumber);
		sb.append("CP");
		sb.append(Mes2DateUtils.getY());
		sb.append(SerialNumberUtils.getNumberStr(String.valueOf(in.getMaxNum()).length(), in.getSerialNum()));
		incRework(type); // 递增
		return sb.toString();
	}

	/**
	 * lat号yyyyMMdd+3位流水号
	 *
	 * @return String
	 */
	@Override
	public String nextLatNumber() {
		String type = "LAT";
		IncrementNumber in = getLat(type);
		StringBuilder sb = new StringBuilder();
		sb.append(Mes2DateUtils.getTodayTime("yyyyMMdd"));
		sb.append(SerialNumberUtils.getNumberStr(String.valueOf(in.getMaxNum()).length(), in.getSerialNum()));
		incLat(type); // 递增
		return sb.toString();
	}

	@Override
	public String peekWorkOrderNumber(Customer customer) {
		String type = customer.getNumber();
		IncrementNumber incrementNumber = getWorkOrder(type);
		return type + incrementNumber.getStartStr() + SerialNumberUtils.getNumberStr(3, incrementNumber.getSerialNum());
	}

	@Override
	public String nextWorkOrderNumber(Customer customer) {
		String type = customer.getNumber();
		String result = peekWorkOrderNumber(customer);
		incWorkOrder(type);
		return result;
	}
}
