package org.seu.acetec.mes2Koala.core.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.seu.acetec.mes2Koala.core.enums.FTNodeState;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "E_FT_NODE")
@Access(AccessType.PROPERTY)
public class FTNode extends MES2AbstractEntity implements Comparable<FTNode> {


    private static final long serialVersionUID = 6696967994464013929L;
    private int turn;
    private String name;

    // 未到这个站0，到这个站没进站1，进站没出站2，已经出站3
    private FTNodeState state;
    private FTResult result;
    private FTProcess ftProcess;

    private FTStatistics statistics;
    
    // 绑定的SBL设定
    private List<SBL> sbls;

    // 绑定的EQC设定
    private List<EQC> eqcs;
    
    public static FTNode instanceFTNode(String ftNodeName) {
        if (ftNodeName.startsWith("IQC")) {
            return new FTIQCNode();
        } else if (ftNodeName.startsWith("Baking")) {
            return new FTBakingNode();
        } else if (ftNodeName.startsWith("Test-")) {
            return new FTComposedTestNode();
        } else if (ftNodeName.startsWith("GuTest")) {
            return new FTGuTestNode();
        } else if (ftNodeName.startsWith("Finish")) {
            return new FTFinishNode();
        } else if (ftNodeName.startsWith("FVI")) {
            return new FTPassNode();
        } else if (ftNodeName.startsWith("FQC")) {
            return new FTPassNode();
        } else if (ftNodeName.startsWith("Packing")) {
            return new FTPassNode();
        } else if (ftNodeName.startsWith("OQC")) {
            return new FTPassNode();
        }
        return null;
    }

    @Enumerated(EnumType.ORDINAL)
    public FTNodeState getState() {
        return state;
    }

    public void setState(FTNodeState state) {
        this.state = state;
    }

    @OneToMany(mappedBy = "ftNode", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    public List<SBL> getSbls() {
        return sbls;
    }

    public void setSbls(List<SBL> sbls) {
        this.sbls = sbls;
    }

    @OneToMany(mappedBy = "ftNode", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    public List<EQC> getEqcs() {
        return eqcs;
    }

    public void setEqcs(List<EQC> eqcs) {
        this.eqcs = eqcs;
    }
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FT_RESULT_ID")
    public FTResult getResult() {
        return result;
    }

    public void setResult(FTResult result) {
        this.result = result;
    }
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "FT_STATISTICS_ID")
    public FTStatistics getStatistics() {
		return statistics;
	}

	public void setStatistics(FTStatistics statistics) {
		this.statistics = statistics;
	}
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "PROCESS_ID")
    public FTProcess getFtProcess() {
        return ftProcess;
    }

    public void setFtProcess(FTProcess ftProcess) {
        this.ftProcess = ftProcess;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }

    // 根据turn来决定顺序
    @Override
    public int compareTo(FTNode o) {
        return this.getTurn() - o.getTurn();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FTNode ftNode = (FTNode) o;

        if (turn != ftNode.turn) return false;
        if (name != null ? !name.equals(ftNode.name) : ftNode.name != null) return false;
        if (state != ftNode.state) return false;
        if (result != null ? !result.equals(ftNode.result) : ftNode.result != null) return false;
        if (ftProcess != null ? !ftProcess.equals(ftNode.ftProcess) : ftNode.ftProcess != null) return false;
        return sbls != null ? sbls.equals(ftNode.sbls) : ftNode.sbls == null;

    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + turn;
        result1 = 31 * result1 + (name != null ? name.hashCode() : 0);
        result1 = 31 * result1 + (state != null ? state.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (ftProcess != null ? ftProcess.hashCode() : 0);
        result1 = 31 * result1 + (sbls != null ? sbls.hashCode() : 0);
        return result1;
    }
}
