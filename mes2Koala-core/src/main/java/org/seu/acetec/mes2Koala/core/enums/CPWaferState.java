package org.seu.acetec.mes2Koala.core.enums;

/**
 * @author harlow
 * @version 2016/4/13
 */
public enum CPWaferState {
    UNPASS,  // 未通过
    PASSED;      // 已通过

    public static int getIntValue(CPWaferState state) {
        switch (state) {
            case UNPASS:
                return 0;
            case PASSED:
                return 1;
            default:
                throw new IllegalArgumentException("");
        }
    }

    public static CPWaferState fromIntValue(int value) {
        switch (value) {
            case 0:
                return UNPASS;
            case 1:
                return PASSED;
            default:
                throw new IllegalArgumentException("");
        }
    }
}
