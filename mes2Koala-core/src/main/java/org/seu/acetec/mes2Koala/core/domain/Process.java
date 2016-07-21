package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "E_PROCESS")
@Access(AccessType.PROPERTY)
public class Process extends MES2AbstractEntity {

    private static final long serialVersionUID = -5973428584073300750L;

    private String name;
    private String content;

    private String testType;
    private String processState;
    private String runCard;

    private String testContent;
    private String specialForm;
    
    private String lotNo;//改变PID时备份和lot的关联


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getRunCard() {
        return runCard;
    }

    public void setRunCard(String runCard) {
        this.runCard = runCard;
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

    public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	@Override
    public String[] businessKeys() {
        return new String[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Process process = (Process) o;

        if (name != null ? !name.equals(process.name) : process.name != null) return false;
        if (content != null ? !content.equals(process.content) : process.content != null) return false;
        if (testType != null ? !testType.equals(process.testType) : process.testType != null) return false;
        if (processState != null ? !processState.equals(process.processState) : process.processState != null)
            return false;
        if (runCard != null ? !runCard.equals(process.runCard) : process.runCard != null) return false;
        if (testContent != null ? !testContent.equals(process.testContent) : process.testContent != null) return false;
        if (specialForm != null ? !specialForm.equals(process.specialForm) : process.specialForm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (testType != null ? testType.hashCode() : 0);
        result = 31 * result + (processState != null ? processState.hashCode() : 0);
        result = 31 * result + (runCard != null ? runCard.hashCode() : 0);
        result = 31 * result + (testContent != null ? testContent.hashCode() : 0);
        result = 31 * result + (specialForm != null ? specialForm.hashCode() : 0);
        return result;
    }
}
