package org.seu.acetec.mes2Koala.facade.dto;


import java.io.Serializable;
import java.util.Date;

public class BomUseDTO implements Serializable {

    private Long id;

    private int version;

    private Date lastModifyTimestamp;

    private String lastModifyEmployNo;

    private String createEmployNo;

    private Integer logic;

    private Date createTimestamp;

    private Boolean selected;

    private BomTemplateDTO bomTemplateDTO;

    public BomTemplateDTO getBomTemplateDTO() {
        return bomTemplateDTO;
    }

    public void setBomTemplateDTO(BomTemplateDTO bomTemplateDTO) {
        this.bomTemplateDTO = bomTemplateDTO;
    }

    public Date getLastModifyTimestamp() {
        return this.lastModifyTimestamp;
    }

    public void setLastModifyTimestamp(Date lastModifyTimestamp) {
        this.lastModifyTimestamp = lastModifyTimestamp;
    }

    public String getLastModifyEmployNo() {
        return this.lastModifyEmployNo;
    }

    public void setLastModifyEmployNo(String lastModifyEmployNo) {
        this.lastModifyEmployNo = lastModifyEmployNo;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getCreateEmployNo() {
        return this.createEmployNo;
    }

    public void setCreateEmployNo(String createEmployNo) {
        this.createEmployNo = createEmployNo;
    }

    public Integer getLogic() {
        return this.logic;
    }

    public void setLogic(Integer logic) {
        this.logic = logic;
    }

    public Date getCreateTimestamp() {
        return this.createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
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
        BomUseDTO other = (BomUseDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}