package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

/**
 * @author 阙宇翔
 * @version 2016/3/5
 */
@Entity
@Table(name = "E_FT_TEST")
@Access(AccessType.PROPERTY)
public class FTTest extends MES2AbstractEntity implements Comparable<FTTest> {

    int turn;
    String name;
    int state;
    String note;
    FTResult result;
    FTComposedTestNode ftComposedTestNode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FT_RESULT_ID")
    public FTResult getResult() {
        return result;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FT_COMPOSED_TEST_ID")
    public FTComposedTestNode getFtComposedTestNode() {
        return ftComposedTestNode;
    }

    public void setFtComposedTestNode(FTComposedTestNode ftComposedTestNode) {
        this.ftComposedTestNode = ftComposedTestNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setResult(FTResult result) {
        this.result = result;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }

    @Override
    public int compareTo(FTTest o) {
        return this.turn - o.turn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FTTest test = (FTTest) o;

        if (turn != test.turn) return false;
        if (state != test.state) return false;
        if (name != null ? !name.equals(test.name) : test.name != null) return false;
        if (note != null ? !note.equals(test.note) : test.note != null) return false;
        if (result != null ? !result.equals(test.result) : test.result != null) return false;
        return ftComposedTestNode != null ? ftComposedTestNode.equals(test.ftComposedTestNode) : test.ftComposedTestNode == null;

    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + turn;
        result1 = 31 * result1 + (name != null ? name.hashCode() : 0);
        result1 = 31 * result1 + state;
        result1 = 31 * result1 + (note != null ? note.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (ftComposedTestNode != null ? ftComposedTestNode.hashCode() : 0);
        return result1;
    }
}
