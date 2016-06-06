package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "E_FT_RESULT")
@Access(AccessType.PROPERTY)
public class FTResult extends MES2AbstractEntity {

    private static final long serialVersionUID = 149099791756764512L;

    private String yield = "0";
    private String loss = "0";
    private String backUp = "0";
    private String other = "0";
    private String resultSum = "0";
    private String markF = "0";
    private String fail = "0";
    private String lat = "0";

    private String bin1 = "-1";
    private String bin2 = "-1";
    private String bin3 = "-1";
    private String bin4 = "-1";
    private String bin5 = "-1";
    private String bin6 = "-1";
    private String bin7 = "-1";
    private String bin8 = "-1";
    private String bin9 = "-1";
    private String bin10 = "-1";
    private String bin11 = "-1";
    private String bin12 = "-1";
    private String bin13 = "-1";
    private String bin14 = "-1";
    private String bin15 = "-1";
    private String bin16 = "-1";
    private String bin17 = "-1";
    private String bin18 = "-1";
    private String bin19 = "-1";
    private String bin20 = "-1";

    public static int[] emptyBins() {
        int[] emptyBins = new int[20];
        for (int i = 0; i < emptyBins.length; i++) {
            emptyBins[i] = -1;
        }
        return emptyBins;
    }

    public static void setBins(FTResult ftResult, int[] bins) {
        ftResult.setBin1(String.valueOf(bins[0]));
        ftResult.setBin2(String.valueOf(bins[1]));
        ftResult.setBin3(String.valueOf(bins[2]));
        ftResult.setBin4(String.valueOf(bins[3]));
        ftResult.setBin5(String.valueOf(bins[4]));
        ftResult.setBin6(String.valueOf(bins[5]));
        ftResult.setBin7(String.valueOf(bins[6]));
        ftResult.setBin8(String.valueOf(bins[7]));
        ftResult.setBin9(String.valueOf(bins[8]));
        ftResult.setBin10(String.valueOf(bins[9]));
        ftResult.setBin11(String.valueOf(bins[10]));
        ftResult.setBin12(String.valueOf(bins[11]));
        ftResult.setBin13(String.valueOf(bins[12]));
        ftResult.setBin14(String.valueOf(bins[13]));
        ftResult.setBin15(String.valueOf(bins[14]));
        ftResult.setBin16(String.valueOf(bins[15]));
        ftResult.setBin17(String.valueOf(bins[16]));
        ftResult.setBin18(String.valueOf(bins[17]));
        ftResult.setBin19(String.valueOf(bins[18]));
        ftResult.setBin20(String.valueOf(bins[9]));
    }

    public static int[] getBins(FTResult ftResult) {
        int[] bins = new int[20];
        bins[0] = Integer.parseInt(ftResult.getBin1());
        bins[1] = Integer.parseInt(ftResult.getBin2());
        bins[2] = Integer.parseInt(ftResult.getBin3());
        bins[3] = Integer.parseInt(ftResult.getBin4());
        bins[4] = Integer.parseInt(ftResult.getBin5());
        bins[5] = Integer.parseInt(ftResult.getBin6());
        bins[6] = Integer.parseInt(ftResult.getBin7());
        bins[7] = Integer.parseInt(ftResult.getBin8());
        bins[8] = Integer.parseInt(ftResult.getBin9());
        bins[9] = Integer.parseInt(ftResult.getBin10());
        bins[10] = Integer.parseInt(ftResult.getBin11());
        bins[11] = Integer.parseInt(ftResult.getBin12());
        bins[12] = Integer.parseInt(ftResult.getBin13());
        bins[13] = Integer.parseInt(ftResult.getBin14());
        bins[14] = Integer.parseInt(ftResult.getBin15());
        bins[15] = Integer.parseInt(ftResult.getBin16());
        bins[16] = Integer.parseInt(ftResult.getBin17());
        bins[17] = Integer.parseInt(ftResult.getBin18());
        bins[18] = Integer.parseInt(ftResult.getBin19());
        bins[19] = Integer.parseInt(ftResult.getBin20());
        return bins;
    }

    // @Column(columnDefinition = "varchar(36) default '0' ")
    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    // @Column(columnDefinition = "varchar(36) default '0' ")
    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    // @Column(columnDefinition = "varchar(36) default '0' ")
    public String getBackUp() {
        return backUp;
    }

    public void setBackUp(String backUp) {
        this.backUp = backUp;
    }

    // @Column(columnDefinition = "varchar(36) default '0' ")
    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    // @Column(columnDefinition = "varchar(36) default '0' ")
    public String getResultSum() {
        return resultSum;
    }

    public void setResultSum(String resultSum) {
        this.resultSum = resultSum;
    }

    // @Column(columnDefinition = "varchar(36) default '0' ")
    public String getMarkF() {
        return markF;
    }

    public void setMarkF(String markF) {
        this.markF = markF;
    }

    // @Column(columnDefinition = "varchar(36) default '0' ")
    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin1() {
        return bin1;
    }

    public void setBin1(String bin1) {
        this.bin1 = bin1;
    }

    // // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin2() {
        return bin2;
    }

    public void setBin2(String bin2) {
        this.bin2 = bin2;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin3() {
        return bin3;
    }

    public void setBin3(String bin3) {
        this.bin3 = bin3;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin4() {
        return bin4;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public void setBin4(String bin4) {
        this.bin4 = bin4;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin5() {
        return bin5;
    }

    public void setBin5(String bin5) {
        this.bin5 = bin5;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin6() {
        return bin6;
    }

    public void setBin6(String bin6) {
        this.bin6 = bin6;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin7() {
        return bin7;
    }

    public void setBin7(String bin7) {
        this.bin7 = bin7;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin8() {
        return bin8;
    }

    public void setBin8(String bin8) {
        this.bin8 = bin8;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin9() {
        return bin9;
    }

    public void setBin9(String bin9) {
        this.bin9 = bin9;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin10() {
        return bin10;
    }

    public void setBin10(String bin10) {
        this.bin10 = bin10;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin11() {
        return bin11;
    }

    public void setBin11(String bin11) {
        this.bin11 = bin11;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin12() {
        return bin12;
    }

    public void setBin12(String bin12) {
        this.bin12 = bin12;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin13() {
        return bin13;
    }

    public void setBin13(String bin13) {
        this.bin13 = bin13;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin14() {
        return bin14;
    }

    public void setBin14(String bin14) {
        this.bin14 = bin14;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin15() {
        return bin15;
    }

    public void setBin15(String bin15) {
        this.bin15 = bin15;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin16() {
        return bin16;
    }

    public void setBin16(String bin16) {
        this.bin16 = bin16;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin17() {
        return bin17;
    }

    public void setBin17(String bin17) {
        this.bin17 = bin17;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin18() {
        return bin18;
    }

    public void setBin18(String bin18) {
        this.bin18 = bin18;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin19() {
        return bin19;
    }

    public void setBin19(String bin19) {
        this.bin19 = bin19;
    }

    // @Column(columnDefinition = "varchar(36) default '-1' ")
    public String getBin20() {
        return bin20;
    }

    public void setBin20(String bin20) {
        this.bin20 = bin20;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
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

        FTResult ftResult = (FTResult) o;

        if (yield != null ? !yield.equals(ftResult.yield) : ftResult.yield != null) return false;
        if (loss != null ? !loss.equals(ftResult.loss) : ftResult.loss != null) return false;
        if (backUp != null ? !backUp.equals(ftResult.backUp) : ftResult.backUp != null) return false;
        if (other != null ? !other.equals(ftResult.other) : ftResult.other != null) return false;
        if (resultSum != null ? !resultSum.equals(ftResult.resultSum) : ftResult.resultSum != null) return false;
        if (markF != null ? !markF.equals(ftResult.markF) : ftResult.markF != null) return false;
        if (fail != null ? !fail.equals(ftResult.fail) : ftResult.fail != null) return false;
        if (lat != null ? !lat.equals(ftResult.lat) : ftResult.lat != null) return false;
        if (bin1 != null ? !bin1.equals(ftResult.bin1) : ftResult.bin1 != null) return false;
        if (bin2 != null ? !bin2.equals(ftResult.bin2) : ftResult.bin2 != null) return false;
        if (bin3 != null ? !bin3.equals(ftResult.bin3) : ftResult.bin3 != null) return false;
        if (bin4 != null ? !bin4.equals(ftResult.bin4) : ftResult.bin4 != null) return false;
        if (bin5 != null ? !bin5.equals(ftResult.bin5) : ftResult.bin5 != null) return false;
        if (bin6 != null ? !bin6.equals(ftResult.bin6) : ftResult.bin6 != null) return false;
        if (bin7 != null ? !bin7.equals(ftResult.bin7) : ftResult.bin7 != null) return false;
        if (bin8 != null ? !bin8.equals(ftResult.bin8) : ftResult.bin8 != null) return false;
        if (bin9 != null ? !bin9.equals(ftResult.bin9) : ftResult.bin9 != null) return false;
        if (bin10 != null ? !bin10.equals(ftResult.bin10) : ftResult.bin10 != null) return false;
        if (bin11 != null ? !bin11.equals(ftResult.bin11) : ftResult.bin11 != null) return false;
        if (bin12 != null ? !bin12.equals(ftResult.bin12) : ftResult.bin12 != null) return false;
        if (bin13 != null ? !bin13.equals(ftResult.bin13) : ftResult.bin13 != null) return false;
        if (bin14 != null ? !bin14.equals(ftResult.bin14) : ftResult.bin14 != null) return false;
        if (bin15 != null ? !bin15.equals(ftResult.bin15) : ftResult.bin15 != null) return false;
        if (bin16 != null ? !bin16.equals(ftResult.bin16) : ftResult.bin16 != null) return false;
        if (bin17 != null ? !bin17.equals(ftResult.bin17) : ftResult.bin17 != null) return false;
        if (bin18 != null ? !bin18.equals(ftResult.bin18) : ftResult.bin18 != null) return false;
        if (bin19 != null ? !bin19.equals(ftResult.bin19) : ftResult.bin19 != null) return false;
        return bin20 != null ? bin20.equals(ftResult.bin20) : ftResult.bin20 == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (yield != null ? yield.hashCode() : 0);
        result = 31 * result + (loss != null ? loss.hashCode() : 0);
        result = 31 * result + (backUp != null ? backUp.hashCode() : 0);
        result = 31 * result + (other != null ? other.hashCode() : 0);
        result = 31 * result + (resultSum != null ? resultSum.hashCode() : 0);
        result = 31 * result + (markF != null ? markF.hashCode() : 0);
        result = 31 * result + (fail != null ? fail.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (bin1 != null ? bin1.hashCode() : 0);
        result = 31 * result + (bin2 != null ? bin2.hashCode() : 0);
        result = 31 * result + (bin3 != null ? bin3.hashCode() : 0);
        result = 31 * result + (bin4 != null ? bin4.hashCode() : 0);
        result = 31 * result + (bin5 != null ? bin5.hashCode() : 0);
        result = 31 * result + (bin6 != null ? bin6.hashCode() : 0);
        result = 31 * result + (bin7 != null ? bin7.hashCode() : 0);
        result = 31 * result + (bin8 != null ? bin8.hashCode() : 0);
        result = 31 * result + (bin9 != null ? bin9.hashCode() : 0);
        result = 31 * result + (bin10 != null ? bin10.hashCode() : 0);
        result = 31 * result + (bin11 != null ? bin11.hashCode() : 0);
        result = 31 * result + (bin12 != null ? bin12.hashCode() : 0);
        result = 31 * result + (bin13 != null ? bin13.hashCode() : 0);
        result = 31 * result + (bin14 != null ? bin14.hashCode() : 0);
        result = 31 * result + (bin15 != null ? bin15.hashCode() : 0);
        result = 31 * result + (bin16 != null ? bin16.hashCode() : 0);
        result = 31 * result + (bin17 != null ? bin17.hashCode() : 0);
        result = 31 * result + (bin18 != null ? bin18.hashCode() : 0);
        result = 31 * result + (bin19 != null ? bin19.hashCode() : 0);
        result = 31 * result + (bin20 != null ? bin20.hashCode() : 0);
        return result;
    }
}
