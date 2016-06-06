package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "E_FTSTATISTICS")
@Access(AccessType.PROPERTY)
public class FT_Statistics extends MES2AbstractEntity {

    private static final long serialVersionUID = -8481838615689790645L;

    private int inpt;
    private int pass;
    private int fail;
    private double yield;
    private int rr;
    private int loss;
    private int scrap;
    private int crack;
    private int pin;
    private int SMDfail;
    private int other;

    public int getInpt() {
        return inpt;
    }

    public void setInpt(int inpt) {
        this.inpt = inpt;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public int getRr() {
        return rr;
    }

    public void setRr(int rr) {
        this.rr = rr;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getScrap() {
        return scrap;
    }

    public void setScrap(int scrap) {
        this.scrap = scrap;
    }

    public int getCrack() {
        return crack;
    }

    public void setCrack(int crack) {
        this.crack = crack;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getSMDfail() {
        return SMDfail;
    }

    public void setSMDfail(int sMDfail) {
        this.SMDfail = sMDfail;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }
}
