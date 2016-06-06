package org.seu.acetec.mes2Koala.core.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * @author yuxiangque
 * @version 2016/3/28
 */
@Entity
@Table(name = "E_FT_PROCESS")
@Access(AccessType.PROPERTY)
@PrimaryKeyJoinColumn(name = "PROCESS_ID")
public class FTProcess extends Process {

    private FTLot ftLot;
    private List<FTNode> ftNodes;
    
    //备注
    private String note;
    
    /**
     * 根据
     *
     * @param processTemplate
     * @return
     */
    public static FTProcess instanceTemplate(ProcessTemplate processTemplate) {
        FTProcess process = new FTProcess();
        process.setName(processTemplate.getName());
        process.setContent(processTemplate.getContent());
        process.setRunCard(processTemplate.getRuncard());
        return process;
    }


    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "LOT_ID")
    public FTLot getFtLot() {
        return ftLot;
    }

    public void setFtLot(FTLot ftLot) {
        this.ftLot = ftLot;
    }

    @OneToMany(mappedBy = "ftProcess", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    public List<FTNode> getFtNodes() {
        return ftNodes;
    }

    public void setFtNodes(List<FTNode> ftNodes) {
        this.ftNodes = ftNodes;
    }


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}
}
