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
@Table(name = "E_CP_PROCESS")
@Access(AccessType.PROPERTY)
@PrimaryKeyJoinColumn(name = "PROCESS_ID")
public class CPProcess extends Process {

    private CPLot cpLot;
    private List<CPNode> cpNodes;
    private Boolean isTransferStorage;

    /**
     * 根据
     *
     * @param processTemplate
     * @return
     */
    public static CPProcess instanceTemplate(ProcessTemplate processTemplate) {
        CPProcess process = new CPProcess();
        process.setName(processTemplate.getName());
        process.setContent(processTemplate.getContent());
        process.setRunCard(processTemplate.getRuncard());
        return process;
    }

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "LOT_ID")
    public CPLot getCpLot() {
        return cpLot;
    }

    public void setCpLot(CPLot cpLot) {
        this.cpLot = cpLot;
    }

    @OneToMany(mappedBy = "cpProcess", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    public List<CPNode> getCpNodes() {
        return cpNodes;
    }

    public void setCpNodes(List<CPNode> cpNodes) {
        this.cpNodes = cpNodes;
    }

	public Boolean getIsTransferStorage() {
		return isTransferStorage;
	}

	public void setIsTransferStorage(Boolean isTransferStorage) {
		this.isTransferStorage = isTransferStorage;
	}
}
