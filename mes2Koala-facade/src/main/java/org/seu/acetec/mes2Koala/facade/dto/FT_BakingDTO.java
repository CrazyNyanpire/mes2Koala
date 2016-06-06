package org.seu.acetec.mes2Koala.facade.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import java.io.Serializable;

public class FT_BakingDTO implements Serializable {

    private Long id;

    private int version;

    private String timeLimit;

    private String ovenTemperature;

    private String ovenNumber;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private Date timeOut;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private Date timeIn;

    private Date timeOutEnd;

    private Date timeInEnd;

    private String timeIn;

    private String timeOut;

    private FT_ResultDTO ftResultDTO;

    public FT_ResultDTO getFtResultDTO() {
        return ftResultDTO;
    }

    public void setFtResultDTO(FT_ResultDTO ftResultDTO) {
        this.ftResultDTO = ftResultDTO;
    }

    public String getTimeLimit() {
        return this.timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getOvenTemperature() {
        return this.ovenTemperature;
    }

    public void setOvenTemperature(String ovenTemperature) {
        this.ovenTemperature = ovenTemperature;
    }

    public String getOvenNumber() {
        return this.ovenNumber;
    }

    public void setOvenNumber(String ovenNumber) {
        this.ovenNumber = ovenNumber;
    }

    public String getTimeOut() {
        return this.timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public Date getTimeOutEnd() {
        return this.timeOutEnd;
    }

    public void setTimeOutEnd(Date timeOutEnd) {
        this.timeOutEnd = timeOutEnd;
    }

    public String getTimeIn() {
        return this.timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeInEnd() {
        return this.timeInEnd;
    }

    public void setTimeInEnd(Date timeInEnd) {
        this.timeInEnd = timeInEnd;
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
        FT_BakingDTO other = (FT_BakingDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}