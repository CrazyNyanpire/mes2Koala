package org.seu.acetec.mes2Koala.core.enums;

/**
 * @author harlow
 * @version 2016/5/30
 */
public enum CPWaferCheck {
	UNCHECKED,// 未通过
	CHECKED; // 已通过

	public static int getIntValue(CPWaferCheck state) {
		switch (state) {
		case UNCHECKED:
			return 0;
		case CHECKED:
			return 1;
		default:
			throw new IllegalArgumentException("");
		}
	}

	public static CPWaferCheck fromIntValue(int value) {
		switch (value) {
		case 0:
			return UNCHECKED;
		case 1:
			return CHECKED;
		default:
			throw new IllegalArgumentException("");
		}
	}
}
