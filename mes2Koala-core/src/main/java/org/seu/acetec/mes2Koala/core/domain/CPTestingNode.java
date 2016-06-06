package org.seu.acetec.mes2Koala.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 测试站点
 *
 * @author yuxiangque
 * @version 2016/3/28
 */
@Entity
@Table(name = "E_CP_Testing_NODE")
@Access(AccessType.PROPERTY)
@PrimaryKeyJoinColumn(name = "CP_NODE_ID")
public class CPTestingNode extends CPNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1896642994105015015L;

	private TestProgram testProgram;
	
	private List<CPProductionSchedule> cpProductionSchedules = new ArrayList<>();

	@OneToOne(mappedBy = "cpTestingNode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public TestProgram getTestProgram() {
		return testProgram;
	}

	public void setTestProgram(TestProgram testProgram) {
		this.testProgram = testProgram;
	}
	
	@OneToMany(mappedBy = "cpTestingNode", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	public List<CPProductionSchedule> getCpProductionSchedules() {
		return cpProductionSchedules;
	}

	public void setCpProductionSchedules(List<CPProductionSchedule> cpProductionSchedules) {
		this.cpProductionSchedules = cpProductionSchedules;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cpProductionSchedules == null) ? 0 : cpProductionSchedules.hashCode());
		result = prime * result + ((testProgram == null) ? 0 : testProgram.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CPTestingNode other = (CPTestingNode) obj;
		if (cpProductionSchedules == null) {
			if (other.cpProductionSchedules != null)
				return false;
		} else if (!cpProductionSchedules.equals(other.cpProductionSchedules))
			return false;
		if (testProgram == null) {
			if (other.testProgram != null)
				return false;
		} else if (!testProgram.equals(other.testProgram))
			return false;
		return true;
	}
	
	
	
}
