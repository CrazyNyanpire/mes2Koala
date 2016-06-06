package org.seu.acetec.mes2Koala.core.enums;

public enum TransferStorageState {
在库,	 
出库未签核,
出库已签核,
丢料,
借料,
重工;

public static String getStringValue(TransferStorageState testtype) {
	switch (testtype) {
	case 在库:
		return "01";
	case 出库未签核:
		return "02";
	case 出库已签核:
		return "03";
	case 丢料:
		return "04";
	case 借料:
		return "05";
	case 重工:
		return "06";
	default:
		throw new IllegalArgumentException("");
	}
}

public static TransferStorageState fromStringValue(String value) {
	if ( null == value )
		return null;
	switch (value) {
	case "01":
		return 在库;
	case "02":
		return 出库未签核;
	case "03":
		return 出库已签核;
	case "04":
		return 丢料;
	case "05":
		return 借料;
	case "06":
		return 重工;
	default:
		throw new IllegalArgumentException("");
	}
}
}
