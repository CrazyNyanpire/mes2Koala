package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;

/**
 * Created by yuxiangque on 2016/3/14.
 */

public class FTFinishNodeStatisticsBinDTO implements Serializable, Comparable<FTFinishNodeStatisticsBinDTO> {
    String type;
    String site;
    String value;
    String quality;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    @Override
    public int compareTo(FTFinishNodeStatisticsBinDTO o) {
        return type.compareTo(o.type);
    }
}