package org.seu.acetec.mes2Koala.facade.dto;

/**
 * Created by LCN on 2016/5/11.
 */
public class ProcessTemplateSignDTO {
    //雇员名字
    private String employeeName;
    //部门
//    private String DepartmentName;
    //签核意见
    private String opinion;
    //签核备注
    private String note;

    //验证id是否合法
    private Boolean validate;


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }
}
