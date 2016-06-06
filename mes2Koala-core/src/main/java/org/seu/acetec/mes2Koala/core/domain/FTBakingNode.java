package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "E_FT_BAKING_NODE")
@Access(AccessType.PROPERTY)
@PrimaryKeyJoinColumn(name = "FT_NODE_ID")
public class FTBakingNode extends FTNode {

    private static final long serialVersionUID = 5237107469850799981L;
    private String ovenNumber;
    private String ovenTemperature;
    private Date timeIn;
    private Date timeOut;
    private String timeLimit;

    public String getOvenNumber() {
        return ovenNumber;
    }

    public void setOvenNumber(String ovenNumber) {
        this.ovenNumber = ovenNumber;
    }

    public String getOvenTemperature() {
        return ovenTemperature;
    }

    public void setOvenTemperature(String ovenTemperature) {
        this.ovenTemperature = ovenTemperature;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FTBakingNode ft_baking = (FTBakingNode) o;

        if (ovenNumber != null ? !ovenNumber.equals(ft_baking.ovenNumber) : ft_baking.ovenNumber != null) return false;
        if (ovenTemperature != null ? !ovenTemperature.equals(ft_baking.ovenTemperature) : ft_baking.ovenTemperature != null)
            return false;
        if (timeIn != null ? !timeIn.equals(ft_baking.timeIn) : ft_baking.timeIn != null) return false;
        if (timeOut != null ? !timeOut.equals(ft_baking.timeOut) : ft_baking.timeOut != null) return false;
        return timeLimit != null ? timeLimit.equals(ft_baking.timeLimit) : ft_baking.timeLimit == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (ovenNumber != null ? ovenNumber.hashCode() : 0);
        result = 31 * result + (ovenTemperature != null ? ovenTemperature.hashCode() : 0);
        result = 31 * result + (timeIn != null ? timeIn.hashCode() : 0);
        result = 31 * result + (timeOut != null ? timeOut.hashCode() : 0);
        result = 31 * result + (timeLimit != null ? timeLimit.hashCode() : 0);
        return result;
    }
}
