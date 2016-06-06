package org.seu.acetec.mes2Koala.facade.dto;

import org.seu.acetec.mes2Koala.core.domain.SBLTemplate;

import java.io.Serializable;

public class SBLTemplateDTO implements Serializable {

    /**
     * {@link org.seu.acetec.mes2Koala.core.domain.MES2AbstractEntity}
     */
    private Long id;
    private int version;

    /**
     * {@link SBLTemplate}
     */
    private String binType;
    private Double lowerLimit;
    private Double upperLimit;
    private String binQuality;
    private String site;
    private String nodeName;

    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getBinType() {
        return this.binType;
    }

    public void setBinType(String BinType) {
        this.binType = BinType;
    }

    public Double getUpperLimit() {
        return this.upperLimit;
    }

    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Double getLowerLimit() {
        return this.lowerLimit;
    }

    public void setLowerLimit(Double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getBinQuality() {
        return this.binQuality;
    }

    public void setBinQuality(String BinQuality) {
        this.binQuality = BinQuality;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SBLTemplateDTO other = (SBLTemplateDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}