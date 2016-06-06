package org.seu.acetec.mes2Koala.core.domain;

import org.openkoala.organisation.core.domain.Employee;

import javax.persistence.*;

@Entity
@Table(name = "E_AcetecAuthorization")
@Access(AccessType.PROPERTY)
public class AcetecAuthorization extends MES2AbstractEntity {

    private static final long serialVersionUID = -8515401100869042961L;

    private Employee employee;
    private String opinion;
    private String note;

    public static boolean acceptedOption(AcetecAuthorization acetecAuthorization) {
        if (acetecAuthorization == null)
            return false;
        if (acetecAuthorization.getOpinion() == null)
            return false;
        return acetecAuthorization.getOpinion().equals("同意");
    }

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    @Override
    public String[] businessKeys() {
        // TODO Auto-generated method stub
        return null;
    }

}
