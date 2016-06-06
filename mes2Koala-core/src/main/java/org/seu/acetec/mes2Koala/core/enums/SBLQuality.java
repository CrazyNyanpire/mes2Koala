package org.seu.acetec.mes2Koala.core.enums;

/**
 * @author yuxiangque
 * @version 2016/3/30
 */
public enum SBLQuality {
    PASS,
    FAIL;

    public static String getStringValue(SBLQuality quality) {
        switch (quality) {
            case PASS:
                return "pass";
            case FAIL:
                return "fail";
            default:
                throw new IllegalArgumentException("");
        }
    }

    public static SBLQuality fromStringValue(String value) {
        if ("pass".equalsIgnoreCase(value)) {
            return PASS;
        } else if ("fail".equalsIgnoreCase(value)) {
            return FAIL;
        } else {
            throw new IllegalArgumentException("");
        }
    }
}