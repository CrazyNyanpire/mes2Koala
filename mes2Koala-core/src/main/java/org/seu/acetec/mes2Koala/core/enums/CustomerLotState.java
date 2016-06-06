package org.seu.acetec.mes2Koala.core.enums;

/**
 * @author yuxiangque
 * @version 2016/3/30
 */
public enum CustomerLotState {
    Unordered,  // 未下单
    Ordered;    // 已下单

    public static int getIntValue(CustomerLotState state) {
        switch (state) {
            case Unordered:
                return 0;
            case Ordered:
                return 1;
            default:
                throw new IllegalArgumentException("");
        }
    }

    public static CustomerLotState fromIntValue(Integer value) {
    	if ( null == value )
    		return null;
        switch (value) {
            case 0:
                return Unordered;
            case 1:
                return Ordered;
            default:
                throw new IllegalArgumentException("");
        }
    }
} 
