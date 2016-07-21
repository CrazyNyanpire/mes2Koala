package org.seu.acetec.mes2Koala.core.domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.seu.acetec.mes2Koala.infra.MyDateUtils;

@Entity
@Table(name = "e_cp_production_schedule")
@Access(AccessType.PROPERTY)
public class CPProductionSchedule extends ProductionSchedule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5107084507696888793L;
	private CPTestingNode cpTestingNode; // 与测试站点建立联系
	private CPLot cpLot;//与批次建立联系

	@Override
	@Transient
	public String getNodeName(){
		return cpTestingNode.getName();
	}

	@ManyToOne
	@JoinColumn(name = "CP_LOT_ID", referencedColumnName = "ID")
	public CPLot getCpLot() {
		return cpLot;
	}
	
	public void setCpLot(CPLot cpLot) {
		this.cpLot = cpLot;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "CP_TESTING_NODE_ID", referencedColumnName = "CP_NODE_ID")
	public CPTestingNode getCpTestingNode() {
		return cpTestingNode;
	}

	public void setCpTestingNode(CPTestingNode cpTestingNode) {
		this.cpTestingNode = cpTestingNode;
	}

	public Double getPlannedTimeTakes() {
		return plannedTimeTakes;
	}

	public void setPlannedTimeTakes(Double plannedTimeTakes) {
		Double hours = -1.0;
		try {
			Float uphReality = getCpTestingNode().getTestProgram().getUphReality();
			if (null != uphReality && 0 != uphReality)
				hours = getAmount() * 1.0 / uphReality;
		} catch (NullPointerException e) {
		}
		this.plannedTimeTakes = hours;
		setPlanedEndTimestamp(null);// 自有逻辑
	}

	public Double getActualTimeTakes() {
		return actualTimeTakes;
	}

	public void setActualTimeTakes(Double actualTimeTakes) {
		Double hours = -1.0;
		try {
			long duration = getActualEndTimestamp().getTime() - getActualStartTimestamp().getTime();
			hours = 1.0 * duration / 1000 / 3000;
		} catch (NullPointerException e) {
		}
		this.actualTimeTakes = hours;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPlanedStartTimestamp() {
		return planedStartTimestamp;
	}

	public void setPlanedStartTimestamp(Date planedStartTimestamp) {
		this.planedStartTimestamp = planedStartTimestamp;
		setPlanedEndTimestamp(null); // 自有逻辑
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPlanedEndTimestamp() {
		return planedEndTimestamp;
	}

	public void setPlanedEndTimestamp(Date planedEndTimestamp) {
		if ( planedEndTimestamp != null ) {
			this.planedEndTimestamp = planedEndTimestamp;
		} else {
			if (getPlanedStartTimestamp() != null && getPlannedTimeTakes() != null)
				this.planedEndTimestamp = MyDateUtils.addMinutes(getPlanedStartTimestamp(),
						(int) Math.ceil(getPlannedTimeTakes()) * 60);
		}
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getActualStartTimestamp() {
		return actualStartTimestamp;
	}

	public void setActualStartTimestamp(Date actualStartTimestamp) {
		if ( actualStartTimestamp != null ){
			this.actualStartTimestamp = actualStartTimestamp;
			setPlanedStartTimestamp(actualStartTimestamp);
			setActualTimeTakes(null);//私有逻辑
		}
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getActualEndTimestamp() {
		return actualEndTimestamp;
	}

	public void setActualEndTimestamp(Date actualEndTimestamp) {
		this.actualEndTimestamp = actualEndTimestamp;
		setPlanedEndTimestamp(actualEndTimestamp);
		setActualTimeTakes(null);//私有逻辑
	}

}
