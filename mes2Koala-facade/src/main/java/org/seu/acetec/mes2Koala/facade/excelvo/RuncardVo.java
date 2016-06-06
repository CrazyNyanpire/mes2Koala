package org.seu.acetec.mes2Koala.facade.excelvo;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015/12/27.
 */
public class RuncardVo {
    private boolean hasIQC;
    private boolean hasBaking;
    private boolean hasPeelForceTest;
    private boolean hasLAT;
    private boolean hasFVI;
    private boolean hasPacking;
    private boolean hasFQC;
    private boolean hasOQC;
    private List<String> noteOfIQC;
    private List<String> noteOfBaking;
    private List<String> noteOfPeelForceTest;
    private List<String> noteOfTesting;
    private List<String> noteOfLAT;
    private List<String> noteOfFVI;
    private List<String> noteOfPacking;
    private List<String> noteOfFQC;
    private List<String> noteOfOQC;
    //参数检查相关
    private String paramCheckTitle;
    private List<FTParamCheckCols> ftParamCheckCols;
    //BGA相关
    private boolean hasEQC;
    private boolean hasOQC1;
    private boolean hasOQC2;
    private List<String> noteOfEQC;
    private List<String> noteOfOQC1;
    private List<String> noteOfOQC2; 
    
    private List<String> noteOfFT; 
    private List<String> holdRules;
    //BGA FT相关
    private List<String> testerSetting;
    private List<String> handlerSetting;
    private List<BGABinAssignCols> bgaBinAssignCols;

    private Set<String> specialForms;

/*    public RuncardVo(boolean hasSummaryForm, boolean hasSizeForm, boolean hasReelcodeForm, boolean hasLossForm, boolean hasCheckSelfForm, boolean hasCheckBoxForm) {
        this.specialForms.add("首页");
        this.specialForms.add("Flow");
        if ( hasSummaryForm == true )
            this.specialForms.add("Summary");
        if ( hasSizeForm == true )
            this.specialForms.add("记录表");
        if ( hasReelcodeForm == true )
            this.specialForms.add("ReelCode");
        if ( hasLossForm == true )
            this.specialForms.add("机台落料记录表");
        if ( hasCheckSelfForm == true )
            this.specialForms.add("自主检查表");
        if ( hasCheckBoxForm == true )
            this.specialForms.add("CheckBox");

        this.hasIQC = true;
        this.hasBaking = true;
        this.hasPeelForceTest = true;
        this.hasLAT = true;
        this.hasFVI = true;
        this.hasPacking = true;
        this.hasFQC = true;
        this.hasOQC = true;

        this.noteOfBaking.add("注意事項(Notes):\n");
        this.noteOfBaking.add("1.产品烘烤后48H内进入铝箔袋真空包装\n");
//        this.noteOfBaking.add("2.产品烘烤时，每个LOT 相对应的烤盘不能混淆\n");
        this.noteOfBaking.add("3.烘烤完成之后，核对铝箔袋标签、实物与流程单信息，保持三者一致。                                   出烤箱人员(Operator):\n");
    }*/

/*    public RuncardVo(boolean hasIQC, boolean hasBaking, boolean hasPeelForceTest, boolean hasLAT, boolean hasFVI, boolean hasPacking, boolean hasFQC, boolean hasOQC) {
        this.hasIQC = hasIQC;
        this.hasBaking = hasBaking;
        this.hasPeelForceTest = hasPeelForceTest;
        this.hasLAT = hasLAT;
        this.hasFVI = hasFVI;
        this.hasPacking = hasPacking;
        this.hasFQC = hasFQC;
        this.hasOQC = hasOQC;
    }*/

	public List<String> getNoteOfFT() {
		return noteOfFT;
	}

	public void setNoteOfFT(List<String> noteOfFT) {
		this.noteOfFT = noteOfFT;
	}

	public List<String> getHoldRules() {
		return holdRules;
	}

	public void setHoldRules(List<String> holdRules) {
		this.holdRules = holdRules;
	}

	public boolean isHasEQC() {
		return hasEQC;
	}

	public void setHasEQC(boolean hasEQC) {
		this.hasEQC = hasEQC;
	}

	public boolean isHasOQC1() {
		return hasOQC1;
	}

	public void setHasOQC1(boolean hasOQC1) {
		this.hasOQC1 = hasOQC1;
	}

	public boolean isHasOQC2() {
		return hasOQC2;
	}

	public void setHasOQC2(boolean hasOQC2) {
		this.hasOQC2 = hasOQC2;
	}

	public List<String> getNoteOfEQC() {
		return noteOfEQC;
	}

	public void setNoteOfEQC(List<String> noteOfEQC) {
		this.noteOfEQC = noteOfEQC;
	}

	public List<String> getNoteOfOQC1() {
		return noteOfOQC1;
	}

	public void setNoteOfOQC1(List<String> noteOfOQC1) {
		this.noteOfOQC1 = noteOfOQC1;
	}

	public List<String> getNoteOfOQC2() {
		return noteOfOQC2;
	}

	public void setNoteOfOQC2(List<String> noteOfOQC2) {
		this.noteOfOQC2 = noteOfOQC2;
	}

	public List<String> getTesterSetting() {
		return testerSetting;
	}

	public void setTesterSetting(List<String> testerSetting) {
		this.testerSetting = testerSetting;
	}

	public List<String> getHandlerSetting() {
		return handlerSetting;
	}

	public void setHandlerSetting(List<String> handlerSetting) {
		this.handlerSetting = handlerSetting;
	}

	public List<BGABinAssignCols> getBgaBinAssignCols() {
		return bgaBinAssignCols;
	}

	public void setBgaBinAssignCols(List<BGABinAssignCols> bgaBinAssignCols) {
		this.bgaBinAssignCols = bgaBinAssignCols;
	}

	public List<FTParamCheckCols> getFtParamCheckCols() {
		return ftParamCheckCols;
	}

	public void setFtParamCheckCols(List<FTParamCheckCols> ftParamCheckCols) {
		this.ftParamCheckCols = ftParamCheckCols;
	}

	public String getParamCheckTitle() {
		return paramCheckTitle;
	}

	public void setParamCheckTitle(String paramCheckTitle) {
		this.paramCheckTitle = paramCheckTitle;
	}

	public List<String> getNoteOfTesting() {
		return noteOfTesting;
	}

	public void setNoteOfTesting(List<String> noteOfTesting) {
		this.noteOfTesting = noteOfTesting;
	}

	public List<String> getNoteOfIQC() {
		return noteOfIQC;
	}

	public void setNoteOfIQC(List<String> noteOfIQC) {
		this.noteOfIQC = noteOfIQC;
	}

	public List<String> getNoteOfPeelForceTest() {
		return noteOfPeelForceTest;
	}

	public void setNoteOfPeelForceTest(List<String> noteOfPeelForceTest) {
		this.noteOfPeelForceTest = noteOfPeelForceTest;
	}

	public List<String> getNoteOfLAT() {
		return noteOfLAT;
	}

	public void setNoteOfLAT(List<String> noteOfLAT) {
		this.noteOfLAT = noteOfLAT;
	}

	public List<String> getNoteOfFVI() {
		return noteOfFVI;
	}

	public void setNoteOfFVI(List<String> noteOfFVI) {
		this.noteOfFVI = noteOfFVI;
	}

	public List<String> getNoteOfPacking() {
		return noteOfPacking;
	}

	public void setNoteOfPacking(List<String> noteOfPacking) {
		this.noteOfPacking = noteOfPacking;
	}

	public List<String> getNoteOfFQC() {
		return noteOfFQC;
	}

	public void setNoteOfFQC(List<String> noteOfFQC) {
		this.noteOfFQC = noteOfFQC;
	}

	public List<String> getNoteOfOQC() {
		return noteOfOQC;
	}

	public void setNoteOfOQC(List<String> noteOfOQC) {
		this.noteOfOQC = noteOfOQC;
	}

	public List<String> getNoteOfBaking() {
        return noteOfBaking;
    }

    public void setNoteOfBaking(List<String> noteOfBaking) {
        this.noteOfBaking = noteOfBaking;
    }

    public Set<String> getSpecialForms() {
        return specialForms;
    }

    public void setSpecialForms(Set<String> specialForms) {
        this.specialForms = specialForms;
    }

    public boolean isHasIQC() {
        return hasIQC;
    }

    public void setHasIQC(boolean hasIQC) {
        this.hasIQC = hasIQC;
    }

    public boolean isHasBaking() {
        return hasBaking;
    }

    public void setHasBaking(boolean hasBaking) {
        this.hasBaking = hasBaking;
    }

    public boolean isHasPeelForceTest() {
        return hasPeelForceTest;
    }

    public void setHasPeelForceTest(boolean hasPeelForceTest) {
        this.hasPeelForceTest = hasPeelForceTest;
    }

    public boolean isHasLAT() {
        return hasLAT;
    }

    public void setHasLAT(boolean hasLAT) {
        this.hasLAT = hasLAT;
    }

    public boolean isHasFVI() {
        return hasFVI;
    }

    public void setHasFVI(boolean hasFVI) {
        this.hasFVI = hasFVI;
    }

    public boolean isHasPacking() {
        return hasPacking;
    }

    public void setHasPacking(boolean hasPacking) {
        this.hasPacking = hasPacking;
    }

    public boolean isHasFQC() {
        return hasFQC;
    }

    public void setHasFQC(boolean hasFQC) {
        this.hasFQC = hasFQC;
    }

    public boolean isHasOQC() {
        return hasOQC;
    }

    public void setHasOQC(boolean hasOQC) {
        this.hasOQC = hasOQC;
    }
}
