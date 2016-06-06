package org.seu.acetec.mes2Koala.core.domain;

import org.seu.acetec.mes2Koala.core.enums.CPNodeState;

import javax.persistence.*;

/**
 * @author 阙宇翔
 * @version 2016/3/8
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "E_CP_NODE")
@Access(AccessType.PROPERTY)
public class CPNode extends MES2AbstractEntity implements Comparable<CPNode> {

    private int turn;

    private CPNodeState state;

    private String name;

    private CPProcess cpProcess;

    public static CPNode instanceCPNode(String cpNodeName) {
    	/*
        if (cpNodeName.startsWith("IQC")) {
            return new CPPassNode();
        } else if (cpNodeName.startsWith("FQC")) {
            return new CPPassNode();
        } else 
        	*/
    	if (cpNodeName.startsWith("CP")&&cpNodeName.length()<5) {
            return new CPTestingNode();            
        } 
    	/*else if (cpNodeName.startsWith("Packing")) {
            return new CPPassNode();
        } else if (cpNodeName.startsWith("OQC")) {
            return new CPPassNode();
        }
        */
        return new CPNode();
    }

    @Enumerated(EnumType.ORDINAL)
    public CPNodeState getState() {
        return state;
    }

    public void setState(CPNodeState state) {
        this.state = state;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "PROCESS_ID")
    public CPProcess getCpProcess() {
        return cpProcess;
    }

    public void setCpProcess(CPProcess cpProcess) {
        this.cpProcess = cpProcess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public int compareTo(CPNode o) {
        return turn - o.turn;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }


}
