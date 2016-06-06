package org.seu.acetec.mes2Koala.core.enums;

public enum TestTypeForWms {
	CP,
	FT;
	public static String getStringValue(TestTypeForWms testtype) {
		switch (testtype) {
		case CP:
			return "01";
		case FT:
			return "02";
		default:
			throw new IllegalArgumentException("");
		}
	}

	public static TestTypeForWms fromStringValue(String value) {
		if ( null == value )
			return null;
		switch (value) {
		case "01":
			return CP;
		case "02":
			return FT;
		default:
			throw new IllegalArgumentException("");
		}
	}
}
