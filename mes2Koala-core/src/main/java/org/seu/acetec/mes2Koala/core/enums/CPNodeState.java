package org.seu.acetec.mes2Koala.core.enums;

/**
 * @author yuxiangque
 * @version 2016/3/30
 */
public enum CPNodeState {
    UNREACHED,  // 未到这个站
    TO_START,   // 到这个站没进站
    STARTED,    // 未出站
    ENDED;      // 已出站

    public static int getIntValue(CPNodeState state) {
        switch (state) {
            case UNREACHED:
                return 0;
            case TO_START:
                return 1;
            case STARTED:
                return 2;
            case ENDED:
                return 3;
            default:
                throw new IllegalArgumentException("");
        }
    }

    public static CPNodeState fromIntValue(int value) {
        switch (value) {
            case 0:
                return UNREACHED;
            case 1:
                return TO_START;
            case 2:
                return STARTED;
            case 3:
                return ENDED;
            default:
                throw new IllegalArgumentException("");
        }
    }
}
