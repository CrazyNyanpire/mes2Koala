package org.seu.acetec.mes2Koala.facade.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FTProcessDTO implements Serializable {

    private Date createTimestamp;

    private Date lastModifyTimestamp;
    
    private String createEmployNo;

    private String lastModifyEmployNo;

    private Long id;

    private int version;

    private String content;

    private String testType;

    private String runCard;

    private String name;

    private String processState;

    private String testContent;

    private String specialForm;

    private String note;
    
    private List<FTNodeDTO> ftNodeDTOs = new ArrayList<FTNodeDTO>();
    
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

	public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTestType() {
        return this.testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getRunCard() {
        return this.runCard;
    }

    public void setRunCard(String runCard) {
        this.runCard = runCard;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessState() {
        return this.processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public List<FTNodeDTO> getFtNodeDTOs() {
        return ftNodeDTOs;
    }

    public void setFtNodeDTOs(List<FTNodeDTO> ftNodeDTOs) {
        this.ftNodeDTOs = ftNodeDTOs;
    }

    public String getTestContent() {
        return testContent;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    public String getSpecialForm() {
        return specialForm;
    }

    public void setSpecialForm(String specialForm) {
        this.specialForm = specialForm;
    }

    public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
        FTProcessDTO other = (FTProcessDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}