package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "E_FT_COMPOSED_TEST_NODE")
@Access(AccessType.PROPERTY)
@PrimaryKeyJoinColumn(name = "FT_NODE_ID")
public class FTComposedTestNode extends FTNode {

    private static final long serialVersionUID = -1158817100272349569L;

    private List<FTTest> tests;

    private TestProgram testProgram;

    private List<FTProductionSchedule> ftProductionSchedules = new ArrayList<>();

	@OneToMany(mappedBy = "ftComposedTestNode", fetch = FetchType.LAZY)
	public List<FTProductionSchedule> getFtProductionSchedules() {
		return ftProductionSchedules;
	}

	public void setFtProductionSchedules(List<FTProductionSchedule> ftProductionSchedules) {
		this.ftProductionSchedules = ftProductionSchedules;
	}

    @OneToOne(mappedBy = "ftComposedTestNode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public TestProgram getTestProgram() {
        return testProgram;
    }

    public void setTestProgram(TestProgram testProgram) {
        this.testProgram = testProgram;
    }

    @OneToMany(mappedBy = "ftComposedTestNode", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<FTTest> getTests() {
        return tests;
    }

    public void setTests(List<FTTest> tests) {
        this.tests = tests;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FTComposedTestNode other = (FTComposedTestNode) obj;
		if (ftProductionSchedules == null) {
			if (other.ftProductionSchedules != null)
				return false;
		} else if (!ftProductionSchedules.equals(other.ftProductionSchedules))
			return false;
		if (testProgram == null) {
			if (other.testProgram != null)
				return false;
		} else if (!testProgram.equals(other.testProgram))
			return false;
		if (tests == null) {
			if (other.tests != null)
				return false;
		} else if (!tests.equals(other.tests))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ftProductionSchedules == null) ? 0 : ftProductionSchedules.hashCode());
		result = prime * result + ((testProgram == null) ? 0 : testProgram.hashCode());
		result = prime * result + ((tests == null) ? 0 : tests.hashCode());
		return result;
	}
}
