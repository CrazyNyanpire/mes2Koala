package org.seu.acetec.mes2Koala.core.domain;


import javax.persistence.*;

/**
 * Created by LCN on 2016/3/6.
 */
@Entity
@Table(name = "E_ProcessInfo")
@Access(AccessType.PROPERTY)
public class ProcessInfo extends MES2AbstractEntity {

    /**
     * 用于和customerftlot的id绑定  紧用于测试
     */

    private String infoId;

    // 必须数据库对此字段进行初始化  否则会失败
//    @Lob
    @Column(columnDefinition = "text")
//    @ElementCollection(fetch = FetchType.LAZY)
    @Basic(fetch = FetchType.LAZY)
    private String IQC;


    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }


    public String getIQC() {
        return IQC;
    }

    public void setIQC(String IQC) {
        this.IQC = IQC;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProcessInfo that = (ProcessInfo) o;

        if (infoId != null ? !infoId.equals(that.infoId) : that.infoId != null) return false;
        return IQC != null ? IQC.equals(that.IQC) : that.IQC == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (infoId != null ? infoId.hashCode() : 0);
        result = 31 * result + (IQC != null ? IQC.hashCode() : 0);
        return result;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }
}
