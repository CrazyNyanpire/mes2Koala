package org.seu.acetec.mes2Koala.core.domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
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
@Table(name = "e_ft_production_schedule")
@Access(AccessType.PROPERTY)
public class FTProductionSchedule extends ProductionSchedule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 942015305331170778L;

	private FTComposedTestNode ftComposedTestNode; // 与测试站点建立联系
	private FTLot ftLot;//与批次建立联系
	
	@Override
	@Transient
	public String getNodeName() {
		String result = null;
		try{
			result = ftComposedTestNode.getName();
		} catch ( NullPointerException exception ) {
			exception.printStackTrace();
			result = "无法获取 ";
		}
		return result;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FT_LOT_ID", referencedColumnName = "ID")
	public FTLot getFtLot() {
		return ftLot;
	}
	
	public void setFtLot(FTLot ftLot) {
		this.ftLot = ftLot;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "FT_COMPOSED_TEST_NODE_ID", referencedColumnName = "FT_NODE_ID")
	public FTComposedTestNode getFtComposedTestNode() {
		return ftComposedTestNode;
	}

	public void setFtComposedTestNode(FTComposedTestNode ftComposedTestNode) {
		this.ftComposedTestNode = ftComposedTestNode;
	}

	public Double getPlannedTimeTakes() {
		return plannedTimeTakes;
	}

	public void setPlannedTimeTakes(Double plannedTimeTakes) {
		Double hours = -1.0;
		try {
			Float uphReality = getFtComposedTestNode().getTestProgram().getUphReality();
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
						(int) Math.ceil(getPlannedTimeTakes() * 60));
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
