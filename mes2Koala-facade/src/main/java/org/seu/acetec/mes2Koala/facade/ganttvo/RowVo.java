package org.seu.acetec.mes2Koala.facade.ganttvo;

import java.io.Serializable;
import java.util.List;

public class RowVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2761400847166267854L;
	private String name;
	private String id;
	private Integer version;
	private Integer state;
	private String mateEquipment;
	private List<TaskVo> tasks;



	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMateEquipment() {
		return mateEquipment;
	}
	public void setMateEquipment(String mateEquipment) {
		this.mateEquipment = mateEquipment;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TaskVo> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskVo> tasks) {
		this.tasks = tasks;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mateEquipment == null) ? 0 : mateEquipment.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((tasks == null) ? 0 : tasks.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RowVo other = (RowVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mateEquipment == null) {
			if (other.mateEquipment != null)
				return false;
		} else if (!mateEquipment.equals(other.mateEquipment))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (tasks == null) {
			if (other.tasks != null)
				return false;
		} else if (!tasks.equals(other.tasks))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
