package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

@Entity
@Table(name = "E_FT_PASS_NODE")
@Access(AccessType.PROPERTY)
@PrimaryKeyJoinColumn(name = "FT_NODE_ID")
public class FTPassNode extends FTNode {

    private static final long serialVersionUID = 398031567253624020L;

    private String site;
    private String passResult;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPassResult() {
        return passResult;
    }

    public void setPassResult(String result) {
        this.passResult = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FTPassNode that = (FTPassNode) o;

        if (site != null ? !site.equals(that.site) : that.site != null) return false;
        return passResult != null ? passResult.equals(that.passResult) : that.passResult == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (site != null ? site.hashCode() : 0);
        result = 31 * result + (passResult != null ? passResult.hashCode() : 0);
        return result;
    }
}
