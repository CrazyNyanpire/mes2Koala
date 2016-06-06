package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuxiangque on 2016/3/15.
 */
public class FTFinishNodeStatisticsDTO implements Serializable {
    List<FTFinishNodeStatisticsBinDTO> bins;
    String loss;
    String other;
    String backUp;
    String markF;

    public List<FTFinishNodeStatisticsBinDTO> getBins() {
        return bins;
    }

    public void setBins(List<FTFinishNodeStatisticsBinDTO> bins) {
        this.bins = bins;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getBackUp() {
        return backUp;
    }

    public void setBackUp(String backUp) {
        this.backUp = backUp;
    }

    public String getMarkF() {
        return markF;
    }

    public void setMarkF(String markF) {
        this.markF = markF;
    }
}
