package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FTNodeDTO implements Serializable {

    private Date createTimestamp;

    private Date lastModifyTimestamp;

    private String createEmployNo;

    private String lastModifyEmployNo;

    private Long id;

    private int version;

    private String name;

    private int ftState;

    private FT_IQCDTO ftIQCDTO;

    private FT_BakingDTO ftBakingDTO;

    private FT_TestDTO ftTestDTO;

    private FT_PassNodeDTO ftPassNodeDTO;

    private FT_GuTestDTO ftGuTestDTO;

    private FT_FinishDTO ftFinishDTO;

    private List<SBLDTO> sblDTOs;    

    public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getLastModifyTimestamp() {
		return lastModifyTimestamp;
	}

	public void setLastModifyTimestamp(Date lastModifyTimestamp) {
		this.lastModifyTimestamp = lastModifyTimestamp;
	}

	public String getCreateEmployNo() {
		return createEmployNo;
	}

	public void setCreateEmployNo(String createEmployNo) {
		this.createEmployNo = createEmployNo;
	}

	public String getLastModifyEmployNo() {
		return lastModifyEmployNo;
	}

	public void setLastModifyEmployNo(String lastModifyEmployNo) {
		this.lastModifyEmployNo = lastModifyEmployNo;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFtState() {
        return ftState;
    }

    public void setFtState(int ftState) {
        this.ftState = ftState;
    }

    public FT_IQCDTO getFtIQCDTO() {
        return ftIQCDTO;
    }

    public void setFtIQCDTO(FT_IQCDTO ftIQCDTO) {
        this.ftIQCDTO = ftIQCDTO;
    }

    public FT_BakingDTO getFtBakingDTO() {
        return ftBakingDTO;
    }

    public void setFtBakingDTO(FT_BakingDTO ftBakingDTO) {
        this.ftBakingDTO = ftBakingDTO;
    }

    public FT_TestDTO getFtTestDTO() {
        return ftTestDTO;
    }

    public void setFtTestDTO(FT_TestDTO ftTestDTO) {
        this.ftTestDTO = ftTestDTO;
    }

    public FT_PassNodeDTO getFtPassNodeDTO() {
        return ftPassNodeDTO;
    }

    public void setFtPassNodeDTO(FT_PassNodeDTO ftPassNodeDTO) {
        this.ftPassNodeDTO = ftPassNodeDTO;
    }

    public FT_GuTestDTO getFtGuTestDTO() {
        return ftGuTestDTO;
    }

    public void setFtGuTestDTO(FT_GuTestDTO ftGuTestDTO) {
        this.ftGuTestDTO = ftGuTestDTO;
    }

    public FT_FinishDTO getFtFinishDTO() {
        return ftFinishDTO;
    }

    public void setFtFinishDTO(FT_FinishDTO ftFinishDTO) {
        this.ftFinishDTO = ftFinishDTO;
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

    public List<SBLDTO> getSblDTOs() {
        return sblDTOs;
    }

    public void setSblDTOs(List<SBLDTO> sblDTOs) {
        this.sblDTOs = sblDTOs;
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
        FTNodeDTO other = (FTNodeDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}